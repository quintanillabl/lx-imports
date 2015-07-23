package com.luxsoft.impapx

import grails.converters.JSON

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat;
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef;
import org.codehaus.groovy.grails.web.json.JSONArray;
import org.codehaus.groovy.grails.web.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
class EmbarqueControllerOld {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

	def jasperService
	def embarqueService
	
	def filterPaneService
	
    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        respond Embarque.list(params), model:[embarqueInstanceCount: Embarque.count()]
        
    }
	
	def filter(){
		if(!params.max) params.max = 50
		println filterPaneService?'OK':'ERROR'
		render( view:'index',
			model:[ embarqueInstanceList: filterPaneService.filter( params, Embarque.class),
			embarqueInstanceCount: filterPaneService.count( params, Embarque.class ),
			filterParams: org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params),
			params:params ] )
	}
	/*
	def filter = {
        println 'Buscando compras: '+org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params)
        if(!params.max) params.max = 10
        render( view:'index',
            model:[ compraInstanceList: filterPaneService.filter( params, Compra ),
            compraInstanceCount: filterPaneService.count( params, Compra ),
            filterParams: org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params),
            params:params ] )
    }
	*/
    def list(Integer max) {
    	forward action: "index", params: params
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[embarqueInstance: new Embarque(params)]
			break
		case 'POST':
			params.nombre=params.nombre.toUpperCase()
			params.bl=params.bl.toUpperCase()
			params.comentario=params.comentario.toUpperCase()
			params.proveedor=Proveedor.get(params.proveedorId);
			params.moneda=Currency.getInstance('USD')
			params.tc=1
			println params
			//params.proveedor=Proveedor.get(params.proveedorId);
	        def embarqueInstance = new Embarque(params)
	        if (!embarqueInstance.save(flush: true)) {
	            render view: 'create', model: [embarqueInstance: embarqueInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarqueInstance.id])
	        redirect action: 'edit', id: embarqueInstance.id
			break
		}
    }

    def show() {
        def embarqueInstance = Embarque.get(params.id)
        if (!embarqueInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarque.label', default: 'Embarque'), params.id])
            redirect action: 'list'
            return
        }

        [embarqueInstance: embarqueInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def embarqueInstance = Embarque.get(params.id)
	        if (!embarqueInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarque.label', default: 'Embarque'), params.id])
	            redirect action: 'list'
	            return
	        }
			//def gastos=GastosDeImportacion.findByEmbarque(embarqueInstance)
	        [embarqueInstance: embarqueInstance,facturaGastos:null]
			break
		case 'POST':
			
	        def embarqueInstance = Embarque.get(params.id)
	        if (!embarqueInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarque.label', default: 'Embarque'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (embarqueInstance.version > version) {
	                embarqueInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'embarque.label', default: 'Embarque')] as Object[],
	                          "Another user has updated this Embarque while you were editing")
	                render view: 'edit', model: [embarqueInstance: embarqueInstance]
	                return
	            }
	        }
			def proveedor=Proveedor.findByNombre(params.proveedor)
			
			params.proveedor=proveedor
	        embarqueInstance.properties = params
			
			
	        if (!embarqueInstance.save(flush: true)) {
	            render view: 'edit', model: [embarqueInstance: embarqueInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarqueInstance.id])
	        redirect action: 'edit', id: embarqueInstance.id
			break
		}
    }

    def delete() {
        def embarqueInstance = Embarque.get(params.id)
        if (!embarqueInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarque.label', default: 'Embarque'), params.id])
            redirect action: 'list'
            return
        }

        try {
            embarqueInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'embarque.label', default: 'Embarque'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'embarque.label', default: 'Embarque'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	
	def proveedoresList(){
		//println params
		def proveedores=Proveedor.findAllByNombreIlike(params.term+"%",[max:10,sort:"nombre",order:"desc"])
		def proveedoresList=proveedores.collect { prov ->
			[id:prov.id,label:prov.nombre,value:prov.nombre]
		}
		//def jsonResult=[provs:proveedoresList]
		render proveedoresList as JSON
	}
	
	def comprasDetDisponiblesJSON(final Long proveedorId,final String term){
		//println 'Parametros para comprasDet disponibles:'+params
		def partidas=CompraDet.findAll("from CompraDet as d where d.compra.proveedor.id=? and d.producto.clave like ? and d.solicitado-d.entregado>0	"
				,[proveedorId,term.toUpperCase()+"%"],[max:20])
		
		def res=partidas.collect{ row ->
			def pendientes="${g.formatNumber([number:row.pendiente,format:"###,###",locale:"es_MX"])}"
			def desc="${row.producto.clave}  ${row.producto.descripcion} Comp: ${row.compra.folio} Pend: ${pendientes}"
			[id:row.id,label:desc,value:desc,kilosPorMillar:row.producto.kilos
				,factor:row.producto.unidad.factor
				,productoId:row.producto.id]
		}
		render res as JSON
	}

	def pickingList(){
		println params
		def embarqueInstance=Embarque.findById(params.long('embarqueId'), [fetch: [partidas: 'join']])
		/*def partidas=EmbarqueDet.findAll{
			"embarque.id"==params.long('embarqueId')
		}*/
		render(template:'partidas',var:'embarqueDet', model:[embarqueInstance:embarqueInstance, partidas:embarqueInstance.partidas])

	}

	def actualizarFactura(){
		//println params
		//println params.partidas
		JSONArray jsonArray=JSON.parse(params.partidas);
		jsonArray.each {
			println 'Actualizando: '+it.toLong()
			def det=EmbarqueDet.get(it.toLong())
			det.factura=params.facturaId
		}
		def res=[factura:params.facturaId]
		render res as JSON 
	}

	def actualizarContenedor(){
		//println 'Actualizar contenedor para embarqueDet params:'+params
		JSONArray jsonArray=JSON.parse(params.partidas);
		jsonArray.each{
			def det=EmbarqueDet.get(it.toLong())
			det.contenedor=params.contenedor
		}
		def res=[contenedor:params.contenedor]
		render res as JSON
	}
	
	def facturasPorAsignarJSON(){
		//println 'Localizando factura para :'+params
		def facturas=CuentaPorPagar.findAll("from CuentaPorPagar as d where d.proveedor.id=? and d.total-d.analisisCosto>10 and d.documento like ? "
			,[params.long('proveedorId'),params.term.toUpperCase()+"%"],[max:20])
		
		def res=facturas.collect{row ->
			def tot=g.formatNumber(number:row.total,format:'\$###,###,###.##')
			def analizado=g.formatNumber(number:row.analisisCosto,format:'\$###,###,###.##')
			def label="Fac: ${row.documento}  F: ${row.fecha.format('dd/MM/yyyy')} T: ${tot} Analizado: ${analizado}"
			[id:row.id,label:label,value:label,documento:row.documento]
		}
		render res as JSON
	}
	
	
	
	def asignarFactura(){
		//println 'Asignando factura a listas de empaque:'+params
		def factura=CuentaPorPagar.get(params.facturaId)
		JSONArray jsonArray=JSON.parse(params.partidas);
		def embarque
		jsonArray.each{it->
			//System.out.println("********************************"+ det);
			def det=EmbarqueDet.get(it.toLong())
			
			if(det.factura==null){
				det.factura=factura
				factura.analisisCosto+=det.costoBruto
			}
			embarque=det.embarque
		}
		if(factura.proveedor.vencimentoBl){
			factura.vencimiento=embarque.fechaEmbarque+factura.proveedor.plazo
		}
		def res=[documento:factura.documento]
		render res as JSON
	}
	
	def cancelarAsignacionDeFacturas(){
		println 'Cancelando asignacion de facturasfacturas:'+params
		JSONArray jsonArray=JSON.parse(params.partidas);
		jsonArray.each{
			def det=EmbarqueDet.get(it.toLong())
			def factura=det.factura
			if(factura!=null){
				println 'Actualizando analisis costo para factura: '+factura+ "  Analisis: "+factura.analisisCosto
				factura.analisisCosto-=det.costoBruto
				if(factura.analisisCosto<0)
					factura.analisisCosto=0
			}
			det.factura=null;
			
		}
		def res=[documento:'']
		render res as JSON
	}
	
	
	def comprasPendientes(long id){
		println 'Localizando compras pendientes por atender '+params
		def embarque=Embarque.get(id)
		def res=CompraDet.findAll("from CompraDet d left join fetch d.compra c left join fetch c.proveedor p where c.fecha>='2013/07/01' and c.proveedor=? and solicitado-entregado>0 order by d.compra.folio"
			,[embarque.proveedor]
			,[max:500]
			)
		println 'Pendientes: '+res.size()
		[embarque:embarque,compraDetInstanceList:res,compraDetInstanceTotal:res.size()]
		
	}
	
	def asignarComprasUnitarias(long embarqueId){
		//def embarque=Embarque.get(embarqueId)
		def embarque=Embarque.findById(embarqueId,[fetch:[partidas:'eager']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		
		def proveedor
		
		jsonArray.each{
			def det=CompraDet.get(it.toLong())
			
			def embarqueDet=new EmbarqueDet()
			embarqueDet.compraDet=det
			embarqueDet.producto=det.producto
			embarqueDet.cantidad=det.pendiente
			embarqueDet.actualizarKilosEstimados()
			//calculando precio
			def provProducto=ProveedorProducto.findByProveedorAndProducto(embarque.proveedor,det.producto)
			embarqueDet.precio=provProducto?.costoUnitario?:0
			
			embarque.addToPartidas(embarqueDet)
			det.entregado+=embarqueDet.cantidad;
			
		}
		
		embarque.save(failOnError:true)
		def res=[res:jsonArray.size()]
		render res as JSON
	}
	
	def eliminarPartidas(){
		def res=embarqueService.eliminarPartidas(params)
		def data=[status:'OK']
		render data as JSON
	}
	
	/**
	 * Elimina las partidas del embaruqe que falten de asignar kilos netos
	 * 
	 * @return
	 */
	def eliminarFaltantes(long id){
		println 'Eliminando faltantes de embarque: '+id
		def res=EmbarqueDet.findAll("from EmbarqueDet d where d.embarque.id=? and d.kilosNetos<=0",[id])
		println 'Partidas a eliminar: '+res.size()
		
		res.each {embarqueDet ->
			println 'eliminando: '+embarqueDet
			def embarque=embarqueDet.embarque
			embarque.removeFromPartidas(embarqueDet)
			embarqueDet.compraDet.entregado-=embarqueDet.cantidad;
			
		}
		redirect action:'edit',id:id
	}
	
	def contenedoresDeEmbarque(long id){
		println 'Contenedores de embarque: '+id
		def contenedores=EmbarqueDet.executeQuery("\
			select d.embarque.id,d.embarque.bl ,d.contenedor,sum(d.kilosNetos) as kilosNetos \
			from EmbarqueDet d \
			where d.embarque.id=?\
			group by d.embarque.id,d.embarque.bl,d.contenedor",[id])
		def rows=contenedores.collect{
			def map=[embarque:it[0],bl:it[1],contenedor:it[2],kilosNetos:it[3]]
		}
		println rows
		render( template:"contenedoresGrid",model:[contenedores:rows])
	}
	
	def actualizarPrecios(long id){
		
		def embarque=Embarque.findById(id,[fetch:[partidas:'eager']])
		def list=embarque.partidas //EmbarqueDet.findAll("from EmbarqueDet e where e.embarque.id=?",[id])
		list.each {
			it.actualizarPrecioDeVenta()
		}
		render (template:'costosGrid' ,model: [partidas: list,embarqueInstance:embarque])
	}
	
	def relacionDeContenedores(){
		println 'Relacion :'+params
		def result = [:]
		params._format = "PDF"
		params._file = "RelacionDeContenedoresPorEmbarque" // set your file name this will call to sample.jrxml
		params.ID=params.id
		chain(controller: 'jasper', action: 'index', params:params)
		
	}
	
}
