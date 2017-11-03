package com.luxsoft.impapx.cxc


import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.exception.ExceptionUtils

import org.codehaus.groovy.grails.web.json.JSONArray

import org.springframework.dao.DataIntegrityViolationException
import com.luxsoft.impapx.Venta
import com.luxsoft.cfdi.Cfdi
import grails.converters.JSON
import util.MonedaUtils

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMPRAS','VENTAS')"])
class CXCNotaController {

    //static allowedMethods = [create:'GET',save:'POST',edit:'GET',update:'PUT',delete: 'DELETE',eliminarAplicaciones:'POST']

	def cobranzaService

	def comprobanteFiscalNotaService

	def printService

	def cfdiService

    def index() {
    	def tipo=params.tipo?:'TODAS'
    	def periodo=session.periodo
        def list=CXCNota.findAll("from CXCNota c where date(c.fecha) between ? and ? order by c.id desc",
        	[periodo.fechaInicial,periodo.fechaFinal])
        [CXCNotaInstanceList:list,tipo:'TODAS']
    }

    def create(){
    	[CXCNotaInstance: new CXCNota(fecha:new Date(),moneda:MonedaUtils.PESOS,tc:1,tipo:'BONIFICACION')]
    }

    def save(CXCNota CXCNotaInstance){
    	if(CXCNotaInstance.hasErrors()){
    		render view:'create',model:[CXCNotaInstance:CXCNotaInstance]
    		return
    	}
    	def res=cobranzaService.registrarNota(CXCNotaInstance)
		flash.message = "Nota de crédito generada: ${res.id}".encodeAsHTML()
		redirect action: 'edit', id: res.id
    }
    

    def show(CXCNota CXCNotaInstance) {
        [CXCNotaInstance: CXCNotaInstance]
    }
    def edit(CXCNota CXCNotaInstance){
    	[CXCNotaInstance:CXCNotaInstance]
    }

    def update(CXCNota CXCNotaInstance){
    	if(CXCNotaInstance.getCfdi()){
    		flash.message="Nota ya timbrada no se puede modificar"
    		respond CXCNotaInstance
    		return;
    	}
    	if(CXCNotaInstance.hasErrors()){
    		render view:'edit',model:[CXCNotaInstance:CXCNotaInstance]
    		return
    	}
    	CXCNotaInstance.save flush:true
    	flash.message="Nota ${CXCNotaInstance.id} actualizada"
    	redirect action:'edit',id:CXCNotaInstance.id
    }

