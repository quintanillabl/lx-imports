package com.luxsoft.impapx

import grails.converters.JSON

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
class CuentaDeGastosController {

    static allowedMethods = [create:'GET',save:'POST', edit:'GET', delete: 'POST',update:'PUT']

	def filterPaneService

	def cuentaDeGastosService

    def index(Integer max) {
	    def periodo=session.periodo
	    def list=CuentaDeGastos.findAll(
            "from CuentaDeGastos c  where date(c.fecha) between ? and ? order by c.fecha desc",
            [periodo.fechaInicial,periodo.fechaFinal])
        [cuentaDeGastosInstanceList:list]
    }

    def filter = {
        if(!params.max) params.max = 10
        render( view:'index',
            model:[ cuentaDeGastosInstanceList: filterPaneService.filter( params, CuentaDeGastos ),
            cuentaDeGastosInstanceCount: filterPaneService.count( params, CuentaDeGastos ),
            filterParams: org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params),
            params:params ] )
    }

    def list() {
       forward action: 'list'
    }

    def create() {
		[cuentaDeGastosInstance: new CuentaDeGastos(fecha:new Date())]
    }

    def save(CuentaDeGastos cuentaDeGastosInstance){
    	if(cuentaDeGastosInstance.hasErrors()){
    		render view:'create',model:[cuentaDeGastosInstance:cuentaDeGastosInstance]
    		return
    	}
    	cuentaDeGastosInstance.save failOnError:true,flush:true
    	flash.message="Cuenta de gastos ${cuentaDeGastosInstance.id} generada"
    	redirect action:'edit',id:cuentaDeGastosInstance.id
    }

    def show(CuentaDeGastos cuentaDeGastosInstance) {
        [cuentaDeGastosInstance: cuentaDeGastosInstance]
    }

    def edit(CuentaDeGastos cuentaDeGastosInstance) {
		[cuentaDeGastosInstance: cuentaDeGastosInstance]
    }

    def update(CuentaDeGastos cuentaDeGastosInstance){
    	if(cuentaDeGastosInstance.hasErrors()){
    		render view:'edit',model:[cuentaDeGastosInstance:cuentaDeGastosInstance]
    		return
    	}
    	cuentaDeGastosInstance.save flush:true
    	flash.message="Cuenta de gastos ${cuentaDeGastosInstance.id} actualizada"
    	redirect action:'edit',id:cuentaDeGastosInstance.id
    }

    def delete() {
        def cuentaDeGastosInstance = CuentaDeGastos.get(params.id)
        if (!cuentaDeGastosInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaDeGastos.label', default: 'CuentaDeGastos'), params.id])
            redirect action: 'list'
            return
        }

        try {
            cuentaDeGastosInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'cuentaDeGastos.label', default: 'CuentaDeGastos'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cuentaDeGastos.label', default: 'CuentaDeGastos'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def facturasAsJSON(){
		
		def dataToRender = [:]
		dataToRender.sEcho = params.sEcho
		dataToRender.aaData=[]
		//def list=CuentaPorPagar.findAll("from CuentaPorPagar d where d.cuentaDeGastos.id=?",[params.long('cuentaDeGastosId')])
		//def list=GastosDeImportacion.findAll("select f from GastosDeImportacion g left join fetch g.facturas f where g.id=?",[params.long('cuentaDeGastosId')])
		def cuenta=CuentaDeGastos.findById(params.long('cuentaDeGastosId'),[fetch:[facturas:'select']])
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
				]
			
		}
		render dataToRender as JSON
	}
	
	def facturasDisponiblesPorAsignarJSON(){
		//println 'Localizando factura para :'+params
		//def facturas=GastosDeImportacion.findAllByCuentaDeGastosIsNullAndDocumentoIlike(params.term+'%')
		def facturas=GastosDeImportacion.findAll("from GastosDeImportacion fac where fac not in(select f from CuentaDeGastos c join c.facturas f)")
		
		def res=facturas.collect{row ->
			def total=lx.moneyFormat(number:row.total)
			def label="Fac: ${row.documento}  F: ${row.fecha.format('dd/MM/yyyy')} T: ${total} Prov:${row.proveedor.nombre}"
			[id:row.id,label:label,value:label,documento:row.documento]
		}
		render res as JSON
		
	}

	
	def agregarFactura(Long cuentaDeGastosId,Long facturaId){

		def dataToRender=[:]
		try{
			def res=cuentaDeGastosService.agregarFactura(cuentaDeGastosId, facturaId)
			dataToRender.total=res.total
			render dataToRender as JSON
		}catch(Exception ex){
			log.error ex
			dataToRender.error=ExceptionUtils.getRootCauseMessage(ex)
		}
		render dataToRender as JSON
	}
		
	def eliminarFacturas(){
		//println 'Eliminando facturas: '+params
		JSONArray jsonArray=JSON.parse(params.partidas);
		def data=[:]
		try {
			def cuenta=cuentaDeGastosService.eliminarFacturas(params.cuentaDeGastosId,jsonArray)
			data.total=cuenta.total
			
		} catch (CuentaDeGastosException e) {
			e.printStackTrace()
			data.error=e.message
		}
		render data as JSON
	}
	
	def embarquesDisponiblesJSONList(){
		//def embarques=Embarque.findAllByBlIlike('%'+params.term+"%",[max:20,sort:"fechaEmbarque",order:"desc"])
		def embarques=Embarque.findAll("from Embarque e where e.id not in (select x.embarque.id from CuentaDeGastos x )")
		def embarquesList=embarques.collect { row ->
			def label=row.toString()
			[id:row.id,label:label,value:label]
		}
		render embarquesList as JSON
	}

	def search(){
	    def term='%'+params.term.trim()+'%'
	    def query=CuentaDeGastos.where{
	        (id.toString()=~term || proveedor.nombre=~term || comentario=~term || embarque.bl=~term) 
	    }
	    def cuentas=query.list(max:30, sort:"id",order:'desc')

	    def cuentasList=cuentas.collect { cuenta ->
	        def label="${cuenta.id} ${cuenta.proveedor} ${cuenta.fecha.format('dd/MM/yyyy')} ${cuenta.embarque}"
	        [id:cuenta.id,label:label,value:label]
	    }
	    render cuentasList as JSON
	}
}
