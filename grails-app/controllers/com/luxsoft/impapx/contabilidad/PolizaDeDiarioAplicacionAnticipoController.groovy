package com.luxsoft.impapx.contabilidad


import org.apache.commons.lang.time.DateUtils
import util.Rounding
import com.luxsoft.impapx.EmbarqueDet
import com.luxsoft.impapx.FacturaDeGastos
import com.luxsoft.impapx.FacturaDeImportacion
import com.luxsoft.impapx.Pedimento
import com.luxsoft.impapx.TipoDeCambio
import com.luxsoft.impapx.Venta
import com.luxsoft.impapx.VentaDet
import com.luxsoft.impapx.cxc.CXCAplicacion
import com.luxsoft.impapx.cxc.CXCNota
import com.luxsoft.impapx.cxp.Aplicacion
import com.luxsoft.impapx.cxp.NotaDeCredito
import com.luxsoft.impapx.tesoreria.Comision
import com.luxsoft.impapx.tesoreria.CompraDeMoneda
import com.luxsoft.impapx.tesoreria.Inversion
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta
import com.luxsoft.impapx.tesoreria.PagoProveedor
import com.luxsoft.impapx.tesoreria.SaldoDeCuenta
import com.luxsoft.impapx.tesoreria.Traspaso

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
class PolizaDeDiarioAplicacionAnticipoController {

	def polizaService
	
    def index() {
    	def sort=params.sort?:'fecha'
    	def order=params.order?:'desc'
    	def periodo=session.periodoContable
    	
    	def q = Poliza.where {
    		subTipo == 'DIARIO_ANTICIPO' && ejercicio == periodo.ejercicio && mes == periodo.mes 

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
		
		def finDeMes=dia.finDeMes().clearTime()
		if(dia.clearTime()!=finDeMes){
			flash.message='Solo se puede ejcurar el fin de mes'
			redirect action:'index'
			return
		}
		
		
		//Prepara la poliza
		Poliza poliza=new Poliza(tipo:'DIARIO',folio:1, fecha:dia,descripcion:'Aplicacion de anticipo'+dia.text(),partidas:[])
		poliza.ejercicio = session.periodoContable.ejercicio
		poliza.mes = session.periodoContable.mes
		poliza.subTipo= 'DIARIO_ANTICIPO'
		procesar(poliza ,dia)
		
		//Salvar la poliza
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		poliza=polizaService.salvarPolizaDiario(poliza)
		redirect action: 'mostrarPoliza', params: [id:poliza.id]
	}
	
	
	private procesar(Poliza poliza ,Date dia){
		
		def pagos=PagoProveedor.findAll(
			"from PagoProveedor p where year(p.egreso.fecha)=? and month(p.egreso.fecha)=? and p.egreso.moneda='USD' and p.egreso.origen=? and p.requisicion.concepto='PAGO'"
			,[dia.toYear(),dia.toMonth(),'CXP'])
		
		pagos.each{ pago->
			
			//Prepara la poliza
			def fp=pago.egreso.tipo.substring(0,2)
			def egreso=pago.egreso
			
			def req=pago.requisicion
			
			def asiento='PAGO CXP'
			def difAcu=0
			req.partidas.each{ det->
				def fac=det.factura
				def embarqueDet=EmbarqueDet.find("from EmbarqueDet x where x.factura=?",[fac])
				def pedimento=embarqueDet.pedimento
				def fechaDocto=fac.fecha.text()
				if(pedimento){
					if(pedimento.fecha.toMonth()== egreso.fecha.toMonth() ){
						
						def importeTcPedimento=Rounding.round(det.total*pedimento.tipoDeCambio,2)
						//Cargo al proveedor
						poliza.addToPartidas(
							cuenta:CuentaContable.buscarPorClave("201-$req.proveedor.subCuentaOperativa"),
							debe:importeTcPedimento,
							haber:0.0,
							asiento:asiento,
							descripcion:"F Fac:$fechaDocto Imp:$det.total TC:$pedimento.tipoDeCambio F.Ped:"+pedimento?.fecha.text()+' F.Pag:'+egreso?.fecha.text(),
							referencia:"$fac.documento"
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'FacturaDeImportacion'
							,origen:fac.id)
						//Abono a anticipos
						def importeTcPago=Rounding.round(det.total*egreso.tc,2)
						poliza.addToPartidas(
							cuenta:CuentaContable.buscarPorClave("111-$req.proveedor.subCuentaOperativa"),
							debe:0.0,
							haber:importeTcPago,
							asiento:asiento,
							descripcion:"F Fac:$fechaDocto Imp:$det.total TC:$egreso.tc F.Ped:"+pedimento?.fecha.text()+' F.Pag:'+egreso?.fecha.text(),
							referencia:"$fac.documento"
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'FacturaDeImportacion'
							,origen:fac.id)
						def dif=importeTcPago-importeTcPedimento
						difAcu+=dif
					}
				}
			}
			//Diferencia cambiaria
			if(difAcu.abs()>0){
				def clave=difAcu<0?'701-0002':'705-0002'
				poliza.addToPartidas(
					cuenta:CuentaContable.buscarPorClave(clave),
					debe:difAcu>0?difAcu.abs():0.0,
					haber:difAcu<0?difAcu.abs():0.0,
					asiento:asiento,
					descripcion:"Pago a proveedor: $req.proveedor",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
			}
			
		}
		
	}
	
	
	
}
