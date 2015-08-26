package com.luxsoft.impapx

class PedimentoService {

    def agregarEmbarquesPorContenedor(long pedimentoId,String contenedor) {
		def pedimento=Pedimento.findById(pedimentoId,[fetch:[embarques:'eager']])
		def embarqueDetList=EmbarqueDet.findAll("from EmbarqueDet d where d.contenedor=? and pedimento is null",[contenedor])
		if(!embarqueDetList)
			throw new PedimentoException(
				message:"No eixsten partidas de embarque disponibles para el contenedor: "+contenedor
				,pedimento:pedimento)
			
		embarqueDetList.each {
			pedimento.addToEmbarques(it)
		} 
		pedimento.save(failOnError:true)
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
    			embarqueDet.save flus:true
    		}
    	}
    	pedimento.actualizarCostos()
    	pedimento.actualizarImpuestos()
    	pedimento.save flush:true

    }
}

class PedimentoException extends RuntimeException{
	String message
	Pedimento pedimento
}
