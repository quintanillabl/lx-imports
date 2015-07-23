package com.luxsoft.impapx

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import com.luxsoft.impapx.contabilidad.CuentaContable;

class Cliente {	
	
	String nombre
	String rfc
	Boolean fisica=false
	String email1
	String formaDePago
	String cuentaDePago
	Direccion direccion
	//CuentaContable cuentaContable
	String subCuentaOperativa
	
	Date dateCreated
	Date lastUpdated

	static embedded = ['direccion']
	
    static constraints = {
		nombre(blank:false,maxSize:255,unique:true)
		rfc(nullable:true)
		email1(nullable:true)
		formaDePago(nullable:true)
		cuentaDePago(nullable:true,maxSize:4)
		direccion(nullable:true)
		subCuentaOperativa(nullable:true,maxSize:4)
    }
	
	String toString(){
		return nombre;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(Cliente)) )
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(nombre, obj.nombre)
		return eb.isEquals()
	}
	@Override
	public int hashCode() {
		def hcb=new HashCodeBuilder(17,35)
		hcb.append(nombre)
		return hcb.toHashCode()
	}
}
