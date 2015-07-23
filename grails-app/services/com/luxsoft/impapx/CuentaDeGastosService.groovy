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
		//factura.cuentaDeGastos=cuenta
		//cuenta=cuenta.save(failOnError:true)
		//factura.save(failOnError:true)
		//println 'Cuenta actualizada ...'
		
		//Actualizacion de costos
		//cuenta.actualizarGastosDeImportacion()
		
		def embarque=cuenta.embarque
		if(embarque){
			println 'Actualiando gastos en embarqueDet: '+embarque+' Facturas en cuenta de gastos: '+cuenta.facturas.size()
			
			def importe=0.0
			def incrementable=0.0
			cuenta.facturas.each{
				if(it.incrementable)
					incrementable+=it.importe*it.tc
				else
					importe+=it.importe*it.tc
			}
			/*
			cuenta.facturas.sum(0.0,{
				println 'Fac: '+it.incrementable
				//if(!it.incrementable)
				if(!it.incrementable)
					it.importe*it.tc
				})*/
			
			println 'Gasto a prorratear: '+importe
			def kilosTotales=embarque.partidas.sum {it.kilosNetos}
			embarque.partidas.each {
				def gasto=it.kilosNetos*importe/kilosTotales
				it.gastosHonorarios=gasto
			}
			
			
			println 'Incrementable a prorratear: '+incrementable
			embarque.partidas.each {
				def res=it.kilosNetos*incrementable/kilosTotales
				it.incrementables=res
			}
		}
		
		cuenta=cuenta.save(failOnError:true)
		return [cuentaDeGastos:cuenta,factura:factura]
		
		/*
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			return [error:e.message]
		}*/
		
    }
	
	def eliminarFacturas(def cuentaDeGastosId,def facturas){
		def cuenta=CuentaDeGastos.findById(cuentaDeGastosId,[fetch:[facturas:'eager']])
		println 'Procesando eliminacion de facturas para cuenta de gastos: '+cuenta
		facturas.each{
			def facturaId=it.toLong()
			def factura=GastosDeImportacion.get(facturaId)
			println 'Factura a eliminar: '+factura
			if(factura){
				def res=cuenta.facturas.remove(factura)
				println 'Eliminacion: '+res
				//factura.cuentaDeGastos=null
			}
		}
		cuenta=cuenta.save(failOnError:true) 
		println 'Facturas asignadas: '+cuenta.facturas.size()
		def embarque=cuenta.embarque
		if(embarque){
			def importe=cuenta.facturas.sum 0, {it.importe}
			
			def kilosTotales=embarque.partidas.sum {it.kilosNetos}
			println 'Importe a prorratear: '+importe+ 'total kilos: '+kilosTotales
			embarque.partidas.each {
				def gasto=it.kilosNetos*importe/kilosTotales
				it.gastosHonorarios=gasto
			}
		}
		
		return cuenta
		
	}
	
} 

class CuentaDeGastosException extends RuntimeException{
	CuentaDeGastos cuenta
	String message
}
