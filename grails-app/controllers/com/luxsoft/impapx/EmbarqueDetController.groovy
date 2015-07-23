package com.luxsoft.impapx

import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
class EmbarqueDetController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [embarqueDetInstanceList: EmbarqueDet.list(params), embarqueDetInstanceTotal: EmbarqueDet.count()]
    }

    def create() {
		//println 'Parametros para alta de EmbarqueDet '+params
		switch (request.method) {
		case 'GET':
			println 'Agregar partida: '+params+'' +request.method
        	[embarqueDetInstance: new EmbarqueDet(params),proveedorId:params.proveedorId]
			break
		case 'POST':
			//println 'Salvando embarque: '+params
			def compraDet=CompraDet.get(params.compraDetId)
			params.compraDet=compraDet
			params.producto=compraDet.producto

			//Actualizar lo atendido
	        def embarqueDetInstance = new EmbarqueDet(params)
			
			def embarque=Embarque.get(params.embarqueId)
			embarqueDetInstance.embarque=embarque
			embarque.partidas.add(embarqueDetInstance)
			
	        if (!embarqueDetInstance.save(flush: true)) {
	            render view: 'create', model: [embarqueDetInstance: embarqueDetInstance]
	            return
	        }
	        println 'Embarque salvado'
	        compraDet.entregado+=embarqueDetInstance.cantidad;
 			flash.message = "Ultimo registro : ${embarqueDetInstance.producto.clave} Cantidad: ${embarqueDetInstance.cantidad}"
	        //redirect controller:'embarque', action: 'edit', id: embarqueDetInstance.embarque.id
	        redirect action:'create',params:['embarque.id':embarque.id,proveedorId:embarque.proveedor.id]
			break
		}
    }

    def show() {
        def embarqueDetInstance = EmbarqueDet.get(params.id)
        if (!embarqueDetInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarqueDet.label', default: 'EmbarqueDet'), params.id])
            redirect action: 'list'
            return
        }

        [embarqueDetInstance: embarqueDetInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def embarqueDetInstance = EmbarqueDet.get(params.id)
	        if (!embarqueDetInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarqueDet.label', default: 'EmbarqueDet'), params.id])
	            redirect action: 'list'
	            return
	        }
			
			
			def pp=ProveedorProducto.findByProveedorAndProducto(embarqueDetInstance.embarque.proveedor,embarqueDetInstance.producto)
			println 'Encontro: '+pp?.id
			def kilosPorMillar=0
			if(pp){
				try{
					def gramos=pp.gramos
					def ancho=pp.producto.ancho
					def largo=pp.producto.largo
					
					def area=ancho*largo/10000
					kilosPorMillar=area*gramos
					def kilosPorMillarProd=pp.producto.kilos
					println "Gramos: $gramos Ancho: $ancho Largo: $largo Kilos calculados: $kilosPorMillar  Kilos Prod: $kilosPorMillarProd"
				}catch(Exception e){
				  log.error e
				}
				
			}
	        [embarqueDetInstance: embarqueDetInstance,kilosPorMillar:kilosPorMillar]
			break
		case 'POST':
			//println 'Actualizando embarqueDet: '+params
			def compraDet=CompraDet.get(params.compraDetId)
			params.compraDet=compraDet
			params.producto=compraDet.producto
	        def embarqueDetInstance = EmbarqueDet.get(params.id)
	        
			if (!embarqueDetInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarqueDet.label', default: 'EmbarqueDet'), params.id])
	            redirect action: 'edit',controller:'embarque',id:params.embarqueId
	            return
	        }
			
			def cantidadOriginal=embarqueDetInstance.cantidad
			

	        if (params.version) {
	            def version = params.version.toLong()
	            if (embarqueDetInstance.version > version) {
	                embarqueDetInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'embarqueDet.label', default: 'EmbarqueDet')] as Object[],
	                          "Another user has updated this EmbarqueDet while you were editing")
	                render view: 'edit', model: [embarqueDetInstance: embarqueDetInstance]
	                return
	            }
	        }

	        embarqueDetInstance.properties = params

	        if (!embarqueDetInstance.save(flush: true)) {
	            render view: 'edit',controller:'embarque',id:embarqueDetInstance.embarque.id, model: [embarqueDetInstance: embarqueDetInstance]
	            return
	        }
			
			def cantidad=embarqueDetInstance.cantidad
			compraDet.entregado-=cantidadOriginal
			compraDet.entregado+=cantidad
			

			flash.message = "Ultima modificacion ${embarqueDetInstance.producto} Cant: ${embarqueDetInstance.cantidad}"
	        redirect action: 'edit',controller:'embarque', id: embarqueDetInstance.embarque.id
			break
		}
    }

    def delete() {
        def embarqueDetInstance = EmbarqueDet.get(params.id)
        if (!embarqueDetInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarqueDet.label', default: 'EmbarqueDet'), params.id])
            redirect action: 'list'
            return
        }

        try {
            embarqueDetInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'embarqueDet.label', default: 'EmbarqueDet'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'embarqueDet.label', default: 'EmbarqueDet'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
