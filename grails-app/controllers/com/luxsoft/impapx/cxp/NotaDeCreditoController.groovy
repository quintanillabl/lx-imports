package com.luxsoft.impapx.cxp

import grails.converters.JSON

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.CuentaPorPagar;

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
class NotaDeCreditoController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def notaDeCreditoService

    def index() {
    	def periodo=session.periodo
    	def tipo=params.tipo?:'PENDIENTES'
        def query=NotaDeCredito.where{
        	fecha>=periodo.fechaInicial && fecha<=periodo.fechaFinal
        }
        if(tipo=='APLICADOS'){
        	query=query.where{
        		disponible<=0.0
        	}
        }
        if(tipo=='PENDIENTES'){
        	query=query.where{
        		aplicado<=0.0
        	}
        }
        [notaDeCreditoInstanceList:query.list([sort:'fecha',order:'desc']),tipo:tipo]
        
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[notaDeCreditoInstance: new NotaDeCredito(params)]
			break
		case 'POST':
		    println 'Generando nota: '+params
	        def notaDeCreditoInstance = new NotaDeCredito(params)
	        if (!notaDeCreditoInstance.save(flush: true)) {
	            render view: 'create', model: [notaDeCreditoInstance: notaDeCreditoInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'notaDeCredito.label', default: 'NotaDeCredito'), notaDeCreditoInstance.id])
	        redirect action: 'show', id: notaDeCreditoInstance.id
			break
		}
    }

    def show() {
        def notaDeCreditoInstance = NotaDeCredito.get(params.id)
        if (!notaDeCreditoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'notaDeCredito.label', default: 'NotaDeCredito'), params.id])
            redirect action: 'list'
            return
        }

        [notaDeCreditoInstance: notaDeCreditoInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def notaDeCreditoInstance = NotaDeCredito.findById(params.id,[fetch:[aplicaciones:'eager']])
	        if (!notaDeCreditoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'notaDeCredito.label', default: 'NotaDeCredito'), params.id])
	            redirect action: 'list'
	            return
	        }
	        [notaDeCreditoInstance: notaDeCreditoInstance]
			break
		case 'POST':
	        def notaDeCreditoInstance = NotaDeCredito.findById(params.id,[fetch:[aplicaciones:'eager']])
	        if (!notaDeCreditoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'notaDeCredito.label', default: 'NotaDeCredito'), params.id])
	            redirect action: 'list'
	            return
	        }
			
	        if (params.version) {
	            def version = params.version.toLong()
	            if (notaDeCreditoInstance.version > version) {
	                notaDeCreditoInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'notaDeCredito.label', default: 'NotaDeCredito')] as Object[],
	                          "Another user has updated this NotaDeCredito while you were editing")
	                render view: 'edit', model: [notaDeCreditoInstance: notaDeCreditoInstance]
	                return
	            }
	        }
			/*
			if(notaDeCreditoInstance.aplicado>0.0){
				flash.message = "Nota de crédito con aplicaciones no se puede modificar "
				redirect action: 'show', id: notaDeCreditoInstance.id
				return
			}*/
			
	        notaDeCreditoInstance.properties = params

	        if (!notaDeCreditoInstance.save(flush: true)) {
	            render view: 'edit', model: [notaDeCreditoInstance: notaDeCreditoInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'notaDeCredito.label', default: 'NotaDeCredito'), notaDeCreditoInstance.id])
	        redirect action: 'edit', id: notaDeCreditoInstance.id
			break
		}
    }

    def delete() {
		println 'Eliminando la nota de credito: '+params
        def notaDeCreditoInstance = NotaDeCredito.get(params.id)
        if (!notaDeCreditoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'notaDeCredito.label', default: 'NotaDeCredito'), params.id])
            redirect action: 'list'
            return
        }

        try {
            notaDeCreditoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'notaDeCredito.label', default: 'NotaDeCredito'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'notaDeCredito.label', default: 'NotaDeCredito'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def selectorDeFacturas(){
		//println 'Selector de factoras para aplicaciones: '+params
		def nota = NotaDeCredito.get(params.id)
		
		def facturas=CuentaPorPagar
			.findAll("from CuentaPorPagar p where p.proveedor=? and p.moneda=? and p.total-p.pagosAplicados>0"
				,[nota.proveedor,nota.moneda])
		[cuentaPorPagarInstanceList:facturas,cuentasPorPagarTotal:facturas.size(),abonoInstance:nota]
	}
	
	def registrarAplicaciones(){
		def data=[:]
		def notaDeCreditoInstance = NotaDeCredito.findById(params.abonoId,[fetch:[aplicaciones:'eager']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		
		try {
			
            notaDeCreditoService?.generaAplicacion(notaDeCreditoInstance,jsonArray)
            data.res='APLICACIONES_GENERADAS'
        }
        catch (RuntimeException e) {
			e.printStackTrace()
			println 'Error: '+e
			data.res="ERROR"
			data.error=ExceptionUtils.getRootCauseMessage(e)
        }
		
		render data as JSON
	}
	
	def eliminarAplicaciones(){
		def data=[:]
		def notaDeCreditoInstance = NotaDeCredito.findById(params.abonoId,[fetch:[aplicaciones:'eager']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		try {
			notaDeCreditoService?.eliminarAplicaciones(notaDeCreditoInstance,jsonArray)
			data.res='APLICACIONES_ELIMINADAS'
		}
		catch (RuntimeException e) {
			e.printStackTrace()
			//println 'Error: '+e
			data.res="ERROR"
			data.error=ExceptionUtils.getRootCauseMessage(e)
		}
		render data as JSON
	}
}
