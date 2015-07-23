package com.luxsoft.impapx

import grails.validation.ValidationException;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.luxsoft.impapx.contabilidad.Poliza;
import com.luxsoft.impapx.cxp.ConceptoDeGasto;

class FacturaDeGastosService {
	
	def save(FacturaDeGastos fac){
		
		actualizar(fac)
		fac.save(failOnError:true)
		return fac
	}

    def agregarPartida(long gastoId,ConceptoDeGasto concepto) {
		
		def gasto=FacturaDeGastos.findById(gastoId,[fetch:[conceptos:'eager']])
		def importe=concepto.importe
		concepto.ietu=importe
		concepto.impuesto=importe*(concepto.impuestoTasa/100)
		
		def ret1=importe*(concepto.retensionTasa/100)
		def retIsr=importe*(concepto.retensionIsrTasa/100)
		concepto.retension=ret1
		concepto.retensionIsr=retIsr
		concepto.total=importe+concepto.impuesto-ret1-retIsr
		
		
		gasto.addToConceptos(concepto)
		actualizar(gasto)
		
		gasto.save(failOnError:true)
		
    }
	
	def eliminarConceptos(FacturaDeGastos fac,def partidas) {
		partidas.each {
			def det=ConceptoDeGasto.get(it.toLong())
			println 'Eliminando concepto: '+det
			if(det){
				fac.removeFromConceptos(det)
				fac=fac.save(failOnError:true)
				actualizar(fac)
			}
		}
	}
	
	def actualizar(FacturaDeGastos gasto){
		gasto.importe=gasto.conceptos.sum(0.0,{it.importe})
		gasto.impuestos=gasto.conceptos.sum(0.0,{it.impuesto})
		gasto.retImp=gasto.conceptos.sum(0.0,{it.retension})
		gasto.retensionIsr=gasto.conceptos.sum(0.0,{it.retensionIsr})
		
		
		
		gasto.total=gasto.importe+gasto.impuestos-gasto.retImp-gasto.retensionIsr
		//def desc=gasto.conceptos.sum(0.0,{it.descuento?:0.0})
		//println 'Descuento total de la factura: '+desc
		gasto.descuento=gasto.conceptos.sum(0.0,{it.descuento?:0.0})
		gasto.rembolso=gasto.conceptos.sum(0.0,{it.rembolso?:0.0})
		gasto.otros=gasto.conceptos.sum(0.0,{it.otros?:0.0})
	}
	
}

class FacturaDeGastosException extends RuntimeException{
	
}
