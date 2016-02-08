package com.luxsoft.econta.polizas


import com.luxsoft.impapx.contabilidad.Poliza
import grails.transaction.Transactional

@Transactional
class PolizaDeComprasService extends ProcesadorService{

	

	def procesar(Poliza poliza){
        
        log.info "Generando poliza de $poliza.subTipo para el ${poliza.fecha.text()}"

        
    }

	
   
}
