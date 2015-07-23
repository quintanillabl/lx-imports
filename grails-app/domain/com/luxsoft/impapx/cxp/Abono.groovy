package com.luxsoft.impapx.cxp

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import util.MonedaUtils;

import com.luxsoft.impapx.Proveedor;

class Abono {
	
	Proveedor proveedor
	Date fecha
	Currency moneda=Currency.getInstance('MXN')
	BigDecimal tc=1
	BigDecimal importe=0
	BigDecimal impuestos=0
	BigDecimal total=0
	BigDecimal disponible=0
	BigDecimal impuestoTasa=0
	String comentario
	List aplicaciones
	BigDecimal aplicado
	static hasMany = [aplicaciones:Aplicacion]
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		importe(scale:2)
		impuestos(scale:2)
		total(scale:2)
		tc(scale:4,validator:{ val,obj ->
			if(obj.moneda!=MonedaUtils.PESOS && val<=1.0)
				return "tipoDeCambioError"
			else
				return true
		})
		impuestoTasa(scale:2)
		comentario(nullable:true,maxSize:200)
    }
	
	static mapping ={
		proveedor fetch:'join'
		//requisitado formula:'select sum(x.total) from requisicion'
		aplicado formula:'(select sum(x.total) from aplicacion x where x.abono_id=id)'
		aplicaciones cascade: "all-delete-orphan"
	}
	
	static transients = ['disponible']
	
	String toString(){
		return " (${fecha?.format('dd/MM/yyyy')})  Total: ${total} ($moneda)";
	}
	
	
	BigDecimal getTotalMN(String property){
		return "${property}"*tc
		
	}
	
	BigDecimal getDisponible(){
		def aplic=aplicado?:0.0
		return total-aplic
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(Abono)) )
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		return eb.isEquals()
	}
	@Override
	public int hashCode() {
		def hcb=new HashCodeBuilder(17,35)
		hcb.append(id)
		return hcb.toHashCode()
	}
}
