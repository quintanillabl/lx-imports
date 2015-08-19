package com.luxsoft.impapx.tesoreria

import com.luxsoft.cfdi.ImporteALetra
import grails.converters.JSON
import groovy.transform.ToString

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.Requisicion

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class ChequeController {
	
	def jasperService

	def chequeService

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

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
		params.sort="fecha"
		params.order="desc"
		def periodo=session.periodoTesoreria
		def list=Cheque.findAll("from Cheque c where date(c.fechaImpresion) between ? and ? order by c.fechaImpresion desc",
			[periodo.inicioDeMes(),periodo.finDeMes()],
			params)
        [chequeInstanceList:list]
    }

    def save(MovimientoDeCuenta egreso){
    	log.info 'Generando cheque para egreso: '+egreso
        def chequeInstance = new Cheque(params)
		chequeInstance.fechaImpresion=new Date()
		chequeInstance.egreso=pago
		chequeInstance.cuenta=pago.cuenta
		pago.referenciaBancaria=chequeInstance.folio?.toString()
        if (!chequeInstance.save(flush: true)) {
            render view: 'create', model: [chequeInstance: chequeInstance]
            return
        }
		flash.message = 'Cheque impreso: '+chequeInstance.id
		def importeALetra=ImporteALetra.aLetra(chequeInstance.egreso.importe.abs())
		redirect action:'show',id:chequeInstance.id
		//render view:'impresionDeCheque',model: [chequeInstance: chequeInstance,importeALetra:importeALetra]
    }
	

    def create() {
		switch (request.method) {
		case 'GET':
        	[chequeInstance: new Cheque(params)]
			break
		case 'POST':
			log.info 'Salvando cheque: '+params
			def pago=MovimientoDeCuenta.get(params.egreso.id)
	        def chequeInstance = new Cheque(params)
			chequeInstance.fechaImpresion=new Date()
			chequeInstance.egreso=pago
			chequeInstance.cuenta=pago.cuenta
			pago.referenciaBancaria=chequeInstance.folio?.toString()
	        if (!chequeInstance.save(flush: true)) {
	            render view: 'create', model: [chequeInstance: chequeInstance]
	            return
	        }
			flash.message = 'Cheque impreso: '+chequeInstance.id
			def importeALetra=ImporteALetra.aLetra(chequeInstance.egreso.importe.abs())
			render view:'impresionDeCheque',model: [chequeInstance: chequeInstance,importeALetra:importeALetra]
			
		}
    }
	
	def delete(){
		def chequeInstance = Cheque.get(params.id)
		if (!chequeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cheque.label', default: 'Cheque'), params.id])
			redirect action: 'list'
			return
		}
		try {
			chequeInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'cheque.label', default: 'Cheque '), params.id])
			redirect action: 'list'
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cheque.label', default: 'Cheque'), params.id])
			redirect action: 'show', id: params.id
		}
	
	}

    def show(Cheque chequeInstance) {
        if (!chequeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cheque.label', default: 'Cheque'), params.id])
            redirect action: 'index'
            return
        }
		def importeALetra=ImporteALetra.aLetra(chequeInstance.egreso.importe.abs())
		def pago=PagoProveedor.findByEgreso(chequeInstance.egreso)
        [chequeInstance: chequeInstance,importeALetra:importeALetra,pago:pago]
    }

    

    def cancelar(CancelacionCommand c) {
		switch (request.method) {
			case 'GET':
			[cancelacionCommand:new CancelacionCommand(id:params.long('id'))]
			break
			case 'POST':
			c.fecha=new Date()
			chequeService.cancelarCheque(c)
			redirect action:'show', params:params
		}
		
    }
	
	def pagosDisponiblesJSONList(){
		def pagos=MovimientoDeCuenta.findAll("from MovimientoDeCuenta p where p.tipo='CHEQUE' and p not in(select x.egreso from Cheque x)")
		
		def pagosList=pagos.collect { pag ->
			def importe=lx.moneyFormat(number:pag.importe.abs())
			def desc="Id: ${pag.id} ${pag.cuenta.nombre} ${pag.cuenta.numero}  Importe: ${importe}"
			[id:pag.id,label:desc,value:desc]
		}
		render pagosList as JSON
	}
	
}

@ToString
class CancelacionCommand{
	long id
	Date fecha=new Date()
	String comentario
}
