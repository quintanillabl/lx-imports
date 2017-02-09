package com.luxsoft.com.sx4.bi

import grails.plugin.springsecurity.annotation.Secured
import groovy.sql.Sql
import com.luxsoft.utils.Periodo
import org.grails.databinding.BindingFormat
import groovy.transform.ToString
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import grails.validation.Validateable
import com.luxsoft.lx.bi.*
import com.luxsoft.impapx.*


@Secured(["hasRole('USUARIO')"])
class EmbarquesBiController {

    def index(SearchEmbarques command) { 

    	params.max = 100
    	def list = []

    	log.info 'Localinzando con command: '+command
    	// def list=Embarque.findAll(
    	//     "from Embarque e  where date(e.dateCreated) between ? and ? order by e.lastUpdated desc",
    	//     [periodo.fechaInicial,periodo.fechaFinal])
    	
    	[embarqueInstanceList:list,searchCommand:new SearchEmbarques()]
    }

    def search(SearchEmbarques command){

    	params.max = 100

    	def q
    	def list = [] 
    	if(command.bl){
    		q = EmbarqueDet.where {
    			embarque.bl =~ command.bl
    		}
    		list = q.list(params)
    	}
    	else if(command.pedimento){
    		q = EmbarqueDet.where {
    			pedimento.pedimento =~ command.pedimento
    		}
    		list = q.list(params)

    	}else if(command.contenedor){

    		q = EmbarqueDet.where {
    			contenedor =~ command.contenedor
    		}
    		list = q.list(params)
    		
    	}else if(command.factura){
    		q = EmbarqueDet.where {
    			factura.documento =~ command.factura
    		}
    		list = q.list(params)
    	}else if(command.descripcion){
    		q = EmbarqueDet.where {
    			producto.descripcion =~ command.descripcion
    		}
    		list = q.list(params)
    	}

    	render view:'index',model:[rows:list,searchCommand:command]
    }

    
}

@Validateable
@ToString(includeNames=true,includePackage=false)
class SearchEmbarques {

	String bl
	String pedimento
	String contenedor
	String factura
	String descripcion
	

	static constraints={
		bl nullable:true
		pedimento nullable:true
		contenedor nullable:true
		factura nullable:true
		descripcion nullable:true
	}


}
