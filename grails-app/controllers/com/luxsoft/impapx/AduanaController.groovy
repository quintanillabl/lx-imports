package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('ADMIN')"])
@Transactional(readOnly = true)
class AduanaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond Aduana.list(params), model:[aduanaInstanceCount: Aduana.count()]
    }

    def show(Aduana aduanaInstance) {
        respond aduanaInstance
    }

    def create() {
        respond new Aduana(params)
    }

    @Transactional
    def save(Aduana aduanaInstance) {
        if (aduanaInstance == null) {
            notFound()
            return
        }

        if (aduanaInstance.hasErrors()) {
            respond aduanaInstance.errors, view:'create'
            return
        }

        aduanaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'aduana.label', default: 'Aduana'), aduanaInstance.id])
                redirect aduanaInstance
            }
            '*' { respond aduanaInstance, [status: CREATED] }
        }
    }

    def edit(Aduana aduanaInstance) {
        respond aduanaInstance
    }

    @Transactional
    def update(Aduana aduanaInstance) {
        if (aduanaInstance == null) {
            notFound()
            return
        }

        if (aduanaInstance.hasErrors()) {
            respond aduanaInstance.errors, view:'edit'
            return
        }

        aduanaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Aduana.label', default: 'Aduana'), aduanaInstance.id])
                redirect aduanaInstance
            }
            '*'{ respond aduanaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Aduana aduanaInstance) {

        if (aduanaInstance == null) {
            notFound()
            return
        }

        aduanaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Aduana.label', default: 'Aduana'), aduanaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'aduana.label', default: 'Aduana'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
