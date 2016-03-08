package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.Venta
import com.luxsoft.cfdi.*
import com.luxsoft.impapx.EmbarqueDet
import com.luxsoft.impapx.FacturaDeGastos
import com.luxsoft.impapx.TipoDeCambio
import com.luxsoft.impapx.tesoreria.Cheque
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta
import com.luxsoft.impapx.tesoreria.PagoProveedor
import util.Rounding

@Transactional
class PolizaDeIngresoVariosService {

        def procesar(Poliza poliza){
            poliza.descripcion = "Poliza de ingreso varios ${poliza.fecha.text()} "
            def dia = poliza.fecha

            def asiento='DEVOLUCION ANTICIPO'
            
            def ingresos = MovimientoDeCuenta.findAll{date(fecha) == poliza.fecha && cuentaDeudora!=null}    

            ingresos.each { egreso ->

    			def fp=egreso.tipo.substring(0,2)
    			
    			def req=pago.requisicion
    			def descripcion="$fp-${egreso.referenciaBancaria?:''} $req.proveedor ($req.concepto) id:$egreso.id"
    			Poliza poliza=build(dia,descripcion)

    			def clave="201-$req.proveedor.subCuentaOperativa"
    			def asiento='ANTICIPO IMPORTACION'
    	        if(req.concepto == 'ANTICIPO_COMPLEMENTO'){
    	            asiento = 'ANTICIPO COMPLEMENTO'
    	        }

    			
    			//Abono a bancos
    			def cuentaDeBanco=pago.egreso.cuenta
    			if(cuentaDeBanco.cuentaContable==null)
    				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
    			poliza.addToPartidas(
    				cuenta:cuentaDeBanco.cuentaContable,
    				debe:0.0,
    				haber:pago.egreso.importe.abs()*pago.egreso.tc,
    				asiento:asiento,
    				descripcion:"$pago.egreso.cuenta ",
    				referencia:"$pago.egreso.referenciaBancaria",
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'PagoProveedor'
    				,origen:pago.id)
    			
    			//Cargo a proveedor
    			def proveedor=pago.requisicion.proveedor
    			clave="107-$proveedor.subCuentaOperativa"
    			def cuenta=CuentaContable.findByClave(clave)
    			if(!cuenta) throw new RuntimeException("No existe la cuenta para el proveedor: "+proveedor.nombre+ 'Clave: '+clave)
    			def requisicion=pago.requisicion
    			requisicion.partidas.each{ reqDet ->
    				poliza.addToPartidas(
    					cuenta:cuenta,
    					debe:reqDet.importe.abs()*pago.egreso.tc,
    					haber:0.0,
    					asiento:asiento,
    					descripcion:"$pago.egreso.cuenta Ref:$reqDet.documento REq: $requisicion.id ",
    					referencia:"$pago.egreso.referenciaBancaria"
    					,fecha:poliza.fecha
    					,tipo:poliza.tipo
    					,entidad:'PagoProveedor'
    					,origen:pago.id)
    			}
            }
            
        	cuadrar(poliza)
    		depurar(poliza)
    		save poliza
            
        }
}
