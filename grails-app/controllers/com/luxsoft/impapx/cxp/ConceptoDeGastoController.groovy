package com.luxsoft.impapx.cxp

import org.springframework.dao.DataIntegrityViolationException

class ConceptoDeGastoController {

    static allowedMethods = [create: ['GET', 'POST'], editConcepto: ['GET', 'POST'], delete: 'POST']
	
	def facturaDeGastosService

    def index() {
        redirect action: 'list', params: params
    }

   

    

    def edit() {
		//println 'Editando concepto de gasto: '+params
		switch (request.method) {
		case 'GET':
	        def conceptoDeGastoInstance = ConceptoDeGasto.get(params.id)
	        if (!conceptoDeGastoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptoDeGasto.label', default: 'ConceptoDeGasto'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [conceptoDeGastoInstance: conceptoDeGastoInstance]
			break
		case 'POST':
	        def conceptoDeGastoInstance = ConceptoDeGasto.get(params.id)
	        if (!conceptoDeGastoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptoDeGasto.label', default: 'ConceptoDeGasto'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (conceptoDeGastoInstance.version > version) {
	                conceptoDeGastoInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'conceptoDeGasto.label', default: 'ConceptoDeGasto')] as Object[],
	                          "Another user has updated this ConceptoDeGasto while you were editing")
	                render view: 'editConcepto', model: [conceptoDeGastoInstance: conceptoDeGastoInstance]
	                return
	            }
	        }

	        conceptoDeGastoInstance.properties = params
			conceptoDeGastoInstance.total=conceptoDeGastoInstance.importe+conceptoDeGastoInstance.impuesto-conceptoDeGastoInstance.retension-conceptoDeGastoInstance.retensionIsr
			facturaDeGastosService.actualizar(conceptoDeGastoInstance.factura)
	        if (!conceptoDeGastoInstance.save(flush: true)) {
	            render view: 'editConcepto', model: [conceptoDeGastoInstance: conceptoDeGastoInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'conceptoDeGasto.label', default: 'ConceptoDeGasto'), conceptoDeGastoInstance.id])
	        redirect controller:'facturaDeGastos',action: 'edit', id: conceptoDeGastoInstance.factura.id
			break
		}
    }

   
}
