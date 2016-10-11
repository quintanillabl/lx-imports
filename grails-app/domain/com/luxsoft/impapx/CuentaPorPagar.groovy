package com.luxsoft.impapx

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder


import com.luxsoft.impapx.cxp.ComprobanteFiscal
import com.luxsoft.impapx.cxp.Aplicacion

class CuentaPorPagar {
	
	static auditable = true	
	
	Proveedor proveedor
	String documento
	Date fecha
	Date vencimiento
	Currency moneda=Currency.getInstance('MXN')
	BigDecimal tc=1
	BigDecimal importe=0
	BigDecimal descuentos=0
	BigDecimal subTotal=0
	BigDecimal impuestos=0
	BigDecimal total=0
	BigDecimal saldo=0
	BigDecimal pagosAplicados=0
	BigDecimal tasaDeImpuesto=0
	BigDecimal totalMN=0
	
	BigDecimal retTasa=0
	BigDecimal retImp=0
	BigDecimal retensionIsr=0
	
	/**
	 * Importa analizado
	 */
	BigDecimal analisisCosto=0;
	
	String comentario
	BigDecimal requisitado=0
	BigDecimal pendienteRequisitar=0
	BigDecimal saldoActual=0
	BigDecimal saldoAlCorte=0

	Boolean gastoPorComprobar = true

	Date dateCreated
	Date lastUpdated	

    static constraints = {
		
		proveedor(nullable:false)
		documento(blank:false,nullable:false)
		fecha(nullable:false)
		vencimiento(nullable:false)
		comentario(blank:false,size:1..300)
		moneda(nullable:false)
		tc(nullable:false,scale:4)
		
		importe(nullable:false,scale:2)
		descuentos(nullable:false,scale:2)
		subTotal(nullable:false,scale:2)
		impuestos(nullable:false,scale:2)
		total(nullable:false,scale:2)
		analisisCosto(nullable:false,scale:2)
		tasaDeImpuesto(nullable:false,scale:4)
		comentario(nullable:true,maxSize:200)
		requisitado(nullable:true)
		retTasa(nullable:true)
		retImp(nullable:true)
		comprobante nullable:true
		retensionIsr(nullable:true)
		gastoPorComprobar nullable:true
		
    }
	
	static mapping ={
		proveedor fetch:'join'
		requisitado formula:'(select ifnull(sum(x.total),0) from requisicion_det x where x.factura_id=id)'
		pagosAplicados formula:'(select ifnull(sum(x.total),0) from aplicacion x where x.factura_id=id)'
	}

	static hasOne = [comprobante: ComprobanteFiscal]
	
	static hasMany = [aplicaciones:Aplicacion]
	
	static transients = ['pendienteRequisitar','saldoActual','saldoAlCorte']
	
	String toString(){
		return "${documento} (${fecha?.format('dd/MM/yyyy')})  Total: ${total} ";
	}   
	
	
	BigDecimal getTotalMN(String property){
		return "${property}"*tc
		
	}
	
	public BigDecimal getPendienteRequisitar(){
		def req=requisitado?:0.0
		return total-req
	}
	
	public BigDecimal getSaldoActual(){
		def pag=pagosAplicados?:0.0
		return total-pag
	}
	  
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(CuentaPorPagar)) )
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
		vencimiento=fecha+proveedor?.plazo
		totalMN=total*tc
		saldo=total
	}
	
	/*
	def afterInsert(){
		saldo=getSaldoActual()
		save()
	}*/
	
	public BigDecimal buscarSaldoAlCorte(Date corte){
		def found=Aplicacion.executeQuery(
			"select sum(a.importe) from Aplicacion a where a.factura=? and date(a.fecha)<=?",[this,corte])
    	def aplicado=found[0]?:0.0
    	def saldo=this.total-aplicado
    	return saldo
	}
	
	
}