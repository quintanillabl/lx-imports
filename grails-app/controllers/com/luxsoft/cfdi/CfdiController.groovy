package com.luxsoft.cfdi



import static org.springframework.http.HttpStatus.*
import groovy.sql.Sql
import javax.sound.midi.SysexMessage
import com.luxsoft.impapx.Venta

import java.io.ByteArrayInputStream
import mx.gob.sat.cfd.x3.ComprobanteDocument
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante


import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.lx.bi.ReportCommand

import com.luxsoft.cfdix.CFDIXUtils
import com.luxsoft.cfdix.v33.V33PdfGenerator
import com.luxsoft.cfdix.v32.V32CfdiUtils
import com.luxsoft.cfdix.v33.ReciboDePagoPdfGenerator

import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat

@Secured(["hasRole('VENTAS')"])
class CfdiController {
    
	static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def cfdiTimbrador
	
	def cfdiService

	def reportService

    def index() {
    	def periodo=session.periodo
    	def list=Cfdi.findAll(
    		"from Cfdi c where date(c.fecha) between ? and ? order by c.dateCreated desc",
    		[periodo.fechaInicial,periodo.fechaFinal])
		[cfdiInstanceList:list]
    }

    def show(Cfdi cfdi) {

		if(cfdi==null){
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
            redirect action: "index", method: "GET"
		}
		def uri=request.getHeader('referer')
		[cfdiInstance:cfdi,origen:uri]
    }
	
	// def mostrarXml(Cfdi cfdi){
	// 	render(text: cfdi.comprobanteDocument.xmlText(), contentType: "text/xml", encoding: "UTF-8")
	// }
	def mostrarXml(long id){
		def cfdi=Cfdi.findById(id)
		def res = CFDIXUtils.parse(cfdi.xml)
		render(text: res, contentType: "text/xml", encoding: "UTF-8")
	}
	
	
	def delete() {
		def cfdi=Cfdi.findById(params.id)
		if(cfdi==null){
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
			redirect action: "index", method: "GET"
		}
		cfdi.delete flush:true
		flash.message="CFDI: $params.id eliminado exitosamente"
		redirect action:'index'
	}

	def facturar(Venta venta){
		if(venta==null){
			flash.message="No existe la venta: "+params.id
			redirect controller:'venta',action:'edit',params:params
		}
		def cfdi=cfdiService.generarCfdi(venta)
		//render view:'/cfdi/show',model:[cfdiInstance:cfdi]
		redirect action:'show', id: cfdi.id
	}
	
	def timbrar(Cfdi cfdi){
		if(cfdi==null){
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
			redirect action: "index", method: "GET"
		}
		//log.info "Timbrando en ${cfdiTimbrador.timbradoDePrueba? 'En modo de prueba':'En modo real'} el cfdi: $cfdi "
		
		//cfdi = cfdiTimbrador.timbrar(cfdi,"PAP830101CR3", "yqjvqfofb")
		cfdi = cfdiService.timbrar(cfdi)
		redirect action:'show', controller:'cfdi', id: cfdi.id //model:[cfdiInstance:cfdi]
	}

	def print(Cfdi cfdi){
		
		if( cfdi.versionCfdi == '3.3') {
			generarPdfV33(cfdi)
			return
			//render(file: pdfStream.toByteArray(), contentType: 'application/pdf',fileName:cfdi.xmlName.replace('.xml','.pdf'))	
		}
		else {
			//generarPdfV32(cfdi)
			//imprimirCfdi()
			
			return 
			//render(file: pdfStream.toByteArray(), contentType: 'application/pdf',fileName:cfdi.xmlName.replace('.xml','.pdf'))
		}
		flash.message = "No esta disponible el reporte de CFDI para esta versión" 
		redirect action:'show',params:[id:cfdi.id]
		
	}

	def generarPdfV32(Cfdi cfdi){
		println 'Generando PDF para CFDI 3.3 ' + cfdi
		def cfd= toComprobante(cfdi)
		def conceptos=cfd.getConceptos().getConceptoArray()
		
		def modelData=conceptos.collect { cc ->
			
			def res=[
			'cantidad':cc.getCantidad()
			 ,'NoIdentificacion':cc.getNoIdentificacion()
			 ,'descripcion':cc.getDescripcion()
			 ,'unidad':cc.getUnidad()
			 ,'ValorUnitario':cc.getValorUnitario()
			 ,'Importe':cc.getImporte()
			 ]
			if(cc.informacionAduaneraArray){
				res.PEDIMENTO_FECHA=cc.informacionAduaneraArray[0]?.fecha.getTime()
				res.PEDIMENTO=cc.informacionAduaneraArray[0]?.numero
				res.ADUANA=cc.informacionAduaneraArray[0]?.aduana
			}
			if(cc.cuentaPredial){
				res.CUENTA_PREDIAL=cc.cuentaPredial.numero
			}
			return res
		}
		def repParams=CfdiPrintUtils.resolverParametros(cfdi)
		params<<repParams
		params.FECHA=cfd.fecha.getTime().format("yyyy-MM-dd'T'HH:mm:ss")

		def command=new ReportCommand()
        command.reportName="CFDI"
        command.empresa=session.empresa
        params.EMPRESA=session.empresa.nombre
        params.COMPANY=session.empresa.nombre
        def stream=reportService.build(command,params,modelData)
        def file="${cfdi.serie}-${cfdi.folio}.pdf"
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
		
		//chain(controller:'jasper',action:'index',model:[data:modelData],params:params)
	}

