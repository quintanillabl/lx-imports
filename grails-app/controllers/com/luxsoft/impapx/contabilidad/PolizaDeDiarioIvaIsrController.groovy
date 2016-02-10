package com.luxsoft.impapx.contabilidad


import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import util.Rounding;


import com.luxsoft.impapx.FacturaDeGastos;
import com.luxsoft.impapx.FacturaDeImportacion;
import com.luxsoft.impapx.Pedimento;
import com.luxsoft.impapx.TipoDeCambio;
import com.luxsoft.impapx.Venta;
import com.luxsoft.impapx.VentaDet;
import com.luxsoft.impapx.cxc.CXCAplicacion;
import com.luxsoft.impapx.cxc.CXCNota;
import com.luxsoft.impapx.cxp.Aplicacion;
import com.luxsoft.impapx.cxp.NotaDeCredito;
import com.luxsoft.impapx.tesoreria.Comision;
import com.luxsoft.impapx.tesoreria.CompraDeMoneda;
import com.luxsoft.impapx.tesoreria.Inversion;
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta;
import com.luxsoft.impapx.tesoreria.PagoProveedor;
import com.luxsoft.impapx.tesoreria.SaldoDeCuenta;
import com.luxsoft.impapx.tesoreria.Traspaso;

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
class PolizaDeDiarioIvaIsrController {

	def polizaService
	
