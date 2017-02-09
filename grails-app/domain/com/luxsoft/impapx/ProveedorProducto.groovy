package com.luxsoft.impapx

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class ProveedorProducto {
	
	Producto producto
	String codigo
	BigDecimal gramos
	String descripcion
	BigDecimal costoUnitario
	
	static belongsTo = [proveedor:Proveedor]

    static constraints = {
		producto(nullable:false)
		
		codigo(nullable:true,size:0..250)
		descripcion(nullable:true,size:0..250)
		costoUnitario(nullable:false)
		gramos(nullable:true)
    }

    static mapping={
    	sort "codigo"
		producto fetch:'join'
		proveedor fetch:'join'
    }
	
	@Override
	public String toString() {
		return "${producto.clave} - ${producto.descripcion} Codigo: ${codigo}"
	}
	
	int hashCode() {
		def hcb=new HashCodeBuilder(17,37)
		hcb.append(producto)
		//hcb.append(codigo)
		return hcb.toHashCode()
	}
	
	boolean equals(Object obj) {
		if(!( obj instanceof ProveedorProducto))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		//eb.append(producto,obj.producto)
		eb.append(codigo,obj.codigo)
		return eb.isEquals()
	}
	
	
}
