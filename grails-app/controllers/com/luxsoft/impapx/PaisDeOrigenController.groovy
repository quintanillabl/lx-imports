package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('ADMIN')"])
@Transactional(readOnly = true)
class PaisDeOrigenController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond PaisDeOrigen.list(params), model:[paisDeOrigenInstanceCount: PaisDeOrigen.count()]
    }

    def show(PaisDeOrigen paisDeOrigenInstance) {
        respond paisDeOrigenInstance
    }

    def create() {
        respond new PaisDeOrigen(params)
    }

    @Transactional
    def save(PaisDeOrigen paisDeOrigenInstance) {
        if (paisDeOrigenInstance == null) {
            notFound()
            return
        }

        if (paisDeOrigenInstance.hasErrors()) {
            respond paisDeOrigenInstance.errors, view:'create'
            return
        }
        paisDeOrigenInstance.nombre = paisDeOrigenInstance.nombre.toUpperCase()
        paisDeOrigenInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'paisDeOrigen.label', default: 'PaisDeOrigen'), paisDeOrigenInstance.id])
                redirect paisDeOrigenInstance
            }
            '*' { respond paisDeOrigenInstance, [status: CREATED] }
        }
    }

    def edit(PaisDeOrigen paisDeOrigenInstance) {
        respond paisDeOrigenInstance
    }

    @Transactional
    def update(PaisDeOrigen paisDeOrigenInstance) {
        if (paisDeOrigenInstance == null) {
            notFound()
            return
        }

        if (paisDeOrigenInstance.hasErrors()) {
            respond paisDeOrigenInstance.errors, view:'edit'
            return
        }

        paisDeOrigenInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'PaisDeOrigen.label', default: 'PaisDeOrigen'), paisDeOrigenInstance.id])
                redirect paisDeOrigenInstance
            }
            '*'{ respond paisDeOrigenInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(PaisDeOrigen paisDeOrigenInstance) {

        if (paisDeOrigenInstance == null) {
            notFound()
            return
        }

        paisDeOrigenInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'PaisDeOrigen.label', default: 'PaisDeOrigen'), paisDeOrigenInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'paisDeOrigen.label', default: 'PaisDeOrigen'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
