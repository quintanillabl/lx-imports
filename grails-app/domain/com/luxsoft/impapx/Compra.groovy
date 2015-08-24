package com.luxsoft.impapx

import java.util.Currency;

class Compra {

	static auditable = true
	
	Proveedor proveedor
	Date fecha
	Date entrega
	Date depuracion
	String folio
	String origen
	String comentario
	Currency moneda=Currency.getInstance('MXN');
	BigDecimal tc=1
	BigDecimal importe=0
	BigDecimal descuentos=0
	BigDecimal subtotal=0
	BigDecimal impuestos=0
	BigDecimal total=0
	
	List partidas
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [partidas:CompraDet]

    static constraints = {
		proveedor(nullable:false)
		fecha(nullable:false)
		entrega(nullable:true)
		depuracion(nullable:true)
		comentario(size:3..255)
		moneda(nullable:false,size:3)
		tc(nullable:false,scale:4)
		folio(nullable:true)
		origen(nullable:true,size:3..255)
		
		importe(nullable:false,scale:2)
		descuentos(nullable:false,scale:2)
		subtotal(nullable:false,scale:2)
		impuestos(nullable:false,scale:2)
		total(nullable:false,scale:2)
    }
	
	static mapping ={
		proveedor fetch:'join'
		//requisitado formula:'select sum(x.total) from requisicion'
		
		partidas cascade: "all-delete-orphan"
	}
}
