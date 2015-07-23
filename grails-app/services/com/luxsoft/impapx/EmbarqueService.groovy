package com.luxsoft.impapx

import org.codehaus.groovy.grails.web.json.JSONArray
import grails.converters.JSON

class EmbarqueService {

	def eliminarPartidas(def params){
		log.debug 'Eliminando partidas: '+params
		def embarque=Embarque.findById(params.embarqueId,[fetch:[partidas:'eager']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		jsonArray.each{
			def embarqueDet=embarque.partidas.find{ ee-> ee.id==it.toLong()}//EmbarqueDet.get(it.toLong())
			def res=embarque.removeFromPartidas(embarqueDet)
			embarqueDet.compraDet.entregado-=embarqueDet.cantidad
		}
		def target=embarque.save(failOnError:true)
		def res=[res:target.partidas.size()]
		return res
	}
	
}
