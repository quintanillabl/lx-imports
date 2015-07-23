package com.luxsoft.impapx.cxp

import grails.converters.JSON

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.FacturaDeGastos;

class CuentaDeGastosGenericaController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def cuentaDeGastosGenericaService

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
		params.sort='id'
		params.order='desc'
        params.max = Math.min(params.max ? params.int('max') : 50, 100)
        [rows: CuentaDeGastosGenerica.list(params)]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[cuentaDeGastosGenericaInstance: new CuentaDeGastosGenerica(params)]
			break
		case 'POST':
	        def cuentaDeGastosGenericaInstance = new CuentaDeGastosGenerica(params)
			cuentaDeGastosGenericaInstance.facturas=[]
	        if (!cuentaDeGastosGenericaInstance.save(flush: true)) {
	            render view: 'create', model: [cuentaDeGastosGenericaInstance: cuentaDeGastosGenericaInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica'), cuentaDeGastosGenericaInstance.id])
	        redirect action: 'edit', id: cuentaDeGastosGenericaInstance.id
			break
		}
    }

    def show() {
        def cuentaDeGastosGenericaInstance = CuentaDeGastosGenerica.get(params.id)
        if (!cuentaDeGastosGenericaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica'), params.id])
            redirect action: 'list'
            return
        }

        [cuentaDeGastosGenericaInstance: cuentaDeGastosGenericaInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def cuentaDeGastosGenericaInstance = CuentaDeGastosGenerica.get(params.id)
	        if (!cuentaDeGastosGenericaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [cuentaDeGastosGenericaInstance: cuentaDeGastosGenericaInstance]
			break
		case 'POST':
	        def cuentaDeGastosGenericaInstance = CuentaDeGastosGenerica.get(params.id)
	        if (!cuentaDeGastosGenericaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (cuentaDeGastosGenericaInstance.version > version) {
	                cuentaDeGastosGenericaInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica')] as Object[],
	                          "Another user has updated this CuentaDeGastosGenerica while you were editing")
	                render view: 'edit', model: [cuentaDeGastosGenericaInstance: cuentaDeGastosGenericaInstance]
	                return
	            }
	        }

	        cuentaDeGastosGenericaInstance.properties = params

	        if (!cuentaDeGastosGenericaInstance.save(flush: true)) {
	            render view: 'edit', model: [cuentaDeGastosGenericaInstance: cuentaDeGastosGenericaInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica'), cuentaDeGastosGenericaInstance.id])
	        redirect action: 'list'
			break
		}
    }

    def delete() {
        def cuentaDeGastosGenericaInstance = CuentaDeGastosGenerica.get(params.id)
        if (!cuentaDeGastosGenericaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica'), params.id])
            redirect action: 'list'
            return
        }

        try {
            cuentaDeGastosGenericaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def facturasAsJSON(){
		
		def dataToRender = [:]
		dataToRender.sEcho = params.sEcho
		dataToRender.aaData=[]
		
		def cuenta=CuentaDeGastosGenerica.findById(params.long('cuentaDeGastosId'),[fetch:[facturas:'select']])
		def list=cuenta.facturas
		dataToRender.iTotalRecords=list.size()
		dataToRender.iTotalDisplayRecords = dataToRender.iTotalRecords
		list.each{ row ->
			
			dataToRender.aaData <<[
				row.id
				,row.documento
				,lx.shortDate(date:row.fecha)
				,row.proveedor.nombre
				,lx.moneyFormat(number:row.importe*row.tc)
				,lx.moneyFormat(number:row.impuestos*row.tc)
				,lx.moneyFormat(number:row.retImp)
				,lx.moneyFormat(number:row.retensionIsr)
				,lx.moneyFormat(number:row.total*row.tc)
				,lx.moneyFormat(number:row.descuento)
				,lx.moneyFormat(number:row.rembolso)
				,lx.moneyFormat(number:row.otros)
				]
			
		}
		render dataToRender as JSON
	}
	
	def facturasDisponiblesPorAsignarJSON(){
		println params
		def key=params.term?.toLowerCase()+'%'
		def facturas=FacturaDeGastos.findAll("\
			from FacturaDeGastos fac \
			where fac.cuentaGenerica is null \
			and (lower(fac.documento) like ?  or lower(fac.proveedor.nombre) like ?)\
			order by fac.documento desc" 
			,[key,key],[max: 50])
		
		def res=facturas.collect{row ->
			def total=lx.moneyFormat(number:row.total)
			def label="Fac: ${row.documento}  F: ${row.fecha.format('dd/MM/yyyy')} T: ${total} Prov:${row.proveedor.nombre}"
			[id:row.id,label:label,value:label,documento:row.documento]
		}
		render res as JSON
		
	}
	
	def agregarFactura(){
		def dataToRender=[:]
		def res=cuentaDeGastosGenericaService.agregarFactura(params.long('cuentaDeGastosId'), params.long('facturaId'))
		dataToRender.importe=res.importe
		println dataToRender
		//render dataToRender as JSON
		render dataToRender as JSON
	}
	
	def eliminarFacturas(){
		//println 'Eliminando facturas: '+params
		JSONArray jsonArray=JSON.parse(params.partidas);
		def data=[:]
		def cuenta=cuentaDeGastosGenericaService.eliminarFacturas(params.cuentaDeGastosId,jsonArray)
		data.total=cuenta.total
		println 'Cuenta actualizada: '+cuenta.facturas.size()
		/*
		try {
			def cuenta=cuentaDeGastosGenericaService.eliminarFacturas(params.cuentaDeGastosId,jsonArray)
			data.total=cuenta.total
			
		} catch (Exception e) {
			e.printStackTrace()
			log.error(e)
			data.error=ExceptionUtils.getRootCauseMessage(e)
		}*/
		
		//render data as JSON
		render cuenta as JSON
	}
}
