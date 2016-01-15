package com.luxsoft.impapx

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.groovy.grails.web.json.JSONArray;
import org.springframework.util.Assert;

class CuentaDeGastosService {

    def agregarFactura(long cuentaDeGastosId,long facturaId) {
		
		def cuenta=CuentaDeGastos.get(cuentaDeGastosId)
		Assert.notNull(cuenta,"No existe la cuenta de gastos: "+cuentaDeGastosId)
		def factura=GastosDeImportacion.get(facturaId)
		Assert.notNull(factura,"No existe la factura de gastos: "+facturaId)
		
		cuenta.facturas.add(factura)
		def embarque=cuenta.embarque
		if(embarque){
			println 'Actualiando gastos en embarqueDet: '+embarque+' Facturas en cuenta de gastos: '+cuenta.facturas.size()
			
			def importe=0.0
			def incrementable=0.0
			cuenta.facturas.each{
				if(it.incrementable)
					incrementable+=it.importe
				else
					importe+=it.importe*it.tc
			}
			
			def kilosTotales=embarque.partidas.sum {it.kilosNetos}
			embarque.partidas.each {
				def gasto=it.kilosNetos*importe/kilosTotales
				it.gastosHonorarios=gasto
			}

			embarque.partidas.each {
				def res=it.kilosNetos*incrementable/kilosTotales
				it.incrementablesUsd=res
			}
			embarque.save flush:true
		}
		
		cuenta=cuenta.save(failOnError:true)
		return [cuentaDeGastos:cuenta,factura:factura]
		
    }
	
	def eliminarFacturas(def cuentaDeGastosId,def facturas){
		def cuenta=CuentaDeGastos.findById(cuentaDeGastosId,[fetch:[facturas:'eager']])
		//println 'Procesando eliminacion de facturas para cuenta de gastos: '+cuenta
		facturas.each{
			def facturaId=it.toLong()
			def factura=GastosDeImportacion.get(facturaId)
			
			if(factura){
				def res=cuenta.facturas.remove(factura)
				if(res){
					log.info "Factura quitada: "+factura
				}
				//log.info 'Eliminacion: '+res
				//factura.cuentaDeGastos=null
			}
		}
		cuenta=cuenta.save(failOnError:true) 
		//println 'Facturas asignadas: '+cuenta.facturas.size()
		def embarque=cuenta.embarque
		if(embarque){
			def importe=cuenta.facturas.sum 0, {it.importe}
			
			def kilosTotales=embarque.partidas.sum {it.kilosNetos}
			//println 'Importe a prorratear: '+importe+ 'total kilos: '+kilosTotales
			embarque.partidas.each {
				def gasto=it.kilosNetos*importe/kilosTotales
				it.gastosHonorarios=gasto
			}
			embarque.save flush:true
		}
		
		return cuenta
		
	}

	def actualizarCostosDeImportacion(CuentaDeGastos cuenta){

		def embarque = cuenta.embarque

		def gastos=0.0

		def incrementable=0.0

		cuenta.facturas.each{
		    if(it.incrementable){
		        //println 'INCREMENTABLE Fac: '+it.id + ' Importe: '+it.importe+ ' T.C:'+it.tc
				incrementable+=it.importe
		    }
		    else{
		        //println 'GASTO Fac: '+it.id + ' Importe: '+it.importe+ ' T.C:'+it.tc
		        gastos+=it.importe*it.tc
		    }
		}

		def kilosTotales=embarque.partidas.sum {it.kilosNetos}
		
		embarque.partidas.each {
			def gasto=it.kilosNetos*gastos/kilosTotales
			it.gastosHonorarios=gasto
		}

		embarque.partidas.each {
			def res=it.kilosNetos*incrementable/kilosTotales
			it.incrementablesUsd=res
			if(it.pedimento){
				it.tc= it.pedimento.tipoDeCambio
				it.incrementables = it.incrementablesUsd*it.pedimento.tipoDeCambio
			}
		}

		embarque.save flush:true


	}
	
} 

class CuentaDeGastosException extends RuntimeException{
	CuentaDeGastos cuenta
	String message
}
