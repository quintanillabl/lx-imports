package com.luxsoft.lx.sat

import groovy.transform.EqualsAndHashCode


@EqualsAndHashCode(includes='codigo')
class CuentaSat {

	String codigo
	String nombre
	String tipo
    Integer nivel
	

    static constraints = {
    	codigo nullable:false,unique:true,maxSize:20
    	tipo maxSize:100,nullable:true
    }
    

    String toString(){
    	return "$codigo $nombre"
    }
}

