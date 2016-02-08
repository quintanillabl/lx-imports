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

    static allowedMethods = [create: 'GET',save:'POST', edit: 'GET', cancelar: 'POST']

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
		params.sort="id"
		params.order="desc"
		def periodo=session.periodoTesoreria
		def list=Cheque.findAll("from Cheque c where date(c.fechaImpresion) between ? and ? order by c.fechaImpresion desc",
			[periodo.inicioDeMes(),periodo.finDeMes()],
			params)
		//def list = Cheque.list(params)
        [chequeInstanceList:list]
    }

    def save(MovimientoDeCuenta egreso){
    	log.info 'Generando cheque para egreso: '+egreso
        def chequeInstance = chequeService.generarCheque(egreso)
		flash.message = 'Cheque generado: '+chequeInstance.folio
		redirect action:'show',id:chequeInstance.id
    }
	

    def create() {
    	respond new Cheque(fechaImpresion: new Date())
    }
	
	def cancelar(Cheque  chequeInstance){
		if (!chequeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cheque.label', default: 'Cheque'), params.id])
			redirect action: 'index'
			return
		}
		def comentario = params.comentario?:''
		chequeInstance = chequeService.cancelarCheque(chequeInstance,comentario)
		flash.message = "Cheque ${chequeInstance.folio} cancelado"
		redirect action:'index'
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
	Cheque cheque
	Date fecha=new Date()
	String comentario
}
