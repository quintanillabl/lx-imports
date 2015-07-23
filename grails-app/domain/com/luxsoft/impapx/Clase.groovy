package com.luxsoft.impapx

import java.util.Date;

class Clase {
	
	String nombre;
	Date dateCreated;
	Date lastUpdated;

	static constraints = {
		nombre(size:3..20,blank:false,unique:true)
	}

	String toString(){
		nombre;
	}
    
}
