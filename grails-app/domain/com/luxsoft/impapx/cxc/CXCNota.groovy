package com.luxsoft.impapx.cxc


import com.luxsoft.cfdi.Cfdi
import com.luxsoft.impapx.Venta

class CXCNota extends CXCAbono{
	
	
	List partidas
	String tipo
	BigDecimal descuento=0.0
	String usoCfdi = 'G01'

	Venta ventaRelacionada
	
	static hasMany =[partidas:CXCNotaDet]

    static constraints = {
		tipo(nullable:false,inList:['DESCUENTO','BONIFICACION','DEVOLUCION'])
		descuento(validator:{val,obj -> 
				
				if(obj.tipo=='DESCUENTO' && val<=0.0){return 'descuentoRequerido'}
			}
		)
		usoCfdi nullable:true, maxSize: 3
		ventaRelacionada nullable:true
    }
	
	static mapping ={
		partidas cascade:"all-delete-orphan"

	}
	
	static transients = ['cfdi','comprobanteFiscal']
	
	def actualizarImportes(){
		if(cfdi==null){
			importe=partidas.sum(0,{ it.importe*this.tc})
			impuesto=importe*(impuestoTasa/100)
			total=importe+impuesto
		}
	}
	
	def beforeUpdate(){
		if(getCfdi()==null){
			actualizarImportes()
		}
	}
	
	
	
	def getCfdi(){
		return Cfdi.findBySerieAndOrigen('CRE',id)?.folio
	}
	
	def getComprobanteFiscal(){
		return getCfdi()
			
	}

	def getCfdiId(){
		return Cfdi.findBySerieAndOrigen('CRE',id)?.id
	}
}
