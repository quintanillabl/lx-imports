package com.luxsoft.impapx

import grails.validation.Validateable;


@Validateable
class LineaDeCredito {
	
	Currency moneda
	BigDecimal importe
	int plazo

	
    static constraints = {
		moneda(nullable:false)
		importe(nullable:false,scale:2)
		palzo(nullable:false)
    }
}
