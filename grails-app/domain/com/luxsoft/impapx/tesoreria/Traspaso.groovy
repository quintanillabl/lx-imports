package com.luxsoft.impapx.tesoreria

import java.util.Date;

import com.luxsoft.impapx.CuentaBancaria;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class Traspaso {
	
	static auditable = true

	Date fecha
	CuentaBancaria cuentaOrigen
	CuentaBancaria cuentaDestino
	Currency moneda
	BigDecimal importe
	BigDecimal comision
	BigDecimal impuesto
	String comentario
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [movimientos:MovimientoDeCuenta]

    static constraints = {
		cuentaDestino validator:{val, obj ->
			if(obj.cuentaOrigen==val)
				return "mismaCuentaError"
			if(obj.cuentaOrigen.moneda!=val.moneda)
				return "diferenteMonedaError"
			
		}
		comentario(blank:true)
    }
	
	static mapping ={
		cuentaOrigen fetch:'join'
		cuentaDestino fetch:'join'
		movimientos cascad:"all-delete-orphan"
	}
	
	String toString(){
		return "$fecha $cuentaOrigen  A $cuentaDestino $importe"
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(Traspaso))
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
