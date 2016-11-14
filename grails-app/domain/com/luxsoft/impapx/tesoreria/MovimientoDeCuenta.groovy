package com.luxsoft.impapx.tesoreria

import com.luxsoft.impapx.CuentaBancaria;
import com.luxsoft.impapx.contabilidad.CuentaContable;

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder


class MovimientoDeCuenta {

	static auditable = true
	
	CuentaBancaria cuenta
	Date fecha
	Currency moneda
	BigDecimal tc
	BigDecimal importe
	String tipo
	String concepto
	String origen
	String comentario
	String referenciaBancaria
	boolean ingreso
	//Cheque cheque
	CuentaContable cuentaDeudora

	boolean grupo = false
	
	static belongsTo =[Traspaso,Comision,PagoProveedor,CompraDeMoneda]
	//static hasOne =[cheque:Cheque]
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		importe(scale:2)
		tc(scale:4)
		comentario(nullable:true,maxSize:250)
		referenciaBancaria(nullable:true,maxSize:250)
		tipo(nullable:false,inList:['TRANSFERENCIA','CHEQUE','EFECTIVO','DEPOSITO','TARJETA'])
		origen(blank:false,maxSize:70)
		concepto(blank:false,maxSize:255)
		cuentaDeudora(nullable:true)
		
		//cheque(nullable:true)
    }
	
	static mapping ={
		cuenta fetch:'join'
	}
	
	String toString(){
		return "Id:$id ${cuenta}  (${fecha.format('dd/MM/yyyy')}) ${importe}"
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(MovimientoDeCuenta))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(cuenta, obj.cuenta)
		eb.append(fecha, obj.fecha)
		eb.append(importe, obj.importe)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(cuenta)
		hb.append(fecha)
		hb.append(importe)
		return hb.toHashCode()
	}
}
