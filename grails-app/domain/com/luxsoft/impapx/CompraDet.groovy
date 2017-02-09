package com.luxsoft.impapx

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class CompraDet {

	static auditable = true
	
	Producto producto
	BigDecimal solicitado
	BigDecimal entregado=0
	
	
	BigDecimal precio
	BigDecimal descuento=0
	BigDecimal importeDescuento=0
	BigDecimal importe=0
	
	BigDecimal getPendiente(){solicitado-entregado}
	
	static belongsTo = [compra:Compra]
	
    static constraints = {
		producto(nullable:false)
		solicitado(nullable:false)
		descuento(scale:2)
    }
	static transients =['pendiente']

	static mapping ={
		entregado formula:'(select ifnull(sum(x.cantidad),0) from embarque_det x where x.compra_det_id=id)'
		
	}
	
	String toString(){
		return "${producto?.clave}  ${producto?.descripcion} Com: ${compra?.folio} Pend: ${pendiente} Sol:${solicitado} Rec:${entregado}"
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(CompraDet))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		return hb.toHashCode()
	}
	
}
