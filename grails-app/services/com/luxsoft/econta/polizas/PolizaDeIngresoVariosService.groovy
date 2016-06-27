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
import com.luxsoft.impapx.cxp.Anticipo
import util.Rounding

@Transactional
class PolizaDeIngresoVariosService extends ProcesadorService{

        def procesar(Poliza poliza){
            poliza.descripcion = "Poliza de ingreso varios ${poliza.fecha.text()} "
            def dia = poliza.fecha
            
            def anticipos = Anticipo.findAll('from Anticipo a where date(a.sobrante.fecha) = ?',[dia])

            def asiento='ANTICIPO DEVOLUCION'

            anticipos.each { anticipo ->

                def ingreso = anticipo.sobrante
                def req = anticipo.requisicion
    			def fp=ingreso.tipo.substring(0,2)
    			
    			
    			def descripcion="$fp-${ingreso.referenciaBancaria?:''} $req.proveedor ($req.concepto) id:$ingreso.id"
    			
    			def clave="201-$req.proveedor.subCuentaOperativa"
    			
    	       
    			
                //Cargo bancos
    			def cuentaDeBanco=ingreso.cuenta
    			if(cuentaDeBanco.cuentaContable==null)
    				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
    			poliza.addToPartidas(
    				cuenta:cuentaDeBanco.cuentaContable,
    				debe: ingreso.importe.abs()*ingreso.tc,
    				haber:0.0,
    				asiento:asiento,
    				descripcion:"$req.proveedor ",
    				referencia:"$ingreso.referenciaBancaria",
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'Anticipo'
    				,origen:anticipo.id)
    			
    			//abono a proveedor
    			def proveedor=req.proveedor
    			clave="107-$proveedor.subCuentaOperativa"
    			def cuenta=CuentaContable.findByClave(clave)
    			
                if(!cuenta) throw new RuntimeException("No existe la cuenta para el proveedor: "+proveedor.nombre+ 'Clave: '+clave)

                def reqDet = req.partidas.first()
    			
    			poliza.addToPartidas(
                    cuenta:cuenta,
                    debe: 0.0,
                    haber: ingreso.importe.abs()*ingreso.tc,
                    asiento:asiento,
                    descripcion:"Ref: ${reqDet?.documento} Req: ${req.id} ",
                    referencia:"$ingreso.referenciaBancaria",
                    fecha:poliza.fecha,
                    tipo:poliza.tipo,
                    entidad:'Anticipo',
                    origen:anticipo.id)
            }

            if(!anticipos){
                asiento='DEVOLUCION PROVEEDOR'
                log.info 'Buscando saldos deudores'
                def ingreso = MovimientoDeCuenta.find('from MovimientoDeCuenta m where date(m.fecha) = ? and m.concepto = ? ',[dia,'SALDO_DEUDOR'])
                assert ingreso, "No existe un ingreso (MovimientoDeCuenta de tipo SALDO_DEUDOR para el dia: $dia"
                //Cargo bancos
                def cuentaDeBanco=ingreso.cuenta
                if(cuentaDeBanco.cuentaContable==null)
                    throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
                poliza.addToPartidas(
                    cuenta:cuentaDeBanco.cuentaContable,
                    debe: ingreso.importe.abs()*ingreso.tc,
                    haber:0.0,
                    asiento:asiento,
                    descripcion:"$ingreso.cuenta TC: ${ingreso.tc}",
                    referencia:"$ingreso.referenciaBancaria",
                    ,fecha:poliza.fecha
                    ,tipo:poliza.tipo
                    ,entidad:'MovimientoDeCuenta'
                    ,origen:ingreso.id)
                
                //abono a proveedor
                def clave=ingreso.comentario
                def cuenta=CuentaContable.findByClave(clave)
                
                if(!cuenta) 
                    throw new RuntimeException("No existe la cuenta: "+clave)
                
                poliza.addToPartidas(
                    cuenta:cuenta,
                    debe: 0.0,
                    haber: ingreso.importe.abs()*ingreso.tc,
                    asiento:asiento,
                    descripcion:"Devolucion TC: ${ingreso.tc}",
                    referencia:"$ingreso.referenciaBancaria",
                    fecha:poliza.fecha,
                    tipo:poliza.tipo,
                    entidad:'MovimientoDeCuenta',
                    origen:ingreso.id)
            }
            
        	cuadrar(poliza)
    		depurar(poliza)
    		save poliza
            
        }
}
