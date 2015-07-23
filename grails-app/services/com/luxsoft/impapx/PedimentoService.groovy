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
}

class PedimentoException extends RuntimeException{
	String message
	Pedimento pedimento
}
