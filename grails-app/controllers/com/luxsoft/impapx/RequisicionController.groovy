package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMPRAS','TESORERIA')"])
@Transactional(readOnly = true)
class RequisicionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def requisicionService

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        respond Requisicion.list(params), model:[requisicionInstanceCount: Requisicion.count()]
    }

    def show(Requisicion requisicionInstance) {
        respond requisicionInstance
    }

    def create() {
        respond new Requisicion(fecha:new Date())
    }

    @Transactional
    def save(Requisicion requisicionInstance) {
        if (requisicionInstance == null) {
            notFound()
            return
        }
        if (requisicionInstance.hasErrors()) {
            respond requisicionInstance.errors, view:'create'
            return
        }
        requisicionInstance.save flush:true
        flash.message = message(code: 'default.created.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), requisicionInstance.id])
        redirect action:'edit',id:requisicionInstance.id
        
    }

    def edit(Requisicion requisicionInstance) {
        respond requisicionInstance
    }

    @Transactional
    def update(Requisicion requisicionInstance) {
        if (requisicionInstance == null) {
            notFound()
            return
        }

        if (requisicionInstance.hasErrors()) {
            respond requisicionInstance.errors, view:'edit'
            return
        }

        requisicionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Requisicion.label', default: 'Requisicion'), requisicionInstance.id])
                redirect requisicionInstance
            }
            '*'{ respond requisicionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Requisicion requisicionInstance) {

        if (requisicionInstance == null) {
            notFound()
            return
        }

        requisicionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Requisicion.label', default: 'Requisicion'), requisicionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Transactional
    def selectorDeFacturas(Requisicion requisicion){
        def hql="from CuentaPorPagar p where p.proveedor=?   and p.total-p.requisitado>0 and moneda=? and total-pagosAplicados>0 order by p.fecha,p.documento "
        def res=CuentaPorPagar.findAll(hql,[requisicion.proveedor,requisicion.moneda])
        [requisicionInstance:requisicion,cuentaPorPagarInstanceList:res,cuentasPorPagarTotal:res.size()]
    }

    @Transactional
    def asignarFacturas(){
        def dataToRender=[:]
        JSONArray jsonArray=JSON.parse(params.partidas);
        def requisicion= Requisicion.findById(params.requisicionId,[fetch:[partidas:'select']])
        requisicionService.asignarFacturas(requisicion, jsonArray)
        dataToRender.requisicionId=requisicion.id
        render dataToRender as JSON
    }

    @Transactional
    def eliminarPartidas(){
        def dataToRender=[:]
        def requisicion= Requisicion.findById(params.requisicionId,[fetch:[partidas:'select']])
        JSONArray jsonArray=JSON.parse(params.partidas);
        requisicionService.eliminarPartidas(requisicion,jsonArray)
        dataToRender.requisicionId=requisicion.id
        render dataToRender as JSON
    }
}
