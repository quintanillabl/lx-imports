package com.luxsoft.impapx


class Unidad {
	
	String clave
	String nombre
	BigDecimal factor
	String claveUnidadSat
    String unidadSat

    static constraints = {
		clave(blank:false,size:1..3,unique:true)
		nombre(blank:false,size:1..50,unique:true)
		factor(nullable:false)
		unidadSat nullable: true
        claveUnidadSat nullable:true
    }
	
	String toString(){
		return nombre
	}
}
