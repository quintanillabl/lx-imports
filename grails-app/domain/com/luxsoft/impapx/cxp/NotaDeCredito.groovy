package com.luxsoft.impapx.cxp

class NotaDeCredito extends Abono{

	//static auditable = true
	
	String documento
	String concepto
	
	

    static constraints = {
		concepto(inList:['DEVOLUCION','DESCUENTO_FINANCIERO','DESCUENTO','DESCUENTO_ANTICIPO','BONIFICACION','REBATE'])
		documento(nullable:true,maxSize:20)
		//DEVLUCION,DESCUENTO_FINANCIERO,DESCUENTO,DESCUENTO_ANTICIPO,BONIFICACION
		//
    }
	
}
