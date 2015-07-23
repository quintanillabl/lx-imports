package com.luxsoft.impapx.cxc

import com.luxsoft.impapx.Venta;

class CancelacionDeCargo {
	
	Date fecha
	String usuario
	String comentario
	Venta cargo
	
	Date dateCreated

    static constraints = {
		fecha(nullable:false)
		usuario(nullable:false)
		comentario(maxSize:300)
		cargo(nullable:false)
    }
}
