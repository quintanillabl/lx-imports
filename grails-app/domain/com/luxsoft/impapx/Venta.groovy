package com.luxsoft.impapx


import com.luxsoft.cfdi.Cfdi

import util.Rounding;


class Venta {

	static auditable = true	
	
	Cliente cliente
	Date fecha=new Date()
	
	Currency moneda=Currency.getInstance('MXN');
	
	BigDecimal tc=1.0
	BigDecimal importe=0
	BigDecimal descuentos=0
	BigDecimal subtotal=0
	
	BigDecimal impuestos=0
	BigDecimal total=0
	
	int plazo=0
	Date vencimiento=new Date()+1
	String formaDePago
	String cuentaDePago="0000"
	
	BigDecimal kilos=0
	
	String comentario
	
	Date dateCreated
	Date lastUpdated
	List partidas
	
	BigDecimal saldo=0
	BigDecimal pagosAplicados=0
	BigDecimal saldoActual=0
	
	String tipo="VENTA";
	String clase="IMPORTACION"
	String usoCfdi = 'G01'
	
	List conceptos
	
	static hasMany = [partidas:VentaDet, conceptos: CargoDet]

    static constraints = {
		
		cliente(nullable:false)
		fecha(nullable:false)
		importe(nullable:false,scale:2)
		descuentos(nullable:false,scale:2)
		subtotal(nullable:false,scale:2)
		impuestos(nullable:false,scale:2)
		total(nullable:false,scale:2)
		moneda(nullable:false)
		tc(nullable:false,scale:4)
		plazo(nullable:false)
		vencimiento(nullable:false)
		formaDePago(nullable:false,maxSize:20)
		cuentaDePago(nullable:false,maxSize:4)
		kilos(nullable:false,scale:3)
		comentario(blank:true,maxSize:300)
		
		saldo(nullable:true)
		tipo(inList:['VENTA','NOTA_DE_CARGO'])
		clase(nullable:true,maxSize:40)
		usoCfdi nullable: true, maxSize:3
    }
	
	static mapping = {
		partidas lazy:false
		cliente fetch:'join'
		partidas cascade: "all-delete-orphan"
		sort "id"
		pagosAplicados formula:'(select ifnull(sum(x.total),0) from CXCAplicacion x where x.factura_id=id)'
		saldoActual formula:'(select total-ifnull(sum(x.total),0) from CXCAplicacion x where x.factura_id=id)'
	}
	
	static transients = ['cfdi','factura','fechaFactura']
	
	// public BigDecimal getSaldoActual(){
	// 	def pag=pagosAplicados?:0.0
	// 	return total-pag
	// }
	
	def getCfdi(){
		def serie='FAC'
		if(tipo=='NOTA_DE_CARGO')
			serie='CAR'
		return Cfdi.findBySerieAndOrigen(serie,id)?.id
	}
	 
	
	def getFactura(){
		return getCfdi()
	}
	
	def getFacturaFolio(){
		def serie=tipo=='VENTA'?'FAC':'CAR'
		Cfdi cfdi= Cfdi.findBySerieAndOrigen(serie,id)
		return cfdi!=null?serie+'-'+cfdi.folio:'NA'
	}
	
	def getFechaFactura(){
		def serie=tipo=='VENTA'?'FAC':'CAR'
		return Cfdi.findBySerieAndOrigen(serie,id)?.fecha?.text()
	}
	
	def beforeUpdate(){
		if(tipo=='VENTA'){
			importe=partidas.sum(0.0,{it.importe})
			importe=Rounding.round(importe, 2)
			
			subtotal=importe-descuentos
			
			impuestos=subtotal*0.16
			impuestos=Rounding.round(impuestos, 2)
			if(cliente.rfc=='XEXX010101000')
				impuestos=0.0
			total=subtotal+impuestos
		} 
	}
	
	def getPedimento(){
		return 'PED_ PENDIENTE'
	}
	
	def getAduana(){
		return 'ADUANA_PENDIENTE'
	}
	
	Date getPedimentoFecha(){
		return null;
	}
	
}
