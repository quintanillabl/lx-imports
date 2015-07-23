package com.luxsoft.impapx.contabilidad

import java.util.Date;

class SaldoPorCuentaContable {
	
	CuentaContable cuenta;
	int year;
	int mes;
	Date fecha
	BigDecimal debe;
	BigDecimal haber;
	BigDecimal saldoInicial;
	BigDecimal saldoFinal;
	Date cierre;
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		debe(nullable:false,scale:6)
		haber(nullable:false,scale:6)
		saldoInicial(nullable:false,scale:6)
		saldoFinal(nullable:false,scale:6)
    }
	
	def beforeInsert(){
		if(mes!=13)
			mes=fecha.toMonth()
		year=fecha.toYear()
	}
}
