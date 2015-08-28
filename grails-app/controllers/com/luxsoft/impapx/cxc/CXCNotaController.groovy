package com.luxsoft.impapx.cxc


import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException
import com.luxsoft.impapx.Venta
import grails.converters.JSON
import util.MonedaUtils

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMPRAS','VENTAS')"])
class CXCNotaController {

    static allowedMethods = [create:'GET',save:'POST',edit:'GET',update:'PUT',delete: 'DELETE']

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
    	[CXCNotaInstance: new CXCNota(fecha:new Date(),moneda:MonedaUtils.PESOS,tc:1,tipo:'DESCUENTO')]
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
	
	def selectorDeFacturas(CXCAbono abono){
		def hql="from Venta p where p.cliente.id=?  and p.total-p.pagosAplicados>0 order by id desc"
		def max=Math.min(params.int('max')?:10, 100)
		def offset=params.int('offset')?:0
		def res = Venta.findAllByClienteAndMoneda(abono.cliente,abono.moneda,[sort:'id',order:'desc',max:max,offset:offset])
		[pago:abono,facturas:res,facturasTotal:Venta.count()]
	}
	
	def generarPartidas(){
		def dataToRender=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		def nota= CXCNota.findById(params.abonoId,[fetch:[partidas:'select']])
		cobranzaService.asignarPartidasParaNota(nota, jsonArray)
		dataToRender.abonoId=nota.id
		render dataToRender as JSON
	}
	
	def generarAplicaciones(){
		def dataToRender=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		def pago= CXCNota.findById(params.abonoId,[fetch:[aplicaciones:'select']])
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
}
