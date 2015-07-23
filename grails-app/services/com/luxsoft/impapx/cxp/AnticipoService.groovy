package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.CuentaDeGastos;

class AnticipoService {

    def actualizarAnticipo(Anticipo anticipoInstance) {
		
		anticipoInstance.requisicion.partidas.each{det->
			def e=det.embarque
			if(e){
				def pedimentos=new HashSet()
				e.partidas.each{
					pedimentos.add(it.pedimento)
				}
				det.impuestosAduanales=pedimentos.sum(0.0,{it.impuesto})
				def cuenta=CuentaDeGastos.findByEmbarque(e)
				if(cuenta){
					det.gastosDeImportacion=cuenta.total
				}
			}
			
		}
		anticipoInstance.impuestosAduanales=anticipoInstance.requisicion.partidas.sum(0.0,{it.impuestosAduanales})
		anticipoInstance.gastosDeImportacion=anticipoInstance.requisicion.partidas.sum(0.0,{it.gastosDeImportacion})
		anticipoInstance.save(failOnError:true)
		return anticipoInstance

    }
}
