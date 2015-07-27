package com.luxsoft.impapx

import grails.converters.JSON

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class TipoDeCambioController {
	
	static scaffold = true
	
	@Secured(["hasAnyRole('TESORERIA','COMPRAS','CONTABILIDAD')"])
	def list(){
		params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.sort="fecha"
		params.order="desc"
		
		[tipoDeCambioInstanceList: TipoDeCambio.list(params), tipoDeCambioInstanceTotal: TipoDeCambio.count()]
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
