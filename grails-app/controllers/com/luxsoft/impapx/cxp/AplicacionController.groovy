package com.luxsoft.impapx.cxp



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured


@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class AplicacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond Aplicacion.list(params), model:[aplicacionInstanceCount: Aplicacion.count()]
    }

    def show(Aplicacion aplicacionInstance) {
        respond aplicacionInstance
    }

    def create() {
        respond new Aplicacion(params)
    }

    @Transactional
    def save(Aplicacion aplicacionInstance) {
        if (aplicacionInstance == null) {
            notFound()
            return
        }

        if (aplicacionInstance.hasErrors()) {
            respond aplicacionInstance.errors, view:'create'
            return
        }

        aplicacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'aplicacion.label', default: 'Aplicacion'), aplicacionInstance.id])
                redirect aplicacionInstance
            }
            '*' { respond aplicacionInstance, [status: CREATED] }
        }
    }

    def edit(Aplicacion aplicacionInstance) {
        respond aplicacionInstance
    }

    @Transactional
    def update(Aplicacion aplicacionInstance) {
        if (aplicacionInstance == null) {
            notFound()
            return
        }

        if (aplicacionInstance.hasErrors()) {
            respond aplicacionInstance.errors, view:'edit'
            return
        }
        aplicacionInstance.save flush:true
        flash.message ="Aplicación modificada ${aplicacionInstance.id} "
        if(aplicacionInstance.abono.instanceOf(NotaDeCredito)){
            
            redirect controller:'notaDeCredito',action:'edit',id:aplicacionInstance.abono.id
        }else{
            redirect controller:'pago',action:'edit',id:aplicacionInstance.abono.id
        }


        
    }

    @Transactional
    def delete(Aplicacion aplicacionInstance) {

        if (aplicacionInstance == null) {
            notFound()
            return
        }

        aplicacionInstance.delete flush:true
        flash.message ="Aplicación eliminada: ${aplicacionInstance.id} "
        if(aplicacionInstance.abono.instanceOf(NotaDeCredito)){
            redirect controller:'notaDeCredito',action:'edit',id:aplicacionInstance.abono.id
        }else{
            redirect controller:'pago',action:'edit',id:aplicacionInstance.abono.id
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'aplicacion.label', default: 'Aplicacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Transactional
    def actualizarAplicacion(Aplicacion aplicacionInstance){
        def importe = params.importe as BigDecimal
        log.info ("Actualizando aplicacion $aplicacionInstance.id  Importe:${importe}")
        def abono = aplicacionInstance.abono
        if(importe>abono.disponible){
            def data = [error:"Importe superior al disponible: ${abono.disponible}"]
            render data as JSON
        }
        aplicacionInstance.total = importe
        aplicacionInstance.save flush:true

        //abono = Abono.get(aplicacionInstance.abono.id)
        abono.refresh()
        
        def data = [
            message:"Aplicacion actualizada: ${aplicacionInstance.id}",
            disponible:abono.disponible,
            aplicado:abono.aplicado
        ]
        render data as JSON
    }
}
