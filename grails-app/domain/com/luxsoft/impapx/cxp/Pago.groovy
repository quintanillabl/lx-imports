package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.tesoreria.PagoProveedor;

class Pago extends Abono{
	
	static belongsTo = [pagoProveedor:PagoProveedor]

    static constraints = {
    }
	
	// static mapping = {
	// 	pagoProveedor fetch:'join'
	// }
}
