package com.luxsoft.impapx.contabilidad

import org.hibernate.AssertionFailure;

import util.Rounding;

import com.luxsoft.impapx.CuentaDeGastos;
import com.luxsoft.impapx.CuentaPorPagar;
import com.luxsoft.impapx.Embarque;
import com.luxsoft.impapx.EmbarqueDet;
import com.luxsoft.impapx.FacturaDeImportacion;
import com.luxsoft.impapx.GastosDeImportacion;
import com.luxsoft.impapx.Pedimento;
import com.luxsoft.impapx.TipoDeCambio;

class PolizaDeProvisionAnualController {
	
	def polizaService

   def index() {
	   redirect action: 'list', params: params
    }
	 
	def mostrarPoliza(long id){
		def poliza=Poliza.findById(id,[fetch:[partidas:'eager']])
		render (view:'/poliza/poliza2' ,model:[poliza:poliza,partidas:poliza.partidas])
	}
	 
	def list() {
		if(!session.periodoContable){
			PeriodoContable periodo=new PeriodoContable()
			periodo.actualizarConFecha()
			session.periodoContable=periodo
		}
		PeriodoContable periodo=session.periodoContable
		def sort=params.sort?:'fecha'
		def order=params.order?:'desc'
		
		def polizas=Poliza.findAllByTipoAndDescripcionLikeAndFechaBetween('COMPRAS'
			,'PROVISION ANUAL %'+periodo.year
			,periodo.inicio,periodo.fin,[sort:sort,order:order])
		[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
	}
	
	def generarPoliza(String fecha){
		Date dia=Date.parse("dd/MM/yyyy",fecha)
		
		params.dia=dia
		
		//Prepara la poliza
		Poliza poliza=new Poliza(tipo:'COMPRAS',folio:1, fecha:dia,descripcion:'PROVISION ANUAL '+dia.toYear(),partidas:[])
		// Procesadores
		procearCuentaPorPagarMateriaPrima(poliza, dia)
		//Salvar la poliza
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		poliza=polizaService.salvarPoliza(poliza)
		redirect action: 'mostrarPoliza', params: [id:poliza.id]
	}
	
	private procearCuentaPorPagarMateriaPrima(def poliza , def dia){
		
		def asiento="Provision "+dia.toYear()
		
		
		def facturas=FacturaDeImportacion.findAll("from FacturaDeImportacion f where f.provisionada=?",[dia.toYear()])
		
		facturas.each{ factura->
			
			//println entry
			def fechaF=factura.fecha.text()
			
			// 1. Cargo al inventario
			def cuenta=CuentaContable.buscarPorClave('119-0003')
			def fechaTc=factura.fecha-1
			def tipoDeCambioInstance=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=? and t.monedaFuente=?",[fechaTc,factura.moneda])
			def valor=factura.importe*tipoDeCambioInstance.factor
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:valor,
				haber:0.0,
				asiento:asiento,
				descripcion:"$factura.proveedor ($fechaF) $factura.importe T.C:$tipoDeCambioInstance.factor",
				referencia:"$factura.documento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CuentaPorPagar'
				,origen:factura.id)
			
			def clave="201-$factura.proveedor.subCuentaOperativa"
			cuenta=CuentaContable.buscarPorClave(clave)
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:0.0,
				haber:valor,
				asiento:asiento,
				descripcion:"$factura.proveedor ($fechaF) $factura.importe T.C:$tipoDeCambioInstance.factor",
				referencia:"$factura.documento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CuentaPorPagar'
				,origen:factura.id)
		}
	}
	
}
