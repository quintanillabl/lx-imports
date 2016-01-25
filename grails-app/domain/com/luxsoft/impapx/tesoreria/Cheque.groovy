package com.luxsoft.impapx.tesoreria

import com.luxsoft.impapx.CuentaBancaria;

class Cheque {

	static auditable = true
	
	CuentaBancaria cuenta
	int folio
	Date fechaImpresion
	MovimientoDeCuenta egreso
	Date cancelacion
	String comentarioCancelacion

	// Date dateCreated
	// Date lastUpdated

	// String creadoPor
	// String modificadoPor

    static constraints = {
		fechaImpresion(nullable:true)
		cancelacion nullable:true
		comentarioCancelacion nullable:true
		egreso(nullable:true)
		// egreso(nullable:false,validator:{val,object->
		// 	if(val.cuenta.tipo!='CHEQUERA'){
		// 		return 'tipoDeEgreso'
		// 	}
		// })
    }
	
	
}
