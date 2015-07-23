package com.luxsoft.impapx.cxc

import org.apache.commons.lang.exception.ExceptionUtils;

import util.MonedaUtils;

import com.luxsoft.impapx.Requisicion;
import com.luxsoft.impapx.Venta;
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta;


import grails.validation.ValidationException;

class CobranzaService {

    def registrarPago(CXCPago pago) {
		
		try {
			pago.impuestoTasa=16.00
			pago.moneda=pago.cuenta.moneda
			if(pago.cuenta.moneda==MonedaUtils.PESOS)
				pago.tc=1
			pago.actualizarImportes()
			def res=pago.save(failOnError:true)
			if(res.instanceOf(CXCPago)){
				if(res.formaDePago=='TRANSFERENCIA'){
					println 'Registrando ingreos en tesoreria'
					
					MovimientoDeCuenta ingreso = new MovimientoDeCuenta(
						 cuenta:pago.cuenta
						,fecha:pago.fecha
						,moneda:pago.cuenta.moneda
						,tc:pago.tc
						,importe:pago.total.abs()
						,ingreso:true
						,tipo:'TRANSFERENCIA'
						,origen:'CXC'
						,concepto:"Cobro: $pago.cliente.nombre"
						,comentario:'INGRESO POR TRANSFERENCIA'
						,referenciaBancaria:pago.referenciaBancaria)
					ingreso.save(failOnError:true)
					pago.ingreso=ingreso
					
				}
			}
			return res
		} catch (ValidationException e) {
			throw new CobranzaEnPagoException(pago:pago,message:ExceptionUtils.getRootCauseMessage(e));
		}
    }
	
	def asignarFacturas(CXCAbono abono,def facturas) {
		
		def disponible=abono.getDisponibleMN()
		
		facturas.each {
			def fac=Venta.get(it.toLong())
			
			def saldo=fac.saldoActual
			def importe=0
			if(disponible>=saldo)
				importe=saldo
			else
				importe=disponible
			disponible-=importe
			println "Aplicando $importe Saldo: $saldo Disponible:$disponible"
			def ap=new CXCAplicacion(
				fecha:new Date()
				,total:importe
				,impuestoTasa:abono.impuestoTasa?:0.0
				,importe:0.0
				,impuesto:0.0
				,factura:fac)
			//ap.actualizarImportes()
			//fac.saldo-=ap.total
			abono.addToAplicaciones(ap)
		}
		def res=abono.save(failOnError:true)
		return res
	}
	
	def eliminarAplicaciones(CXCAbono abono,def partidas){
		
		partidas.each {
			def id=it.toLong()
			def found=abono.aplicaciones.find{ det->
				det.id==id
			}
			if(found){
				
				boolean ok=abono.removeFromAplicaciones(found)
				println 'Eliminando: '+found+ 'Res: '+ok
			}
		}
		def res=abono.save(failOnError:true)
		return res
	}
	
	def registrarNota(CXCNota nota) {
		
		
		if(nota.moneda==MonedaUtils.PESOS){
			nota.tc=1.0
			nota.impuestoTasa=16.00
		}else{
			nota.impuestoTasa=0
		}
		nota.partidas=[]
		if(nota.tipo=='BONIFICACION'){
			nota.addToPartidas(
				cantidad:1
				,unidad:'NO APLICA'
				,numeroDeIdentificacion:'BON'
				,descripcion:nota.comentario?:'BONIFICACION'
				,valorUnitario:nota.importe
				,importe:nota.importe
				,comentario:nota.comentario)
			println 'Concepto agregado'
		}else{
			nota.importe=0
			nota.impuesto=0
			nota.total=0
		}
		nota.actualizarImportes()		
		nota.save(flush:true)
		return nota
		/*
		try {
			nota.impuestoTasa=16.00
			nota.total=0
			nota.actualizarImportes()
			def res=nota.save(failOnError:true)
			return res
		} catch (Exception e) {
			throw new CobranzaEnPagoException(pago:nota,message:ExceptionUtils.getRootCauseMessage(e));
		}*/
	}
	
	def asignarPartidasParaNota(CXCNota nota,def facturas) {
		if(nota.tipo=='DESCUENTO'){
			facturas.each {
				def fac=Venta.get(it.toLong())
				def importe=fac.importe*(nota.descuento/100)
				def documento=fac.cfd?.folio?:fac.id
				def desc="Descuento del $nota.descuento % en la factura: $documento"
				nota.addToPartidas(
					cantidad:1
					,unidad:'NO APLICA'
					,numeroDeIdentificacion:'DESCUENTO'
					,descripcion:desc
					,valorUnitario:importe
					,importe:importe
					,comentario:desc
					,venta:fac)
			}
			def res=nota.save(failOnError:true)
			return res
		}
		
	}
	
	def aplicarFacturas(CXCAbono abono,def facturas) {
		
		def disponible=abono.getDisponibleMN()
		
		facturas.each {
			def fac=Venta.get(it.toLong())
			
			def saldo=fac.saldoActual
			def importe=0
			if(disponible>=saldo)
				importe=saldo
			else
				importe=disponible
			disponible-=importe
			println "Aplicando $importe Saldo: $saldo Disponible:$disponible"
			def ap=new CXCAplicacion(
				fecha:new Date()
				,total:importe
				,impuestoTasa:abono.impuestoTasa?:0.0
				,importe:0.0
				,impuesto:0.0
				,factura:fac)
			//ap.actualizarImportes()
			//fac.saldo-=ap.total
			abono.addToAplicaciones(ap)
		}
		def res=abono.save(failOnError:true)
		abono.actualizarAplicado()
		return res
	}
	
}

class CobranzaEnPagoException extends RuntimeException{
	CXCAbono pago
	String message
}
