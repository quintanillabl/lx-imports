package com.luxsoft.impapx.tesoreria

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.Requisicion;
import com.luxsoft.impapx.TipoDeCambio;

class PagoProveedorController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	def pagoProveedorService

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 500, 1000)
		
		params.sort="fecha"
		params.order="desc"
        [pagoProveedorInstanceList: PagoProveedor.list(params), pagoProveedorInstanceTotal: PagoProveedor.count()]
    }

    def create() {
		
		switch (request.method) {
		case 'GET':
			
        	[pagoProveedorInstance: new PagoProveedor(fecha:new Date())]
			break
		case 'POST':
			
			def requisicion=Requisicion.get(params.requisicion.id)
	        def pagoProveedorInstance = new PagoProveedor(params)
			pagoProveedorInstance.requisicion=requisicion
			def res=pagoProveedorService.registrarEgreso(pagoProveedorInstance);
	        if (!res) {
	            render view: 'create', model: [pagoProveedorInstance: pagoProveedorInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), pagoProveedorInstance.id])
	        redirect action: 'show', id: pagoProveedorInstance.id
			break
		}
    }

    def show() {
        def pagoProveedorInstance = PagoProveedor.get(params.id)
        if (!pagoProveedorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
            redirect action: 'list'
            return
        }

        [pagoProveedorInstance: pagoProveedorInstance]
    }

    def edit() {
		
		switch (request.method) {
		case 'GET':
	        def pagoProveedorInstance = PagoProveedor.get(params.id)
	        if (!pagoProveedorInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [pagoProveedorInstance: pagoProveedorInstance]
			break
		case 'POST':
			
	        def pagoProveedorInstance = PagoProveedor.get(params.id)
	        if (!pagoProveedorInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (pagoProveedorInstance.version > version) {
	                pagoProveedorInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'pagoProveedor.label', default: 'PagoProveedor')] as Object[],
	                          "Another user has updated this PagoProveedor while you were editing")
	                render view: 'edit', model: [pagoProveedorInstance: pagoProveedorInstance]
	                return
	            }
	        }

	        pagoProveedorInstance.properties = params

	        if (!pagoProveedorInstance.save(flush: true)) {
	            render view: 'edit', model: [pagoProveedorInstance: pagoProveedorInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), pagoProveedorInstance.id])
	        redirect action: 'show', id: pagoProveedorInstance.id
			break
		}
    }

    def delete() {
        def pagoProveedorInstance = PagoProveedor.get(params.id)
        if (!pagoProveedorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
            redirect action: 'list'
            return
        }

        try {
            pagoProveedorInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def requisicionesDisponiblesJSONList(){
		println 'Term '+params.term
		def requisiciones=Requisicion.findAll("from Requisicion r left join fetch r.pagoProveedor pp where r.total>0  and str(r.id) like ? and pp is  null",['%'+params.term+'%'])
		//def requisiciones=Requisicion.findAll("from Requisicion r left join fetch r.pagoProveedor pp where r.total>0  and pp is  null")
		/*
		def requisiciones=Requisicion.findAll(sort:'id'){
			(total>0.0 )&&(pagoProveedor==null)&&
			 (proveedor.nombre=~this.params.term+'%')
			
		}*/
		
		def requisicionesList=requisiciones.collect { req ->
			def desc="Id: ${req.id} ${req.proveedor.nombre}  ${req.total} ${req.moneda}"
			[id:req.id,label:desc,value:desc]
		}
		render requisicionesList as JSON
	}
}
