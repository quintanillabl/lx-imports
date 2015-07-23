package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class GastosDeImportacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        respond GastosDeImportacion.list(params), model:[gastosDeImportacionInstanceCount: GastosDeImportacion.count()]
    }

    def show(GastosDeImportacion gastosDeImportacionInstance) {
        respond gastosDeImportacionInstance
    }

    def create() {
        respond new GastosDeImportacion(fecha:new Date(),vencimiento:new Date()+30)
    }

    @Transactional
    def save(GastosDeImportacion gastosDeImportacionInstance) {
        
        if (gastosDeImportacionInstance == null) {
            notFound()
            return
        }

        if (gastosDeImportacionInstance.hasErrors()) {
            respond gastosDeImportacionInstance.errors, view:'create'
            return
        }

        gastosDeImportacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'gastosDeImportacion.label', default: 'GastosDeImportacion'), gastosDeImportacionInstance.id])
                redirect gastosDeImportacionInstance
            }
            '*' { respond gastosDeImportacionInstance, [status: CREATED] }
        }
    }

    def edit(GastosDeImportacion gastosDeImportacionInstance) {
        respond gastosDeImportacionInstance
    }

    @Transactional
    def update(GastosDeImportacion gastosDeImportacionInstance) {
        if (gastosDeImportacionInstance == null) {
            notFound()
            return
        }

        if (gastosDeImportacionInstance.hasErrors()) {
            respond gastosDeImportacionInstance.errors, view:'edit'
            return
        }

        gastosDeImportacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'GastosDeImportacion.label', default: 'GastosDeImportacion'), gastosDeImportacionInstance.id])
                redirect gastosDeImportacionInstance
            }
            '*'{ respond gastosDeImportacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(GastosDeImportacion gastosDeImportacionInstance) {

        if (gastosDeImportacionInstance == null) {
            notFound()
            return
        }

        gastosDeImportacionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'GastosDeImportacion.label', default: 'GastosDeImportacion'), gastosDeImportacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'gastosDeImportacion.label', default: 'GastosDeImportacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
