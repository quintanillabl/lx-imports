package com.luxsoft.impapx

import org.codehaus.groovy.grails.web.json.JSONArray
import org.hibernate.FetchMode;

import com.luxsoft.impapx.cxc.CancelacionDeCargo;

import grails.converters.JSON
import grails.validation.ValidationException

class VentaException extends RuntimeException{
	String message
	Venta venta
}

class VentaService {
	
	def springSecurityService

    Venta agregarContenedor(long ventaId,String contenedor) {
		
		def venta=Venta.findById(ventaId,[fetch:[partidas:'select']])
		if(!venta){
			throw new VentaException(message:"Venta invalida o inexistente: "+ventaId)
		}
		
		
		
		
		def embarques=EmbarqueDet.findAllByContenedor(contenedor)
		
		if(!embarques){
			throw new VentaException(
				message:"No existen embarques disponibles para el contenedor: "+contenedor
				,venta:venta)
		}
		
		embarques.each {
			if(it.pedimento==null)
				throw new RuntimeException("EmbarqueDet $it.id sin pedimento, no se puede facturar")
			/*def found =venta.partidas.find{ ventaDet->
				ventaDet.embarque.id==it.id
			}*/
			def found=VentaDet.findByEmbarque(it)
			if(found)
				throw new VentaException(message:"Contenedor $it.contenedor ya registrado",venta:venta)
			//if(!found){
				def ventaDet=new VentaDet(
					producto:it.producto
				   ,cantidad:it.cantidad
				   ,precio:it.precioDeVenta
				   ,embarque:it
				   ,contenedor:it.contenedor
				   ,kilos:it.kilosNetos
				   ,costo:it.costoNeto)
			   
			   ventaDet.actualizarImportes()
			   venta.addToPartidas(ventaDet)
			
		}
		if(!venta.validate()){
			throw new VentaException(message:"Errroes de validacion en la venta",venta:venta)
		}
		def res=venta.save(failOnError:true)
		return res
		
    }
	
	def eliminarPartidas(def partidas){
		JSONArray jsonArray=JSON.parse(partidas);
		def venta=null
		jsonArray.each {
			def det=VentaDet.get(it.toLong())
			if(venta==null)
				venta=det.venta
			def contenedor=det.embarque.contenedor
			
			println 'Eliminando ventas asignadas con contenedor: '+contenedor
			def partidasPorContenedor=venta.partidas.find { ventaDet->
				ventaDet.embarque.contenedor==contenedor
			}
			//println 'Partidas encontradas con contenedor: '+partidas
			partidasPorContenedor.each {ventaDet->
				venta.removeFromPartidas(ventaDet)
			}
			
			
		}
		venta=venta.save(flush:true)
		
	}
	
	def contenedoresDisponiblesJSON(String contenedor){
		def embarques=EmbarqueDet.findAllByContenedorILike(contenedor,[max:20])
		def contenedores=embarques.groupBy{
			it.contenedor
		}
		
		println contenedores
		
		def res=contenedores.collect{ row ->
			[id:row.contenedor,label:row.contenedor,value:row.contenedor]
		}
		render res as JSON
	}
	
	def comprobanteFiscalService
	
	def facturar(long ventaId){
		def res=comprobanteFiscalService.generarComprobanteFiscalDigital(ventaId)
		return res
	}
	 
	def cancelarCargo(def venta,def comentario){
		
		
		
		
		CancelacionDeCargo c=new CancelacionDeCargo()
		c.cargo=venta
		c.fecha=new Date()
		c.comentario=comentario
		c.usuario=springSecurityService?.getCurrentUser().username
		if(!c.usuario)
			c.usuario="NA"
		c.save(failOnError:true)
		
		venta.importe=0.0
		venta.impuestos=0.0
		venta.total=0.0
		venta.subtotal=0.0
		venta.descuentos=0.0
		venta.comentario+=" (CANCELADA)"
		venta.partidas.clear()
		
		venta.save(fialOnError:true)
		
	}
	
	def refacturar(long id){
		comprobanteFiscalService.generarComprobanteFiscalDigital(id)
	}
	
}
