package com.luxsoft.impapx.tesoreria



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import util.MonedaUtils

@Secured(["hasRole('TESORERIA')"])
@Transactional(readOnly = true)
class TraspasoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def traspasoService

    def beforeInterceptor = {
    	if(!session.periodoTesoreria){
    		session.periodoTesoreria=new Date()
    	}
	}

	def cambiarPeriodo(){
		def fecha=params.date('fecha', 'dd/MM/yyyy')
		session.periodoTesoreria=fecha
		redirect(uri: request.getHeader('referer') )
	}

    def index(Integer max) {
		params.sort="fecha"
		params.order="desc"
		def periodo=session.periodoTesoreria
		def list=Traspaso.findAll("from Traspaso t where date(t.fecha) between ? and ?",
			[periodo.inicioDeMes(),periodo.finDeMes()],
			params)
        [traspasoInstanceList:list]
    }

    def show(Traspaso traspasoInstance) {
        respond traspasoInstance
    }

    def create() {
        respond new Traspaso(fecha:new Date())
    }

    @Transactional
    def save(Traspaso traspasoInstance) {
        if (traspasoInstance == null) {
            notFound()
            return
        }
        traspasoInstance.impuesto=0.0
        traspasoInstance.comision=0.0
        traspasoInstance.moneda=MonedaUtils.PESOS
        traspasoInstance.validate()
        
        if (traspasoInstance.hasErrors()) {
            respond traspasoInstance.errors, view:'create'
            return
        }
        
        traspasoInstance=traspasoService.generarTraspaso(traspasoInstance)
        flash.message="Traspaso ${traspasoInstance.id} generado"
        redirect traspasoInstance
        
    }

    def edit(Traspaso traspasoInstance) {
        respond traspasoInstance
    }

    @Transactional
    def update(Traspaso traspasoInstance) {
        if (traspasoInstance == null) {
            notFound()
            return
        }

        if (traspasoInstance.hasErrors()) {
            respond traspasoInstance.errors, view:'edit'
            return
        }

        traspasoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Traspaso.label', default: 'Traspaso'), traspasoInstance.id])
                redirect traspasoInstance
            }
            '*'{ respond traspasoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Traspaso traspasoInstance) {

        if (traspasoInstance == null) {
            notFound()
            return
        }

        traspasoService.deleteTraspaso(traspasoInstance)
        flash.message = "Traspaso ${traspasoInstance.id} eliminado"
        redirect action:"index", method:"GET"
        
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'traspaso.label', default: 'Traspaso'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
