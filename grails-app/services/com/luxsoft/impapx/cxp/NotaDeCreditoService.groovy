package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.CuentaPorPagar;

class NotaDeCreditoService {

    def generaAplicacion(NotaDeCredito nota,def facturas) {
		facturas.each {
			def cxp=CuentaPorPagar.get(it.toLong())
			//println 'Aplicando abono a :'+cxp
			if(cxp){
				def aplicable=0
				if(nota.disponible>cxp.getSaldoActual()){
					aplicable=cxp.getSaldoActual()
				}else
					aplicable=nota.disponible
				if(aplicable){
					//println 'Aplicando: '+aplicable
					def aplicacion=new Aplicacion(fecha:new Date(),factura:cxp,total:aplicable,importe:0.0,impuesto:0.0
						,impuestoTasa:nota.impuestoTasa)
					nota.addToAplicaciones(aplicacion)
					nota=nota.save(failOnError:true)
				}
			}
		}
    }
	
	def eliminarAplicaciones(NotaDeCredito nota,def aplicaciones) {
		aplicaciones.each {
			def aplicacion=Aplicacion.get(it.toLong())
			//println 'Aplicando abono a :'+cxp
			if(aplicacion){
				nota.removeFromAplicaciones(aplicacion)
				nota=nota.save(failOnError:true)
			}
		}
	}
}
