package com.luxsoft.impapx.contabilidad



import grails.converters.JSON
import grails.validation.Validateable;
import groovy.transform.ToString;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException
import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.lx.contabilidad.PeriodoContable

@Secured(["hasRole('CONTABILIDAD')"])
class PolizaController {

    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",eliminarPartida:'GET']
	
	def polizaService

	def reportService

 

	def cambiarPeriodo(PeriodoContable periodoContable){
		log.info 'Cambiando periodo contable: '+periodoContable
		log.info ' Params: '+params
		session.periodoContable=periodoContable
		redirect(uri: request.getHeader('referer') )
	}

	def genericas() {
		def sort=params.sort?:'fecha'
		def order=params.order?:'desc'
		def periodo=session.periodoContable
		def polizas=Poliza.findAllByTipoAndFechaBetween('GENERICA',periodo.inicioDeMes(),periodo.finDeMes(),[sort:sort,order:order])
		
		render view:'index',model:[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
	}	
	
	def index() {
		// def sort=params.sort?:'fecha'
		// def order=params.order?:'desc'
		// def periodo=session.periodoContable
		// def polizas=Poliza.findAllByTipoAndFechaBetween('GENERICA',periodo.inicioDeMes(),periodo.finDeMes(),[sort:sort,order:order])
		// [polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
		def subTipo=params.subTipo?:'TODAS'
		def ejercicio=session.periodoContable.ejercicio
		def mes=session.periodoContable.mes
		
		def polizas=Poliza.where{
		    ejercicio==ejercicio &&
		    mes==mes
		}
		if(subTipo!='TODAS')
			polizas=polizas.where {subTipo==subTipo}
		/*
		def procesador = null
		if(subTipo!='TODAS'){
		    polizas=polizas.where {subTipo==subTipo}
		    procesador = ProcesadorDePoliza.findByNombre(subTipo)
		}
		*/

		def list=polizas.list(sort:'tipo',order:'asc')
		
		//respond list,model:[subTipo:subTipo,procesador:procesador]
		respond list,model:[subTipo:subTipo]
	}

    

    def create() {

    	respond new Poliza(tipo:params.tipo,subTipo:params.subTipo,manual:false)
		
    }

    
    def save(Poliza polizaInstance) {
        if (polizaInstance == null) {
            notFound()
            return
        }
        log.info 'Salvando: '+params
        if (polizaInstance.hasErrors()) {
            respond polizaInstance.errors, view:'create'
            return
        }
        //polizaInstance = polizaService.save polizaInstance
        polizaInstance = polizaService.salvarPoliza polizaInstance 

        flash.message = "Poliza ${polizaInstance.folio} generada"
        redirect action:'edit',id:polizaInstance.id
    }

    def edit(Poliza polizaInstance) {
    	
		respond polizaInstance
    }

    def update(Poliza polizaInstance){
    	if (polizaInstance == null) {
    	    notFound()
    	    return
    	}
    	if (polizaInstance.hasErrors()) {
    	    respond polizaInstance, view:'edit'
    	    return
    	}
    	polizaInstance = polizaService.save polizaInstance
    	flash.message="Poliza  ${polizaInstance.id} actualizada "
    	redirect action:'edit',id:polizaInstance.id
    }
	
	

    def show(Poliza polizaInstance) {
        respond polizaInstance
    }

    

    def delete(Poliza polizaInstance){
    	if (polizaInstance == null) {
    	    notFound()
    	    return
    	}
    	//polizaInstance = polizaService.delete polizaInstance
    	flash.message="Poliza  ${polizaInstance.id} eliminada "
    	redirect action:'index',params:[subTipo:polizaInstance.subTipo]
    }
	
	def agregarPartida(Poliza poliza){
		poliza = polizaService.agregarPartida(poliza,params)
		flash.message = "Partida agregada"
		redirect action:'edit',id:poliza.id
	}
	
	def eliminarPartida(PolizaDet det){
		if (det == null) {
		    notFound()
		    return
		}
		if (det.hasErrors()) {
		    respond det.poliza, view:'edit'
		    return
		}
		def poliza = polizaService.eliminarPartida(det)
		flash.message = "Partida eliminada: "+det.id
		redirect action:'edit',id:poliza.id
	}

	def print(Poliza poliza){
	    def command=reportService.buildCommand(session.empresa,'PolizaContable')
	    params.COMPANY=session.empresa.nombre
	    params.ID=poliza.id
	    def stream=reportService.build(command,params)
	    def file="Poliza_${poliza.subTipo}_${poliza.folio}.pdf"
	    render(
	        file: stream.toByteArray(), 
	        contentType: 'application/pdf',
	        fileName:file)
	}
	
	
	
	
}


