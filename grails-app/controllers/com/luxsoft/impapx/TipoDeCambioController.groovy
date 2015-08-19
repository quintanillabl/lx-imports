package com.luxsoft.impapx

import grails.converters.JSON

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class TipoDeCambioController {
	
	static scaffold = true


	def create(){
		def last=TipoDeCambio.last()
		def fecha=last?last.fecha+1:new Date()-1
		[tipoDeCambioInstance:new TipoDeCambio(fecha:fecha)]
	}
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

	@Secured(["hasAnyRole('TESORERIA','COMPRAS','CONTABILIDAD')"])
    def index(Integer max) {
        def periodo=session.periodoTesoreria
        def list=TipoDeCambio
        	.findAll("from TipoDeCambio t where date(t.fecha) between ? and ?  order by t.fecha desc",[periodo.inicioDeMes(),periodo.finDeMes()])
        [tipoDeCambioInstanceList: list]
    }

	def save(TipoDeCambio tipoDeCambioInstance){
		assert tipoDeCambioInstance,'No existe la instancia de tipo de cabio '+params
		if(tipoDeCambioInstance.hasErrors()){
			render view:'create',model:[tipoDeCambioInstance:tipoDeCambioInstance]
			return
		}
		def found=TipoDeCambio.findByFechaAndMonedaOrigenAndMonedaFuente(
			tipoDeCambioInstance.fecha,
			tipoDeCambioInstance.monedaOrigen,
			tipoDeCambioInstance.monedaFuente)
		if(found){
			flash.message="T.C. Ya registrado"
			render view:'show',model:[tipoDeCambioInstance:found]
			return	
		}
		tipoDeCambioInstance.save flush:true,failOnError:true
		flash.message="Tipo de cambio registrado"
		//redirect view:'show',params:[id:tipoDeCambioInstance.id]
		respond view:'show',tipoDeCambioInstance
	}
	
	@Secured(["hasRole('USUARIO')"])
	def ajaxTipoDeCambioDiaAnterior(String fecha){
		
		def dia=new Date().parse("dd/MM/yyyy",fecha)-1;
		println 'Calculando el tipo de cambio: '+dia 
		def tc=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=?",[dia])
		println 'Tipo: '+tc
		def res=[:]
		if(tc)
			res.factor=tc.factor
		else
			res.error="No existe tipo de cambio regsitrado en el sistema para el día:"+dia.text() 
		render res as JSON
	}


}
