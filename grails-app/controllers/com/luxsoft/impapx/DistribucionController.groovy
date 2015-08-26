package com.luxsoft.impapx

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('VENTAS','COMPRAS')"])
class DistribucionController {

    static allowedMethods = [create: 'GET',save:'POST', edit: 'GET', delete: 'DELETE',update:'PUT']

    def index(Integer max) {

        def periodo=session.periodo
        def tipo=params.tipo
        def hql="from Distribucion c  where date(c.fecha) between ? and ? order by c.fecha desc"
        def list=Distribucion.findAll(hql,[periodo.fechaInicial,periodo.fechaFinal])
        [distribucionInstanceList:list,tipo:tipo]
    }

    def create(){
    	[distribucionInstance: new Distribucion(fecha:new Date())]
    }
    
    def save(Distribucion distribucionInstance){
    	if(distribucionInstance.hasErrors()){
    		render view:'create',model:[distribucionInstance:distribucionInstance]
    		return
    	}
    	distribucionInstance.save failOnError:true,flush:true
    	flash.message="Distribución ${distribucionInstance.id} generada"
    	redirect action:'edit',id:distribucionInstance.id
    }

    

    def show() {
        def distribucionInstance = Distribucion.get(params.id)
        if (!distribucionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'distribucion.label', default: 'Distribucion'), params.id])
            redirect action: 'list'
            return
        }

        [distribucionInstance: distribucionInstance]
    }

    def edit(Distribucion distribucionInstance){
    	[distribucionInstance:distribucionInstance]
    }

    def update(Distribucion distribucionInstance){
    	if(distribucionInstance.hasErrors()){
    		render view:'edit',model:[distribucionInstance:distribucionInstance]
    		return
    	}
    	distribucionInstance.save failOnError:true
    }

    def delete(Distribucion distribucionInstance) {
		
        distribucionInstance.delete flush:true
        flash.message="Distribución ${distribucionInstance.id} eliminada"
        redirect action:'index'
    }
	
	def embarquesDisponiblesJSONList(){
		//def embarques=Embarque.findAllByBlIlike('%'+params.term+"%",[max:20,sort:"fechaEmbarque",order:"desc"])
		def embarques=Embarque.findAll("from Embarque e where e.id not in (select x.embarque.id from Distribucion x )")
		def embarquesList=embarques.collect { row ->
			def label=row.toString()
			[id:row.id,label:label,value:label]
		}
		render embarquesList as JSON
	}
	
	def selectorDePartidas(Distribucion distribucion ){
		def embarques=EmbarqueDet.findAll(
			"from EmbarqueDet d where d.embarque.id=? and d.contenedor not in(select x.contenedor from DistribucionDet x where x.distribucion.id=? )"
				,[params.long('embarqueId'),params.long('id')])
		embarques=embarques.findAll{
			it.contenedor
		}
		def res =embarques.groupBy({it.contenedor})
		[distribucionId:params.id
			,sucursal:params.sucursal
			,embarqueId:params.embarqueId
			,partidas:res]
	}
	
	def asignarContenedor(Distribucion dist,String sucursal){
		//println 'asignando contenedor: '+params
		//def dist=Distribucion.get(params.distribucionId)
		//println 'Agregando adignacion a coleccion: '+dist.partidas.size()
		def embarques=EmbarqueDet.findAll("from EmbarqueDet d left join fetch d.compraDet c left join fetch c.compra com where d.embarque=?",[dist.embarque])
		JSONArray jsonArray=JSON.parse(params.partidas);
		
		jsonArray.each {
			def asignables=embarques.findAll { row ->
				row.contenedor==it
			}
			asignables.each { row ->
				println 'Asignando EmbarqueDet: '+row.class+'  Registro: '+row
				def dd=new DistribucionDet()
				dd.embarqueDet=row
				dd.contenedor=row.contenedor
				dd.tarimas=row.tarimas
				dd.cantidad=row.cantidad
				dd.kilosNetos=row.kilosNetos
				dd.cantidadPorTarima=dd.cantidad/dd.tarimas
				dd.sucursal=sucursal
				dd.comentarios=row.compraDet.compra.comentario
				def res=dist.addToPartidas(dd)
				dist.save flush:true
			}
			dist.save failOnError:true,flush:true
		}
		//dist.save(failOnError:true)
		def res=[disttribucionId:dist.id]
		render res as JSON
	}
	
	def eliminarPartida(){
		//println 'Eliminando partidas: '+params
		def dist=Distribucion.findById(params.distribucionId,[fetch:[partidas:'eager']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		jsonArray.each {det ->
			def found=dist.partidas.find{ it.id==det.toLong()}
			//println 'Eliminando: DistribucionDet: '+found.id+ " Size: "+dist.partidas.size()
			dist.removeFromPartidas(found)
			//println 'After:'+dist.partidas.size()
		}
		dist.save failOnError:true,flush:true
		render 'OK'
		
	}
	
	def impresionDeDistribucion(){
		println 'Imprimiendo con parametros: '+params
		def distribucion=Distribucion.get(params.id)
		def empresa=Empresa.findAll([max:1]).get(0)
		[distribucion:distribucion,empresa:empresa]
		
	}
	
	def contenedoresJSON(long distribucionId){
		//println 'Contenedores de distribucion: '+params
		//def distribucion=Distribucion.findById(distribucionId,[fetch:[partidas:'eager']])
		//render (template="partidasPanel",model:[partidas:distribucion.partidas],bean:distribucion)
		def contenedores=DistribucionDet.executeQuery("select distinct(contenedor) from DistribucionDet d where d.distribucion.id=? and upper(d.contenedor) like ?",[distribucionId,'%'+params.term.toUpperCase()+'%'])
		def contenedoresList=contenedores.collect { row ->
			def label=row
			[id:label,label:label,value:label]
		}
		render contenedoresList as JSON
	}
	
	
	
	def asignarFechaEntrada(long id){
		Date fechaEntrada=params.date('fechaEntrada','dd/MM/yyyy')
		def distribucion=Distribucion.findById(id,[fetch:[partidas:'eager']])
		
		def dataToRender=[:]
		def selected=distribucion.partidas.findAll{
			it.contenedor==params.contenedor
		}
		selected.each {
			println 'Asignando fecha de entrada: '+it.id+'  Fecha; '+fechaEntrada
			it.fechaDeEntrada=fechaEntrada
			dataToRender.id=it.id
		}
		render dataToRender as JSON
	}
	
	def selectorParaFechaDeEntrega(){
		//println 'selector de partidas: '+params
		def embarques=EmbarqueDet.findAll(
			"from EmbarqueDet d where d.embarque.id=? and d.contenedor  in(select x.contenedor from DistribucionDet x where x.distribucion.id=? )"
				,[params.long('embarqueId'),params.long('id')])
		embarques=embarques.findAll{
			it.contenedor
		}
		def res =embarques.groupBy({it.contenedor})
		
		[distribucionId:params.id
			,sucursal:params.sucursal
			,embarqueId:params.embarqueId
			,partidas:res]
	}
	
	def asignarFechaEntrega(){
		//println 'Params;'+params
		def dataToRender=[:]
		Date fechaEntrega=params.date('fechaEntrega','dd/MM/yyyy')
		def distribucion=Distribucion.findById(params.distribucionId,[fetch:[partidas:'eager']])
		def contenedores=JSON.parse(params.partidas);
		//println 'Contenedores a procesar: '+contenedores
		contenedores.each{ contenedor ->
			def selected=distribucion.partidas.findAll{
				it.contenedor==contenedor
			}
			//println 'Partidas seleccionadas: '+selected
			selected.each {
				//println 'Entrega: '+it.id+'  Fecha; '+fechaEntrega
				it.programacionDeEntrega=fechaEntrega
			}
		}
		distribucion.save failOnError:true,flush:true
		dataToRender.res='OK'
		render dataToRender as JSON
	}
}
