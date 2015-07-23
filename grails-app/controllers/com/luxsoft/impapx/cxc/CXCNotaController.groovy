package com.luxsoft.impapx.cxc


import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException
import com.luxsoft.impapx.Venta
import grails.converters.JSON
import util.MonedaUtils

class CXCNotaController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

	def cobranzaService
	def comprobanteFiscalNotaService
	def printService
	def cfdiService
    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 100, 500)
        [CXCNotaInstanceList: CXCNota.list(params), CXCNotaInstanceTotal: CXCNota.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[CXCNotaInstance: new CXCNota(fecha:new Date(),moneda:MonedaUtils.PESOS,tc:1,tipo:'DESCUENTO')]
			break
		case 'POST':
		//println 'Posting: '+params
	        def CXCNotaInstance = new CXCNota(params)
			if(StringUtils.isBlank( CXCNotaInstance.tipo)){
				flash.message="Requiere definir el tipo de nota"
				render view: 'create', model: [CXCNotaInstance: CXCNotaInstance]
				return
			}
			def res=cobranzaService.registrarNota(CXCNotaInstance)
			if (res.hasErrors() ) {
				render view: 'create', model: [CXCNotaInstance: res]
	            return
			}
			flash.message = "Nota de crédito generada: ${res.id}".encodeAsHTML()
			redirect action: 'edit', id: res.id
			
			/*
			try {
				
			} catch (CobranzaEnPagoException e) {
				render view: 'create', model: [CXCPagoInstance: e.pago]
				return
			}*/
		}
    }

    def show() {
        def CXCNotaInstance = CXCNota.get(params.id)
        if (!CXCNotaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'CXCNota.label', default: 'CXCNota'), params.id])
            redirect action: 'list'
            return
        }

        [CXCNotaInstance: CXCNotaInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def CXCNotaInstance = CXCNota.findById(params.id,[fetch:[aplicaciones:'select',partidas:'select']])
	        if (!CXCNotaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'CXCNota.label', default: 'CXCNota'), params.id])
	            redirect action: 'list'
	            return
	        }
			
	        [CXCNotaInstance: CXCNotaInstance]
			break
		case 'POST':
			flash.message = "Las porpiedades generales de la nota no son editables"
			redirect action: 'edit', id: CXCNotaInstance.id
		}
    }

    def delete() {
        def CXCNotaInstance = CXCNota.get(params.id)
        if (!CXCNotaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'CXCNota.label', default: 'CXCNota'), params.id])
            redirect action: 'list'
            return
        }

        try {
            CXCNotaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'CXCNota.label', default: 'CXCNota'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'CXCNota.label', default: 'CXCNota'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def selectorDeFacturas(long pagoId){
		def abono=CXCAbono.get(pagoId)
		//def hql="from Venta p where p.cliente.id=?  and p.moneda=? order by id desc"
		def hql="from Venta p where p.cliente.id=?  and p.total-p.pagosAplicados>0 order by id desc"
		//def res=Venta.findAll(hql,[abono.cliente.id,abono.moneda])
		def max=Math.min(params.int('max')?:10, 100)
		def offset=params.int('offset')?:0
		def res = Venta.findAllByClienteAndMoneda(abono.cliente,abono.moneda,[sort:'id',order:'desc',max:max,offset:offset])
		[pago:abono,facturas:res,facturasTotal:Venta.count()]
	}
	
	def generarPartidas(){
		println 'Generando partidas par nota de credito'+params
		def dataToRender=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		def nota= CXCNota.findById(params.abonoId,[fetch:[partidas:'select']])
		cobranzaService.asignarPartidasParaNota(nota, jsonArray)
		println 'Partidas: '+jsonArray
		dataToRender.abonoId=nota.id
		render dataToRender as JSON
	}
	
	def generarAplicaciones(){
		println 'Generando aplicaciones  para  facturas a requisicion'+params
		def dataToRender=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		def pago= CXCNota.findById(params.abonoId,[fetch:[aplicaciones:'select']])
		println 'Nota :'+pago
		cobranzaService.aplicarFacturas(pago, jsonArray)
		
		dataToRender.pagoId=pago.id
		render dataToRender as JSON
	}
	
	/*
	def generarCFD(long id){
		
	}
	*/
	
	def generarCFDI(long id){
		println 'Generando CFD para nota: '+params
		def nota=CXCNota.get(id)
		if(nota==null){
			flash.message='No existe la nota: '+id
			redirect action:'list'
		}
		def cfdi=cfdiService.generarCfdi(nota)
		//render view:'/cfdi/show',model:[cfdiInstance:cfdi]
		//redirect action:'show',params:[id:id]
		redirect controller:"cfdi", action:"show",params:[id:cfdi.id]
	}
	
	def imprimirCfd(){
		//println 'Generando CFD: '+params
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
		//def repParams=CFDIPrintServices.resolverParametros(nota.getCfd().getComprobante())
		def repParams=printService.resolverParametros(nota.getCfd().getComprobante())
		params<<repParams
		params.FECHA=cfd.fecha.getTime().format("yyyy-MM-dd'T'HH:mm:ss")
		chain(controller:'jasper',action:'index',model:[data:modelData],params:params)
		
	}
}
