package com.luxsoft.lx.sat



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import grails.converters.JSON

@Secured(["hasRole('CONTABILIDAD')"])
@Transactional(readOnly = true)
class BancoSatController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "GET"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BancoSat.list(params), model:[bancoSatInstanceCount: BancoSat.count()]
    }

    def show(BancoSat bancoSatInstance) {
        respond bancoSatInstance
    }

    def create() {
        respond new BancoSat(params)
    }

    @Transactional
    def save(BancoSat bancoSatInstance) {
        if (bancoSatInstance == null) {
            notFound()
            return
        }

        if (bancoSatInstance.hasErrors()) {
            respond bancoSatInstance.errors, view:'create'
            return
        }

        bancoSatInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bancoSat.label', default: 'BancoSat'), bancoSatInstance.id])
                redirect bancoSatInstance
            }
            '*' { respond bancoSatInstance, [status: CREATED] }
        }
    }

    def edit(BancoSat bancoSatInstance) {
        respond bancoSatInstance
    }

    @Transactional
    def update(BancoSat bancoSatInstance) {
        if (bancoSatInstance == null) {
            notFound()
            return
        }

        if (bancoSatInstance.hasErrors()) {
            respond bancoSatInstance.errors, view:'edit'
            return
        }

        bancoSatInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'BancoSat.label', default: 'BancoSat'), bancoSatInstance.id])
                redirect bancoSatInstance
            }
            '*'{ respond bancoSatInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(BancoSat bancoSatInstance) {

        if (bancoSatInstance == null) {
            notFound()
            return
        }

        bancoSatInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BancoSat.label', default: 'BancoSat'), bancoSatInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bancoSat.label', default: 'BancoSat'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def bancos(){
        def term = params.term + '%'
        def list=BancoSat.where{ clave =~ term || nombreCorto =~ term || razonSocial =~ term}.list([sort:'clave'])

        list=list.collect{ r->
            def nombre="${r.nombreCorto} (${r.clave}) "
            [id:r.id,
            label:nombre,
            value:nombre]
        }
        def res=list as JSON
        
        render res

    }
}
