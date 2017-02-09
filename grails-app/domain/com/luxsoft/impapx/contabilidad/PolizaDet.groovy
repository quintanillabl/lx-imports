package com.luxsoft.impapx.contabilidad

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includes='cuenta,debe,haber,concepto,referencia',includeNames=true,includePackage=false)
class PolizaDet {
	
	CuentaContable cuenta
	BigDecimal debe
	BigDecimal haber
	String concepto
	String descripcion
	String asiento
	String referencia
	String origen
	String entidad
	
	static belongsTo = [poliza:Poliza]

	static hasOne = [comprobanteNacional: ComprobanteNacional, 
		comprobanteExtranjero: ComprobanteExtranjero,
		transaccionCheque: TransaccionCheque,
		transaccionTransferencia: TransaccionTransferencia]

    static constraints = {
    	concepto(nullable:true,maxSize:300)
    	asiento nullable:true,maxSize:255
    	referencia nullable:true
		origen(nullable:true,maxSize:255)
		entidad(nullable:true,maxSize:255)
		descripcion(nullable:true,maxSize:255)
		comprobanteNacional nullable:true
		comprobanteExtranjero nullable:true
		transaccionCheque nullable:true
		transaccionTransferencia nullable:true
    }

    static mapping ={
		poliza fetch:'join'
	}
	
	
	public List complementos(){
		def res =  [];
		if(comprobanteExtranjero) res.add(comprobanteExtranjero)
		if(comprobanteNacional) res.add(comprobanteNacional)
		if(transaccionCheque) res.add(transaccionCheque)
		if(transaccionTransferencia) res.add(transaccionTransferencia)
		return res;
	}
	
	
}
