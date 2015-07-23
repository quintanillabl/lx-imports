package com.luxsoft.impapx.tesoreria

import com.luxsoft.impapx.CuentaBancaria;
import com.luxsoft.impapx.Proveedor;
import com.luxsoft.impapx.Requisicion;
import com.luxsoft.impapx.cxp.Pago;

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import util.MonedaUtils;

class PagoProveedor {
	
	Date fecha
	CuentaBancaria cuenta
	Requisicion requisicion
	String comentario
	MovimientoDeCuenta egreso
	BigDecimal tipoDeCambio
	
	Date dateCreated
	Date lastUpdated
	
	static hasOne = [pago:Pago]

    static constraints = {
		comentario(nullable:true,maxSize:200)
		requisicion(unique:true)
		cuenta(nullable:false,validator:{ val,obj ->
			if(val.moneda!=obj.requisicion.moneda)
				return "tipoDeMonedaError"
		})
		
    }
	
	static mapping ={
		requisicion fetch:'join'
		egreso fetch:'join'
		pago fetch:'join'
	}
	
	String toString(){
		return "${id}  ($requisicion) "
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(PagoProveedor))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(requisicion, obj.requisicion)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(requisicion)
		return hb.toHashCode()
	}
	
	
}
