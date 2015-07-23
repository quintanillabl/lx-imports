package com.luxsoft.impapx.tesoreria

import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.CuentaBancaria;
import com.luxsoft.impapx.cxc.CXCPago;
import com.luxsoft.impapx.cxp.Anticipo;

class MovimientoDeCuentaController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
		println 'Movimientos de cuentas..'+params
		if(!session.periodo){
			session.periodo=new Date()
		}
		if(params.periodo){
			session.periodo=Date.parse('dd/MM/yyyy',params.periodo)
		}
        params.max = 1000
		params.sort='id'
		params.order='desc'
		def periodo=session.periodo
		def list=MovimientoDeCuenta.findAllByFechaBetween(periodo.inicioDeMes(),periodo.finDeMes(),params)
        [movimientoDeCuentaInstanceList: list, movimientoDeCuentaInstanceTotal: list.size(),periodo:periodo]
    }
	
	def cobros() {
		params.max = Math.min(params.max ? params.int('max') : 100, 1000)
		[CXCPagoInstanceList: CXCPago.list(params), CXCPagoInstanceTotal: CXCPago.count()]
	}
	

    def create() {
		switch (request.method) {
		case 'GET':
			println 'Alta de movimiento :'+params
        	[movimientoDeCuentaInstance: new MovimientoDeCuenta(params),conceptos:params.conceptos]
			break
		case 'POST':
			println 'Generando movimiento: '+params
			
	        def movimientoDeCuentaInstance = new MovimientoDeCuenta(params)
			def cuenta=CuentaBancaria.get(params.cuenta.id)
			movimientoDeCuentaInstance.cuenta=cuenta
			movimientoDeCuentaInstance.moneda=cuenta.moneda
			movimientoDeCuentaInstance.origen='TESORERIA'
			movimientoDeCuentaInstance.tipo='TRANSFERENCIA'
			if(movimientoDeCuentaInstance.ingreso){
				movimientoDeCuentaInstance.importe=movimientoDeCuentaInstance.importe.abs()
			}else{
				movimientoDeCuentaInstance.importe=movimientoDeCuentaInstance.importe.abs()*-1
			}
			movimientoDeCuentaInstance.concepto="$movimientoDeCuentaInstance.concepto $movimientoDeCuentaInstance.comentario"
			
	        if (!movimientoDeCuentaInstance.save(flush: true)) {
	            render view: 'create', model: [movimientoDeCuentaInstance: movimientoDeCuentaInstance]
	            return
	        }
			
			if(params.anticipoId){
				def anticipo=Anticipo.get(params.long('anticipoId'))
				anticipo.sobrante=movimientoDeCuentaInstance
				anticipo.save()
			}

			flash.message = message(code: 'default.created.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), movimientoDeCuentaInstance.id])
	        redirect action: 'show', id: movimientoDeCuentaInstance.id
			break
		}
    }

    def show() {
        def movimientoDeCuentaInstance = MovimientoDeCuenta.get(params.id)
        if (!movimientoDeCuentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
            redirect action: 'list'
            return
        }

        [movimientoDeCuentaInstance: movimientoDeCuentaInstance]
    }

    def edit() {
		/*
		switch (request.method) {
		case 'GET':
		
	        def movimientoDeCuentaInstance = MovimientoDeCuenta.get(params.id)
	        if (!movimientoDeCuentaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
	            redirect action: 'list'
	            return
	        }
			if(movimientoDeCuentaInstance.ingreso)
				params.conceptos=Conceptos.INGRESOS
			else
				params.conceptos=Conceptos.EGRESOS
	        [movimientoDeCuentaInstance: movimientoDeCuentaInstance,conceptos:params.conceptos]
			break
		case 'POST':
			println 'Actualizando movimiento: '+params
	        def movimientoDeCuentaInstance = MovimientoDeCuenta.get(params.id)
	        if (!movimientoDeCuentaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (movimientoDeCuentaInstance.version > version) {
	                movimientoDeCuentaInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta')] as Object[],
	                          "Another user has updated this MovimientoDeCuenta while you were editing")
	                render view: 'edit', model: [movimientoDeCuentaInstance: movimientoDeCuentaInstance]
	                return
	            }
	        }
			bindData(movimientoDeCuentaInstance,params,[exclude:['origen','tipo']])
	       
	        if (!movimientoDeCuentaInstance.save(flush: true)) {
	            render view: 'edit', model: [movimientoDeCuentaInstance: movimientoDeCuentaInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), movimientoDeCuentaInstance.id])
	        redirect action: 'show', id: movimientoDeCuentaInstance.id
			break
		}
		 */
		redirect action:'show',model:params
    }

    def delete() {
        def movimientoDeCuentaInstance = MovimientoDeCuenta.get(params.id)
        if (!movimientoDeCuentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
            redirect action: 'list'
            return
        }

        try {
            movimientoDeCuentaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
            redirect action: 'show', id: params.id
        }
       
    }
	
	def depositar(){
		redirect(action:'create'
			,params:[origen:'TESORERIA',tc:'1.00',ingreso:true,conceptos:Conceptos.INGRESOS,anticipoId:params.anticipoId])
	}
	
	def retirar(){
		redirect(action:'create'
			,params:[origen:'TESORERIA',tc:'1.00',ingreso:false,conceptos:Conceptos.EGRESOS])
	}
}
