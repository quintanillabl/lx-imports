package com.luxsoft.lx.sat

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes='claveProdServ')
class ProductoSat {
	
	String claveProdServ
    String descripcion
    

    static constraints = {
		claveProdServ unique:true
		descripcion maxSie:255
    }
	
	String toString(){
		return "${clave} - ${descripcion}"
	}
	
}
