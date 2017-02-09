package com.luxsoft.lx.contabilidad



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
@Transactional(readOnly = true)
class ProcesadorDePolizaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond ProcesadorDePoliza.list(params), model:[procesadorDePolizaInstanceCount: ProcesadorDePoliza.count()]
    }

    def show(ProcesadorDePoliza procesadorDePolizaInstance) {
        respond procesadorDePolizaInstance
    }

    def create() {
        respond new ProcesadorDePoliza(params)
    }

    @Transactional
    def save(ProcesadorDePoliza procesadorDePolizaInstance) {
        if (procesadorDePolizaInstance == null) {
            notFound()
            return
        }

        if (procesadorDePolizaInstance.hasErrors()) {
            respond procesadorDePolizaInstance.errors, view:'create'
            return
        }

        procesadorDePolizaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'procesadorDePoliza.label', default: 'ProcesadorDePoliza'), procesadorDePolizaInstance.id])
                redirect procesadorDePolizaInstance
            }
            '*' { respond procesadorDePolizaInstance, [status: CREATED] }
        }
    }

    def edit(ProcesadorDePoliza procesadorDePolizaInstance) {
        respond procesadorDePolizaInstance
    }

    @Transactional
    def update(ProcesadorDePoliza procesadorDePolizaInstance) {
        if (procesadorDePolizaInstance == null) {
            notFound()
            return
        }

        if (procesadorDePolizaInstance.hasErrors()) {
            respond procesadorDePolizaInstance.errors, view:'edit'
            return
        }

        procesadorDePolizaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ProcesadorDePoliza.label', default: 'ProcesadorDePoliza'), procesadorDePolizaInstance.id])
                redirect procesadorDePolizaInstance
            }
            '*'{ respond procesadorDePolizaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ProcesadorDePoliza procesadorDePolizaInstance) {

        if (procesadorDePolizaInstance == null) {
            notFound()
            return
        }

        procesadorDePolizaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ProcesadorDePoliza.label', default: 'ProcesadorDePoliza'), procesadorDePolizaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'procesadorDePoliza.label', default: 'ProcesadorDePoliza'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
