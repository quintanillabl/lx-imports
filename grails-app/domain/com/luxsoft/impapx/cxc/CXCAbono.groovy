package com.luxsoft.impapx.cxc



import com.luxsoft.impapx.Cliente
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import util.MonedaUtils
import util.Rounding

class CXCAbono {
	
	static auditable = true

	Date fecha
	Cliente cliente
	Currency moneda
	BigDecimal tc=1
	BigDecimal importe=0
	BigDecimal impuesto=0
	BigDecimal total=0
	BigDecimal totalMN=0
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
	
	static transients = ['disponibleMN','disponible','totalMN']

	BigDecimal getTotalMN(){
		return Rounding.round(total*tc,2)
	}

	BigDecimal getAplicadoMN(){
		return Rounding.round(getAplicadoCalculado()*tc,2)
	}
	
	public BigDecimal getDisponibleMN(){
		return getTotalMN()-getAplicadoMN()
	}

	BigDecimal getDisponible(){
		return total-getAplicadoCalculado()
	}
	
	def actualizarImportes(){
		def imp=impuestoTasa/100
		if(imp>0){
			def ff=1+imp
			this.importe=total/ff
			this.impuesto=importe*imp
		}
	}

	def BigDecimal getAplicadoCalculado(){
		if(aplicaciones){
			return aplicaciones.sum(0,{it.total/tc})
		}else
			return 0.0
	}
	
	def actualizarAplicado(){
		if(aplicaciones){
			aplicado=getAplicadoCalculado()
		}else
			aplicado=0.0
	}
	
	
	
	def beforeUpdate(){
		actualizarAplicado()
	}
	
	String toString(){
		return " Folio: $id ${fecha?.format('dd/MM/yyyy')} $cliente    $total ($moneda)"
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
