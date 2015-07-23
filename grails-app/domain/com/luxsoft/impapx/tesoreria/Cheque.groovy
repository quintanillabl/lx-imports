package com.luxsoft.impapx.tesoreria

import com.luxsoft.impapx.CuentaBancaria;

class Cheque {
	
	CuentaBancaria cuenta
	int folio
	Date fechaImpresion
	MovimientoDeCuenta egreso
	Date cancelacion
	String comentarioCancelacion

    static constraints = {
		fechaImpresion(nullable:true)
		cancelacion nullable:true
		comentarioCancelacion nullable:true
    }
	
	static mapping ={
		egreso fetch:'join'
		//id name:'folio', generator:'assigned'
	}
}
