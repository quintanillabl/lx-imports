package com.luxsoft.nomina



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import com.luxsoft.cfdi.Cfdi

@Secured(["hasAnyRole('COMPRAS','ADMIN')"])
@Transactional(readOnly = true)
class NominaAsimiladoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    
    def nominaAsimiladoService

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond NominaAsimilado.list(params), model:[nominaAsimiladoInstanceCount: NominaAsimilado.count()]
    }

    def show(NominaAsimilado nominaAsimiladoInstance) {
        respond nominaAsimiladoInstance
    }

    def create() {
        params.fecha = new Date()
        params.pago = new Date()
        respond new NominaAsimilado(params)
    }

    @Transactional
    def save(NominaAsimilado nominaAsimiladoInstance) {
        if (nominaAsimiladoInstance == null) {
            notFound()
            return
        }

        if (nominaAsimiladoInstance.hasErrors()) {
            respond nominaAsimiladoInstance.errors, view:'create'
            return
        }


        nominaAsimiladoInstance = nominaAsimiladoService.save(nominaAsimiladoInstance)
        flash.message = message(code: 'default.created.message', args: [message(code: 'nominaAsimilado.label', default: 'NominaAsimilado'), nominaAsimiladoInstance.id])
        redirect nominaAsimiladoInstance
    }

    def edit(NominaAsimilado nominaAsimiladoInstance) {
        respond nominaAsimiladoInstance
    }

    @Transactional
    def update(NominaAsimilado nominaAsimiladoInstance) {
        if (nominaAsimiladoInstance == null) {
            notFound()
            return
        }

        if (nominaAsimiladoInstance.hasErrors()) {
            respond nominaAsimiladoInstance.errors, view:'edit'
            return
        }

        nominaAsimiladoInstance = nominaAsimiladoService.save(nominaAsimiladoInstance)

        flash.message = message(code: 'default.updated.message', args: [message(code: 'NominaAsimilado.label', default: 'NominaAsimilado'), nominaAsimiladoInstance.id])
        redirect nominaAsimiladoInstance
        
    }

    @Transactional
    def delete(NominaAsimilado nominaAsimiladoInstance) {

        if (nominaAsimiladoInstance == null) {
            notFound()
            return
        }

        nominaAsimiladoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'NominaAsimilado.label', default: 'NominaAsimilado'), nominaAsimiladoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def generarCfdi(NominaAsimilado ne) {
        def cfdi = nominaAsimiladoService.generarCfdi(ne)
        flash.message = "Cfdi ${cfdi.id} generado para nomina por empleado: ${ne.id} "
        redirect action:'show', id: ne.id
    }

    def mostrarXml(Cfdi cfdi){
        render(text: cfdi.comprobanteDocument.xmlText(), contentType: "text/xml", encoding: "UTF-8")
    }
    
    @Transactional
    def timbrar(NominaAsimilado ne){
        nominaAsimiladoService.timbrar(ne)
        flash.message=" Nomina de asimilads para  ${ne.asimilado} timbrara exitosamente"
        redirect action:'show', id: ne.id
    }

    def descargarXml(Cfdi cfdi){
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=${cfdi.serie}_${cfdi.folio}_${cfdi.receptor}")
        response.outputStream << cfdi.xml
        return
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'nominaAsimilado.label', default: 'NominaAsimilado'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
