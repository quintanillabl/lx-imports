package com.luxsoft.econta.polizas

import grails.transaction.Transactional

import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
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

@Transactional
class PolizaDeFleteService extends ProcesadorService{

    def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de flete "

        def dia = poliza.fecha

            	
    	def asiento="GASTO CHOFERES"
    
    	def facturasList=FacturaDeGastos.executeQuery("from FacturaDeGastos f where date(f.fecha)=? and f.retImp>0",[dia])
    	
    	def facturas=new HashSet()
            	
    	facturasList.each{ fac->
    		fac.conceptos.each{
    			if(it.concepto.clave.startsWith("600-F"))
    				facturas.add(fac)
    		}
    	}
            	
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
    					cuenta:CuentaContable.buscarPorClave("205-P001"),
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
    					cuenta:CuentaContable.buscarPorClave("205-P001"),
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
    					cuenta:CuentaContable.buscarPorClave("205-P001"),
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
    			cuenta:CuentaContable.buscarPorClave("118-0001"),
    			debe:fac.impuestos-fac.retImp,
    			haber:0.0,
    			asiento:asiento,
    			descripcion:"Fac: $fac.documento ${fac.fecha.text()} ${fac.proveedor}",
    			referencia:"$fac.documento"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'FacturaDeGasto'
    			,origen:fac.id)
    		
    		//Cargo a Retencion Flete
    		poliza.addToPartidas(
    			cuenta:CuentaContable.buscarPorClave("119-0003"),
    			debe:fac.retImp,
    			haber:0.0,
    			asiento:asiento,
    			descripcion:"Fac: $fac.documento ${fac.fecha.text()} ${fac.proveedor}",
    			referencia:"$fac.documento"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'FacturaDeGasto'
    			,origen:fac.id)
    		
    		//Abono a Retencion Flete
    		poliza.addToPartidas(
    			cuenta:CuentaContable.buscarPorClave("216-0001"),
    			debe:0.0,
    			haber:fac.retImp,
    			asiento:asiento,
    			descripcion:"Fac: $fac.documento ${fac.fecha.text()} ${fac.proveedor}",
    			referencia:"$fac.documento"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'FacturaDeGasto'
    			,origen:fac.id)
    		
    		//Abono a Acredores
    		poliza.addToPartidas(
    			cuenta:CuentaContable.buscarPorClave("205-F001"),
    			debe:0.0,
    			haber:fac.total-(fac.descuento?:0.0)-(fac.rembolso?:0.0)-(fac.otros?:0.0),
    			asiento:asiento,
    			descripcion:"Pago Fac: $fac.documento "+fac.fecha.text()+ " $fac.proveedor",
    			referencia:"$fac.documento"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'FacturaDeGasto'
    			,origen:fac.id)
		}
        procesarComplementos(poliza)
        cuadrar(poliza)
		depurar(poliza)
		save poliza
    }

    def procesarComplementos(def poliza){
        poliza.partidas.each {polizaDet ->
            def cxp = com.luxsoft.impapx.CuentaPorPagar.get(polizaDet.origen)
            if(cxp?.comprobante){
                def cfdi = cxp.comprobante
                def comprobante = new ComprobanteNacional(
                  polizaDet:polizaDet,
                  uuidcfdi:cfdi.uuid,
                  rfc: cfdi.emisorRfc,
                  montoTotal: cfdi.total,
                  moneda: cxp.moneda.getCurrencyCode(),
                  tipCamb: cxp.tc
                )
                polizaDet.comprobanteNacional = comprobante
            }
            
        }
    }

    
}
