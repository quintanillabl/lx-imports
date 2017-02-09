package com.luxsoft.cfdi.retenciones



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ImpuestoRetenidoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ImpuestoRetenido.list(params), model:[impuestoRetenidoInstanceCount: ImpuestoRetenido.count()]
    }

    def show(ImpuestoRetenido impuestoRetenidoInstance) {
        respond impuestoRetenidoInstance
    }

    def create() {
        respond new ImpuestoRetenido(params)
    }

    @Transactional
    def save(ImpuestoRetenido impuestoRetenidoInstance) {
        if (impuestoRetenidoInstance == null) {
            notFound()
            return
        }

        if (impuestoRetenidoInstance.hasErrors()) {
            respond impuestoRetenidoInstance.errors, view:'create'
            return
        }

        impuestoRetenidoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'impuestoRetenido.label', default: 'ImpuestoRetenido'), impuestoRetenidoInstance.id])
                redirect impuestoRetenidoInstance
            }
            '*' { respond impuestoRetenidoInstance, [status: CREATED] }
        }
    }

    def edit(ImpuestoRetenido impuestoRetenidoInstance) {
        respond impuestoRetenidoInstance
    }

    @Transactional
    def update(ImpuestoRetenido impuestoRetenidoInstance) {
        if (impuestoRetenidoInstance == null) {
            notFound()
            return
        }

        if (impuestoRetenidoInstance.hasErrors()) {
            respond impuestoRetenidoInstance.errors, view:'edit'
            return
        }

        impuestoRetenidoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ImpuestoRetenido.label', default: 'ImpuestoRetenido'), impuestoRetenidoInstance.id])
                redirect impuestoRetenidoInstance
            }
            '*'{ respond impuestoRetenidoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ImpuestoRetenido impuestoRetenidoInstance) {

        if (impuestoRetenidoInstance == null) {
            notFound()
            return
        }

        impuestoRetenidoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ImpuestoRetenido.label', default: 'ImpuestoRetenido'), impuestoRetenidoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'impuestoRetenido.label', default: 'ImpuestoRetenido'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
