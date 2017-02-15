package com.luxsoft.nomina

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes='claveSat')
class NominaAsimiladoDet {


	String clave

	String descripcion

	String claveSat

	String tipo 

	BigDecimal importeGravado=0.00

	BigDecimal importeExcento=0.00

    static constraints = {
    	claveSat minSize: 1, maxSize: 5
    	tipo inList:['PERCEPCION','DEDUCCION']

    }

    static belongsTo = [nominaAsimilado: NominaAsimilado]	

    static transients = ['total']


    String toString(){
    	"$descripcion Grabado: $importeGravado Excento: $importeExcento "
    }
	
	BigDecimal getTotal() {
		return importeExcento + importeGravado
	}
}
