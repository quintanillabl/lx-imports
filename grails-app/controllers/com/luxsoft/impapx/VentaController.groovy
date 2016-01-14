package com.luxsoft.impapx


import luxsoft.cfd.ImporteALetra
import grails.converters.JSON
import grails.validation.ValidationException
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException
import com.luxsoft.cfdi.Cfdi

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('VENTAS','COMPRAS')"])
class VentaController {

    static allowedMethods = [create: 'GET', edit:'GET',save:'POST',update:'PUT',delete: 'DELETE']
	
	def ventaService
	def printService

    def index() {
		
		def periodo=session.periodo
		def args=[periodo.fechaInicial,periodo.fechaFinal,'VENTA']
		def list=Venta.findAll(
			"from Venta v where date(v.fecha) between ? and ? and v.tipo=? order by v.lastUpdated desc",
			args)
        [ventaInstanceList:list]
    }

    def create() {
    	respond new Venta(fecha:new Date(),cuentaDePago:'0000',clase:params.clase,formaDePago:'TRANSFERENCIA')
    }

    def save(Venta ventaInstance){
    	if(ventaInstance.hasErrors()){
    		render view:'create',model:[ventaInstance:ventaInstance]
    		return
    	}
    	ventaInstance.save failOnError:true,flush:true
    	flash.message = message(code: 'default.created.message', args: [message(code: 'venta.label', default: 'Venta'), ventaInstance.id])
	    redirect action: 'edit', id: ventaInstance.id
    }

    def show(Venta ventaInstance) {
        if (!ventaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
            redirect action: 'list'
            return
        }
		def cfdi=Cfdi.findBySerieAndOrigen('FAC',ventaInstance.id)
		if(cfdi?.comentario?.contains("CANCELADO"))
			cfdi=null
        [ventaInstance: ventaInstance,cfdi:cfdi]
    }

    def update(Venta ventaInstance){
    	if(ventaInstance.hasErrors()){
    		def cfdi=Cfdi.findBySerieAndOrigen('FAC',params.id)
    		render view:'edit',model:[ventaInstance:ventaInstance,cfdi:cfdi]
    		return
    	}
    	ventaInstance.save failOnError:true,flush:true
    	flash.message="Venta ${ventaInstance.id} actualizada"
    	redirect action:'edit',id:ventaInstance.id
    }

    def edit(Venta ventaInstance) {
    	def cfdi=Cfdi.findBySerieAndOrigen('FAC',params.id)
	    [ventaInstance: ventaInstance,cfdi:cfdi]
    } 

    def delete(Venta ventaInstance) {
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
        ventaInstance.delete(flush: true)
		flash.message = message(code: 'default.deleted.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
        redirect action: 'index'
    }
	
	def contenedoresPendientes(){
		def contenedores=DistribucionDet.executeQuery("\
			select distinct(d.contenedor) from EmbarqueDet d \
			 where d.precioDeVenta>=0\
			 and upper(d.contenedor) like ?\
			 and d.pedimento!=null \
			 and d not in(select x.embarque from VentaDet x )\
			 order by d.contenedor ",[params.term+'%'],[max: 10])
		def res=contenedores.collect{ row ->
			[id:row,label:row,value:row]
		}
		render res as JSON
	}
	
	def agregarContenedor(long ventaId,String contenedor){
		println 'Agregando contenedor: '+params
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
			if(det.embarque){
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
				}else{
					venta.removeFromPartidas(det)
				}
			
			
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

	def search(){
		def term=params.term.trim()

		log.info 'Buscando venta por: '+term
	    
	    def query=Venta.where{
	        id==term 
	    }
	    def ventas=query.list(max:30, sort:"id",order:'desc')

	    def ventasList=ventas.collect { venta ->
	        def label="Id: ${venta.id}  ${venta.cliente.nombre} ${venta.fecha.text()} ${venta.total}"
	        
	        [id:venta.id,label:label,value:label]
	    }
	    render ventasList as JSON
	}
	
}
