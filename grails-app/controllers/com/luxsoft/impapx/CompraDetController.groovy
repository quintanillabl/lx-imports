package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class CompraDetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond CompraDet.list(params), model:[compraDetInstanceCount: CompraDet.count()]
    }

    def show(CompraDet compraDetInstance) {
        respond compraDetInstance
    }

    def create() {
        respond new CompraDet(params)
    }

    @Transactional
    def save(CompraDet compraDetInstance) {
        if (compraDetInstance == null) {
            notFound()
            return
        }

        if (compraDetInstance.hasErrors()) {
            respond compraDetInstance.errors, view:'create'
            return
        }

        compraDetInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), compraDetInstance.id])
                redirect compraDetInstance
            }
            '*' { respond compraDetInstance, [status: CREATED] }
        }
    }

    def edit(CompraDet compraDetInstance) {
        respond compraDetInstance
    }

    @Transactional
    def update(CompraDet compraDetInstance) {
        if (compraDetInstance == null) {
            notFound()
            return
        }

        if (compraDetInstance.hasErrors()) {
            respond compraDetInstance.errors, view:'edit'
            return
        }

        compraDetInstance.save flush:true
        flash.message = message(code: 'default.updated.message', args: [message(code: 'CompraDet.label', default: 'CompraDet'), compraDetInstance.id])
        redirect controller:'compra',action:'edit',id:compraDetInstance.compra.id
        
    }

    @Transactional
    def delete(CompraDet compraDetInstance) {

        if (compraDetInstance == null) {
            notFound()
            return
        }

        compraDetInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CompraDet.label', default: 'CompraDet'), compraDetInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'compraDet.label', default: 'CompraDet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
