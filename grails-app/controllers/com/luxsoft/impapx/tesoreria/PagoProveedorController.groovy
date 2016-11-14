package com.luxsoft.impapx.tesoreria

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.Requisicion;
import com.luxsoft.impapx.TipoDeCambio;
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class PagoProveedorController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'DELETE']
	
	def pagoProveedorService

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
    	params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        def periodo=session.periodoTesoreria
        def list=PagoProveedor.findAllByFechaBetween(periodo.inicioDeMes(),periodo.finDeMes(),params)
        def	count=PagoProveedor.countByFechaBetween(periodo.inicioDeMes(),periodo.finDeMes())
        [pagoProveedorInstanceList: list, pagoProveedorInstanceTotal: count]
    }

    def save(PagoProveedor pagoProveedorInstance){
    	log.info('Salvando pago proveedor con params: ' + params)
    	pagoProveedorInstance.validate(["requisicion","fecha","tipoDeCambio","comentario"])
        if (pagoProveedorInstance.hasErrors()) {
            render view:'create',model:[pagoProveedorInstance:pagoProveedorInstance]
            return
        }
		def res=pagoProveedorService.registrarEgreso(pagoProveedorInstance);
        if (!res) {
            render view: 'create', model: [pagoProveedorInstance: pagoProveedorInstance]
            return
        }
		flash.message = "Pago registrado: ${res.id}"
        redirect action: 'show', id: pagoProveedorInstance.id
    }
    

    def create() {
		
		switch (request.method) {
		case 'GET':
			
        	[pagoProveedorInstance: new PagoProveedor(fecha:new Date(),tipoDeCambio:1.0)]
			break
		case 'POST':
			log.info('Salvando pago proveedor con params: ' + params)
			def requisicion=Requisicion.get(params.requisicion.id)
	        def pagoProveedorInstance = new PagoProveedor(params)
			pagoProveedorInstance.requisicion=requisicion
			def res=pagoProveedorService.registrarEgreso(pagoProveedorInstance);
	        if (!res) {
	            render view: 'create', model: [pagoProveedorInstance: pagoProveedorInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), pagoProveedorInstance.id])
	        redirect action: 'show', id: pagoProveedorInstance.id
			break
		}
    }

    def show() {
        def pagoProveedorInstance = PagoProveedor.get(params.id)
        if (!pagoProveedorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
            redirect action: 'list'
            return
        }

        [pagoProveedorInstance: pagoProveedorInstance]
    }

    def edit() {
		
		switch (request.method) {
		case 'GET':
	        def pagoProveedorInstance = PagoProveedor.get(params.id)
	        if (!pagoProveedorInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [pagoProveedorInstance: pagoProveedorInstance]
			break
		case 'POST':
			
	        def pagoProveedorInstance = PagoProveedor.get(params.id)
	        if (!pagoProveedorInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (pagoProveedorInstance.version > version) {
	                pagoProveedorInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'pagoProveedor.label', default: 'PagoProveedor')] as Object[],
	                          "Another user has updated this PagoProveedor while you were editing")
	                render view: 'edit', model: [pagoProveedorInstance: pagoProveedorInstance]
	                return
	            }
	        }

	        pagoProveedorInstance.properties = params

	        if (!pagoProveedorInstance.save(flush: true)) {
	            render view: 'edit', model: [pagoProveedorInstance: pagoProveedorInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), pagoProveedorInstance.id])
	        redirect action: 'show', id: pagoProveedorInstance.id
			break
		}
    }

    def delete(PagoProveedor pagoProveedorInstance) {
        if (!pagoProveedorInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
            redirect action: 'index'
            return
        }
        pagoProveedorInstance.delete(flush: true)
		flash.message = message(code: 'default.deleted.message', args: [message(code: 'pagoProveedor.label', default: 'PagoProveedor'), params.id])
        redirect action: 'index'
        
    }
	
	def requisicionesDisponiblesJSONList(){
		
		def requisiciones=Requisicion.findAll("from Requisicion r left join fetch r.pagoProveedor pp where r.total>0  and str(r.id) like ? and pp is  null",['%'+params.term+'%'])
		def requisicionesList=requisiciones.collect { req ->
			def desc="Id: ${req.id} ${req.proveedor.nombre}  ${req.total} ${req.moneda}"
			[id:req.id,
			label:desc,
			value:desc,
            formaDePago:req.formaDePago.toString(),
            subCuentaOperativa: req.proveedor.subCuentaOperativa
			]
		}
		render requisicionesList as JSON
	}
}
