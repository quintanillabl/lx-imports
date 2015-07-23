package com.luxsoft.impapx.tesoreria

import org.springframework.dao.DataIntegrityViolationException

class TraspasoController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def traspasoService

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.sort="fecha"
		params.order="desc"
        [traspasoInstanceList: Traspaso.list(params), traspasoInstanceTotal: Traspaso.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[traspasoInstance: new Traspaso(params)]
			break
		case 'POST':
	        def traspasoInstance = new Traspaso(params)
			traspasoInstance=traspasoService.generarTraspaso(traspasoInstance)
			/*
			def cuenta=traspasoInstance.cuentaOrigen
			traspasoInstance.comision=0
			traspasoInstance.impuesto=0
			traspasoInstance.moneda=cuenta.moneda
			*/
			/*Generando el egreso
			MovimientoDeCuenta egreso=new MovimientoDeCuenta(traspasoInstance.properties)
			egreso.cuenta=traspasoInstance.cuentaOrigen
			egreso.tipo='TRANSFERENCIA'
			egreso.origen='TESORERIA'
			egreso.concepto='TRASPASO'
			traspasoInstance.addToMovimientos(egreso)
			*/
			
			/*Generando el ingreso
			MovimientoDeCuenta ingreso=new MovimientoDeCuenta(
				cuenta:traspasoInstance.cuentaOrigen
				,fecha:traspasoInstance.fecha
				,moneda:traspasoInstance.moneda
				,tc:1
				,importe:traspasoInstance.importe
				,comentario:'TRASPASO')
			ingreso.tipo='TRANSFERENCIA'
			ingreso.origen='TESORERIA'
			ingreso.concepto='TRASPASO'
			*/
			/*
			if(!ingreso.save(flush:true)){
				println 'Errores en mov: '+ingreso.errors
				render view: 'create', model: [traspasoInstance: traspasoInstance]
				return
			}
			*/
			//traspasoInstance.addToMovimientos(ingreso)
			/*
	        if (!traspasoInstance.save(flush: true)) {
	            render view: 'create', model: [traspasoInstance: traspasoInstance]
	            return
	        }*/
	        
			if(traspasoInstance.hasErrors()){
				render view: 'create', model: [traspasoInstance: traspasoInstance]
				return
			}

			flash.message = message(code: 'default.created.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), traspasoInstance.id])
	        redirect action: 'show', id: traspasoInstance.id
			break
		}
    }

    def show() {
        def traspasoInstance = Traspaso.get(params.id)
        if (!traspasoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), params.id])
            redirect action: 'list'
            return
        }

        [traspasoInstance: traspasoInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def traspasoInstance = Traspaso.get(params.id)
	        if (!traspasoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [traspasoInstance: traspasoInstance]
			break
		case 'POST':
	        def traspasoInstance = Traspaso.get(params.id)
	        if (!traspasoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (traspasoInstance.version > version) {
	                traspasoInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'traspaso.label', default: 'Traspaso')] as Object[],
	                          "Another user has updated this Traspaso while you were editing")
	                render view: 'edit', model: [traspasoInstance: traspasoInstance]
	                return
	            }
	        }

	        traspasoInstance.properties = params

	        if (!traspasoInstance.save(flush: true)) {
	            render view: 'edit', model: [traspasoInstance: traspasoInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), traspasoInstance.id])
	        redirect action: 'show', id: traspasoInstance.id
			break
		}
    }

    def delete() {
        def traspasoInstance = Traspaso.get(params.id)
        if (!traspasoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), params.id])
            redirect action: 'list'
            return
        }

        try {
            traspasoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
