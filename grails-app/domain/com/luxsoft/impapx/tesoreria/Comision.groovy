package com.luxsoft.impapx.tesoreria

import com.luxsoft.impapx.CuentaBancaria;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import com.luxsoft.impapx.CuentaPorPagar

class Comision {

	static auditable = true
	
	Date fecha

	CuentaBancaria cuenta

	BigDecimal tc

	BigDecimal comision

	BigDecimal impuestoTasa

	BigDecimal impuesto

	String comentario

	String referenciaBancaria

	CuentaPorPagar cxp
	
	static hasMany =[movimientos:MovimientoDeCuenta]

    static constraints = {
		comentario(nullable:true,maxSize:200)
		referenciaBancaria(nullable:true,maxSize:100)
		tc(scale:4)
		cxp nullable:true
    }
	
	static mapping ={
		cuenta fetch:'join'
		movimientos cascad:"all-delete-orphan"
	}
	
	// String toString(){
	// 	return "${fecha.format('dd/MM/yyyy')} ${cuenta.numero}    ${importe}"
	// }
	
	boolean equals(Object obj){
		if(!obj.instanceOf(Comision))
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
	
	BigDecimal getImpuestoMN(){
		return impuesto*tc
	}
	
	BigDecimal getComisionMN(){
		return comision*tc
	}
	
	def generarMovimientos(){
		
		addToMovimientos(cuenta:this.cuenta
			,fecha:this.fecha
			,moneda:this.cuenta.moneda
			,tc:this.tc
			,importe:comision.abs()*-1.0
			,ingreso:false
			,tipo:'TRANSFERENCIA'
			,origen:'TESORERIA'
			,concepto:"Comision "+comentario
			,comentario:'COMISION_BANCARIA')
		if(getImpuestoMN().abs()>0){
			addToMovimientos(cuenta:this.cuenta
				,fecha:this.fecha
				,moneda:this.cuenta.moneda
				,tc:this.tc
				,importe:getImpuestoMN().abs()*-1.0
				,ingreso:false
				,tipo:'TRANSFERENCIA'
				,origen:'TESORERIA'
				,concepto:"Iva comision "+comentario
				,comentario:'IMPUESTO_COMISION_BANCARIA')
		}
		
	}
	
	def beforeInsert(){
		generarMovimientos()
	}
}