	private generarPdfV33(Cfdi cfdi){

		log.info("Impresion de CFDI: ${cfdi.id}")
		def data 
		def reportName
		if(cfdi.tipo == 'PAGO') {
			data = ReciboDePagoPdfGenerator.getReportData(cfdi)
			log.info("CfdiPrint Data: ${data}")
			reportName = 'ReciboDePagoCFDI33.jrxml'
		} else {
			reportName = 'CFDI33'
			data = V33PdfGenerator.getReportData(cfdi)
		}
		def modelData = data['CONCEPTOS']
		def repParams = data['PARAMETROS']
		params<<repParams
		
		def command=new ReportCommand()
        command.reportName = reportName
        command.empresa=session.empresa

        params.EMPRESA=session.empresa.nombre
        params.COMPANY=session.empresa.nombre
        
        def stream = reportService.build(command,params,modelData)
        def file="${cfdi.serie}-${cfdi.folio}.pdf"
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
	}
	
	def imprimirCfdi(){
		
		def cfdi=Cfdi.findById(params.id)
		if(cfdi==null){
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
            redirect action: "show", params:[id:id]
		}
		//Comprobante cfd=cfdi.getComprobante()
		def cfd= toComprobante(cfdi)
		def conceptos=cfd.getConceptos().getConceptoArray()
		
		def modelData=conceptos.collect { cc ->
			
			def res=[
			'cantidad':cc.getCantidad()
			 ,'NoIdentificacion':cc.getNoIdentificacion()
			 ,'descripcion':cc.getDescripcion()
			 ,'unidad':cc.getUnidad()
			 ,'ValorUnitario':cc.getValorUnitario()
			 ,'Importe':cc.getImporte()
			 ]
			if(cc.informacionAduaneraArray){
				res.PEDIMENTO_FECHA=cc.informacionAduaneraArray[0]?.fecha.getTime()
				res.PEDIMENTO=cc.informacionAduaneraArray[0]?.numero
				res.ADUANA=cc.informacionAduaneraArray[0]?.aduana
			}
			if(cc.cuentaPredial){
				res.CUENTA_PREDIAL=cc.cuentaPredial.numero
			}
			return res
		}
		def repParams=CfdiPrintUtils.resolverParametros(cfdi, cfd)
		params<<repParams
		params.FECHA=cfd.fecha.getTime().format("yyyy-MM-dd'T'HH:mm:ss")
		println 'Imprimiendo CFDI: '+params
		chain(controller:'jasper',action:'index',model:[data:modelData],params:params)
	}

	def toComprobante(Cfdi cfdi ){
		ByteArrayInputStream is=new ByteArrayInputStream(cfdi.getXml())
		return ComprobanteDocument.Factory.parse(is).getComprobante()
	}
	
	def descargarXml(){
		
	
		Cfdi cfdi=Cfdi.findById(params.id)
		if(cfdi==null){
			println "Este cfdi es nulo"
			notFound()
			return
		}
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment; filename=\"$cfdi.xmlName\"")
		response.outputStream << cfdi.getComprobanteDocument().newInputStream()
		
	}
	
	def cancelar(Cfdi cfdi,String comentario){
		if(cfdi==null){
			notFound()
			return
		}
		log.info 'Cancelando cfdi: '+cfdi
		cfdiService.cancelar(cfdi,comentario)
		redirect action:'show',params:[id:cfdi.id]
	}
	
	def dataSource_importacion
	
	def archivoDeCancelacionesPapel(){
		
		Sql sql=new Sql(dataSource_importacion)
		def query="""
			SELECT cc.CARGO_ID,cf.ORIGEN_ID,UUID,cc.COMENTARIO,cf.TIPO,cf.TIPO_CFD
			FROM sx_cxc_cargos_cancelados cc join sx_cfdi cf on(cc.CARGO_ID=cf.ORIGEN_ID)
			where date(cf.CREADO) BETWEEN '2014/01/1' and '2014/01/31' and UUID is not null
			union
			SELECT a.ABONO_ID,c.ORIGEN_ID,UUID,a.COMENTARIO,c.tipo,c.TIPO_CFD
			FROM sx_cxc_abonos a join sx_cfdi c on (a.ABONO_ID=c.ORIGEN_ID)
			where TIPO_ID like 'nota%' and fecha BETWEEN '2014/01/01' and '2014/01/31' and a.total=0
		"""
	}
}
