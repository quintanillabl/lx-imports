package com.luxsoft.impapx

/**
 * Tipo de cuenta por pagar que corresponde a una factura de importacion
 * 
 * @author Ruben Cancino
 *
 */
class FacturaDeImportacion extends CuentaPorPagar{
	
	Pedimento pedimento
	Date fechaBL
	Integer provisionada

    static constraints = {
		pedimento(nullable:true)
		provisionada(nullable:true)
    }
	
	def beforeUpdate() {
		if(pedimento)
			totalMN=total*pedimento.tipoDeCambio
		else
			totalMN=0
		
	}
	
	static mapping ={
		fechaBL formula:'(select max(E.fecha_embarque) from EMBARQUE_DET x join EMBARQUE E ON(E.ID=X.EMBARQUE_ID) where x.factura_id=id)'
	}
	
	//static transients = ['fechaBL']
	
	
}
