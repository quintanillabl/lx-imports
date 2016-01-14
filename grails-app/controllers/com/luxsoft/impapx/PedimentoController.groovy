package com.luxsoft.impapx

import grails.converters.JSON

import org.codehaus.groovy.grails.web.json.JSONArray;
import org.grails.datastore.mapping.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.utils.Periodo

@Secured(["hasRole('COMPRAS')"])
class PedimentoController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'DELETE']
	
	def pedimentoService

    def index(Integer max) {
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        def periodo=session.periodo
        def list=Pedimento.findAll("from Pedimento p where date(p.fecha) between ? and ? order by p.id desc ",[periodo.fechaInicial,periodo.fechaFinal])
        
       	[pedimentoInstanceList: list]
    }

    def create() {
    	[pedimentoInstance: new Pedimento(fecha:new Date())]
    }

    def save(Pedimento pedimentoInstance){
    	
    	pedimentoInstance.pedimento=pedimentoInstance.pedimento?.replace('-', '')
    	pedimentoInstance.validate()
    	if(pedimentoInstance.hasErrors()){
    		flash.message="Errores de validacion"
    		render view:'create',model:[pedimentoInstance:pedimentoInstance]
    		return
    	}
    	pedimentoInstance.save failOnError:true
    	flash.message="Pedimento ${pedimentoInstance.id} registrado"
    	redirect action:'edit', id:pedimentoInstance.id 
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

    def update(Pedimento pedimentoInstance){
    	if(pedimentoInstance.hasErrors()){
    		render view:'edit',model:[pedimentoInstance:pedimentoInstance]
    		return
    	}
    	pedimentoInstance.save failOnError:true,flush:true
    	flash.message="Pedimento ${pedimentoInstance.id} actualizado "
    	redirect action:'edit',id:pedimentoInstance.id
    }

    def delete() {
        def pedimentoInstance = Pedimento.get(params.id)
        if (!pedimentoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), params.id])
            redirect action: 'index'
            return
        }

        try {
            pedimentoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'pedimento.label', default: 'Pedimento'), params.id])
            redirect action: 'index'
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
		def pedimentoId=params.long('pedimentoId')
		def pedimento=pedimentoService.quitarEmbarques(pedimentoId,jsonArray)
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
