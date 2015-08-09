package com.luxsoft.lx.sat

import groovy.transform.EqualsAndHashCode


@EqualsAndHashCode(includes='clave')
class BancoSat {

	String clave
	String nombreCorto
	String razonSocial
	

    static constraints = {
    	clave nullable:false,unique:true,maxSize:20
    }
    

    String toString(){
    	return "$clave $nombreCorto"
    }
}

