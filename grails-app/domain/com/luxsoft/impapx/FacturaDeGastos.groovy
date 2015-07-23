package com.luxsoft.impapx



import com.luxsoft.impapx.cxp.ConceptoDeGasto;
import com.luxsoft.impapx.cxp.CuentaDeGastosGenerica;

class FacturaDeGastos extends CuentaPorPagar{
	
	BigDecimal retensionIsr=0
	BigDecimal descuento=0
	BigDecimal rembolso=0
	BigDecimal otros=0
	
	CuentaDeGastosGenerica cuentaGenerica
	
	static hasMany =[conceptos:ConceptoDeGasto]
	
	//static belongsTo=[cuentaGenerica:CuentaDeGastosGenerica]

    static constraints = {
		retensionIsr(nullable:true)
		cuentaGenerica(nullable:true)
		otros nullable:true
		
    }
	
	static mapping = {
		conceptos cascade: "all-delete-orphan"
	}
	
	public BigDecimal getSaldoActual(){
		def pag=pagosAplicados?:0.0
		return total-(descuento?:0)-(rembolso?:0)-(otros?:0)-pag
	}
}
