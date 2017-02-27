package com.luxsoft.nomina



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMPRAS','ADMIN')"])
@Transactional(readOnly = true)
class AsimiladoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond Asimilado.list(params), model:[asimiladoInstanceCount: Asimilado.count()]
    }

    def show(Asimilado asimiladoInstance) {
        respond asimiladoInstance
    }

    def create() {
        respond new Asimilado(params)
    }

    @Transactional
    def save(Asimilado asimiladoInstance) {
        if (asimiladoInstance == null) {
            notFound()
            return
        }

        if (asimiladoInstance.hasErrors()) {
            respond asimiladoInstance.errors, view:'create'
            return
        }

        asimiladoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'asimilado.label', default: 'Asimilado'), asimiladoInstance.id])
                redirect asimiladoInstance
            }
            '*' { respond asimiladoInstance, [status: CREATED] }
        }
    }

    def edit(Asimilado asimiladoInstance) {
        respond asimiladoInstance
    }

    @Transactional
    def update(Asimilado asimiladoInstance) {
        if (asimiladoInstance == null) {
            notFound()
            return
        }

        if (asimiladoInstance.hasErrors()) {
            respond asimiladoInstance.errors, view:'edit'
            return
        }

        asimiladoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Asimilado.label', default: 'Asimilado'), asimiladoInstance.id])
                redirect asimiladoInstance
            }
            '*'{ respond asimiladoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Asimilado asimiladoInstance) {

        if (asimiladoInstance == null) {
            notFound()
            return
        }

        asimiladoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Asimilado.label', default: 'Asimilado'), asimiladoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'asimilado.label', default: 'Asimilado'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
