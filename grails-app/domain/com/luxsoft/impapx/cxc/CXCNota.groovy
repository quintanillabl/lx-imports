package com.luxsoft.impapx.cxc


import com.luxsoft.cfdi.Cfdi

class CXCNota extends CXCAbono{
	
	
	List partidas
	String tipo
	BigDecimal descuento=0.0
	
	static hasMany =[partidas:CXCNotaDet]

    static constraints = {
		tipo(nullable:false,inList:['DESCUENTO','BONIFICACION','DEVOLUCION'])
		descuento(validator:{val,obj -> 
				
				if(obj.tipo=='DESCUENTO' && val<=0.0){return 'descuentoRequerido'}
			}
		)
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
