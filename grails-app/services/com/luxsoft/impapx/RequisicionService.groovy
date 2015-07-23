package com.luxsoft.impapx

import org.apache.commons.lang.exception.ExceptionUtils;

import grails.validation.ValidationException;

import com.luxsoft.impapx.cxp.Anticipo;

import util.MonedaUtils;

class RequisicionService {

    def asignarFacturas(Requisicion r,def facturas) {
		
		facturas.each {
			if(it){
				def fac=CuentaPorPagar.get(it.toLong())
				def total=fac.getSaldoActual()
				if(total>0){
					def importe=MonedaUtils.calcularImporteDelTotal(total)
					def impuesto=MonedaUtils.calcularImpuesto(importe)
					def det=new RequisicionDet(factura:fac
						,documento:fac.documento
						,fechaDocumento:fac.fecha
						,totalDocumento:fac.total
						,importe:importe
						,impuestos:impuesto
						,total:total)
					
					if(r.descuentoFinanciero>0 && r.descuentoFinanciero<50){
						
						def factor=1-(r.descuentoFinanciero/100)
						total=total*factor
						importe=MonedaUtils.calcularImporteDelTotal(total)
						impuesto=MonedaUtils.calcularImpuesto(importe)
						
						det.total=total
						det.importe=importe
						det.impuestos=impuesto
						//det.actualizarImportes()
					}
					//fac.requisitado+=det.total
					r.addToPartidas(det)
				}
			}
		}
		r.actualizarImportes()
		
    }
	
	def eliminarPartidas(Requisicion r,def partidas){
		
		
		partidas.each { 
			def id=it.toLong()
			def found=r.partidas.find{ det->
				det.id==id
			}
			if(found){
				//println 'Eliminando: '+found
				r.removeFromPartidas(found)
				
			}
		}
		r.actualizarImportes()
		r.save(flush:true)
		if(r.concepto=='ANTICIPO'){
			def a=Anticipo.findByRequisicion(r)
			a.total=r.total
			a.save(flush:true)
		}
	}
	
	def generarAnticipo(Requisicion r,Embarque e){
		println 'Generando anticipo : '+r
		//def anticipo=new Anticipo(requisicion:r,embarque:e,fecha:r.fecha,total:0.0)
		def anticipo=new Anticipo(requisicion:r,fecha:r.fecha,total:0.0)
		try {
			r.save(failOnError:true)
			anticipo.save(failOnError:true)
		} catch (ValidationException ve) {
			println ExceptionUtils.getRootCauseMessage(ve)
			return r
		}
		
	}
	
	def agregarPartida(long requisicionId,RequisicionDet det){
		def requisicion=Requisicion.get(requisicionId)
		det.importe=det.total
		requisicion.addToPartidas(det)
		requisicion.actualizarImportes()
		requisicion.save(flush:true)
		
		if(requisicion.concepto.startsWith('ANTICIPO')){
			def a=Anticipo.findByRequisicion(requisicion)
			a.total=requisicion.total
			a.save(flush:true)
		}
		return requisicion
	}
}
