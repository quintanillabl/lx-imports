package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.Empresa



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

            def descripcion = "$compra.requisicion.proveedor ${compra.fecha.text()} $compra.ingreso.importe T.C.$compra.tipoDeCambioCompra "
            def referencia = "${compra.pagoProveedor.egreso.referenciaBancaria?: 'ND'}"
            
            //Cargo a la cuenta destino
            def cuentaDestino = compra.cuentaDestino
           
            def importe = compra.ingreso.importe.abs()*compra.tipoDeCambioCompra
            //(def poliza,def cuenta,def importe,def descripcion,def asiento,def referencia,def entidad)
            def det = cargoA(
                poliza,
                cuentaDestino.cuentaContable,
                importe,
                descripcion, 
                asiento, 
                referencia, 
                compra)
            addComplemento(det, compra)
            
            //Abono a la cuenta origen
            // compra.pagoProveedor.egreso.importe.abs()
            def cuentaOrigen = compra.cuentaOrigen
            
            def det2 = abonoA(
                poliza,
                cuentaOrigen.cuentaContable,
                importe,
                descripcion, 
                asiento, 
                referencia, 
                compra)
            addComplemento(det2, compra)
            
        }
    }

    def addComplemento(def polizaDet, def entidad){
        def empresa = Empresa.first()
        def rfc=empresa.rfc
        
        def compra = entidad
        
        def bancoOrigenNacional = compra.cuentaOrigen.banco.nacional?compra.cuentaOrigen.banco.bancoSat:null
        def bancoOrigenExt = compra.cuentaOrigen.banco.nacional?null:compra.cuentaOrigen.banco.nombre
        
        def bancoDestinoNacional = compra.cuentaDestino.banco.bancoSat ?compra.cuentaDestino.banco.bancoSat : null
        def bancoDestinoExt = compra.cuentaDestino.banco.nacional ? compra.cuentaDestino.banco.bancoSat:compra.cuentaDestino.banco.nombre
       
        log.info('Generando complemento.....' + entidad.class)
        log.info('Generando registro de Transaccion transferencia SAT para compra de moneda: ' + entidad.id)
       
        def transferencia=new TransaccionTransferencia(
            polizaDet:polizaDet,
            bancoOrigenNacional:bancoOrigenNacional,
            bancoOrigenExtranjero: bancoOrigenExt, 
            cuentaOrigen:compra.cuentaOrigen.numero,
            fecha:compra.fecha,
            beneficiario:empresa.nombre,
            rfc:rfc,
            monto:compra.pagoProveedor.egreso.importe.abs(),
            bancoDestinoNacional: bancoDestinoNacional,
            bancoDestinoExtranjero: bancoDestinoExt,
            cuentaDestino: compra.cuentaDestino.numero
        )
        polizaDet.transaccionTransferencia=transferencia
        transferencia.validate()
        log.info(transferencia.errors)
       /* 
        
       
        assert  compra.cuentaOrigen.banco.bancoSat, 'No se ha registrado el banco  SAT para :' +  compra.cuentaOrigen.banco
        
        
        
        
        println 'Tr: ' +transferencia
        //
        
        */
    }
}
