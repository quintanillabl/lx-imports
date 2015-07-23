package com.luxsoft.impapx

import org.springframework.dao.DataIntegrityViolationException

class ProveedorProductoController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [proveedorProductoInstanceList: ProveedorProducto.list(params), proveedorProductoInstanceTotal: ProveedorProducto.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[proveedorProductoInstance: new ProveedorProducto(params)]
			break
		case 'POST':
			println 'alta de producto'+ params
			Proveedor proveedor=Proveedor.get(params.proveedorId)
			params.proveedor=proveedor
			params.producto=Producto.findByClave(params.productoId)
	        def proveedorProductoInstance = new ProveedorProducto(params)
			proveedor.getProductos().add(proveedorProductoInstance)
	        if (!proveedorProductoInstance.save(flush: true)) {
	            render view: 'create', model: [proveedorProductoInstance: proveedorProductoInstance]
	            return
	        }

			//flash.message = message(code: 'default.created.message', args: [message(code: 'proveedorProducto.label', default: 'ProveedorProducto'), proveedorProductoInstance.id])
			flash.message="Último producto asignado: "+proveedorProductoInstance.producto.toString()
			println 'ProveedorProducto salvado: '+proveedorProductoInstance.id
	        //redirect action: 'show', id: proveedorProductoInstance.id
	        redirect controller:'proveedor',action:'edit',id:proveedor.id
			break
		}
    }

    def show() {
        def proveedorProductoInstance = ProveedorProducto.get(params.id)
        if (!proveedorProductoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'proveedorProducto.label', default: 'ProveedorProducto'), params.id])
            redirect action: 'list'
            return
        }

        [proveedorProductoInstance: proveedorProductoInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def proveedorProductoInstance = ProveedorProducto.get(params.id)
	        if (!proveedorProductoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'proveedorProducto.label', default: 'ProveedorProducto'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [proveedorProductoInstance: proveedorProductoInstance]
			break
		case 'POST':
			//println 'Actualizando producto asignado: '+params
	        def proveedorProductoInstance = ProveedorProducto.get(params.id)
			//println 'Localizado: '+proveedorProductoInstance.id
	        if (!proveedorProductoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'proveedorProducto.label', default: 'ProveedorProducto'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (proveedorProductoInstance.version > version) {
	                proveedorProductoInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'proveedorProducto.label', default: 'ProveedorProducto')] as Object[],
	                          "Another user has updated this ProveedorProducto while you were editing")
	                render view: 'edit', model: [proveedorProductoInstance: proveedorProductoInstance]
	                return
	            }
	        }

	        proveedorProductoInstance.properties = params

	        if (!proveedorProductoInstance.save(flush: true)) {
	            render view: 'edit', model: [proveedorProductoInstance: proveedorProductoInstance]
				
				//redirect controller:'proveedor', action:'edit',params:[proveedorInstance:proveedorProductoInstance.proveedor]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'proveedorProducto.label', default: 'ProveedorProducto'), proveedorProductoInstance.id])
	        //redirect action: 'show', id: proveedorProductoInstance.id
			redirect controller:'proveedor',action:'edit', id:proveedorProductoInstance.proveedor.id
			break
		}
    }

    def delete() {
        def proveedorProductoInstance = ProveedorProducto.get(params.id)
        if (!proveedorProductoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'proveedorProducto.label', default: 'ProveedorProducto'), params.id])
            redirect action: 'list'
            return
        }

        try {
            proveedorProductoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'proveedorProducto.label', default: 'ProveedorProducto'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'proveedorProducto.label', default: 'ProveedorProducto'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
