package com.luxsoft.impapx

/**
 * Factura correspondiente a los gastos y servicios aduanales relacionados con un
 * embarque. La particularidad de este tipo de factura es que impacta en el costo 
 * de venta de todos los articulos contenidos en un embarque
 * 
 * @author Ruben Cancino
 *
 */
class GastosDeImportacion extends CuentaPorPagar{
	
	
	boolean incrementable=false
	
    static constraints = {
		incrementable(nullable:true)
    }
}
