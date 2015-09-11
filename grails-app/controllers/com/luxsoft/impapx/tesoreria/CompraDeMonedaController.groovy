package com.luxsoft.impapx.tesoreria

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.Requisicion;
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class CompraDeMonedaController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def compraDeMonedaService

    def pagoProveedorService

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
    	params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        def periodo=session.periodoTesoreria
        def list=CompraDeMoneda.findAllByFechaBetween(periodo.inicioDeMes(),periodo.finDeMes(),params)
        def	count=CompraDeMoneda.countByFechaBetween(periodo.inicioDeMes(),periodo.finDeMes())
        [compraDeMonedaInstanceList: list, compraDeMonedaInstanceTotal: count]
    }

    def save(CompraDeMoneda compraDeMonedaInstance){
    	compraDeMonedaInstance.validate(["requisicion","fecha","moneda","tipoDeCambioCompra","tipoDeCambio","cuentaOrigen","cuentaDestino"])
    	if(compraDeMonedaInstance.hasErrors()){
    		render view:'create',model:[compraDeMonedaInstance: compraDeMonedaInstance]
    		return
    	}
    	compraDeMonedaInstance=compraDeMonedaService.registrarCompra2(compraDeMonedaInstance)
    	flash.message="Compra de moneda ${compraDeMonedaInstance.id} registrada"
    	redirect action:'edit',params:[id:compraDeMonedaInstance.id]
    	
    }

    def create() {
		params.fecha=new Date()
        [compraDeMonedaInstance: new CompraDeMoneda(params)]
    }

    def edit(CompraDeMoneda compraDeMonedaInstance){
    	 [compraDeMonedaInstance: compraDeMonedaInstance]
    }

    // def update(CompraDeMoneda compraDeMonedaInstance){

    // 	if(compraDeMonedaInstance.hasErrors()){
    // 		render view:'edit',model:[compraDeMonedaInstance: compraDeMonedaInstance]
    // 		return
    // 	}
    // 	compraDeMonedaInstance=compraDeMonedaService.update(compraDeMonedaInstance)
    // 	flash.message="Compra de moneda actualizada"
    // 	redirect action:'edit',params:[id:compraDeMonedaInstance.id]
    // }

    def delete(CompraDeMoneda compraDeMonedaInstance) {
        compraDeMonedaService.delete(compraDeMonedaInstance)
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda'), params.id])
        redirect action: 'index'
    }
	
	// def requisicionesDisponiblesJSONList(){
	// 	def requisiciones=Requisicion.findAll(
	// 		"from Requisicion r left join fetch r.pagoProveedor pp where r.concepto='COMPRA_MONEDA' and r.total>0 and pp is  null")
	// 	def requisicionesList=requisiciones.collect { req ->
	// 		def desc="Id: ${req.id} ${req.proveedor.nombre}  ${req.total} "
	// 		[id:req.id,label:desc,value:desc]
	// 	}
	// 	render requisicionesList as JSON
	// }
}
