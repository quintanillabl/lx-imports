package com.luxsoft.impapx.tesoreria

class Inversion extends Traspaso{
	
	Date rendimientoFecha

	BigDecimal rendimientoReal=0

	BigDecimal rendimientoCalculado=0

	BigDecimal rendimientoImpuesto=0
	
	BigDecimal tasa

	BigDecimal tasaIsr

	BigDecimal importeIsr=0
	
	int plazo
	
	Date vencimiento
	

    static constraints = {
		rendimientoFecha(nullable:true)
		vencimiento(validator:{val,obj ->
			if(val<obj.fecha)
				return "vencimientoInvalido" 
		})
		cuentaDestino validator:{val, obj ->
			if(obj.cuentaOrigen!=val)
				return "mismaCuentaError"
		}
    }
	
	
	
	
}
