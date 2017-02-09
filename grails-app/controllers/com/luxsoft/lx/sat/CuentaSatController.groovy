package com.luxsoft.lx.sat



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
@Transactional(readOnly = true)
class CuentaSatController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond CuentaSat.list(params), model:[cuentaSatInstanceCount: CuentaSat.count()]
    }

    def show(CuentaSat cuentaSatInstance) {
        respond cuentaSatInstance
    }

    def create() {
        respond new CuentaSat(params)
    }

    @Transactional
    def save(CuentaSat cuentaSatInstance) {
        if (cuentaSatInstance == null) {
            notFound()
            return
        }

        if (cuentaSatInstance.hasErrors()) {
            respond cuentaSatInstance.errors, view:'create'
            return
        }

        cuentaSatInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cuentaSat.label', default: 'CuentaSat'), cuentaSatInstance.id])
                redirect cuentaSatInstance
            }
            '*' { respond cuentaSatInstance, [status: CREATED] }
        }
    }

    def edit(CuentaSat cuentaSatInstance) {
        respond cuentaSatInstance
    }

    @Transactional
    def update(CuentaSat cuentaSatInstance) {
        if (cuentaSatInstance == null) {
            notFound()
            return
        }

        if (cuentaSatInstance.hasErrors()) {
            respond cuentaSatInstance.errors, view:'edit'
            return
        }

        cuentaSatInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CuentaSat.label', default: 'CuentaSat'), cuentaSatInstance.id])
                redirect cuentaSatInstance
            }
            '*'{ respond cuentaSatInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(CuentaSat cuentaSatInstance) {

        if (cuentaSatInstance == null) {
            notFound()
            return
        }

        cuentaSatInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CuentaSat.label', default: 'CuentaSat'), cuentaSatInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaSat.label', default: 'CuentaSat'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def getCuentasJSON() {
        
        def term=params.term+'%'
        def list=CuentaSat.findAllByCodigoIlikeOrNombreIlike(term,term,[max:20,sort:"codigo",order:"asc"])
        list=list.collect{ c->
            def nombre="$c.codigo $c.nombre"
            
            [id:c.id,
            label:nombre,
            value:nombre]
        }
        def res=list as JSON
        render res
    }
}
