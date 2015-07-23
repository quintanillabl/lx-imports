package com.luxsoft.impapx

import com.luxsoft.impapx.cxp.Anticipo
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

import util.MonedaUtils;

class RequisicionController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def requisicionService

    def index() {
        redirect controller:'cuentaPorPagar', action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 100, 200)
		params.sort=params.sort?:'id'
		params.order=params.order?:'desc'
        [requisicionInstanceList: Requisicion.list(params), requisicionInstanceTotal: Requisicion.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[requisicionInstance: new Requisicion(fecha:new Date(),fechaDelPago:new Date()+1,moneda:MonedaUtils.DOLARES,tc:1)]
			break
		case 'POST':
			println 'Persistiendo requisicon: '+params
	        def requisicionInstance = new Requisicion(params)
			
			if(requisicionInstance.concepto.startsWith('ANTICIPO')){
				def embarque=Embarque.get(params.long('embarque.id'))
				requisicionService.generarAnticipo(requisicionInstance, embarque)
				
				flash.message = message(code: 'default.created.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), requisicionInstance.id])
				redirect action: 'edit', id: requisicionInstance.id
				break
				
			}
	        if (!requisicionInstance.save(flush: true)) {
	            render view: 'create', model: [requisicionInstance: requisicionInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), requisicionInstance.id])
	        redirect action: 'edit', id: requisicionInstance.id
			break
		}
    }

    def show() {
        def requisicionInstance = Requisicion.get(params.id)
        if (!requisicionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
            redirect action: 'list'
            return
        }

        [requisicionInstance: requisicionInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def requisicionInstance = Requisicion.findById(params.id,[fetch:[partidas:'select']])
	        if (!requisicionInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [requisicionInstance: requisicionInstance]
			break
		case 'POST':
			def requisicionInstance = Requisicion.findById(params.id,[fetch:[partidas:'select']])
	        if (!requisicionInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (requisicionInstance.version > version) {
	                requisicionInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'requisicion.label', default: 'Requisicion')] as Object[],
	                          "Another user has updated this Requisicion while you were editing")
	                render view: 'edit', model: [requisicionInstance: requisicionInstance]
	                return
	            }
	        }

			//println 'Actualizando requisicion: '+params
	        requisicionInstance.properties = params
			requisicionInstance.actualizarImportes()
			
	        if (!requisicionInstance.save(flush: true)) {
	            render view: 'edit', model: [requisicionInstance: requisicionInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), requisicionInstance.id])
	        redirect action: 'show', id: requisicionInstance.id
			break
		}
    }

    def delete() {
        def requisicionInstance = Requisicion.get(params.id)
        if (!requisicionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
            redirect action: 'list'
            return
        }

        try {
            requisicionInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def selectorDeFacturas(long requisicionId){
		def requisicion=Requisicion.get(requisicionId)
		//def res=CuentaPorPagar.findAllByProveedorAndMonedaAndSaldoGreaterThan(requisicion.proveedor,requisicion.moneda,0.0)
		//def hql="from CuentaPorPagar p where p.proveedor=?  and p.saldo-p.requisitado>0 and moneda=?"
		def hql="from CuentaPorPagar p where p.proveedor=?   and p.total-p.requisitado>0 and moneda=?"
		def res=CuentaPorPagar.findAll(hql,[requisicion.proveedor,requisicion.moneda])
		
		[requisicionInstance:requisicion,cuentaPorPagarInstanceList:res,cuentasPorPagarTotal:res.size()]
	}
	
	def asignarFacturas(){
		println 'Asignando facturas a requisicion'+params
		def dataToRender=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		def requisicion= Requisicion.findById(params.requisicionId,[fetch:[partidas:'select']])
		requisicionService.asignarFacturas(requisicion, jsonArray)
		
		dataToRender.requisicionId=requisicion.id
		render dataToRender as JSON
	}
	 
	def eliminarPartidas(){
		
		def dataToRender=[:]
		def requisicion= Requisicion.findById(params.requisicionId,[fetch:[partidas:'select']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		requisicionService.eliminarPartidas(requisicion,jsonArray)
		dataToRender.requisicionId=requisicion.id
		render dataToRender as JSON
	}
	
	/**
	 * @TODO Verificar si se sigue ocupando
	 * @param requisicionId
	 * @return
	 */
	def registrarFactura(long requisicionId){
		println ' Agregando requisicion a factura'+params
		def requisicionInstance = Requisicion.get(params.requisicionId)
        if (!requisicionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
            redirect action: 'list'
            return
        }
		[requisicionInstance: requisicionInstance]

	}
	
	/**
	 * @TODO Verificar si se sigue ocupando
	 * @return
	 */
	def facturasDisponiblesJSON(){
		println 'Obteniento json data: '+params
		//Usamos Where Queries http://grails.org/doc/latest/guide/GORM.html#whereQueries
		def facturas=CuentaPorPagar.findAll(sort:"fecha"){
			proveedor.id==params.long('proveedorId') && documento=~params.term+'%'
		}
		println 'Facturas localizadas: '+facturas.size
		def facturasList=facturas.collect {
			[id:it.documento,label:it.toString(),value:it.toString(),facturaId:it.id]
		}
		render facturasList as JSON
	}
	
	def anticipos() {
		params.max = Math.min(params.max ? params.int('max') : 50, 100)
		[requisicionInstanceList: Requisicion.findAllByConceptoLike('ANTICIPO%',params)
			, requisicionInstanceTotal: Requisicion.countByConceptoLike('ANTICIPO%')]
	}
	
	def createAnticipo(){
		switch (request.method) {
		case 'GET':
        	[requisicionInstance: new Requisicion(fecha:new Date(),fechaDelPago:new Date()+1,concepto:'ANTICIPO')]
			break
		case 'POST':
	        def requisicionInstance = new Requisicion(params)
			requisicionInstance=requisicionService.generarAnticipo(requisicionInstance)
	        if (!requisicionInstance) {
	            render view: 'createAnticipo', model: [requisicionInstance: requisicionInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), requisicionInstance.id])
	        redirect action: 'showAnticipo', id: requisicionInstance.id
			break
		}
	}
	
	def editAnticipo() {
		switch (request.method) {
		case 'GET':
			def requisicionInstance = Requisicion.findById(params.id,[fetch:[partidas:'select']])
			if (!requisicionInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
				redirect action: 'list'
				return
			}

			[requisicionInstance: requisicionInstance]
			break
		case 'POST':
			def requisicionInstance = Requisicion.findById(params.id,[fetch:[partidas:'select']])
			if (!requisicionInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
				redirect action: 'list'
				return
			}

			if (params.version) {
				def version = params.version.toLong()
				if (requisicionInstance.version > version) {
					requisicionInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
							  [message(code: 'requisicion.label', default: 'Requisicion')] as Object[],
							  "Another user has updated this Requisicion while you were editing")
					render view: 'edit', model: [requisicionInstance: requisicionInstance]
					return
				}
			}
			requisicionInstance.properties = params
			if (!requisicionInstance.save(flush: true)) {
				render view: 'edit', model: [requisicionInstance: requisicionInstance]
				return
			}
			flash.message = message(code: 'default.updated.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), requisicionInstance.id])
			redirect action: 'show', id: requisicionInstance.id
			break
		}
	}
	
	
	
	def showAnticipo() {
		def requisicionInstance = Requisicion.get(params.id)
		if (!requisicionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
			redirect action: 'list'
			return
		}

		[requisicionInstance: requisicionInstance]
	}
}
