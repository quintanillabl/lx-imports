package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.tesoreria.*

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
    			def cuentaDeBanco=traspaso.cuentaDestino
    			if(cuentaDeBanco.cuentaContable==null)
    				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
    			
    			poliza.addToPartidas(
    				cuenta:traspaso.cuentaDestino.cuentaContable,
    				debe:traspaso.importe.abs(),
    				haber:0.0,
    				asiento:asiento,
    				descripcion:"$traspaso.cuentaDestino ",
    				referencia:traspaso.comentario,
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'Traspaso'
    				,origen:traspaso.id)
    			
    			//Abono a la cuenta origen
    			cuentaDeBanco=traspaso.cuentaOrigen
    			if(cuentaDeBanco.cuentaContable==null)
    				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
    			
    			poliza.addToPartidas(
    				cuenta:traspaso.cuentaOrigen.cuentaContable,
    				debe:0.0,
    				haber:traspaso.importe.abs(),
    				asiento:asiento,
    				descripcion:"$traspaso.cuentaOrigen  ",
    				referencia:traspaso.comentario,
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'Traspaso'
    				,origen:traspaso.id)
    		}
    		
    		
    	}
    }

    
}