    def delete(CXCNota CXCNotaInstance) {
        if(CXCNotaInstance.getCfdi()){
        	flash.message="Nota ${CXCNotaInstance.id} ya timbrada no se puede eliminar"
        	respond CXCNotaInstance
        	return
        }
        CXCNotaInstance.delete(flush: true)
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'CXCNota.label', default: 'CXCNota'), params.id])
        redirect action: 'index'
        
    }

    /**
     * Endpoint para seleccionar la factura con la que se relaciona la nota de credito
     * (Requerido para generar CFDI 3.3)
     * @param  abono [description]
     * @return       [description]
     */
    def selectorDeVenta(CXCNota abono){
		def hql="from Venta p where p.cliente.id=?  and year(p.fecha)>=2017 and p.id not in (select x.id from CXCNota x where x.ventaRelacionada !=null) order by p.id desc"
		def res=Venta.findAll(hql,[abono.cliente.id])
		[pago:abono,facturas:res,facturasTotal:res.size()]
	}

	/**
	 * Endpoint para asignar la factura a la nota de credito (Requerido para CFDI 3.3)
	 * @param  id [description]
	 * @return    [description]
	 */
	def asignarFactura(CXCNota nota){
		
		JSONArray jsonArray=JSON.parse(params.facturas);
		def ventaId = jsonArray[0]
		def venta = Venta.get(ventaId)
		nota.ventaRelacionada = venta
		nota.save flush:true, failOnError:true

		def dataToRender=[:]
		log.info('Asignando factura ' + venta.id + ' a la nota: ' + nota.id)
		dataToRender.pagoId = nota.id
		render dataToRender as JSON
	}

	def quitarAsignacionDeFactura(CXCNota nota){
		nota.ventaRelacionada = null
		nota.save flush:true, failOnError:true
		redirect action:'edit', id:nota.id

	}
	
	
	def selectorDeFacturas(CXCNota abono){
		if(abono.disponible<=0.0){
			flash.message="Cobro sin disponible para aplicar"
			redirect action:'edit', id:abono.id
			return
		}
		def hql="from Venta p where p.cliente.id=?  and p.total-p.pagosAplicados>0 order by p.fecha desc"
		def res=Venta.findAll(hql,[abono.cliente.id])
		[pago:abono,facturas:res,facturasTotal:res.size()]
	}
	
	def generarPartidas(CXCNota nota){
		def dataToRender=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		cobranzaService.asignarPartidasParaNota(nota, jsonArray)
		dataToRender.abonoId=nota.id
		render dataToRender as JSON
	}
	
	def generarAplicaciones(Long id){
		def dataToRender=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		cobranzaService.generarAplicacionesDeNota(id, jsonArray)
		dataToRender.pagoId=id
		render dataToRender as JSON
	}
	
	
	
	def generarCFDI(long id){
		println 'Generando CFD para nota: '+params
		def nota=CXCNota.get(id)
		if(nota==null){
			flash.message='No existe la nota: '+id
			redirect action:'list'
		}
		def cfdi=cfdiService.generarCfdi(nota)
		redirect controller:"cfdi", action:"show",params:[id:cfdi.id]
	}
	
	def imprimirCfd(){
		def nota=CXCNota.get(params.ID)
		def cfd=nota.getCfd().comprobante
		def conceptos=nota.getCfd().getComprobante().getConceptos().getConceptoArray()
		
		def modelData=conceptos.collect { cc ->
			//Concepto cc=(Concepto)c
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
			return res
		}
		def repParams=printService.resolverParametros(nota.getCfd().getComprobante())
		params<<repParams
		params.FECHA=cfd.fecha.getTime().format("yyyy-MM-dd'T'HH:mm:ss")
		chain(controller:'jasper',action:'index',model:[data:modelData],params:params)
		
	}



	def eliminarAplicaciones(Long id){
		
		log.info 'Params: '+params
		def data=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		try {
			cobranzaService.eliminarAplicacionDeNota(id,jsonArray)
			data.res='APLICACIONES_ELIMINADAS'
		}
		catch (RuntimeException e) {
			e.printStackTrace()
			data.res="ERROR"
			data.error=ExceptionUtils.getRootCauseMessage(e)
		}
		
		render data as JSON
	}

	def ventasAsJSONList(){
		
		//def ventas = Venta.findAll("from Venta r  where  str(r.id) like ? order by r.id desc",['%'+params.term+'%'],[max:20])
		def ventas = Venta.executeQuery("select r from Venta r, Cfdi c  where r.id = c.origen  and c.folio like ? order by c.id desc",['%'+params.term+'%'],[max:20])
		def ventasList = ventas.collect { req ->
			def desc="Cfdi: ${req.getFacturaFolio()} Id: ${req.id} ${req.cliente.nombre} (${req.fecha.text()}) ${req.total} ${req.moneda}"
			[id:req.id,
			label:desc,
			value:desc,
            formaDePago:req.formaDePago.toString()
			]
		}
		render ventasList as JSON
		
	}

	def cfdisAsJSONList(){
		
		def cfdis = Cfdi.findAll("from Cfdi r  where  str(r.folio) like ? order by r.id desc",['%'+params.term+'%'],[max:20])
		def list = cfdis.collect { req ->
			def desc="Folio: ${req.folio} Serie: ${req.serie} ${req.receptor} (${req.fecha.text()}) ${req.total}"
			[id:req.id,
			label:desc,
			value:desc
			]
		}
		render list as JSON
		
	}
}
