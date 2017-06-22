package com.luxsoft.impapx.cxp



import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import com.luxsoft.impapx.CuentaPorPagar
import com.luxsoft.impapx.cxp.ComprobanteFiscal

class Aplicacion {
	
	Date fecha
	BigDecimal importe
	BigDecimal impuesto
	BigDecimal total
	
	BigDecimal impuestoTasa
	String comentario
	
	Date dateCreated
	Date lastUpdated

	
	
	static belongsTo = [abono:Abono,factura:CuentaPorPagar]

    static constraints = {
		importe(scale:2)
		impuesto(scale:2)
		total(scale:2)
		impuestoTasa(scale:2)
		comentario(nullable:true,maxSize:200)
		
    }
	
	/*
	static mapping ={
		abono fetch:'join'
		factura fetch:'join'
	}
	*/
	
	String toString(){
		return " Folio: $id  $fecha   $total"
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(Aplicacion))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(fecha, obj.fecha)
		eb.append(total, obj.total)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(fecha)
		hb.append(total)
		return hb.toHashCode()
	}
}
