package com.luxsoft.impapx

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class DistribucionDet {
	
	String sucursal
	EmbarqueDet embarqueDet
	String contenedor

	int tarimas=0 // Lo que trae embarque det por default editable
	BigDecimal cantidad=0 // Lo q trae el embarque det
	BigDecimal cantidadPorTarima=0 // cantidad/tarimas
	BigDecimal kilosNetos=0 // Lo que tree el embaque por default en kilos netos/1000
	String comentarios
	String instrucciones
	
	Date programacionDeEntrega
	Date fechaDeEntrada
	
	static belongsTo = [distribucion:Distribucion]

    static constraints = {
		cantidadPorTarima(scale:3)
		comentarios(nullable:true)
		sucursal(nullable:false,blank:false,maxSize:30)
		instrucciones(nullable:true,maxSize:150)
		programacionDeEntrega(nullable:true)
		fechaDeEntrada(nullable:true)
    }
	
	static mapping={
		embarqueDet fetch:'join'

	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(DistribucionDet)) )
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(sucursal, obj.sucursal)
		eb.append(embarqueDet, obj.embarqueDet)
		eb.append(contenedor, obj.contenedor)
		return eb.isEquals()
	}
	@Override
	public int hashCode() {
		def hcb=new HashCodeBuilder(17,35)
		hcb.append(id)
		hcb.append(sucursal)
		hcb.append(embarqueDet)
		hcb.append(contenedor)
		return hcb.toHashCode()
	}
}
