package com.luxsoft.impapx.tesoreria

import com.luxsoft.impapx.CuentaBancaria;

class SaldoDeCuenta {
	
	CuentaBancaria cuenta
	BigDecimal saldoInicial
	BigDecimal ingresos
	BigDecimal egresos
	BigDecimal saldoFinal
	BigDecimal saldoFinalMN
	BigDecimal tc
	int year
	int mes
	Date cierre
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		cierre(nullable:true)
		tc(scale:6)
    }
}
