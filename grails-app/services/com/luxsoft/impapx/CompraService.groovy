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
}
