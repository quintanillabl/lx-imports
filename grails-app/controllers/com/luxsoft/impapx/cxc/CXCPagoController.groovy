package com.luxsoft.impapx.cxc

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.Venta
import util.MonedaUtils

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class CXCPagoController {
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def cobranzaService

	def filterPaneService

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
    	println request.class
        def periodo=session.periodoTesoreria
        def list=CXCPago.findAll(
        	"from CXCPago c where date(c.fecha) between ? and ?  order by c.id desc"
        	,[periodo.inicioDeMes(),periodo.finDeMes()])
        log.info 'Cobros registrados en el periodo '+list.size()
        [cobros: list]
    }
	
	def filter(){
		params.max = Math.min(params.max ? params.int('max') : 30, 50)
		render( view:'list',
			model:[CXCPagoInstanceList: filterPaneService.filter( params, CXCPago.class),
			CXCPagoInstanceTotal: filterPaneService.count( params, CXCPago.class ),
			filterParams: org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params),
			params:params ] )
	}

    def create() {
		switch (request.method) {
		case 'GET':
		
        	[CXCPagoInstance: new CXCPago(fecha:new Date()
				,fechaBancaria:new Date(),moneda:MonedaUtils.PESOS,tc:1,formaDePago:'TRANSFERENCIA')]
			break
		case 'POST':
			println 'Registrando pago: '+params
	        def CXCPagoInstance = new CXCPago(params)
			try {
				def res=cobranzaService.registrarPago(CXCPagoInstance)
				flash.message = message(code: 'default.created.message', args: [message(code: 'CXCPago.label', default: 'Pago'), res.id])
				redirect action: 'show', id: res.id
				break
			} catch (CobranzaEnPagoException e) {
				render view: 'create', model: [CXCPagoInstance: e.pago]
	            return
			}
			
		}
    }

    def save(CXCPago CXCPagoInstance){
    	if(CXCPagoInstance==null){
    		notFound()
    		return
    	}
    	if(!CXCPagoInstance.cuenta){
    		flash.error="Seleccione la cuenta destino"
    		render view: 'create', model: [CXCPagoInstance: CXCPagoInstance]
    		return
    	}
		try {
			def res=cobranzaService.registrarPago(CXCPagoInstance)
			flash.message = "Cobro registrado ${res.id}"
			redirect action: 'edit', id:res.id
			return
		} catch (CobranzaEnPagoException e) {
			flash.error=e.message
			render view: 'create', model: [CXCPagoInstance: e.pago]
            return
		}

    }

    def show(CXCPago CXCPagoInstance) {
        [CXCPagoInstance: CXCPagoInstance]
    }

    def edit(CXCPago CXCPagoInstance ) {
		[CXCPagoInstance: CXCPagoInstance]
    }

    def delete(CXCPago CXCPagoInstance) {
        CXCPagoInstance.delete(flush: true)
		flash.message = message(code: 'default.deleted.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), params.id])
        redirect action: 'index'
    }
	
	def pagosAsJSON(){
		
		def dataToRender = [:]
		dataToRender.sEcho = params.sEcho
		dataToRender.aaData=[]
		def list=CXCPago.list()
		dataToRender.iTotalRecords=list.size
		dataToRender.iTotalDisplayRecords = dataToRender.iTotalRecords
		list.each{ row ->
			dataToRender.aaData <<[
				row.id
				,row.cliente.nombre
				,lx.shortDate(date:row.fecha)
				,row.formaDePago
				,row.moneda
				,row.tc
				,lx.moneyFormat(number:row.total)
				,lx.moneyFormat(number:row.disponible)
				]
			
		}
		render dataToRender as JSON
	}
	
	def selectorDeFacturas(CXCPago pago){
		if(pago.disponible<=0.0){
			flash.message="Cobro sin disponible para aplicar"
			redirect action:'edit', id:pago.id
			return
		}
		def hql="from Venta p where p.cliente.id=?  and p.total-p.pagosAplicados>0 order by p.fecha desc"
		def res=Venta.findAll(hql,[pago.cliente.id])
		[pago:pago,facturas:res,facturasTotal:res.size()]
	}
	
	def generarAplicaciones(){
		//println 'Generando aplicaciones  para  facturas a requisicion'+params
		def dataToRender=[:]
		JSONArray jsonArray=JSON.parse(params.partidas);
		def pago= CXCPago.findById(params.pagoId,[fetch:[aplicaciones:'select']])
		cobranzaService.asignarFacturas(pago, jsonArray)
		
		dataToRender.pagoId=pago.id
		render dataToRender as JSON
	}
	
	def eliminarAplicaciones(){
		
		def dataToRender=[:]
		def pago= CXCPago.findById(params.pagoId,[fetch:[aplicaciones:'select']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		println 'Aplicaciones a eliminar: '+jsonArray
		cobranzaService.eliminarAplicaciones(pago,jsonArray)
		dataToRender.pagoId=pago.id
		render dataToRender as JSON
	}

	def mostrarIngreso(CXCPago cobro){
		println cobro
		render(template:'ingresoForm',bean:cobro.ingreso)
	}

	

	protected void notFound() {
	    request.withFormat {
	        form multipartForm {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), params.id])
	            redirect action: "index", method: "GET"
	        }
	        '*'{ render status: NOT_FOUND }
	    }
	}
	
	
}
