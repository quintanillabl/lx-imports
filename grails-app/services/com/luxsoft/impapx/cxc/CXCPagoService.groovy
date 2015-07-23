package com.luxsoft.impapx.cxc

import org.apache.commons.lang.exception.ExceptionUtils;

import com.luxsoft.impapx.Venta;



import grails.validation.ValidationException

class CXCPagoService {

    def registrarPago(CXCPago pago) {
		
		try {
			
			pago.save(failOnError:true)
		} catch (ValidationException e) {
			throw new CobranzaEnPagoException(pago:pago,message:ExceptionUtils.getRootCauseMessage(e));
		}
    }
	
	 def asignarFacturas(CXCPago pago,def facturas) {
		
		facturas.each {
			def fac=Venta.get(it.toLong())
			def ap=new CXCAplicacion(
				fecha:new Date()
				,total:fac.saldo
				,impuestoTasa:pago.impuestoTasa)
			fac.saldo-=ap.total
			pago.addToAplicaciones(ap)
		}
		def res=pago.save(failOnError:true)
		return res
		
    }
}
