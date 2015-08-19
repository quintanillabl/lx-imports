package com.luxsoft.impapx.cxc


import java.util.Currency;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import com.luxsoft.impapx.Venta;



class CXCAplicacion {
	
	static auditable = true
	
	Date fecha
	BigDecimal importe
	BigDecimal impuesto
	BigDecimal total
	
	BigDecimal impuestoTasa
	String comentario
	Venta factura
	
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = [abono:CXCAbono]

    static constraints = {
		importe(scale:2)
		impuesto(scale:2)
		total(scale:2)
		
		impuestoTasa(scale:2)
		comentario(nullable:true,maxSize:200)
		factura(nullable:true)
    }
	static mapping ={
		abono fetch:'join'
		//cuentaPorPagar fetch:'join'
	}
	
	String toString(){
		return " Folio: $id  $fecha   $total"
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(CXCAplicacion))
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
	
	def actualizarImportes(){
		def imp=impuestoTasa/100
		if(imp>0){
			def ff=1+imp
			this.importe=0
			this.impuesto=0
			//(this.importe=total)/ff
			//(this.impuesto=importe)*imp
		}else{
			this.importe=0
			this.impuesto=0
		}
	}
	
	def beforeInsert(){
		
	}
}
