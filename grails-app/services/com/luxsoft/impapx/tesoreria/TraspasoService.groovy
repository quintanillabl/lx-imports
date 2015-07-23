package com.luxsoft.impapx.tesoreria

import grails.validation.ValidationException;



class TraspasoService {

    def  generarTraspaso(Traspaso traspaso) {
		def cuenta=traspaso.cuentaOrigen
		traspaso.comision=0
		traspaso.impuesto=0
		traspaso.moneda=cuenta.moneda
		
		/*Generando el egreso
		MovimientoDeCuenta egreso=new MovimientoDeCuenta(traspaso.properties)
		egreso.cuenta=traspaso.cuentaOrigen
		egreso.tipo='TRANSFERENCIA'
		egreso.origen='TESORERIA'
		egreso.concepto='TRASPASO'
		traspaso.addToMovimientos(egreso)
		*/
		
		
		
		try {
			
			MovimientoDeCuenta egreso=new MovimientoDeCuenta(
				cuenta:traspaso.cuentaOrigen
				,fecha:traspaso.fecha
				,moneda:traspaso.moneda
				,tc:1
				,importe:traspaso.importe.abs()*-1.0
				,comentario:'TRASPASO'
				,ingreso:false)
			egreso.tipo='TRANSFERENCIA'
			egreso.origen='TESORERIA'
			egreso.concepto="Traspaso: $traspaso.cuentaDestino"
			traspaso.addToMovimientos(egreso)
			
			//Generando el ingreso
			MovimientoDeCuenta ingreso=new MovimientoDeCuenta(
				cuenta:traspaso.cuentaDestino
				,fecha:traspaso.fecha
				,moneda:traspaso.moneda
				,tc:1
				,importe:traspaso.importe
				,comentario:'TRASPASO'
				,ingreso:true)
			ingreso.tipo='TRANSFERENCIA'
			ingreso.origen='TESORERIA'
			ingreso.concepto="Traspaso: $traspaso.cuentaOrigen"
			traspaso.addToMovimientos(ingreso)
			
			return traspaso.save(failOnError:true)
		} catch (ValidationException e) {
			return traspaso
		}
		
    }
}
