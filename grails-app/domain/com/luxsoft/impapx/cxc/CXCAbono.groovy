package com.luxsoft.impapx.cxc



import com.luxsoft.impapx.Cliente
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import util.MonedaUtils;
import util.Rounding;

class CXCAbono {
	
	Date fecha
	Cliente cliente
	Currency moneda
	BigDecimal tc
	BigDecimal importe
	BigDecimal impuesto
	BigDecimal total
	BigDecimal aplicado=0
	BigDecimal disponible=0
	BigDecimal disponibleMN=0
	BigDecimal impuestoTasa=16
	String comentario
	List aplicaciones
	static hasMany= [aplicaciones:CXCAplicacion]

    static constraints = {
		importe(scale:2)
		impuesto(scale:2)
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
		cliente fetch:'join'
		aplicaciones cascade:"all-delete-orphan"
	}
	
	static transients = ['disponibleMN']
	
	public BigDecimal getDisponibleMN(){
		//return Rounding.round(disponible*tc,2)
		//return disponible
		def totalmn=Rounding.round(total*tc,2)
		return totalmn-aplicado
	}
	
	def actualizarImportes(){
		def imp=impuestoTasa/100
		if(imp>0){
			def ff=1+imp
			this.importe=total/ff
			this.impuesto=importe*imp
		}
	}
	
	def actualizarAplicado(){
		//disponible=Rounding.round(total*tc,2)
		if(aplicaciones){
			aplicado=aplicaciones.sum(0,{it.total})
			//disponible=total-aplicado
		}else
			aplicado=0.0
	}
	
	def beforeInsert(){
		
	}
	
	def beforeUpdate(){
		actualizarAplicado()
	}
	
	String toString(){
		return " Folio: $id $fecha?.format('dd/MM/yyyy') $cliente    $total ($moneda)"
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(CXCAbono))
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
