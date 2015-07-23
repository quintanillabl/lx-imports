package com.luxsoft.impapx.cxc

import com.luxsoft.impapx.CuentaBancaria;
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta;

class CXCPago extends CXCAbono{
	
	String formaDePago
	String referenciaBancaria
	Date fechaBancaria
	CuentaBancaria cuenta
	MovimientoDeCuenta ingreso

    static constraints = {
		formaDePago(inList:['TRANSFERENCIA','CHEQUE','EFECTIVO','DEPOSITO','TARJETA'])
		referenciaBancaria(nullable:true,maxSize:100)
		cuenta(nullable:true)
		ingreso(nullable:true)
    }
}
