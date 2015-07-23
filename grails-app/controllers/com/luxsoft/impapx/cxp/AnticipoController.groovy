package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.contabilidad.PeriodoContable
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta;

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class AnticipoController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET','POST'], delete: 'POST']
	
	def anticipoService;

    def index() {
		
        redirect action: 'list', params: params
    }

    def list() {
		if(!session.periodoContable){
			println 'Asignando periodo contable:..'
			PeriodoContable periodo=new PeriodoContable()
			periodo.actualizarConFecha()
			session.periodoContable=periodo
		}
        //params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def periodo=session.periodoContable
		def sort=params.sort?:'fecha'
		def order=params.order?:'desc'
		def anticipos=Anticipo.findAllByFechaBetween(periodo.inicio,periodo.fin,[sort:sort,order:order])
        [anticipos: anticipos, anticiposTotal: anticipos.size(),periodo:periodo]
    }

    def show() {
        def anticipoInstance = Anticipo.get(params.id)
        if (!anticipoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), params.id])
            redirect action: 'list'
            return
        }

        [anticipoInstance: anticipoInstance]
    }

    def edit() {
		
		switch (request.method) {
			case 'GET':
				def anticipoInstance = Anticipo.get(params.id)
				println 'Editando anticipo: '+anticipoInstance
				if (!anticipoInstance) {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), params.id])
					redirect action: 'list'
					return
				}
				[anticipoInstance: anticipoInstance]
				break
			
			case 'POST':
				def anticipoInstance = Anticipo.get(params.id)
				if (!anticipoInstance) {
					flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), params.id])
					redirect action: 'list'
					return
				}
				if (params.version) {
					def version = params.version.toLong()
					if (anticipoInstance.version > version) {
						anticipoInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
									 [message(code: 'anticipo.label', default: 'Anticipo')] as Object[],
									 "Another user has updated this Anticipo while you were editing")
						render view: 'edit', model: [anticipoInstance: anticipoInstance]
						return
					}
				}
				println 'Actualizando anticipo: '+params	
				//anticipoInstance.properties = params
				anticipoInstance=anticipoService.actualizarAnticipo(anticipoInstance)
				if (anticipoInstance.hasErrors()) {
					render view: 'edit', model: [anticipoInstance: anticipoInstance]
					return
				}
		
				flash.message = message(code: 'default.updated.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), anticipoInstance.id])
				redirect action: 'show', id: anticipoInstance.id
				break
			}
		
    }

    def delete() {
        def anticipoInstance = Anticipo.get(params.id)
        if (!anticipoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), params.id])
            redirect action: 'list'
            return
        }

        try {
            anticipoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def embarquesDisponiblesJSONList(){
		//println 'Embarques disponibles para anticipo: '+params
		def embarques=Embarque.findAll(
			"from Embarque e where  e.id not in (select x.embarque.id from Anticipo x ) and str(e.id) like ?"
			,[params.term+'%'])
		def embarquesList=embarques.collect { row ->
			def label=row.toString()
			[id:row.id,label:label,value:label]
		}
		render embarquesList as JSON
	}
	
	def depositosDeTesoreriaDisponiblesJSONList(){
		//println 'Embarques disponibles para anticipo: '+params
		def depositos=MovimientoDeCuenta.findAll(
			"from MovimientoDeCuenta m where  m.tipo='DEPOSITO' and m.id not in (select x.sobrante.id from Anticipo x ) and str(m.id) like ?"
			,[params.term+'%'])
		def depositosList=depositos.collect { row ->
			def label=row.toString()
			[id:row.id,label:label,value:label]
		}
		render depositosList as JSON
	}
	
	def disponiblesJSONList(){
		//println 'Embarques disponibles para anticipo: '+params
		def anticipos=Anticipo.findAll(
			"from Anticipo a where  a.sobrante is null and a.requisicion.concepto=? and a.total-a.requisicion.total>0"
			,['ANTICIPO'])
		def anticiposList=anticipos.collect { row ->
			def label=" ${row.id}  Req: ${row.requisicion} Saldo: ${row.total-row.requisicion.total}"
			[id:row.id,label:label,value:label]
		}
		render anticiposList as JSON
	}
}
