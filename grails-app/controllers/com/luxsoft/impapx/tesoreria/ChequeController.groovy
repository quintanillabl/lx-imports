package com.luxsoft.impapx.tesoreria

import luxsoft.cfd.ImporteALetra
import grails.converters.JSON
import groovy.transform.ToString

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.Requisicion

class ChequeController {
	
	def jasperService
	def chequeService

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }
	
	def list() {
		
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
		def list=Cheque.list(params)
		[chequeInstanceList: list, chequeInstanceTotal: list.size(),periodo:periodo]
	}

    

    def create() {
		switch (request.method) {
		case 'GET':
			//def folio=Cheque
        	[chequeInstance: new Cheque(params)]
			break
		case 'POST':
			println 'Salvando cheque: '+params
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
	       // redirect action: 'impresionDeCheque', id: chequeInstance.id
			def importeALetra=ImporteALetra.aLetra(chequeInstance.egreso.importe.abs())
			render view:'impresionDeCheque',model: [chequeInstance: chequeInstance,importeALetra:importeALetra]
			//break
			/*
			def reportDef = JasperReportDef(name:chequeInstance.cueta.nombre+'formato',
				fileFormat:JasperExportFormat.PDF_FORMAT,
				parameters:[ID:cheuqInstance.id]
				)
			def res=jasperService.generateReport(reportDef)
			if (!reportDef.fileFormat.inline && !reportDef.parameters._inline) {
				response.setHeader("Content-disposition", "attachment filename=" + (reportDef.parameters._name ?: reportDef.name) + "." + reportDef.fileFormat.extension);
				response.contentType = reportDef.fileFormat.mimeTyp
				response.characterEncoding = "UTF-8"
				response.outputStream << reportDef.contentStream.toByteArray()
				redirect action: 'list'
				break
			} else {
				render(text: reportDef.contentStream, contentType: reportDef.fileFormat.mimeTyp, encoding: reportDef.parameters.encoding ? reportDef.parameters.encoding : 'UTF-8');
			}*/
	        
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

    def show() {
        def chequeInstance = Cheque.get(params.id)
        if (!chequeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cheque.label', default: 'Cheque'), params.id])
            redirect action: 'list'
            return
        }
		def importeALetra=ImporteALetra.aLetra(chequeInstance.egreso.importe.abs())
		def pago=PagoProveedor.findByEgreso(chequeInstance.egreso)

        [chequeInstance: chequeInstance,importeALetra:importeALetra,pago:pago]
    }

    

    def cancelar(CancelacionCommand c) {
		//println 'Cancelando cheque: '+params
		switch (request.method) {
			case 'GET':
			[cancelacionCommand:new CancelacionCommand(id:params.long('id'))]
			break
			case 'POST':
			c.fecha=new Date()
			//println 'Mandando a cancelar: '+c
			chequeService.cancelarCheque(c)
			redirect action:'show', params:params
		}
		
    }
	
	def pagosDisponiblesJSONList(){
		//println 'Pagos con cheque disponibles para: '+params
		//def id=params.long('term')
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
