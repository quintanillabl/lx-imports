package com.luxsoft.impapx.tesoreria

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;

import grails.validation.ValidationException

class InversionService {

    def  generarInversion(Inversion inversion) {
		def cuenta=inversion.cuentaOrigen
		inversion.cuentaDestino=cuenta
		inversion.vencimiento=inversion.fecha+inversion.plazo
		inversion.comision=0
		inversion.impuesto=0
		inversion.moneda=cuenta.moneda
		inversion.comentario=inversion.comentario?:'RE-INVERSION AUTOMATICA'
		try {
			
			MovimientoDeCuenta egreso=new MovimientoDeCuenta(
				cuenta:inversion.cuentaOrigen
				,fecha:inversion.fecha
				,moneda:inversion.moneda
				,tc:1
				,importe:inversion.importe.abs()*-1.0
				,comentario:'INVERSION'
				,ingreso:true)
			egreso.tipo='TRANSFERENCIA'
			egreso.origen='TESORERIA'
			egreso.concepto='INVERSION'
			
			//Calculando la utilitdad
			def rendimiento=0
			def tasa=inversion.tasa
			def importe=inversion.importe
			def importeIsr=(inversion.importe*(inversion.tasaIsr/100))/inversion.cuentaOrigen.diasInversionIsr
			importeIsr=importeIsr*inversion.plazo
			if(tasa>0){
				rendimiento=importe*(tasa/100)
				rendimiento=(rendimiento/360)*inversion.plazo
				rendimiento=rendimiento-importeIsr
			}
			importe+=rendimiento
			
			inversion.importeIsr=importeIsr
			
			inversion.rendimientoCalculado=rendimiento
			inversion.rendimientoReal=rendimiento
			
			//Generando el ingreso
			MovimientoDeCuenta ingreso=new MovimientoDeCuenta(
				cuenta:inversion.cuentaOrigen
				,fecha:inversion.vencimiento
				,moneda:inversion.moneda
				,tc:1
				,importe:importe
				,comentario:'INVERSION'
				,ingreso:true)
			ingreso.tipo='TRANSFERENCIA'
			ingreso.origen='TESORERIA'
			ingreso.concepto='INVERSION'
			
			inversion.addToMovimientos(ingreso)
			inversion.addToMovimientos(egreso)
			
			return inversion.save(failOnError:true)
		} catch (ValidationException e) {
			return inversion
		}
		
    }
	
	def eliminarInversion(Inversion inversion){
		
		//def inversion=Inversion.findById(id,[fetch:[movimientos:'eager']])
		//def movs=inversion.movimientos.collect {it.id}
		//inversion.movimientos.clear()
		inversion.delete(flush:true)
		/*
		movs.each{
			//it.delete(flush:true)
			def mov=MovimientoDeCuenta.get(it)
			mov.delete(flush:true)
		}*/
		
	}
}
