package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.Empresa

@Transactional
class PolizaDeTraspasosService extends ProcesadorService{



	def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de traspasos e inversiones "

        def dia = poliza.fecha
        log.info "Procesando poliza de $poliza.subTipo para el ${dia.text()}"
        procesarTraspasosBancarios poliza
         
        
    }

    private procesarTraspasosBancarios(def poliza){
    	def dia = poliza.fecha
    	def asiento='TRASPASO BANCARIO'
    	
    	def traspasos=Traspaso.findAll("from Traspaso c where date(c.fecha)=?",[dia])
    	
    	traspasos.each{ traspaso ->
    		
    		
    		if(traspaso.comentario!='RE-INVERSION AUTOMATICA'){
    			//Cargo a la cuenta destino
    			def cuentaDestino=traspaso.cuentaDestino
    			if(cuentaDestino.cuentaContable==null)
    				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDestino")
                    
    			def det = cargoA(poliza,cuentaDestino.cuentaContable,traspaso.importe,traspaso.comentario, asiento, traspaso.comentario, traspaso)
    			addComplemento(det, traspaso)
                /*
                poliza.addToPartidas(
    				cuenta:traspaso.cuentaDestino.cuentaContable,
    				debe:traspaso.importe.abs(),
    				haber:0.0,
    				asiento:asiento,
    				descripcion:traspaso.comentario,
    				referencia:traspaso.comentario,
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'Traspaso'
    				,origen:traspaso.id)
    			*/
    			//Abono a la cuenta origen
    			def cuentaOrigen=traspaso.cuentaOrigen
    			if(cuentaOrigen.cuentaContable==null)
    				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaOrigen")
    			det = abonoA(poliza,cuentaOrigen.cuentaContable,traspaso.importe,traspaso.comentario, asiento, traspaso.comentario, traspaso)
                addComplemento(det, traspaso)
                /*
    			poliza.addToPartidas(
    				cuenta:traspaso.cuentaOrigen.cuentaContable,
    				debe:0.0,
    				haber:traspaso.importe.abs(),
    				asiento:asiento,
    				descripcion:traspaso.comentario,
    				referencia:traspaso.comentario,
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'Traspaso'
    				,origen:traspaso.id)
                    */
    		}
    		
    		
    	}
    }

    def addComplemento(def polizaDet, def entidad){
        log.info('Generando complemento.....' + entidad.class)
        def empresa = Empresa.first()
        if(entidad.instanceOf(Traspaso)){
            def inversion = entidad
            def bancoOrigen = inversion.cuentaOrigen.banco.bancoSat
            def bancoDestino = inversion.cuentaDestino.banco.bancoSat

            if(bancoOrigen && bancoDestino){
                log.info('Generando registro de Transaccion transferencia SAT para inversion: '+inversion.id)
                log.info("Transferencia entre bano $bancoOrigen y $bancoDestino")
                def rfc=empresa.rfc
                def transferencia=new TransaccionTransferencia(
                    polizaDet:polizaDet,
                    bancoOrigenNacional:bancoOrigen,
                    cuentaOrigen:inversion.cuentaOrigen.numero,
                    fecha:inversion.fecha,
                    beneficiario:empresa.nombre,
                    rfc:rfc,
                    monto:inversion.importe.abs(),
                    bancoDestinoNacional: bancoDestino,
                    cuentaDestino: inversion.cuentaDestino.numero,
                    moneda: inversion.moneda.toString()
                )
               polizaDet.transaccionTransferencia=transferencia
            }
        }
    }

    
}
