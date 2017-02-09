package com.luxsoft.cfdi.retenciones

class TipoDeImpuesto {

	String clave
	String descripcion

    static constraints = {
    	clave minSize:2,maxSize:2,unique:true

    }

    String toString(){
    	return "$clave $descripcion"
    }
}
