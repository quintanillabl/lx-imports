package com.luxsoft.impapx.tesoreria

import java.util.Currency;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import com.luxsoft.impapx.CuentaBancaria;
import com.luxsoft.impapx.CuentaPorPagar;
import com.luxsoft.impapx.Requisicion;

class CompraDeMoneda {
	
	Date fecha
	Requisicion requisicion
	Currency moneda=Currency.getInstance('USD');
	BigDecimal tipoDeCambioCompra
	BigDecimal tipoDeCambio
	
	BigDecimal diferenciaCambiaria
	
	CuentaBancaria cuentaOrigen
	CuentaBancaria cuentaDestino
	String formaDePago='TRANSFERENCIA'
	PagoProveedor pagoProveedor
	MovimientoDeCuenta ingreso
	
	//static hasOne = [ingreso:MovimientoDeCuenta]
	
	Date dateCreated
	Date lastUpdated
	

    static constraints = {
		formaDePago(blank:false,inLis:['TRANSFERENCIA','CHEQUE'])
		tipoDeCambioCompra(nullable:false,scale:6)
		tipoDeCambio(nullable:false,scale:6)
		diferenciaCambiaria(scale:2)
		cuentaDestino(nullable:false,validator:{val,object->
			if(object.cuentaOrigen==val){
				return 'cuentasIdenticas'
			}
			if(object.moneda!=val?.moneda)
				return 'monedaDeCuentaDestinoIncorrecta'
			
		})
    }
	
	static mapping ={
		requisicion fetch:'join'
		pagoProveedor fetch:'join'
		//ingreso fetch:'join'
	}
	
	def actualizarDiferenciaCambiaria(){
		def total=requisicion.total
		def total2=(total/tipoDeCambioCompra)*tipoDeCambio
		diferenciaCambiaria=total-total2
		//valorOrigentipoDeCambioCompra
		//def valorFinal=
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(CompraDeMoneda)) )
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
	
	def beforeInsert(){
		actualizarDiferenciaCambiaria()
	}
	
	def beforeUpdate(){
		actualizarDiferenciaCambiaria()
	}
	
	
}
