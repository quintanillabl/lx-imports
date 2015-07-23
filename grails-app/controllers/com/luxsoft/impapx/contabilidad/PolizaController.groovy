package com.luxsoft.impapx.contabilidad



import grails.converters.JSON
import grails.validation.Validateable;
import groovy.transform.ToString;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

class PolizaController {

    static allowedMethods = [create: ['POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def polizaService

    def index() {
       if(!session.periodoContable){
		   println 'Asignando periodo contable:..'
		   PeriodoContable periodo=new PeriodoContable()
		   periodo.actualizarConFecha()
		   session.periodoContable=periodo
	   }
    }
	
	def actualizarPeriodo(PeriodoContable periodo){
		//println 'Actualizando periodo...'+params
		//def periodo=new PeriodoContable()
		//periodo.properties=params
		periodo.actualizarConFecha()
		session.periodoContable=periodo
		redirect action:'index'
		//println periodo
	}

    def list() {
		if(!session.periodoContable){
			PeriodoContable periodo=new PeriodoContable()
			periodo.actualizarConFecha()
			session.periodoContable=periodo
		}
		PeriodoContable periodo=session.periodoContable
		def sort=params.sort?:'fecha'
		def order=params.order?:'desc'
		
		def polizas=Poliza.findAllByTipoAndFechaBetween('GENERICA',periodo.inicio,periodo.fin,[sort:sort,order:order])
		[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
	}

    def create() {
		switch (request.method) {
		case 'GET':
			params.tipo='GENERICA'
			params.fecha=new Date()
        	[polizaInstance: new Poliza(params)]
			break
		case 'POST':
			params.tipo='GENERICA'
	        def polizaInstance = new Poliza(params)
			polizaInstance=polizaService.crearPolizaGenerica(polizaInstance)
	        if (polizaInstance.hasErrors()) {
	            render view: 'create', model: [poliza: polizaInstance]
				//flash.message="Poliza generica con errores no se puede salvar..."
				//redirect action:'list' model
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'poliza.label', default: 'Poliza'), polizaInstance.id])
	        redirect action: 'edit', id: polizaInstance.id
			break
		}
    }
	
	def mostrarPoliza(long id){
		def poliza=Poliza.findById(id,[fetch:[partidas:'eager']])
		render (view:'/poliza/poliza2' ,model:[poliza:poliza,partidas:poliza.partidas])
	}

    def show() {
        def polizaInstance = Poliza.get(params.id)
        if (!polizaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'poliza.label', default: 'Poliza'), params.id])
            redirect action: 'list'
            return
        }

        [polizaInstance: polizaInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def polizaInstance = Poliza.get(params.id)
	        if (!polizaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poliza.label', default: 'Poliza'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [poliza: polizaInstance,partidas:polizaInstance.partidas]
			break
		case 'POST':
	        def polizaInstance = Poliza.get(params.id)
	        if (!polizaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'poliza.label', default: 'Poliza'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (polizaInstance.version > version) {
	                polizaInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'poliza.label', default: 'Poliza')] as Object[],
	                          "Another user has updated this Poliza while you were editing")
	                render view: 'edit', model: [polizaInstance: polizaInstance]
	                return
	            }
	        }

	        polizaInstance.properties = params

	        if (!polizaInstance.save(flush: true)) {
	            render view: 'edit', model: [polizaInstance: polizaInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'poliza.label', default: 'Poliza'), polizaInstance.id])
	        redirect action: 'show', id: polizaInstance.id
			break
		}
    }

    def delete() {
        def polizaInstance = Poliza.get(params.id)
        if (!polizaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'poliza.label', default: 'Poliza'), params.id])
            redirect action: 'list'
            return
        }

        try {
            polizaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'poliza.label', default: 'Poliza'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'poliza.label', default: 'Poliza'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def agregarPartida(long id){
		println 'Agregando partida: '+params
		def poliza=Poliza.get(id)
		def cuenta=CuentaContable.get(params.cuenta.id)
		poliza.addToPartidas(cuenta:cuenta,
			debe:params.debe,
			haber:params.haber,
			asiento:params.asiento,
			descripcion:params.descripcion,
			referencia:params.referencia
			,fecha:poliza.fecha
			,tipo:poliza.tipo)
		//poliza.cuadrar()
		//poliza.save(flush:true)
		//def poliza=polizaService.agregarPartida(id,params)
		render view:'edit', model:[poliza:poliza,partidas:poliza.partidas]
	}
	
	def eliminarPartidas(){
		//println 'Eliminando partidas de poliza: '+params
		def data=[:]
		def poliza = Poliza.findById(params.polizaId,[fetch:[partidas:'eager']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		try {
			polizaService.eliminarPartidas(poliza,jsonArray)
			data.res='APLICACIONES_ELIMINADAS'
		}
		catch (RuntimeException e) {
			e.printStackTrace()
			//println 'Error: '+e
			data.res="ERROR"
			data.error=ExceptionUtils.getRootCauseMessage(e)
		}
		render data as JSON
	}
	
	def editPartida(long id) {
		println 'Editando partida de poliza'
		switch (request.method) {
		case 'GET':
	        def polizaDet = PolizaDet.get(id)
	        

	        [poliza: polizaDet.poliza,polizaDet:polizaDet]
			break
		case 'POST':
	        def polizaDet = PolizaDet.get(id)
			if(!polizaDet) throw new RuntimeException("No existe la partida: "+id)
	        if (params.version) {
	            def version = params.version.toLong()
	            if (polizaDet.version > version) {
	                polizaDet.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'polizaDet.label', default: 'Poliza Det')] as Object[],
	                          "Another user has updated this Poliza while you were editing")
	                render view: 'edit', model: [polizaInstance: polizaDet.poliza]
	                return
	            }
	        }
	        polizaDet.properties = params
			def poliza=polizaDet.poliza
			poliza.actualizarImportes()
			//poliza=polizaService.salvarPoliza(poliza)
	        if (!poliza.save(failOnError:true)) {
	            render view: 'editPartida', model: [polizaDet:polizaDet,poliza:poliza]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'polizaDet.label', default: 'Poliza Det')
				, poliza.id])
	        redirect action: 'edit', id: poliza.id
			break
		}
    }
	
	
}

@Validateable
@ToString
class PeriodoContable{
	int year
	int month
	Date currentDate=new Date()
	
	String toString(){
		return "$year - $month "
	}
	
	def actualizarConFecha(){
		year=currentDate.toYear();
		month=currentDate.toMonth();
	}
	
	static constraints = {
		//year(nullable:false,inList:[2012..2018])
		//mes(nullable:false,inList:[1..12])
	}
	
	Date getInicio(){
		return currentDate.inicioDeMes();
	}
	Date getFin(){
		return currentDate.finDeMes();
	}
	
}
