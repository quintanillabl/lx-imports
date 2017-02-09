package com.luxsoft.impapx

import util.Rounding

class PedimentoService {


    def save(Pedimento pedimento){
        //log.info 'Actualizndo pedimento: '+pedimento.id
        actualizarCostos(pedimento)
        pedimento = pedimento.save failOnError:true,flush:true
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
    	//pedimento.actualizarImpuestos()
    	//pedimento.save flush:true
        //actualizarCostos(pedimento)
        save pedimento
        

    }

    def actualizarCostos(def pedimento){

        def embarques = pedimento.embarques

        def importe=pedimento.getTotal()
        
        def kilosTotales=embarques.sum {it.kilosNetos}


        embarques.each {

            def gasto=it.kilosNetos*importe/kilosTotales
            gasto=gasto.setScale(2, BigDecimal.ROUND_HALF_UP);
            it.gastosPorPedimento=gasto
            it.actualizarCostos()
            it.save flush:true
            
        }

        
    }

    
    
    
    
}

class PedimentoException extends RuntimeException{
	String message
	Pedimento pedimento
}
