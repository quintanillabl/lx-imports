package com.luxsoft.impapx.contabilidad

import util.MonedaUtils;
import util.Rounding;

import com.luxsoft.impapx.cxc.CXCAplicacion;
import com.luxsoft.impapx.cxc.CXCPago;
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
class PolizaDeIngresosController {

    def polizaService

    def index() {
    	def sort=params.sort?:'fecha'
    	def order=params.order?:'desc'
    	def periodo=session.periodoContable
    	
    	def q = Poliza.where {
    		subTipo == 'INGRESO' && ejercicio == periodo.ejercicio && mes == periodo.mes 

    	}
    	respond q.list(params)
    	
    }
     
    def mostrarPoliza(long id){
    	def poliza=Poliza.findById(id,[fetch:[partidas:'eager']])
    	render (view:'/poliza/poliza2' ,model:[poliza:poliza,partidas:poliza.partidas])
    }
	
	
	 
	
	
	def generarPoliza(String fecha){
		Date dia=Date.parse("dd/MM/yyyy",fecha)
		
		params.dia=dia
		
		
		def asiento='CXC COBRO'
		def descripcion="Registro de cobro de factura"
		Poliza poliza=new Poliza(tipo:'INGRESO',fecha:dia,descripcion:descripcion,partidas:[])
		poliza.ejercicio = session.periodoContable.ejercicio
		poliza.mes = session.periodoContable.mes
		poliza.subTipo= 'INGRESO'
		
		def pagos=CXCPago.findAll("from CXCPago p where date(p.fecha)=? ",[dia])
		
		pagos.each{ pago->
			
			//Cargo al banco
			def desc="Cobro de facturas $pago.formaDePago "
			if(pago.moneda==MonedaUtils.DOLARES){
				desc+="$pago.total * $pago.tc"
			}
			//def desc="Cobro de facturas "
			poliza.addToPartidas(
				cuenta:pago.cuenta.cuentaContable,
				debe:pago.total.abs()*pago.tc,
				haber:0.0,
				asiento:asiento,
				descripcion:desc,
				referencia:"$pago.referenciaBancaria"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CXCPago'
				,origen:pago.id)
			
			pago.aplicaciones.each{aplic->
				
				//Abono a clientes
				def clave="105-"+aplic.abono.cliente.subCuentaOperativa
				poliza.addToPartidas(
					cuenta:CuentaContable.buscarPorClave(clave),
					debe:0.0,
					haber:aplic.total,
					asiento:asiento,
					descripcion:"Cobro Fac: ${aplic?.factura?.facturaFolio} ${pago.tc>1?'T.C'+pago.tc:''} ",
					referencia:"$pago.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'CXCAplicacion'
					,origen:aplic.id)
				
				
				
			}
			
			//Abono al IVA por trasladar
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("208-0001"),
				debe:0.0,
				haber:pago.impuesto.abs()*pago.tc,
				asiento:asiento,
				descripcion:"Cobro de facturas",
				referencia:"$pago.referenciaBancaria"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CXCPago'
				,origen:pago.id)
			
			pago.aplicaciones.each{aplic->
				
				//Cargo a IVA por trasladar
				def importe=MonedaUtils.calcularImporteDelTotal(aplic.total)
				def impuesto=Rounding.round(MonedaUtils.calcularImpuesto(importe),2)
				
				poliza.addToPartidas(
					cuenta:CuentaContable.buscarPorClave("209-0001"),
					debe:impuesto,
					haber:0.0,
					asiento:asiento,
					descripcion:"Cobro Fac: ${aplic?.factura?.facturaFolio} ${pago.tc>1?'T.C'+pago.tc:''} ",
					referencia:"$pago.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'CXCAplicacion'
					,origen:aplic.id)
			}
			
			//IETU
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("902-0001"),
				debe:pago.importe.abs()*pago.tc,
				haber:0.0,
				asiento:asiento,
				descripcion:"Cobro de facturas",
				referencia:"$pago.referenciaBancaria"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CXCPago'
				,origen:pago.id)
			
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("903-0001"),
				debe:0.0,
				haber:pago.importe.abs()*pago.tc,
				asiento:asiento,
				descripcion:"Cobro de facturas",
				referencia:"${pago.referenciaBancaria}"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CXCPago'
				,origen:pago.id)
			
			
		}
		
		//Salvar la poliza
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		//poliza.folio=polizaService.nextFolio(poliza)
	//	poliza.save(failOnError:true)
		poliza=polizaService.salvarPoliza(poliza)
		redirect action: 'index'
	}
}
