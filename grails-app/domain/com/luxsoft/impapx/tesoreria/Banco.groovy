package com.luxsoft.impapx.tesoreria

import com.luxsoft.impapx.CuentaBancaria;

class Banco {
	
	String nombre
	
	static hasMany = [cuentas:CuentaBancaria]

    static constraints = {
		nombre(blank:false,size:5..150)
    }
	
	String toString(){
		return nombre
	}
}
