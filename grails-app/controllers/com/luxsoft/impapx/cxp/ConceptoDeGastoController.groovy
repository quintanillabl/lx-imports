package com.luxsoft.impapx.cxp

import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('TESORERIA','CONTABILIDAD','GASTOS','COMPRAS')"])
class ConceptoDeGastoController {

    static allowedMethods = [create: 'GET',edit:'GET',save:'POST',update:'PUT',delete: 'DELETE']
	
	def facturaDeGastosService

    def index() {
        redirect action: 'list', params: params
    }
    

    def edit() {
		def conceptoDeGastoInstance = ConceptoDeGasto.get(params.id)
		if (!conceptoDeGastoInstance) {
		    flash.message = message(code: 'default.not.found.message', args: [message(code: 'conceptoDeGasto.label', default: 'ConceptoDeGasto'), params.id])
		    redirect action: 'list'
		    return
		}
		[conceptoDeGastoInstance: conceptoDeGastoInstance]
    }

    def update(ConceptoDeGasto conceptoDeGastoInstance){
    	if(conceptoDeGastoInstance.hasErrors()){
    		render view:'create',model:[conceptoDeGastoInstance:conceptoDeGastoInstance]
    		return
    	}
    	conceptoDeGastoInstance.save flush:true
    	flash.message="Concepto ${conceptoDeGastoInstance.id} actualizado"
    	redirect controller:'facturaDeGastos' ,action:'edit',id:conceptoDeGastoInstance.factura.id
    }

   
}
