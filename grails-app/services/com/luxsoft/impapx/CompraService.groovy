package com.luxsoft.impapx

class CompraService {

    def eliminarPartidas(Compra compra,def partidas) {
		partidas.each {
			def det=CompraDet.get(it.toLong())
			if(det){
				compra.removeFromPartidas(aplicacion)
				compra=compra.save(failOnError:true)
			}
		}
    }


    def agregarPartida(Compra compra,Producto producto,BigDecimal cantidad){
    	def det=new CompraDet(
    		producto:producto,
    		solicitado:cantidad,
    		precio:0.0,
    		descuento:0.0
    	)
    	compra.addToPartidas(det)
    	compra.save failOnError:true,flush:true
    	return compra;
    }
}
