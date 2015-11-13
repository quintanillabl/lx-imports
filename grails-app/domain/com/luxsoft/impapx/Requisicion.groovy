package com.luxsoft.impapx

import com.luxsoft.impapx.cxp.Anticipo;
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta
import com.luxsoft.impapx.tesoreria.PagoProveedor;

import java.util.Currency;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import util.Rounding;
//import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode

//@ToString(includes='nombre',includeNames=true,includePackage=false)
@EqualsAndHashCode(includes='id')
class Requisicion {

	static auditable = true
	
	Proveedor proveedor
	String concepto
	Date fecha
	Date fechaDelPago
	String formaDePago
	Currency moneda=Currency.getInstance('MXN');
	BigDecimal tc=1
	BigDecimal descuentoFinanciero=0
	
	
	BigDecimal importe=0
	BigDecimal impuestos=0
	BigDecimal total=0
	
	String comentario

	//Moneda nacional
	BigDecimal ietu=0
	BigDecimal retencionHonorarios=0
	BigDecimal retencionFlete=0
	BigDecimal retencionISR=0
	
	//Anticipo anticipo;
	
	
	Date dateCreated
	Date lastUpdated

	
	List partidas

	static hasMany=[partidas:RequisicionDet]
	static belongsTo = [pagoProveedor:PagoProveedor]


    static constraints = {
		proveedor(nullable:false)
		concepto(blank:false,inList:['PAGO','ANTICIPO','ANTICIPO_COMPLEMENTO','ANTICIPO_COMPRA','ANTICIPO_GASTO','PARCIALIDAD','COMPRA_MONEDA','FLETE','DEPOSITO_EN_GARANTIA'])
		formaDePago(blank:false,inList:['TRANSFERENCIA','CHEQUE'])
		tc(nullable:false,scale:6,validator:{ val,obj ->
			if(obj.moneda!=Currency.getInstance('MXN') && val<=1.0)
				return "tcRequerido"
			
		})
		descuentoFinanciero(nullable:false,scale:6,maxSize:99.99)
		importe(scale:2)
		impuestos(scale:2)
		total(scale:2)
		comentario(maxSize:200)
		pagoProveedor(nullable:true)
		//anticipo(nullable:true)
		
    }
	
	static mapping ={
		//proveedor fetch:'join'
		//pagoProveedor fetch:'join'
		partidas cascade: "all-delete-orphan"
	}
	
	
	
	def actualizarImportes(){
		//println 'Actualizando importes de requisicion'
		importe=sumar('importe')
		impuestos=sumar('impuestos')
		total=sumar('total')
		
		ietu=sumar('ietu')
		retencionFlete=sumar('retencionFlete')
		retencionHonorarios=sumar('retencionHonorarios')
		retencionISR=sumar('retencionISR')
		
	}
	
	BigDecimal sumar(String property){
		return partidas.sum(0.0,{
			it."${property}"
		})
	}
	
	String toString(){
		return "Id: ${id}  $proveedor $total ($moneda) $formaDePago"
	}
	
	
	

}
