package com.luxsoft.impapx

import util.Rounding

class PedimentoService {


    def save(Pedimento pedimento){
        pedimento = pedimento.save failOnError:true,flush:true
        actualizarCostos(pedimento)
        return pedimento
    }

    def agregarEmbarquesPorContenedor(long pedimentoId,String contenedor) {
		def pedimento=Pedimento.findById(pedimentoId,[fetch:[embarques:'eager']])
		def embarqueDetList=EmbarqueDet.findAll("from EmbarqueDet d where d.contenedor=? and pedimento is null",[contenedor])
		if(!embarqueDetList)
			throw new PedimentoException(
				message:"No eixsten partidas de embarque disponibles para el contenedor: "+contenedor
				,pedimento:pedimento)
			
		embarqueDetList.each {
			pedimento.addToEmbarques(it)
            //pedimento.actualizarCostos()
		} 
		//pedimento.save(failOnError:true)
        save(pedimento)
    }


    def quitarEmbarques(Long pedimentoId,def jsonArray){
    	def pedimento=Pedimento.get(pedimentoId)
    	jsonArray.each{
    		def id=it.toLong()
    		def embarqueDet=pedimento.embarques.find{ det ->
    			det.id==id
    		}
    		if(embarqueDet){
    			pedimento.removeFromEmbarques(embarqueDet)
    			embarqueDet.pedimento=null
    			embarqueDet.gastosPorPedimento=0
                embarqueDet.save flush:true
    		}
    	}
    	pedimento.actualizarImpuestos()
    	pedimento.save flush:true
        actualizarCostos(pedimento)
        

    }

    def actualizarCostos(def pedimento){

        def importe=pedimento.getTotal()
        
        def kilosTotales=pedimento.embarques.sum {it.kilosNetos}

        embarques.each {

            def gasto=it.kilosNetos*importe/kilosTotales
            gasto=gasto.setScale(2, BigDecimal.ROUND_HALF_UP);
            it.gastosPorPedimento=gasto
            it.save flush:true
            
        }
        
    }

    def actualizarImpuestos(def pedimento){
        def impuesto=0
        
        impuesto=pedimento.embarques.sum (0.0,{
            it.importe*tipoDeCambio*(this.impuestoTasa/100)
            }
        )
        impuesto=impuesto.setScale(2, BigDecimal.ROUND_HALF_UP);
        
        def iva=0
        def ivaPrev=Rounding.round(this.prevalidacion*(1+this.impuestoTasa/100),0)
        iva=(this.dta+arancel)*(1+this.impuestoTasa/100)
        impuesto=Rounding.round(impuesto+iva,0)+ivaPrev
        pedimento.impuesto=impuesto
    }
    
    
    
}

class PedimentoException extends RuntimeException{
	String message
	Pedimento pedimento
}
