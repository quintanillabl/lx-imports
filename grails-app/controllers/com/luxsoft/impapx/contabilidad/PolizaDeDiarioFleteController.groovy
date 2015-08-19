package com.luxsoft.impapx.contabilidad

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

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
class PolizaDeDiarioFleteController {

	def polizaService
	
    def beforeInterceptor = {
    	if(!session.periodoContable){
    		session.periodoContable=new Date()
    	}
	}

	def cambiarPeriodo(){
		def fecha=params.date('fecha', 'dd/MM/yyyy')
		session.periodoContable=fecha
		redirect(uri: request.getHeader('referer') )
	}	
	
	def index() {
		def sort=params.sort?:'fecha'
		def order=params.order?:'desc'
		def periodo=session.periodoContable
		def polizas=Poliza.findAllByTipoAndDescripcionIlikeAndFechaBetween(
			'DIARIO',
			'%Flete%',
			periodo.inicioDeMes(),
			periodo.finDeMes(),
			[sort:sort,order:order]
			)
		[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
	}    
	 
	def mostrarPoliza(long id){
		def poliza=Poliza.findById(id,[fetch:[partidas:'eager']])
		render (view:'poliza' ,model:[poliza:poliza,partidas:poliza.partidas])
	}
	 
	
	
	def generarPoliza(String fecha){
		Date dia=Date.parse("dd/MM/yyyy",fecha)
		
		params.dia=dia
		
		//println 'Generando poliza: '+params
		
		//Prepara la poliza
		Poliza poliza=new Poliza(tipo:'DIARIO',folio:1, fecha:dia,descripcion:'Poliza (Flete)'+dia.text(),partidas:[])
		
		procesarGastosChoferes(poliza ,dia)
		
		//Salvar la poliza
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		poliza=polizaService.salvarPolizaDiario(poliza)
		redirect action: 'mostrarPoliza', params: [id:poliza.id]
	}
	
	
	private procesarGastosChoferes(Poliza poliza ,Date dia){
			
		def asiento="GASTO CHOFERES"
		
	//	def facturasList=FacturaDeGastos.executeQuery("from FacturaDeGastos f where year(f.fecha)=? and month(f.fecha)=? and f.retImp>0",[dia.toYear(),dia.toMonth()])
		def facturasList=FacturaDeGastos.executeQuery("from FacturaDeGastos f where date(f.fecha)=? and f.retImp>0",[dia])
		
		//println 'Factuars de chofer en el periodo: '+facturasList.size()
		def facturas=new HashSet()
		
		facturasList.each{ fac->
			fac.conceptos.each{
				if(it.concepto.clave.startsWith("600-F"))
					facturas.add(fac)
			}
		}
		println 'Facturas de chofer: '+facturas.size()
		
		facturas.each{ fac->
			fac.conceptos.each{c->
				//Cargo a gasto concepto
				if(c.importe>0){
					poliza.addToPartidas(
						cuenta:c.concepto,
						debe:c.importe,
						haber:0.0,
						asiento:asiento,
						descripcion:"$c.descripcion",
						referencia:"$fac.documento"
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'FacturaDeGasto'
						,origen:fac.id)
				}
				
				
				if(c.descuento>0){
					poliza.addToPartidas(
						cuenta:CuentaContable.buscarPorClave("203-P004"),
						debe:0.0,
						haber:c.descuento,
						asiento:asiento,
						descripcion:"Prestamo Fac: $fac.documento "+fac.fecha.text()+" $fac.proveedor",
						referencia:"$fac.documento"
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'FacturaDeGasto'
						,origen:fac.id)
				}
				
				if(c.rembolso>0){
					poliza.addToPartidas(
						cuenta:CuentaContable.buscarPorClave("203-P004"),
						debe:0.0,
						haber:c.rembolso,
						asiento:asiento,
						descripcion:"Vale  "+c?.fechaRembolso?.text()+" $fac.proveedor",
						referencia:"$fac.documento"
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'FacturaDeGasto'
						,origen:fac.id)
				}
				
				if(c.otros>0){
					poliza.addToPartidas(
						cuenta:CuentaContable.buscarPorClave("203-P004"),
						debe:0.0,
						haber:c.otros,
						asiento:asiento,
						descripcion:"Varios Fac: $fac.documento "+fac.fecha.text()+" $fac.proveedor "+c.comentarioOtros,
						referencia:"$fac.documento"
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'FacturaDeGasto'
						,origen:fac.id)
				}
				
				
			}
			//Cargo a IVA Gasto
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("117-0001"),
				debe:fac.impuestos-fac.retImp,
				haber:0.0,
				asiento:asiento,
				descripcion:"Fac: $fac.documento "+fac.fecha.text(),
				referencia:"$fac.documento"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'FacturaDeGasto'
				,origen:fac.id)
			
			//Cargo a Retencion Flete
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("117-0009"),
				debe:fac.retImp,
				haber:0.0,
				asiento:asiento,
				descripcion:"Fac: $fac.documento "+fac.fecha.text(),
				referencia:"$fac.documento"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'FacturaDeGasto'
				,origen:fac.id)
			
			//Abono a Retencion Flete
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("205-0002"),
				debe:0.0,
				haber:fac.retImp,
				asiento:asiento,
				descripcion:"Fac: $fac.documento "+fac.fecha.text(),
				referencia:"$fac.documento"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'FacturaDeGasto'
				,origen:fac.id)
			
			//Abono a Acredores
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("203-F001"),
				debe:0.0,
				haber:fac.total-(fac.descuento?:0.0)-(fac.rembolso?:0.0)-(fac.otros?:0.0),
				asiento:asiento,
				descripcion:"Pago Fac: $fac.documento "+fac.fecha.text()+ " $fac.proveedor",
				referencia:"$fac.documento"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'FacturaDeGasto'
				,origen:fac.id)
			
			 
			 
			//IETUs
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("900-0003"),
				debe:fac.importe,
				haber:0.0,
				asiento:asiento,
				descripcion:"$fac.documento "+fac.fecha.text(),
				referencia:"$fac.documento"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'FacturaDeGasto'
				,origen:fac.id)
			
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("901-0003"),
				debe:0.0,
				haber:fac.importe,
				asiento:asiento,
				descripcion:"$fac.documento "+fac.fecha.text(),
				referencia:"$fac.documento"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'FacturaDeGasto'
				,origen:fac.id)
		}
	}
	
}
