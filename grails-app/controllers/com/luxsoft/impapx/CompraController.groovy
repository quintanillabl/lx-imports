package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class CompraController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def filterPaneService

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'fecha'
        params.order='desc'
        respond Compra.list(params), model:[compraInstanceCount: Compra.count()]
    }

    def filter = {
        
        if(!params.max) params.max = 10
        render( view:'index',
            model:[ compraInstanceList: filterPaneService.filter( params, Compra ),
            compraInstanceCount: filterPaneService.count( params, Compra ),
            filterParams: org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params),
            params:params ] )
    }

    def show(Compra compraInstance) {
        respond compraInstance
    }

    def create() {
        respond new Compra(params)
    }

    @Transactional
    def save(Compra compraInstance) {
        if (compraInstance == null) {
            notFound()
            return
        }

        if (compraInstance.hasErrors()) {
            respond compraInstance.errors, view:'create'
            return
        }

        compraInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'compra.label', default: 'Compra'), compraInstance.id])
                redirect compraInstance
            }
            '*' { respond compraInstance, [status: CREATED] }
        }
    }

    def edit(Compra compraInstance) {
        respond compraInstance
    }

    @Transactional
    def update(Compra compraInstance) {
        if (compraInstance == null) {
            notFound()
            return
        }

        if (compraInstance.hasErrors()) {
            respond compraInstance.errors, view:'edit'
            return
        }

        compraInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Compra.label', default: 'Compra'), compraInstance.id])
                redirect compraInstance
            }
            '*'{ respond compraInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Compra compraInstance) {

        if (compraInstance == null) {
            notFound()
            return
        }

        compraInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Compra.label', default: 'Compra'), compraInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'compra.label', default: 'Compra'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def comprasAsJSONList(){
        def term='%'+params.term.trim()+'%'
        def query=Compra.where{
            (folio=~term || proveedor.nombre=~term || comentario=~term) 
        }
        def compras=query.list(max:30, sort:"folio",order:'desc')

        def comprasList=compras.collect { compra ->
            def label="${compra.folio} ${compra.proveedor} ${compra.fecha.format('dd/MM/yyyy')} ${compra.total} (${compra.moneda})"
            [id:compra.id,label:label,value:label]
        }
        render comprasList as JSON
    }
}
