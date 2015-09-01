package com.luxsoft.impapx.cxp



import com.luxsoft.impapx.Embarque;
import com.luxsoft.impapx.Proveedor;
import com.luxsoft.impapx.Requisicion;
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta;

class Anticipo {

	static auditable = true
	
	Date fecha
	
	BigDecimal total=0.0
	BigDecimal impuestosAduanales=0.0
	BigDecimal gastosDeImportacion=0.0
	Requisicion requisicion
	Requisicion complemento
	MovimientoDeCuenta sobrante
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		complemento(nullable:true)
		sobrante(nullable:true)
    }
	
	//static belongsTo = [requisicion:Requisicion]
	
	static mapping ={
		requisicion fetch:'join'
		embarque fetch:'join'
	}
	
	static transients = ['diferencia']
	
	public BigDecimal getDiferencia(){
		return total-(impuestosAduanales+gastosDeImportacion)
	}
}
