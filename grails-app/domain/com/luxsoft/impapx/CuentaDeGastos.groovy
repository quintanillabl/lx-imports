package com.luxsoft.impapx

class CuentaDeGastos {
	
	Date fecha
	String comentario
	String referencia
	BigDecimal importe=0
	BigDecimal impuestos=0
	BigDecimal total=0
	Embarque embarque
	Proveedor proveedor
	
	static hasMany = [facturas:CuentaPorPagar]

    static constraints = {
		comentario(nullable:true,maxSize:250)
		referencia(nullable:true)
		proveedor(nullable:true)
    }
	
	def actualizarImportes(){
		importe=facturas.sum 0, {it.importe*it.tc}
		impuestos=facturas.sum 0,{it.impuestos*it.tc}
		total=facturas.sum 0,{it.total*it.tc}
		
	}
	
	def actualizarGastosDeImportacion(){
		if(embarque!=null){
			def importe=facturas.sum 0, {it.importe*it.tc}
			def kilosTotales=embarque.partidas.sum {it.kilosNetos}
			
			embarque.partidas.each {
				def gasto=it.kilosNetos*importe/kilosTotales
				it.gastosHonorarios=gasto
			}
			
			def incrementable=facturas.sum 0, {it.importe*it.tc}
			embarque.partidas.each {
				def val=it.kilosNetos*incrementable/kilosTotales
				it.incrementables=val
			}
		}
	}
	
	def beforeUpdate(){
		actualizarImportes();
		actualizarReferenciaEnPedimento()
	}
	
	def beforeInsert(){
		actualizarReferenciaEnPedimento()
	}
	
	def actualizarReferenciaEnPedimento(){
		embarque.partidas.each{
			if(it.pedimento){
				it.pedimento.referenciacg=referencia
				it.pedimento.proveedor=proveedor
			}
		}
	}
	
	
}
