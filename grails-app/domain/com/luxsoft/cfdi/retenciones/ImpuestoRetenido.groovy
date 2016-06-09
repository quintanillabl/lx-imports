package com.luxsoft.cfdi.retenciones

class ImpuestoRetenido {

	/*
	* Atributo opcional para expresar la base del impuesto, 
	* que puede ser la diferencia entre los ingresos percibidos y las deducciones autorizadas
	*/
	BigDecimal baseRet

	/*
	* Atributo opcional para señalar el tipo de impuesto retenido del periodo o ejercicio conforme al catalogo.
	*/
	TipoDeImpuesto impuesto

	/*
	* Atributo requerido para expresar el importe del impuesto retenido en el periodo o ejercicio
	*/
	BigDecimal montoRet

	/*
	* Atributo requerido para precisar si el monto de la retención es considerado pago definitivo o pago provisional
	*/
	String tipoPagoRet


    static constraints = {
    	baseRet scale:6
    	montoRet scale:6
    	tipoPagoRet inList:['Pago provisional','Pago definitivo']
    }

    static belongsTo = [retencion: CfdiRetenciones]
}
