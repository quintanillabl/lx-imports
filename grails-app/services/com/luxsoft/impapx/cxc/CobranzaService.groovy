package com.luxsoft.impapx.cxc

import org.apache.commons.lang.exception.ExceptionUtils

import util.MonedaUtils

import com.luxsoft.impapx.Requisicion
import com.luxsoft.impapx.Venta
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta
import com.luxsoft.impapx.TipoDeCambio


import grails.validation.ValidationException

class CobranzaService {

    def registrarPago(CXCPago pago) {
		
		try {
			pago.impuestoTasa=16.00
			pago.importe=MonedaUtils.calcularImporteDelTotal(pago.total)
			pago.impuesto=MonedaUtils.calcularImpuesto(pago.importe)
			//pago.actualizarImportes()
			def cuenta=pago.cuenta
			pago.moneda=cuenta?.moneda
			pago.tc=1
			pago.validate()

			if(cuenta.moneda!=MonedaUtils.PESOS){
				def dia=pago.fecha-1
				def tc=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=? and t.monedaFuente=?",[dia,cuenta.moneda])
				
				if(!tc){
					log.error "No existe Tipo de cambio  en ${pago.cuenta.moneda} para el ${dia.format('dd/MM/yyyy')} "
					throw new CobranzaEnPagoException(
						pago:pago,
						message:"No existe Tipo de cambio  en ${pago.cuenta.moneda} para el ${dia.format('dd/MM/yyyy')} "
						)
				}
				pago.tc=tc.factor
			}
			
			
			if(pago.instanceOf(CXCPago)){

				if(pago.formaDePago=='TRANSFERENCIA'){
					//Generamos el ingreso en tesoreria
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
					log.info 'Ingreso registrado: '+ingreso
				}

				pago.save(failOnError:true)
				log.info 'Cobro generado: '+pago
			}
			return pago
		} catch (ValidationException e) {
			throw new CobranzaEnPagoException(pago:pago,message:ExceptionUtils.getRootCauseMessage(e))
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
	
	def generarAplicacionesDeNota(Long id,def facturas) {
		
		CXCNota abono=CXCNota.get(id)
		
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
			abono.addToAplicaciones(ap)
		}
		def res=abono.save(failOnError:true)
		abono.actualizarAplicado()
		return res
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

	def eliminarAplicacionDeNota(Long id,def partidas){

		CXCNota nota=CXCNota.get(id)
		log.info 'Eliminando aplicaciones: '+partidas + ' De Nota: '+nota
		partidas.each {
			log.info 'Buscando aplicacion: '+it
			def found=nota.aplicaciones.find{ det->
				det.id==it.toLong()
			}
			if(found){
				boolean ok=nota.removeFromAplicaciones(found)
				log.info 'Eliminando aplicacion: '+found+ 'Res: '+ok
			}else{
				log.info 'No existe la aplicaicon: '+it
			}

		}
		def res=nota.save(failOnError:true)
		return res
	}
	
}

class CobranzaEnPagoException extends RuntimeException{
	CXCAbono pago
	String message
}
