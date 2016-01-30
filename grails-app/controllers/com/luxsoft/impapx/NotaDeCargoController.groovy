package com.luxsoft.impapx

import luxsoft.cfd.ImporteALetra
import grails.converters.JSON
import grails.validation.ValidationException
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException
import com.luxsoft.cfdi.Cfdi

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('VENTAS','COMPRAS')"])
class NotaDeCargoController {
	
	def ventaService
	
	static allowedMethods = [create: 'GET', edit:'GET',save:'POST',update:'PUT',delete: 'DELETE']

    def index() {
		
		def periodo=session.periodo
		def args=[periodo.fechaInicial,periodo.fechaFinal,'NOTA_CARGO']
		def list=Venta.findAll(
			"from Venta v where date(v.fecha) between ? and ? and v.tipo=? order by v.lastUpdated desc",
			args)
        [ventaInstanceList:list]
    }

    def create() {
    	respond new Venta(fecha:new Date(),cuentaDePago:'0000',clase:params.clase,formaDePago:'TRANSFERENCIA',tipo:'NOTA_CARGO')
    }

    def save(Venta ventaInstance){
    	if (ventaInstance == null) {
    	    notFound()
    	    return
    	}
    	if (ventaInstance.hasErrors()) {
    	    respond ventaInstance, view:'create'
    	    return
    	}
    	ventaInstance.save failOnError:true,flush:true
    	flash.message = "Nota de cargo generada: $ventaInstance.id"
	    redirect action: 'edit', id: ventaInstance.id
    }

    def update(Venta ventaInstance){
    	if (ventaInstance == null) {
    	    notFound()
    	    return
    	}
    	if (ventaInstance.hasErrors()) {
    		def cfdi=Cfdi.findBySerieAndOrigen('NOTA_CARGO',params.id)
    	    render view:'edit',model:[ventaInstance:ventaInstance,cfdi:cfdi]
    		return
    	}
    	ventaInstance.save failOnError:true,flush:true
    	flash.message="Venta ${ventaInstance.id} actualizada"
    	redirect action:'edit',id:ventaInstance.id
    }

    def show(Venta ventaInstance) {
        
		def cfdi=Cfdi.findBySerieAndOrigen('NOTA_CARGO',ventaInstance.id)
		if(cfdi?.comentario?.contains("CANCELADO"))
			cfdi=null
        [ventaInstance: ventaInstance,cfdi:cfdi]
    }
	
    def edit(Venta ventaInstance) {
    	def cfdi=Cfdi.findBySerieAndOrigen('NOTA_CARGO',params.id)
	    [ventaInstance: ventaInstance,cfdi:cfdi]
    } 

    def delete(Venta ventaInstance) {
        if (!ventaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'venta.label', default: 'Venta'), params.id])
            redirect action: 'index'
            return
        }
		if(ventaInstance.cfdi){
			flash.message ="Nota de cargo facturada imposible eliminar"
			redirect action: 'index'
			return
		}
        ventaInstance.delete(flush: true)
		flash.message = "Nota de cargo ${ventaInstance.id} eliminada"
        redirect action: 'index'
    }

    def eliminarPartidas(){
    	
    	JSONArray jsonArray=JSON.parse(params.partidas);
    	
    	def venta=null
    	jsonArray.each {
    		def det=VentaDet.get(it.toLong())
    		if(venta==null)
    			venta=det.venta
    		venta.removeFromPartidas(det)
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
    
    def imprimirCfd(){
    	
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
    		 ]
    		
    		return res
    	}
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
