package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMPRAS','TESORERIA')"])
@Transactional(readOnly = true)
class RequisicionDetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond RequisicionDet.list(params), model:[requisicionDetInstanceCount: RequisicionDet.count()]
    }

    def show(RequisicionDet requisicionDetInstance) {
        respond requisicionDetInstance
    }

    def create() {
        respond new RequisicionDet(params)
    }

    @Transactional
    def save(RequisicionDet requisicionDetInstance) {
        if (requisicionDetInstance == null) {
            notFound()
            return
        }
        if (requisicionDetInstance.hasErrors()) {
            respond requisicionDetInstance.errors, view:'create'
            return
        }
        requisicionDetInstance.save flush:true
        flash.message = message(code: 'default.created.message', args: [message(code: 'requisicionDet.label', default: 'RequisicionDet'), requisicionDetInstance.id])
        redirect controller:'requisicion',action:'edit',id:requisicionInstance.requisicion
    }

    def edit(RequisicionDet requisicionDetInstance) {
        respond requisicionDetInstance
    }

    @Transactional
    def update(RequisicionDet requisicionDetInstance) {
        if (requisicionDetInstance == null) {
            notFound()
            return
        }

        if (requisicionDetInstance.hasErrors()) {
            respond requisicionDetInstance.errors, view:'edit'
            return
        }

        requisicionDetInstance.save flush:true
        flash.message = message(code: 'default.updated.message', args: [message(code: 'RequisicionDet.label', default: 'RequisicionDet'), requisicionDetInstance.id])
        redirect requisicionDetInstance

        
    }

    @Transactional
    def delete(RequisicionDet requisicionDetInstance) {

        if (requisicionDetInstance == null) {
            notFound()
            return
        }

        requisicionDetInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'RequisicionDet.label', default: 'RequisicionDet'), requisicionDetInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicionDet.label', default: 'RequisicionDet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def getFacturasDisponibles() {

        def term=params.term+'%'
        log.info 'Buscando facturas: '+term
        def args=[term.toLowerCase()]
        def params=[max:30,sort:"fecha",order:"desc"]
        def hql="from FacturaDeGastos g where g.proveedor.tipo = 'FLETES' and g.comprobante.id != null and ( lower(g.proveedor.nombre) like ?)  and g not in(select c.factura from RequisicionDet c) order by g.fecha desc"
        def list=FacturaDeGastos.findAll(hql,args,params)
        
        list=list.collect{ c->
            def nombre="$c.proveedor.nombre ${c.fecha.text()} F: ${c.comprobante?.folio} ($c.importe) "
            [id:c.id,
            label:nombre,
            value:nombre]
        }
        def res=list as JSON
        render res
    }
}
