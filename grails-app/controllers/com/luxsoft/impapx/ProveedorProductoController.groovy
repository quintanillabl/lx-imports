package com.luxsoft.impapx

import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
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
			Proveedor proveedor=Proveedor.get(params.proveedorId)
			params.proveedor=proveedor
			params.producto=Producto.findByClave(params.productoId)
	        def proveedorProductoInstance = new ProveedorProducto(params)
			proveedor.getProductos().add(proveedorProductoInstance)
	        if (!proveedorProductoInstance.save(flush: true)) {
	            render view: 'create', model: [proveedorProductoInstance: proveedorProductoInstance]
	            return
	        }
			flash.message="Último producto asignado: "+proveedorProductoInstance.producto.toString()
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
	        log.info 'Producto por proveedor: '+proveedorProductoInstance
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

    def update(ProveedorProducto proveedorProductoInstance){
    	
    	log.info 'Actualizando proveedorProducto: '+proveedorProductoInstance.id
    	log.info 'Errores: '+proveedorProductoInstance.hasErrors()
    	log.info proveedorProductoInstance.errors
    	// if(proveedorProductoInstance==null){
    	// 	notFound()
    	// 	return
    	// }
    	if(proveedorProductoInstance.hasErrors()){
    		redirect action:'edit',model:[proveedorProductoInstance:proveedorProductoInstance]
    		return
    	}
    	proveedorProductoInstance.save flush:true,failOnError:true
    	flash.message="Producto por proveedor ${proveedorProductoInstance.id} actualizado"
    	redirect controller:'proveedor',action:'edit',id:proveedorProductoInstance.proveedor.id
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

    // protected void notFound() {
    //     request.withFormat {
    //         form multipartForm {
    //             flash.message = "No localizo el producto por proveedor "
    //             redirect action: "index", method: "GET"
    //         }
    //         '*'{ render status: NOT_FOUND }
    //     }
    // }
}
