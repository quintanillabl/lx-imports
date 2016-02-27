package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.tesoreria.*


@Transactional
class PolizaDeCompraDolaresService extends ProcesadorService{

	def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de compra de dolares "

        def dia = poliza.fecha
        procesarCompraDeMonedaExtranjera poliza,poliza.fecha
        
    }

    private procesarCompraDeMonedaExtranjera(def poliza,def dia){
        def asiento='COMPRA MONEDA'
        
        def compras=CompraDeMoneda.findAll("from CompraDeMoneda c where date(c.fecha)=?",[dia])
        
        compras.each{ compra ->
            
            
            
            //Cargo a la cuenta destino
            def cuentaDeBanco=compra.cuentaDestino
            if(cuentaDeBanco.cuentaContable==null)
                throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
            poliza.addToPartidas(
                cuenta:cuentaDeBanco.cuentaContable,
                debe:compra.ingreso.importe.abs()*compra.tipoDeCambioCompra,
                haber:0.0,
                asiento:asiento,
                descripcion:"T.C: $compra.tipoDeCambioCompra $compra.ingreso.importe ($compra.cuentaDestino.moneda )",
                referencia:"$compra.pagoProveedor.egreso.referenciaBancaria",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CompraDeMoneda'
                ,origen:compra.id)
            
            //Abono a la cuenta origen
            cuentaDeBanco=compra.cuentaOrigen
            if(cuentaDeBanco.cuentaContable==null)
                throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
            poliza.addToPartidas(
                cuenta:cuentaDeBanco.cuentaContable,
                debe:0.0,
                haber:compra.pagoProveedor.egreso.importe.abs(),
                asiento:asiento,
                descripcion:"T.C: $compra.tipoDeCambioCompra $compra.ingreso.importe ($compra.cuentaDestino.moneda )",
                referencia:"$compra.pagoProveedor.egreso.referenciaBancaria",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CompraDeMoneda'
                ,origen:compra.id)
            
        }
    }
}
