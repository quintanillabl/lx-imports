package com.luxsoft.impapx

import org.springframework.dao.DataIntegrityViolationException

class DistribucionDetController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [distribucionDetInstanceList: DistribucionDet.list(params), distribucionDetInstanceTotal: DistribucionDet.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[distribucionDetInstance: new DistribucionDet(params)]
			break
		case 'POST':
	        def distribucionDetInstance = new DistribucionDet(params)
	        if (!distribucionDetInstance.save(flush: true)) {
	            render view: 'create', model: [distribucionDetInstance: distribucionDetInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'distribucionDet.label', default: 'DistribucionDet'), distribucionDetInstance.id])
	        redirect action: 'show', id: distribucionDetInstance.id
			break
		}
    }

    

    def fraccionar() {
		
		switch (request.method) {
		case 'GET':
			def distribucionDetInstance = DistribucionDet.get(params.id)
			if (!distribucionDetInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribucionDet.label', default: 'DistribucionDet'), params.id])
				redirect action: 'list'
				return
			}

			[distribucionDetInstance: distribucionDetInstance]
			break
		case 'POST':
			println 'Fraccionando: '+params
	        def distribucionDetInstance = DistribucionDet.get(params.id)
	        if (!distribucionDetInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribucionDet.label', default: 'DistribucionDet'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (distribucionDetInstance.version > version) {
	                distribucionDetInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'distribucionDet.label', default: 'DistribucionDet')] as Object[],
	                          "Another user has updated this DistribucionDet while you were editing")
	                render view: 'edit', model: [distribucionDetInstance: distribucionDetInstance]
	                return
	            }
	        }	      
			
			//Validaciones
			
			def cantidad=new BigDecimal(params.cantidad)
			def tarimas=params.int('tarimas')
			def kilosNetos=new BigDecimal(params.kilosNetos)
			
			def distribucion=distribucionDetInstance.distribucion
			
			
			if(distribucionDetInstance.cantidad-cantidad<=0.00){
				flash.message=" Error cantidad debe ser menor a: "+distribucionDetInstance.cantidad
				redirect action: 'edit',controller:'distribucion', id: distribucion.id
			}
			
			distribucionDetInstance.cantidad-=cantidad
			distribucionDetInstance.tarimas-=tarimas
			distribucionDetInstance.kilosNetos-=kilosNetos
			distribucionDetInstance.cantidadPorTarima=distribucionDetInstance.cantidad/distribucionDetInstance.tarimas
			
			
			def target=new DistribucionDet();
			target.embarqueDet=distribucionDetInstance.embarqueDet
			target.contenedor=distribucionDetInstance.contenedor
			target.comentarios=distribucionDetInstance.comentarios
			target.cantidad=cantidad
			target.tarimas=tarimas
			target.kilosNetos=kilosNetos
			target.cantidadPorTarima=target.cantidad/target.tarimas
			target.sucursal=distribucionDetInstance.sucursal
			distribucion.addToPartidas(target)
			
			println 'preparando para salvar distribucion'

	        if (!distribucion.save(failOnError: true)) {
	            render view: 'edit',controller:'distribucion', model: [distribucionInstance: distribucion]
	            return
	        }

			println 'Distribucion actualizada: '+distribucion
			flash.message = "Partida fraccionada"
	        redirect action: 'edit',controller:'distribucion', id: distribucion.id, model:[distribucionInstance: distribucion]
			break
		}
    }
	
	def edit() {
		switch (request.method) {
		case 'GET':
			def distribucionDetInstance = DistribucionDet.get(params.id)
			if (!distribucionDetInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribucionDet.label', default: 'DistribucionDet'), params.id])
				redirect action: 'list'
				return
			}

			[distribucionDetInstance: distribucionDetInstance]
			break
		case 'POST':
			
			def distribucionDetInstance = DistribucionDet.get(params.id)
			if (!distribucionDetInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribucionDet.label', default: 'DistribucionDet'), params.id])
				redirect action: 'list'
				return
			}

			if (params.version) {
				def version = params.version.toLong()
				if (distribucionDetInstance.version > version) {
					distribucionDetInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
							  [message(code: 'distribucionDet.label', default: 'DistribucionDet')] as Object[],
							  "Another user has updated this DistribucionDet while you were editing")
					render view: 'edit', model: [distribucionDetInstance: distribucionDetInstance]
					return
				}
			}

			//distribucionDetInstance.sucursal = params.sucursal
			distribucionDetInstance.instrucciones=params.instrucciones
			
			if (!distribucionDetInstance.save(flush: true)) {
				render view: 'edit',controller:'distribucion', model: [distribucionInstance:distribucionDetInstance]
				return
			}

			flash.message = "Partida editada: "+distribucionDetInstance.contenedor
			redirect action: 'edit',controller:'distribucion', id: distribucionDetInstance.distribucion.id
			break
		}
	}

    def delete() {
        def distribucionDetInstance = DistribucionDet.get(params.id)
        if (!distribucionDetInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribucionDet.label', default: 'DistribucionDet'), params.id])
            redirect action: 'list'
            return
        }

        try {
            distribucionDetInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'distribucionDet.label', default: 'DistribucionDet'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'distribucionDet.label', default: 'DistribucionDet'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
