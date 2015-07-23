package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.CuentaPorPagar;

class PagoService {

   def generaAplicacion(Pago pago,def facturas) {
		facturas.each {
			def cxp=CuentaPorPagar.get(it.toLong())
			println 'Aplicando abono a :'+cxp
			if(cxp){
				def aplicable=0
				if(pago.disponible>cxp.getSaldoActual()){
					aplicable=cxp.getSaldoActual()
				}else
					aplicable=pago.disponible
				if(aplicable){
					//println 'Aplicando: '+aplicable
					def aplicacion=new Aplicacion(fecha:new Date(),factura:cxp,total:aplicable,importe:0.0,impuesto:0.0
						,impuestoTasa:pago.impuestoTasa)
					pago.addToAplicaciones(aplicacion)
					if(!pago.aplicado)
						pago.aplicado=0.0
					pago=pago.save(failOnError:true)
				}
			}
		}
    }
	
	def eliminarAplicaciones(Pago pago,def aplicaciones) {
		aplicaciones.each {
			def aplicacion=Aplicacion.get(it.toLong())
			//println 'Aplicando abono a :'+cxp
			if(aplicacion){
				pago.removeFromAplicaciones(aplicacion)
				pago=pago.save(failOnError:true)
			}
		}
	}
}
