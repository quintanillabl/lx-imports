package com.luxsoft.impapx

import com.luxsoft.impapx.contabilidad.CuentaContable;
import com.luxsoft.impapx.tesoreria.Banco;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class CuentaBancaria {
	
	String numero
	String tipo
	String nombre
	Currency moneda
	boolean activo
	int folioInicial=0
	int folioFinal=0
	int folio=0
	BigDecimal tasaDeInversion=0
	BigDecimal tasaIsr=0.6
	int diasInversionIsr=365
	int plazo=1
	CuentaContable cuentaContable
	String subCuentaOperativa

	String cuentaRetencion
	
	static belongsTo = [banco:Banco]

    static constraints = {
		numero(blank:false,size:1..50)
		nombre(blank:false,size:1..100)
		tipo(blank:false,size:1..50,inList:['CHEQUERA','INVERSION'])
		moneda(nullable:false)
		activo(nullabel:false)
		folioInicial(nullable:true)
		folioFinal(nullable:true)
		folio(nullable:true)
		plazo(nullable:true)
		cuentaContable(nullable:true)
		tasaDeInversion (nullable:true)
		tasaIsr(nullabl:true) 
		diasInversionIsr(nullable:true)
		cuentaRetencion nullable:true
		subCuentaOperativa(nullable:true,maxSize:4)
    }
	
	static mapping ={
		banco fetch:'join'
	}
	
	String toString() {
		return "${numero} ${moneda} (${banco?.nombre} - ${banco.nacional?'Nac':'Ext'}) "
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(CuentaBancaria))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(numero, obj.numero)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(numero)
		return hb.toHashCode()
	}

	
	
	
}
