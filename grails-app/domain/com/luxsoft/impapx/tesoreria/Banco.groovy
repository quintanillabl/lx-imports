package com.luxsoft.impapx.tesoreria

import com.luxsoft.impapx.CuentaBancaria
import com.luxsoft.lx.sat.BancoSat

class Banco {
	
	String nombre
	BancoSat bancoSat
	Boolean nacional = true
	
	static hasMany = [cuentas:CuentaBancaria]

    static constraints = {
		nombre(blank:false,size:5..150)
		bancoSat nullable:true
    }
	
	String toString(){
		return nombre
	}
}
