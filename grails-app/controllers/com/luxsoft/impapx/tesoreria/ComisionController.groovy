package com.luxsoft.impapx.tesoreria

import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.TipoDeCambio;

import util.MonedaUtils;

class ComisionController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 100, 500)
		params.sort="fecha"
		params.order="desc"
        [comisionInstanceList: Comision.list(params), comisionInstanceTotal: Comision.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
			params.tc=1
			params.fecha=new Date()
			params.impuestoTasa=16
        	[comisionInstance: new Comision(params)]
			break
		case 'POST':
			 println 'Alta de comision '+params
	        def comisionInstance = new Comision(params)
			
			if(comisionInstance.cuenta.moneda!=MonedaUtils.PESOS){
				// Pendiente hasta tener el bean de TipoDeCambio
				/*
				def fecha=comisionInstance.fecha
				def tipoDeCambio=TipoDeCambio.findByFechaAndMonedaOrigenAndMonedaFuenta(
						fecha
						,MonedaUtils.PESOS
						,comisionInstance.cuenta.moneda)
				if(!tipoDeCambio){
					flash.message="No existe tipo de cambio registrado para la fecha: "+fecha+ " debe solicitar que se registre para poder proceeder"
					render view: 'create', model: [comisionInstance: comisionInstance]
					return
				}¡*/
			}else{
				comisionInstance.tc=1
			}
	        if (!comisionInstance.save(flush: true)) {
	            render view: 'create', model: [comisionInstance: comisionInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'comision.label', default: 'Comision'), comisionInstance.id])
	        redirect action: 'show', id: comisionInstance.id
			break
		}
    }

    def show() {
        def comisionInstance = Comision.get(params.id)
        if (!comisionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'comision.label', default: 'Comision'), params.id])
            redirect action: 'list'
            return
        }

        [comisionInstance: comisionInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def comisionInstance = Comision.get(params.id)
	        if (!comisionInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comision.label', default: 'Comision'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [comisionInstance: comisionInstance]
			break
		case 'POST':
	        def comisionInstance = Comision.get(params.id)
	        if (!comisionInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comision.label', default: 'Comision'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (comisionInstance.version > version) {
	                comisionInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'comision.label', default: 'Comision')] as Object[],
	                          "Another user has updated this Comision while you were editing")
	                render view: 'edit', model: [comisionInstance: comisionInstance]
	                return
	            }
	        }

	        comisionInstance.properties = params

	        if (!comisionInstance.save(flush: true)) {
	            render view: 'edit', model: [comisionInstance: comisionInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'comision.label', default: 'Comision'), comisionInstance.id])
	        redirect action: 'show', id: comisionInstance.id
			break
		}
    }

    def delete() {
        def comisionInstance = Comision.get(params.id)
        if (!comisionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'comision.label', default: 'Comision'), params.id])
            redirect action: 'list'
            return
        }

        try {
            comisionInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'comision.label', default: 'Comision'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'comision.label', default: 'Comision'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
