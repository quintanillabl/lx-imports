package com.luxsoft.impapx.contabilidad


import java.util.Date

import org.apache.commons.lang.time.DateUtils


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

class PolizaDeImpuestosController {

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
		
		def polizas=Poliza.findAllByTipoAndFechaBetween('DIARIO',periodo.inicio,periodo.fin,[sort:sort,order:order])
		//[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
		render (view:'/poliza/list2',model:[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size(),polizaListTitle:'Polizas de impuestos (Diario)'])
	}
	
	def generarPoliza(String fecha){
		Date dia=Date.parse("dd/MM/yyyy",fecha)
		params.dia=dia
		//Prepara la poliza
		Poliza poliza=new Poliza(tipo:'DIARIO',folio:1, fecha:dia,descripcion:'Poliza de Impuestos '+dia.asPeriodoText(),partidas:[])
		// Procesadores
		
		
		
		//Salvar la poliza
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		poliza=polizaService.salvarPoliza(poliza)
		redirect action: 'mostrarPoliza', params: [id:poliza.id]
	}
	
	def procesarX(def poliza,def dia,def asiento){
		def finDeMes=dia.finDeMes().clearTime()
		if(dia.clearTime()!=finDeMes)
			return
		
		def saldos=SaldoPorCuentaContable.executeQuery("",[])
		
		
	}
	
	
}
