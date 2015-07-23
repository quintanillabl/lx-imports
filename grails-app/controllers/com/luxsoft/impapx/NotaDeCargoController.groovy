package com.luxsoft.impapx

class NotaDeCargoController {
	
	def ventaService

    def index() {
		redirect action: 'list', params: params
	}
	
	def list(){
		params.max = Math.min(params.max ? params.int('max') : 50, 500)
		params.sort='id'
		params.order= "desc"		
		[ventaInstanceList: Venta.findAllByTipo('NOTA_DE_CARGO',params), ventaInstanceTotal: Venta.countByTipo('NOTA_DE_CARGO')]
	}
	
	def create() {
		switch (request.method) {
		case 'GET':
		println 'Creando venta con: '+params
			params.tc=1.0
			def venta=new Venta(params)
			venta.fecha=new Date()
			venta.cuentaDePago="0000"
			
			[ventaInstance:venta]
			break
		case 'POST':
			
			//params.cliente=Cliente.get(params.cliente)
			params.vencimiento=new Date()
			params.kilos=0
			params.tipo='NOTA_DE_CARGO'
			def ventaInstance = new Venta(params)
			if (!ventaInstance.save(flush: true)) {
				render view: 'create', model: [ventaInstance: ventaInstance]
				return
			}

			flash.message = "Nota de cargo generada: $ventaInstance.id"
			redirect action: 'list', id: ventaInstance.id
			break
		}
	}
	
	def facturar(long id){
		try {
			def res=ventaService.facturar(id)
			//render view:"factura",model:[ventaInstance:res['venta']]
			//def ventaInstance=res['venta']
			//redirect(controller:"venta",action:"facturar",params:[ventaInstance:ventaInstance])
			redirect (action:'list')
		} catch (VentaException e) {
			flash.message=e.message
			render view: 'edit', model: [ventaInstance: e.venta]
		}
		
		
	}
	
	def edit() {
		switch (request.method) {
		case 'GET':
			
			def ventaInstance=Venta.findById(params.id,[fetch:[partidas:'select']])
			if (!ventaInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
				redirect action: 'list'
				return
			}

			[ventaInstance: ventaInstance,partidas:ventaInstance.partidas]
			break
		case 'POST':
			def ventaInstance = Venta.findById(params.id,[fetch:[partidas:'select']])
			if (!ventaInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
				redirect action: 'list'
				return
			}

			if (params.version) {
				def version = params.version.toLong()
				if (ventaInstance.version > version) {
					ventaInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
							  [message(code: 'venta.label', default: 'Venta')] as Object[],
							  "Another user has updated this Venta while you were editing")
					render view: 'edit', model: [ventaInstance: ventaInstance]
					return
				}
			}
			//no se permiten cambios de cliente
			bindData(ventaInstance,params,[exclude:'cliente'])
			//ventaInstance.cuentaDePago=ventaInstance.cliente.cuentaDePago?:'0000'
			ventaInstance.subtotal=ventaInstance.importe	
			//ventaInstance.properties = params

			if (!ventaInstance.save(flush: true)) {
				render view: 'edit', model: [ventaInstance: ventaInstance]
				return
			}

			flash.message = message(code: 'default.updated.message', args: [message(code: 'venta.label', default: 'Venta'), ventaInstance.id])
			redirect action: 'show', id: ventaInstance.id
			break
		}
	}
	
	def show() {
		def ventaInstance = Venta.get(params.id)
		if (!ventaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
			redirect action: 'list'
			return
		}

		[ventaInstance: ventaInstance]
	}
	
	
}