	def index() {
		def sort=params.sort?:'fecha'
		def order=params.order?:'desc'
		def periodo=session.periodoContable
		
		def q = Poliza.where {
			subTipo == 'DIARIO_IVA_ISR' && ejercicio == periodo.ejercicio && mes == periodo.mes 

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
		Poliza poliza=new Poliza(tipo:'DIARIO',folio:1, fecha:dia,descripcion:'Poliza IVA-ISR'+dia.text(),partidas:[])
		poliza.ejercicio = session.periodoContable.ejercicio
		poliza.mes = session.periodoContable.mes
		poliza.subTipo= 'DIARIO_IVA_ISR'

		procesar(poliza ,dia)
		procesarIva(poliza, dia)
		//Salvar la poliza
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		poliza=polizaService.salvarPolizaDiario(poliza)
		redirect action: 'mostrarPoliza', params: [id:poliza.id]
	}
	
	
	private procesar(Poliza poliza ,Date dia){
		println 'Generando poliza de provision ISR..... Dia: '+dia.text()
		def asiento="PROVISION-ISR"
		int year=dia.toYear()
		int mes=dia.toMonth()
		
		def coeficientes=[:]
		coeficientes.put(1, 4.57)
		coeficientes.put(2, 4.57)
		coeficientes.put(3, 4.42)
		coeficientes.put(4, 4.42)
		coeficientes.put(5, 4.42)
		coeficientes.put(6, 4.42)
		coeficientes.put(7, 4.42)
		coeficientes.put(8, 4.42)
		coeficientes.put(9, 4.42)
		coeficientes.put(10, 4.42)
		coeficientes.put(11, 4.42)
		coeficientes.put(12, 4.42)
		
		//println 'Coeficientes: '+coeficientes
		
		def historia=[:]
		
		def isrAcumulado=0
		
		(1..mes).each{it->
			
			
			def isr=calcular(year,it,coeficientes.get(it))
			//println 'Mes: '+it+ " Isr: "+isr
			if(it==1){
				historia.put(it,Rounding.round(isr, 2))
			}else{
				
				def isrAnterior=historia.get(it-1)
				isrAcumulado+=isrAnterior
				historia.put(it, Rounding.round(isr-isrAcumulado,2))
			}
			
		}
		println historia
		def importe=historia.get(mes)
		poliza.addToPartidas(
			cuenta:CuentaContable.buscarPorClave("750-0002"),
			debe:importe,
			haber:0.0,
			asiento:asiento,
			descripcion:"Provision ISR ${year} - ${mes}",
			referencia:""
			,fecha:poliza.fecha
			,tipo:poliza.tipo
			,entidad:'SalodPorCuentaContable')
		poliza.addToPartidas(
			cuenta:CuentaContable.buscarPorClave("213-0001"),
			debe:0.0,
			haber:importe,
			asiento:asiento,
			descripcion:"Provision ISR ${year} - ${mes}",
			referencia:""
			,fecha:poliza.fecha
			,tipo:poliza.tipo
			,entidad:'SalodPorCuentaContable')
	}
	
	private calcular(int year,int mes,def coeficiente){	
		//println "Calculando para ${year} / ${mes} / Coef: ${coeficiente}"
		
		def saldos=SaldoPorCuentaContable.findAll("from SaldoPorCuentaContable s where s.cuenta.id in (28,35,210,225) and s.year=? and s.mes=?"
			,[year,mes])
		//println 'Registros encontrados: '+saldos
		
		def ingresosNominales=0
		def isrRetenidoBancario=0
		saldos.each {
			//println it.cuenta.id+" Sf:"+it.saldoFinal.abs()
			if(it.cuenta.id!=225l){
				ingresosNominales+=it.saldoFinal.abs()
			}
			if(it.cuenta.id==225l){
				isrRetenidoBancario+=it.saldoFinal.abs()
			}
		
		}
		
		def utilidadFiscalEstimada=ingresosNominales*(coeficiente/100)
		def pagoProvisionalAcumuladoIsr=utilidadFiscalEstimada*0.3
		def isrAPagar=pagoProvisionalAcumuladoIsr-isrRetenidoBancario
		
		//println 'IngresoNominal: '+ingresosNominales+ ' Utilidad fiscal estimada:'+utilidadFiscalEstimada+ 'Pago provisional acumulado: '+pagoProvisionalAcumuladoIsr
		
		return isrAPagar
		
	}
	
	private procesarIva(Poliza poliza ,Date dia){
		def asiento="DETERMINACION-IVA"
		int year=dia.toYear()
		int mes=dia.toMonth()
		def rows=PolizaDet
			.findAll("from PolizaDet p where p.cuenta.id in (61,62,63) and p.poliza.ejercicio=? and p.poliza.mes=?"
			,[year,mes])
		
		Map res=rows.groupBy({p-> p.cuenta?.id})
		//res.
		println 'Map keys: '+res.keySet()
		if(!res){
			log.info 'No ejecutar...'
		}
		

		def p61=res.get(61l)
		def p62=res.get(62l)
		def p63=res.get(63l)
		
		
		
		def row61
		def row62
		def row63
		
		def debe61=p61?.sum(0.0,{it.debe})
		def debe62=p62?.sum(0.0,{it.debe})
		def debe63=p63?.sum(0.0,{it.debe})
		
		
		if(p61){
			//Abono a
			poliza.addToPartidas(
				cuenta:row61?.cuenta,
				debe:0.0,
				haber:debe61,
				asiento:asiento,
				descripcion:"Determinacion IVA ${year} - ${mes}",
				referencia:""
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'PolizaDet')
		}
		if(p62){
			//Abono a
			poliza.addToPartidas(
				cuenta:row62?.cuenta,
				debe:0.0,
				haber:debe62,
				asiento:asiento,
				descripcion:"Determinacion IVA ${year} - ${mes}",
				referencia:""
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'PolizaDet')
		}
		if(p63){
			//Abono a
			poliza.addToPartidas(
				cuenta:row63?.cuenta,
				debe:0.0,
				haber:debe63,
				asiento:asiento,
				descripcion:"Determinacion IVA ${year} - ${mes}",
				referencia:""
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'PolizaDet')
		}
		
		if(p61 && p62 && p63){

			def ptotal=debe61+debe62+debe63
			//Abono a
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("208-0001"),
				debe:ptotal,
				haber:0.0,
				asiento:asiento,
				descripcion:"Determinacion IVA ${year} - ${mes}",
				referencia:""
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'PolizaDet')

		}
		
		
		
			
	}
	
}
