package com.luxsoft.impapx

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import util.MonedaUtils;

class TipoDeCambio {
	
	Date fecha
	Currency monedaOrigen=MonedaUtils.PESOS
	Currency monedaFuente=MonedaUtils.DOLARES
	BigDecimal factor
	String fuente='BANCO DE MEXICO'
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		fecha(nullable:false)
		monedaOrigen(nullable:false)
		monedaFuente(nullable:false,validator:{ val,obj ->
			if(val==obj.monedaOrigen)
				return "mismaMonedaError";
		})
		factor(scale:6)
		fuente(maxSize:200)
    }
	
	String toString(){
		return "$monedaOrigen $monedaFuente    $factor "
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(TipoDeCambio))
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
