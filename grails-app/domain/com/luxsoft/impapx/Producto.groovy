package com.luxsoft.impapx

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.luxsoft.lx.sat.ProductoSat

class Producto {
	
	String clave
	String descripcion
	Unidad unidad
	
	Linea linea
	Marca marca
	Clase clase

	BigDecimal kilos
	BigDecimal gramos
	BigDecimal largo
	BigDecimal ancho
	int calibre
	int caras
	String acabado
	String color
	BigDecimal m2

	
	BigDecimal precioCredito
	BigDecimal precioContado

	String claveProdServ
    String claveUnidadSat
    String unidadSat

    ProductoSat productoSat
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		clave (blank:false,unique:true,size:2..10)
		descripcion(blank:false,size:5..250)
		unidad(nullable:false)
		linea(nullable:false)
		marca(nullable:true)
		clase(nullable:true)
		acabado(nullable:true)
		color(nullable:true)
		//claveProdServ nullable:true
        //claveUnidadSat nullable:true
        //unidadSat nullable: true
        productoSat nullable:true
    }
	
	static mapping ={
		sort "clave"
		unidad fetch:'join'
	}

	static transients = ['claveProdServ','claveUnidadSat','unidadSat']
	
	
	String getClaveProdServ(){
		return productoSat.claveProdServ
	}
	String getClaveUnidadSat(){
		return unidad.claveUnidadSat
	}
	String getUnidadSat(){
		return unidad.unidadSat
	}
	

	String toString(){
		return "${clave} - ${descripcion}"
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(Producto)) )
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(clave, obj.clave)
		return eb.isEquals()
	}
	@Override
	public int hashCode() {
		def hcb=new HashCodeBuilder(17,35)
		hcb.append(clave)
		return hcb.toHashCode()
	}
	
	
}
