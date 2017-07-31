package com.luxsoft.impapx.cxc

import com.luxsoft.impapx.Venta;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class CXCNotaDet {
	
	BigDecimal cantidad
	String unidad
	String numeroDeIdentificacion
	String descripcion
	BigDecimal valorUnitario
	BigDecimal importe
	String comentario
	Venta venta

	String claveUnidadSat
    String unidadSat
    String claveProdServ
	
	static belongsTo =[nota:CXCNota]

    static constraints = {
		cantidad(nullable:false,scale:2)
		unidad(nullable:false,maxSize:100)
		numeroDeIdentificacion(nullable:false,maxSize:50)
		descripcion(nullable:false,maxSize:200)
		valorUnitario(nullable:false,scale:2)
		importe(nullable:false,scale:2)
		comentario(nullable:false,maxSize:300)
		venta(nullable:true)
		unidadSat nullable: true
        claveUnidadSat nullable:true
        claveProdServ nullable: true
    }
	
	static mapping ={
		venta fetch:'join'
	}
	
	
	String toString(){
		return "$descripcion $cantidad $importe"
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(CXCNotaDet))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(descripcion, obj.descripcion)
		eb.append(cantidad, obj.cantidad)
		eb.append(importe, obj.importe)
		eb.append(comentario, obj.comentario)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(descripcion)
		hb.append(cantidad)
		hb.append(importe)
		hb.append(comentario)
		return hb.toHashCode()
	}
}
