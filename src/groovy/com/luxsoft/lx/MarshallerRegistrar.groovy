package com.luxsoft.lx

import java.text.SimpleDateFormat
import javax.annotation.PostConstruct
import grails.converters.JSON
import com.luxsoft.impapx.*

class MarshallerRegistrar {

	def df=new SimpleDateFormat('dd/MM/yyyy')
	def tf=new SimpleDateFormat('dd/MM/yyyy hh:mm')

	@PostConstruct
	void registerMarshallers() {
		println 'Registrando custom marchallers......'


		JSON.registerObjectMarshaller(Requisicion){ Requisicion r ->
			return[id:r.id,
				proveedor:r.proveedor.nombre,
				concepto:r.concepto,
				fecha:df.format(r.fecha),
				fechaDelPago:df.format(r.fechaDelPago),
				moneda:r.moneda.getCurrencyCode(),
				tc:r.tc,
				descuentoFinanciero:r.descuentoFinanciero,
				importe:r.importe,
				impuestos:r.impuestos,
				total:r.total,
				comentario:r.comentario,
				pago:r.pagoProveedor?.id,
				dateCreated:tf.format(r.dateCreated),
				lastUpdated:tf.format(r.lastUpdated)
			]
		}
	}
}


/*
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
*/
