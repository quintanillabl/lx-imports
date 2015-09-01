package com.luxsoft.cfdi



import static org.springframework.http.HttpStatus.*
import groovy.sql.Sql
import javax.sound.midi.SysexMessage
import com.luxsoft.impapx.Venta
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante

import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.lx.bi.ReportCommand

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
	
	def mostrarXml(Cfdi cfdi){
		// if(cfdi==null){
		// 	flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
  //           redirect action: "index", method: "GET"
		// }
		// render view:'cfdiXml',model:[cfdi:cfdi,xml:cfdi.getComprobanteDocument().xmlText()]
		render(text: cfdi.comprobanteDocument.xmlText(), contentType: "text/xml", encoding: "UTF-8")
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
		render view:'/cfdi/show',model:[cfdiInstance:cfdi]
	}
	
	def timbrar(){
		def cfdi=Cfdi.findById(params.id)
		if(cfdi==null){
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
			redirect action: "index", method: "GET"
		}
		println "Timbrando en ${cfdiTimbrador.timbradoDePrueba? 'En modo de prueba':'En modo real'} el cfdi: $cfdi "
		
		cfdi=cfdiTimbrador.timbrar(cfdi,"PAP830101CR3", "yqjvqfofb")
		render view:'/cfdi/show',model:[cfdiInstance:cfdi]
	}

	def print(Cfdi cfdi){
		Comprobante cfd=cfdi.getComprobante()
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
	
	def imprimirCfdi(){
		
		def cfdi=Cfdi.findById(params.id)
		if(cfdi==null){
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiInstance.label', default: 'Cfdi'), params.id])
            redirect action: "show", params:[id:id]
		}
		Comprobante cfd=cfdi.getComprobante()
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
		println 'Imprimiendo CFDI: '+params
		chain(controller:'jasper',action:'index',model:[data:modelData],params:params)
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
	
	def cancelar(long id){
		Cfdi cfdi=Cfdi.findById(id)
		if(cfdi==null){
			notFound()
			return
		}
		println 'Cancelando cfdi: '+id
		
		cfdi=cfdiService.cancelar(cfdi)
		redirect action:'show',params:[id:id]
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
