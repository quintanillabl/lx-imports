package com.luxsoft.impapx

class Aduana {
	
	String nombre
	Direccion direccion
	static embedded =['direccion']

    static constraints = {
		nombre(blank:false,unique:true,maxSize:50)
		//direccion nullable:true
		
    }
	
	String toString(){
		return nombre;
	}
}
