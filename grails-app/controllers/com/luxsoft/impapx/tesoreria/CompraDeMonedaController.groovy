package com.luxsoft.impapx.tesoreria

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.Requisicion;

class CompraDeMonedaController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def compraDeMonedaService

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [compraDeMonedaInstanceList: CompraDeMoneda.list(params), compraDeMonedaInstanceTotal: CompraDeMoneda.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
			params.fecha=new Date()
        	[compraDeMonedaInstance: new CompraDeMoneda(params)]
			break
		case 'POST':
			println 'Salvando compra: '+params
	        def compraDeMonedaInstance = new CompraDeMoneda(params)
			compraDeMonedaInstance=compraDeMonedaService.registrarCompra2(compraDeMonedaInstance)
	        if (compraDeMonedaInstance.hasErrors()) {
	            render view: 'create', model: [compraDeMonedaInstance: compraDeMonedaInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), compraDeMonedaInstance.id])
	        redirect action: 'show', id: compraDeMonedaInstance.id
			break
		}
    }

    def show() {
        def compraDeMonedaInstance = CompraDeMoneda.get(params.id)
        if (!compraDeMonedaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), params.id])
            redirect action: 'list'
            return
        }

        [compraDeMonedaInstance: compraDeMonedaInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def compraDeMonedaInstance = CompraDeMoneda.get(params.id)
			
	        if (!compraDeMonedaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [compraDeMonedaInstance: compraDeMonedaInstance]
			break
		case 'POST':
	        def compraDeMonedaInstance = CompraDeMoneda.get(params.id)
	        if (!compraDeMonedaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (compraDeMonedaInstance.version > version) {
	                compraDeMonedaInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda')] as Object[],
	                          "Another user has updated this CompraDeMoneda while you were editing")
	                render view: 'edit', model: [compraDeMonedaInstance: compraDeMonedaInstance]
	                return
	            }
	        }

	        compraDeMonedaInstance.properties = params

	        if (!compraDeMonedaInstance.save(flush: true)) {
	            render view: 'edit', model: [compraDeMonedaInstance: compraDeMonedaInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), compraDeMonedaInstance.id])
	        redirect action: 'show', id: compraDeMonedaInstance.id
			break
		}
    }

    def delete() {
        def compraDeMonedaInstance = CompraDeMoneda.get(params.id)
        if (!compraDeMonedaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), params.id])
            redirect action: 'list'
            return
        }

        try {
            compraDeMonedaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	
	def requisicionesDisponiblesJSONList(){
		
		def requisiciones=Requisicion.findAll("from Requisicion r left join fetch r.pagoProveedor pp where r.concepto='COMPRA_MONEDA' and r.total>0 and pp is  null")
		
		
		def requisicionesList=requisiciones.collect { req ->
			def desc="Id: ${req.id} ${req.proveedor.nombre}  ${req.total} "
			[id:req.id,label:desc,value:desc]
		}
		render requisicionesList as JSON
	}
}
