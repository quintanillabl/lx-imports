package com.luxsoft.impapx

import org.springframework.dao.DataIntegrityViolationException

class CompraDetController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [compraDetInstanceList: CompraDet.list(params), compraDetInstanceTotal: CompraDet.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[compraDetInstance: new CompraDet(params)]
			break
		case 'POST':
	        def compraDetInstance = new CompraDet(params)
	        if (!compraDetInstance.save(flush: true)) {
	            render view: 'create', model: [compraDetInstance: compraDetInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), compraDetInstance.id])
	        redirect action: 'show', id: compraDetInstance.id
			break
		}
    }

    def show() {
        def compraDetInstance = CompraDet.get(params.id)
        if (!compraDetInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), params.id])
            redirect action: 'list'
            return
        }

        [compraDetInstance: compraDetInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def compraDetInstance = CompraDet.get(params.id)
	        if (!compraDetInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [compraDetInstance: compraDetInstance]
			break
		case 'POST':
	        def compraDetInstance = CompraDet.get(params.id)
	        if (!compraDetInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (compraDetInstance.version > version) {
	                compraDetInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'compraDet.label', default: 'CompraDet')] as Object[],
	                          "Another user has updated this CompraDet while you were editing")
	                render view: 'edit', model: [compraDetInstance: compraDetInstance]
	                return
	            }
	        }

	        compraDetInstance.properties = params

	        if (!compraDetInstance.save(flush: true)) {
	            render view: 'edit', model: [compraDetInstance: compraDetInstance]
	            return
	        }

			flash.message = 'Partida actualizada: '+compraDetInstance.producto.clave
	        redirect controller:'compra', action: 'edit', id: compraDetInstance.compra.id
			break
		}
    }

    def delete() {
        def compraDetInstance = CompraDet.get(params.id)
        if (!compraDetInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), params.id])
            redirect action: 'list'
            return
        }

        try {
            compraDetInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
