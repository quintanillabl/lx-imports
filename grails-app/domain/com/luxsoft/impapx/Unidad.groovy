package com.luxsoft.impapx


class Unidad {
	
	String clave
	String nombre
	BigDecimal factor

    static constraints = {
		clave(blank:false,size:1..3,unique:true)
		nombre(blank:false,size:1..50,unique:true)
		factor(nullable:false)
    }
	
	String toString(){
		return nombre
	}
}
