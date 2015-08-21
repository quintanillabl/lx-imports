package com.luxsoft.impapx

import grails.converters.JSON

import org.codehaus.groovy.grails.web.json.JSONArray;
import org.grails.datastore.mapping.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.utils.Periodo

@Secured(["hasRole('COMPRAS')"])
class PedimentoController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def pedimentoService

	def beforeInterceptor = {
	    if(!session.periodo){
	        def d1=new Date()-30
	        session.periodo=new Periodo(d1.inicioDeMes(),new Date())
	    }
	}

	def cambiarPeriodo(Periodo periodo){
	    session.periodo=periodo
	    redirect(uri: request.getHeader('referer') )
	}
	
	def list() {
        forward action: 'index', params: params
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        respond Pedimento.list(params), model:[pedimentoInstanceCount: Embarque.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[pedimentoInstance: new Pedimento(params)]
			break
		case 'POST':
			println 'Alta de pedimento: '+params
			params.pedimento=params.pedimento?.replace('-', '')
	        def pedimentoInstance = new Pedimento(params)
	        if (!pedimentoInstance.save(flush: true)) {
	            render view: 'create', model: [pedimentoInstance: pedimentoInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), pedimentoInstance.id])
	        redirect action: 'edit', id: pedimentoInstance.id
			break
		}
    }

    def show() {
        def pedimentoInstance = Pedimento.get(params.id)
        if (!pedimentoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), params.id])
            redirect action: 'list'
            return
        }

        [pedimentoInstance: pedimentoInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def pedimentoInstance = Pedimento.get(params.id)
	        if (!pedimentoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), params.id])
	            redirect action: 'list'
	            return
	        }
			def incrementables=pedimentoInstance.embarques.sum(0.0,{it.incrementables})
	        [pedimentoInstance: pedimentoInstance,incrementables:incrementables]
			break
		case 'POST':
	        def pedimentoInstance = Pedimento.get(params.id)
	        if (!pedimentoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (pedimentoInstance.version > version) {
	                pedimentoInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'pedimento.label', default: 'Pedimento')] as Object[],
	                          "Another user has updated this Pedimento while you were editing")
	                render view: 'edit', model: [pedimentoInstance: pedimentoInstance]
	                return
	            }
	        }

	        pedimentoInstance.properties = params

	        if (!pedimentoInstance.save(flush: true)) {
	            render view: 'edit', model: [pedimentoInstance: pedimentoInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), pedimentoInstance.id])
	        redirect action: 'edit', id: pedimentoInstance.id
			break
		}
    }

    def delete() {
        def pedimentoInstance = Pedimento.get(params.id)
        if (!pedimentoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), params.id])
            redirect action: 'list'
            return
        }

        try {
            pedimentoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def contenedoresPendientes(){
		
		def contenedores=EmbarqueDet.executeQuery("\
			select distinct(d.contenedor) from EmbarqueDet d \
			where d.factura!=null \
			  and d.pedimento is null\
			  and d.contenedor like ?",[params.term+'%'])
		
		def res=contenedores.collect{ row ->
			[id:row,label:row,value:row]
		}
		render res as JSON
	}
	
	def agregarEmbarquesPorContenedor(long pedimentoId,String contenedor){
		
		try {
			def pedimento=pedimentoService.agregarEmbarquesPorContenedor(pedimentoId, contenedor)
			render template:"embarquesGrid",model:[pedimentoInstance:pedimento]
		} catch (PedimentoException e) {
			flash.gridMessage=e.message
			render template:"embarquesGrid",model:[pedimentoInstance:e.pedimento]
		}
	}
	
	/**
	 * TODO Urgente pasar la logica de este metodo al pedimentoService
	 * 
	 * @return
	 */
	def eliminarAsignacionDeEmbarques(){
		
		JSONArray jsonArray=JSON.parse(params.partidas);
		def pedimento=Pedimento.findById(params.long('pedimentoId'),[fetch:[embarques:'select']])
		jsonArray.each{
			def id=it.toLong()
			def embarqueDet=pedimento.embarques.find{ det ->
				det.id==id
			}
			if(embarqueDet){
				pedimento.removeFromEmbarques(embarqueDet)
				embarqueDet.pedimento=null
				embarqueDet.gastosPorPedimento=0
			}
		}
		pedimento.actualizarCostos()
		pedimento.actualizarImpuestos()
		render(template:'embarquesGrid',model:[pedimentoInstance:pedimento])
	}
	
	
	def embarquesAsJSON(){
		
		def dataToRender = [:]
		dataToRender.sEcho = params.sEcho
		dataToRender.aaData=[]
		def list=EmbarqueDet.findAll("from EmbarqueDet d where pedimento.id=?",[params.long('pedimentoId')])
		
		dataToRender.iTotalRecords=list.size
		dataToRender.iTotalDisplayRecords = dataToRender.iTotalRecords
		list.each{ row ->
			
			dataToRender.aaData <<[
				row.id
				,row.embarque.id
				,row.embarque.bl
				,row.contenedor
				,row.producto.clave
				,row.producto.descripcion
				,lx.millaresFormat(number:row.cantidad)
				,lx.kilosFormat(number:row.kilosNetos)
				,lx.kilosFormat(number:row.kilosEstimados)
				,lx.moneyFormat(number:row.gastosPorPedimento)
				]
			
		}
		render dataToRender as JSON
	}

	def search(){
	    def term='%'+params.term.trim()+'%'
	    def query=Pedimento.where{
	        (pedimento=~term || proveedor.nombre=~term || comentario=~term) 
	    }
	    def pedimentos=query.list(max:30, sort:"id",order:'desc')

	    def pedimentoList=pedimentos.collect { pedimento ->
	        def label="Id:${pedimento.id} ${pedimento.proveedor.nombre} Ped:${pedimento.pedimento} ${pedimento.fecha.format('dd/MM/yyyy')} Imp: ${pedimento.impuesto}"
	        [id:pedimento.id,label:label,value:pedimento.id]
	    }
	    render pedimentoList as JSON
	}

}
