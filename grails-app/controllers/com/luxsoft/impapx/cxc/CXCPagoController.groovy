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
    	//params.max = Math.min(max ?: 40, 100)
        // params.sort=params.sort?:'lastUpdated'
        // params.order='desc'
        def periodo=session.periodoTesoreria
        def list=CXCPago.findAll("from CXCPago c where date(c.ingreso.fecha) between ? and ?  order by c.ingreso.fecha desc"
        	,[periodo.inicioDeMes(),periodo.finDeMes()]
        	)
        log.info 'Cobros registrados en el periodo '+list.size()
        [cobros: list ]
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

    def show() {
        def CXCPagoInstance = CXCPago.get(params.id)
        if (!CXCPagoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), params.id])
            redirect action: 'list'
            return
        }

        [CXCPagoInstance: CXCPagoInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def CXCPagoInstance = CXCPago.get(params.id)
	        if (!CXCPagoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [CXCPagoInstance: CXCPagoInstance]
			break
		case 'POST':
			
	        def CXCPagoInstance = CXCPago.get(params.id)
	        if (!CXCPagoInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (CXCPagoInstance.version > version) {
	                CXCPagoInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'CXCPago.label', default: 'CXCPago')] as Object[],
	                          "Another user has updated this CXCPago while you were editing")
	                render view: 'edit', model: [CXCPagoInstance: CXCPagoInstance]
	                return
	            }
	        }

	        CXCPagoInstance.properties = params

	        if (!CXCPagoInstance.save(flush: true)) {
	            render view: 'edit', model: [CXCPagoInstance: CXCPagoInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), CXCPagoInstance.id])
	        redirect action: 'show', id: CXCPagoInstance.id
			break
		}
    }

    def delete() {
        def CXCPagoInstance = CXCPago.get(params.id)
        if (!CXCPagoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), params.id])
            redirect action: 'list'
            return
        }

        try {
            CXCPagoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'CXCPago.label', default: 'CXCPago'), params.id])
            redirect action: 'show', id: params.id
        }
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
	
	def selectorDeFacturas(long pagoId){
		def pago=CXCPago.get(pagoId)
		
		def hql="from Venta p where p.cliente.id=?  and p.total-p.pagosAplicados>0 "
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
	
	
}
