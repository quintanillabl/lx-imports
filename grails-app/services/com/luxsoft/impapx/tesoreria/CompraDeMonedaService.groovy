package com.luxsoft.impapx.tesoreria

import util.MonedaUtils;

import com.luxsoft.impapx.cxp.Aplicacion
import com.luxsoft.impapx.cxp.Pago
import grails.validation.ValidationException;

class CompraDeMonedaService {
	
	def pagoProveedorService

    def registrarCompra(CompraDeMoneda c) {
		//c.moneda=MonedaUtils.PESOS
		c.actualizarDiferenciaCambiaria();
		try {
			println 'Salvando compra ...'
			def requisicion=c.requisicion
			PagoProveedor pago=new PagoProveedor(requisicion:c.requisicion,fecha:c.fecha,comentario:'PAGO DE COMPRA DE MONEDA ',cuenta:c.cuentaOrigen)
			//pagoProveedorService.registrarEgreso(pago)
			
			 MovimientoDeCuenta egreso = new MovimientoDeCuenta(cuenta:pago.cuenta
				,fecha:pago.fecha
				,moneda:pago.cuenta.moneda
				,tc:requisicion.tc
				,importe:requisicion.total.abs()*-1.0
				,ingreso:false
				,tipo:requisicion.formaDePago
				,origen:'TESORERIA'
				,concepto:"T.C.:$c.tipoDeCambioCompra $requisicion.proveedor.nombre "
				,comentario:'COMPRA DE MONEDA REQUISICION:'+requisicion.id)
			 
			 MovimientoDeCuenta ingreso = new MovimientoDeCuenta(cuenta:c.cuentaDestino
				 ,fecha:c.fecha
				 ,moneda:c.cuentaDestino.moneda
				 ,tc:c.tipoDeCambioCompra
				 ,importe:requisicion.total.abs()/c.tipoDeCambioCompra
				 ,ingreso:true
				 ,tipo:requisicion.formaDePago
				 ,origen:'TESORERIA'
				 ,concepto:"T.C.:$c.tipoDeCambioCompra $requisicion.proveedor.nombre "
				 ,comentario:'COMPRA DE MONEDA REQUISICION:'+requisicion.id)
			 
			def cxp=new Pago(
				proveedor:requisicion.proveedor
				,fecha:pago.fecha
				,moneda:pago.cuenta.moneda
				,tc:requisicion.tc
				,importe:requisicion.importe
				,impuestos:requisicion.impuestos
				,total:requisicion.total
				,comentario:'COMPRA DE MONEDA REQUISICION:'+requisicion.id
				,pagoProveedor:pago
				,aplicado:0.0)
			pago.requisicion.partidas.each{
				if(it.factura){
					def aplicacion=new Aplicacion(
						fecha:new Date()
						,factura:it.factura
						,total:it.total
						,importe:0.0
						,impuesto:0.0
						,impuestoTasa:0.0)
					
					cxp.addToAplicaciones(aplicacion)
				}
			}
			pago.egreso=egreso
			
			pago.pago=cxp
			pago.save()
			c.ingreso=ingreso
			c.pagoProveedor=pago
			c.save(failOnError:true)
		} catch (Exception e) {
			e.printStackTrace()
			return c;
		}
		
    }
	
	def registrarCompra2(CompraDeMoneda c) {
		//c.moneda=MonedaUtils.PESOS
		c.actualizarDiferenciaCambiaria();
		println 'Salvando compra ...'
			def requisicion=c.requisicion
			PagoProveedor pago=new PagoProveedor(requisicion:c.requisicion,fecha:c.fecha,comentario:'PAGO DE COMPRA DE MONEDA ',cuenta:c.cuentaOrigen)
			//pagoProveedorService.registrarEgreso(pago)
			
			 MovimientoDeCuenta egreso = new MovimientoDeCuenta(cuenta:pago.cuenta
				,fecha:pago.fecha
				,moneda:pago.cuenta.moneda
				,tc:requisicion.tc
				,importe:requisicion.total.abs()*-1.0
				,ingreso:false
				,tipo:requisicion.formaDePago
				,origen:'TESORERIA'
				,concepto:"T.C.:$c.tipoDeCambioCompra $requisicion.proveedor.nombre "
				,comentario:'COMPRA DE MONEDA REQUISICION:'+requisicion.id)
			 
			 MovimientoDeCuenta ingreso = new MovimientoDeCuenta(cuenta:c.cuentaDestino
				 ,fecha:c.fecha
				 ,moneda:c.cuentaDestino.moneda
				 ,tc:c.tipoDeCambioCompra
				 ,importe:requisicion.total.abs()/c.tipoDeCambioCompra
				 ,ingreso:true
				 ,tipo:requisicion.formaDePago
				 ,origen:'TESORERIA'
				 ,concepto:"T.C.:$c.tipoDeCambioCompra $requisicion.proveedor.nombre "
				 ,comentario:'COMPRA DE MONEDA REQUISICION:'+requisicion.id)
			 
			def cxp=new Pago(
				proveedor:requisicion.proveedor
				,fecha:pago.fecha
				,moneda:pago.cuenta.moneda
				,tc:requisicion.tc
				,importe:requisicion.importe
				,impuestos:requisicion.impuestos
				,total:requisicion.total
				,comentario:'COMPRA DE MONEDA REQUISICION:'+requisicion.id
				,pagoProveedor:pago
				,aplicado:0.0)
			pago.requisicion.partidas.each{
				if(it.factura){
					def aplicacion=new Aplicacion(
						fecha:new Date()
						,factura:it.factura
						,total:it.total
						,importe:0.0
						,impuesto:0.0
						,impuestoTasa:0.0)
					
					cxp.addToAplicaciones(aplicacion)
				}
			}
			pago.egreso=egreso
			pago.tipoDeCambio=c.tipoDeCambioCompra
			pago.pago=cxp
			pago.save(failOnError:true)
			c.ingreso=ingreso
			c.pagoProveedor=pago
			c.save(failOnError:true)
		
	}
	
}
