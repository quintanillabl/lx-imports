package com.luxsoft.impapx


import luxsoft.cfd.ImporteALetra
import grails.converters.JSON
import grails.validation.ValidationException
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException
import com.luxsoft.cfdi.Cfdi


class VentaController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def ventaService
	def printService

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 50, 500)
		params.sort='id'
		params.order= "desc"
        [ventaInstanceList: Venta.findAllByTipo('VENTA',params), ventaInstanceTotal: Venta.countByTipo('VENTA')]
    }

    def create() {
		switch (request.method) {
		case 'GET':
		println 'Creando venta con: '+params
			params.tc=1.0
			def venta=new Venta(params)
			venta.fecha=new Date()
			venta.cuentaDePago="0000"
			venta.clase=params.clase
        	[ventaInstance:venta]
			break
		case 'POST':
			println 'Generando venta: '+params
			//params.cliente=Cliente.get(params.cliente)
			params.vencimiento=new Date()
			params.importe=0
			params.impuestos=0
			params.descuentos=0
			params.subtotal=0
			params.total=0
			params.kilos=0
	        def ventaInstance = new Venta(params)
	        if (!ventaInstance.save(flush: true)) {
	            render view: 'create', model: [ventaInstance: ventaInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'venta.label', default: 'Venta'), ventaInstance.id])
	        redirect action: 'edit', id: ventaInstance.id
			break
		}
    }

    def show() {
        def ventaInstance = Venta.get(params.id)
        if (!ventaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
            redirect action: 'list'
            return
        }
		def cfdi=Cfdi.findBySerieAndOrigen('FAC',params.id)
		if(cfdi?.comentario?.contains("CANCELADO"))
			cfdi=null
        [ventaInstance: ventaInstance,cfdi:cfdi]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
			
			def ventaInstance=Venta.findById(params.id,[fetch:[partidas:'select']])
	        if (!ventaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
	            redirect action: 'list'
	            return
	        }
			//def cfdi=Cfdi.findByOrigen(params.id.toString())
			def cfdi=Cfdi.findBySerieAndOrigen('FAC',params.id)
			//println 'Cfdi localizado: '+cfdi
	        [ventaInstance: ventaInstance,partidas:ventaInstance.partidas,cfdi:cfdi]
			break
		case 'POST':
	        def ventaInstance = Venta.findById(params.id,[fetch:[partidas:'select']])
	        if (!ventaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (ventaInstance.version > version) {
	                ventaInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'venta.label', default: 'Venta')] as Object[],
	                          "Another user has updated this Venta while you were editing")
	                render view: 'edit', model: [ventaInstance: ventaInstance]
	                return
	            }
	        }
			//no se permiten cambios de cliente
			bindData(ventaInstance,params,[exclude:'cliente'])
			ventaInstance.cuentaDePago=ventaInstance.cliente.cuentaDePago
				
	        //ventaInstance.properties = params

	        if (!ventaInstance.save(flush: true)) {
	            render view: 'edit', model: [ventaInstance: ventaInstance]
	            return
	        } 

			flash.message = message(code: 'default.updated.message', args: [message(code: 'venta.label', default: 'Venta'), ventaInstance.id])
	        redirect action: 'show', id: ventaInstance.id
			break
		}
    } 

    def delete() {
        def ventaInstance = Venta.get(params.id)
		
        if (!ventaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
            redirect action: 'list'
            return
        }
		if(ventaInstance.cfdi){
			flash.message ="Venta facturada imposible eliminar"
			redirect action: 'list'
			return
		}

        try {
            ventaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def contenedoresPendientes(){
		
		//println 'Buscando contenedores pendientes...'+params
		/*
		def contenedores=EmbarqueDet.executeQuery("\
			select distinct(d.contenedor) from DistribucionDet d \
			where d.embarqueDet.precioDeVenta>0 \
			  and d.embarqueDet not in(select x.embarque.id from VentaDet x )")
			  */
		def contenedores=DistribucionDet.executeQuery("\
			select distinct(d.contenedor) from EmbarqueDet d \
			 where d.precioDeVenta>=0\
			 and upper(d.contenedor) like ?\
			 and d.pedimento!=null \
			 and d not in(select x.embarque from VentaDet x )\
			 order by d.contenedor ",[params.term+'%'],[max: 10])
		//println 'Contenedores registrados: '+contenedores.size()
		def res=contenedores.collect{ row ->
			[id:row,label:row,value:row]
		}
		render res as JSON
	}
	
	def agregarContenedor(long ventaId,String contenedor){
		try {
			def venta=ventaService.agregarContenedor(ventaId, contenedor)
			venta=Venta.findById(venta.id,[fetch:[partidas:'select']])
			//render template:"partidas",model:[partidas:venta.partidas]
			render template:"partidas",model:[partidas:venta.partidas,ventaInstance:venta]
		} catch (VentaException e) {
			flash.gridMessage=e.message
			def partidas=VentaDet.findAll("from VentaDet d where d.venta.id=?",[e.venta.id])
			render template:"partidas",model:[ventaInstance:e.venta,partidas:partidas]
		} 
		
		
	}
	
	def eliminarPartidas(){
		
		JSONArray jsonArray=JSON.parse(params.partidas);
		def venta=null
		jsonArray.each {
			println 'Eliminando partida: '+it
			def det=VentaDet.get(it.toLong())
			if(venta==null)
				venta=det.venta
			def contenedor=det.embarque.contenedor
				
			println 'Eliminando ventas asignadas con contenedor: '+contenedor
			def partidasPorContenedor=venta.partidas.findAll { ventaDet->
				ventaDet.embarque.contenedor==contenedor
			}
			println 'Partidas de la venta: '+venta.partidas.size()
			println 'Partidas encontradas con contenedor: '+partidasPorContenedor.size()
			partidasPorContenedor.each {ventaDet->
				venta.removeFromPartidas(ventaDet)
			}
			println 'Partidas de la venta after delete: '+venta.partidas.size()
			
		}
		venta.save(flush:true)
		render "OK"
	}
	
	def facturar(long id){
		try {
			def res=ventaService.facturar(id)
			render view:"factura",model:[ventaInstance:res['venta']]
		} catch (VentaException e) {
			flash.message=e.message
			render view: 'edit', model: [ventaInstance: e.venta]
		}
		
		
	}
	
	def showFactura(long id){
		def cfd=ComprobanteFiscal.get(id)
		if (!cfd) {
			flash.message = 'No se encontro el CFD con folio: '+id
			redirect action: 'list'
			return
		}
		def ventaInstance = Venta.findById(cfd.origen,[fetch:[partidas:'select']])
		render view:"factura",model:[ventaInstance:ventaInstance,cfd:cfd]
	}
	
	def imprimirCfd(){
		//println 'Generando CFD: '+params
		def venta=Venta.get(params.ID)
		def cfd=venta.getCfd().comprobante
		def conceptos=venta.getCfd().getComprobante().getConceptos().getConceptoArray()
		
		def modelData=conceptos.collect { cc ->
			//Concepto cc=(Concepto)c
			def res=[
			'cantidad':cc.getCantidad()
			 ,'NoIdentificacion':cc.getNoIdentificacion()
			 ,'descripcion':cc.getDescripcion()
			 ,'unidad':cc.getUnidad()
			 ,'ValorUnitario':cc.getValorUnitario()
			 ,'Importe':cc.getImporte()
			 //,'PEDIMENTO_FECHA':cc.informacionAduaneraArray[0]?.fecha.getTime()
			 //,'PEDIMENTO':cc.informacionAduaneraArray[0]?.numero
			 //,'ADUANA':cc.informacionAduaneraArray[0]?.aduana
			 ]
			if(cc.informacionAduaneraArray){
				res.PEDIMENTO_FECHA=cc.informacionAduaneraArray[0]?.fecha.getTime()
				res.PEDIMENTO=cc.informacionAduaneraArray[0]?.numero
				res.ADUANA=cc.informacionAduaneraArray[0]?.aduana
			}
			return res
		}
		//def repParams=CFDIPrintServices.resolverParametros(venta.getCfd().getComprobante())
		def repParams=printService.resolverParametros(venta.getCfd().getComprobante())
		params<<repParams
		params.FECHA=cfd.fecha.getTime().format("yyyy-MM-dd'T'HH:mm:ss")
		chain(controller:'jasper',action:'index',model:[data:modelData],params:params)
		
	}
	
	def cancelar() {
		println 'Cancelando venta: '+params
		def ventaInstance=Venta.findById(params.id,[fetch:[partidas:'eager']])
		
		if (!ventaInstance) { 
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
			redirect action: 'list'
			return
		}
		try {
			def comentario=params.comentario
			println 'Cancelando venta: '+comentario
			
			def venta=ventaService.cancelarCargo(ventaInstance,comentario)
			flash.message = "Cargo cancelado ${venta.id}"
			redirect action: 'list'
		}
		catch (DataIntegrityViolationException e) {
			flash.message = "Imposible cancelar cargo"
			redirect action: 'show', id: params.id
		}
	}
	
	def refacturar(long id){
		ventaService.refacturar(id)
	}
	
	def agregarConcepto(long id){
		def venta=Venta.findById(id,[fetch:[partidas:'eager']])
		switch (request.method) {
			case 'GET': 
				def ventaDet=new VentaDet()
				[ventaInstance:venta,ventaDetInstance:ventaDet]
				break
			case 'POST':
				println 'Agrgando partida: '+params
				params.kilos=0
				
				def ventaDetInstance=new VentaDet()
				bindData(ventaDetInstance,params,[includes:['producto','cantidad','precio','descuentos','comentarios']])
				ventaDetInstance.actualizarImportes()
				ventaDetInstance.costo=0
				venta.addToPartidas(ventaDetInstance)
				if (!venta.save(flush: true)) {
					render view: 'agregarConcepto', model: ['ventaInstance':venta,ventaDetInstance:ventaDetInstance]
					//render view: 'edit', model: ['ventaInstance': venta]
					return
				}
	
				flash.message = 'Partida agregada'
				redirect action: 'edit', id: venta.id
				break
			}
	}
	
}
