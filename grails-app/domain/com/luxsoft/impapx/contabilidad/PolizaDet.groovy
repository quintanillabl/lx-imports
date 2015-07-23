package com.luxsoft.impapx.contabilidad

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class PolizaDet {
	
	CuentaContable cuenta
	BigDecimal debe
	BigDecimal haber
	String asiento
	String descripcion
	String referencia
	Long origen
	String entidad
	Date fecha
	String tipo
	String concepto
	
	static belongsTo = [poliza:Poliza]

    static constraints = {
		tipo(inList:['INGRESO','EGRESO','DIARIO','COMPRAS','GENERICA','CIERRE_ANUAL'])
		entidad(nullable:true,maxSize:50)
		origen(nullable:true)
		concepto(nullable:true,maxSize:50)
    }
	
	static mapping ={
		poliza fetch:'join'
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(PolizaDet))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(id, obj.asiento)
		eb.append(id, obj.descripcion)
		eb.append(id, obj.referencia)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(asiento)
		hb.append(descripcion)
		hb.append(referencia)
		return hb.toHashCode()
	}
	
}
