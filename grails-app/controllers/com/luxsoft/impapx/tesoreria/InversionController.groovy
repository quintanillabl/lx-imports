package com.luxsoft.impapx.tesoreria

import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.CuentaBancaria;

class InversionController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def inversionService

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.sort="fecha"
		params.order="desc"
        [inversionInstanceList: Inversion.list(params), inversionInstanceTotal: Inversion.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
			def cuenta=CuentaBancaria.get(params.cuentaOrigen.id)
			def res=MovimientoDeCuenta.executeQuery("select sum(m.importe) from MovimientoDeCuenta m where m.cuenta=? and date(m.fecha)<=?"
				,[cuenta,new Date().clearTime()])
			
			println 'Saldo :'+res
			
			def disponible=res[0]?:0.0
			if(disponible<=0.0){
				flash.message="No hay disponible en la cuenta $cuenta Su saldo es: $disponible"
				redirect action:'list'
				return
			}
			
			def inversionInstance=new Inversion(cuentaOrigen:cuenta,cuentaDestino:cuenta
				,fecha:new Date()
				,tasa:cuenta.tasaDeInversion
				,tasaIsr:cuenta.tasaIsr
				,plazo:cuenta.plazo
				,importe:disponible)
        	[inversionInstance: inversionInstance]
			break
		case 'POST':
			println 'Procesando inversion: '+params
	        def inversionInstance = new Inversion(params)
			
			inversionInstance=inversionService.generarInversion(inversionInstance)
	        if(inversionInstance.hasErrors()){
				render view: 'create', model: [inversionInstance: inversionInstance]
				return
			}

			flash.message = message(code: 'default.created.message', args: [message(code: 'inversion.label', default: 'Inversion'), inversionInstance.id])
	        redirect action: 'show', id: inversionInstance.id
			break
		}
    }

    def show() {
        def inversionInstance = Inversion.findById(params.id,[fetch:[movimientos:'select']])
        if (!inversionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'inversion.label', default: 'Inversion'), params.id])
            redirect action: 'list'
            return
        }
        [inversionInstance: inversionInstance,movimientos:inversionInstance.movimientos]
    }

    

    def delete() {
		
        def inversionInstance = Inversion.findById(params.id,[fetch:[movimientos:'eager']])
        if (!inversionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'inversion.label', default: 'Inversion'), params.id])
            redirect action: 'list'
            return
        }
        try {
			inversionService.eliminarInversion(inversionInstance)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'inversion.label', default: 'Inversion'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'inversion.label', default: 'Inversion'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
