package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMRAS','TESORERIA')"])
@Transactional(readOnly = true)
class FacturaDeGastosController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        respond FacturaDeGastos.list(params), model:[facturaDeGastosInstanceCount: FacturaDeGastos.count()]
    }

    def show(FacturaDeGastos facturaDeGastosInstance) {
        respond facturaDeGastosInstance
    }

    def create() {
        respond new FacturaDeGastos(fecha:new Date(),vencimiento:new Date()+30)
    }

    @Transactional
    def save(FacturaDeGastos facturaDeGastosInstance) {
        if (facturaDeGastosInstance == null) {
            notFound()
            return
        }
        if (facturaDeGastosInstance.hasErrors()) {
            respond facturaDeGastosInstance.errors, view:'create'
            return
        }
        facturaDeGastosInstance.save flush:true
        flash.message = message(code: 'default.created.message', args: [message(code: 'facturaDeGastos.label', default: 'FacturaDeGastos'), facturaDeGastosInstance.id])
        redirect facturaDeGastosInstance
    }

    def edit(FacturaDeGastos facturaDeGastosInstance) {
        respond facturaDeGastosInstance
    }

    @Transactional
    def update(FacturaDeGastos facturaDeGastosInstance) {
        if (facturaDeGastosInstance == null) {
            notFound()
            return
        }

        if (facturaDeGastosInstance.hasErrors()) {
            respond facturaDeGastosInstance.errors, view:'edit'
            return
        }

        facturaDeGastosInstance.save flush:true
        flash.message = message(code: 'default.updated.message', args: [message(code: 'FacturaDeGastos.label', default: 'FacturaDeGastos'), facturaDeGastosInstance.id])
        redirect facturaDeGastosInstance
    }

    @Transactional
    def delete(FacturaDeGastos facturaDeGastosInstance) {

        if (facturaDeGastosInstance == null) {
            notFound()
            return
        }

        facturaDeGastosInstance.delete flush:true
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'FacturaDeGastos.label', default: 'FacturaDeGastos'), facturaDeGastosInstance.id])
        redirect action:"index", method:"GET"
    }

    protected void notFound() {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'facturaDeGastos.label', default: 'FacturaDeGastos'), params.id])
        redirect action: "index", method: "GET"
    }
}
