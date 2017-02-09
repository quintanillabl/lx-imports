package com.luxsoft.impapx

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

import groovy.sql.Sql;
import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;

import com.luxsoft.lx.contabilidad.PeriodoContable
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
class DiotController {

   def dataSource

   def diotService

    def beforeInterceptor = {
       	if(!session.periodoContable){
       		session.periodoContable=new Date()
       	}
   	}

   	def cambiarPeriodo(){
   		def fecha=params.date('fecha', 'dd/MM/yyyy')
   		session.periodoContable=fecha
   		redirect(uri: request.getHeader('referer') )
   	}	
   
   def index(){
	   render view:'diot' ,model:[rows:[]]
   }
   
   
   
   	def generar(){
   		def periodo=session.periodoContable
	   	def res=diotService.generarDiot(periodo)
	   	render view:'diot' ,model:[rows:res.rows,diots:res.gdiots,downloadFile:periodo.text()] 
   	}
   
   def generarArchivo(){
	   
	   if(!params.downloadFile)
	   	 redirect action:'generar'
	   def fecha=new Date().parse('dd/MM/yyyy',params.downloadFile)
	   def rows=diotService.generarDiot(fecha).gdiots
	   
	   String pattern = "{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|||{10}|||{8}||||||{9}|{11}||\n"
	   
	   StringWriter writer=new StringWriter()
	   rows.each {d->
		   def e=(d.base-d.base11).setScale(0, BigDecimal.ROUND_HALF_UP)
		   
		   String line = MessageFormat.format(pattern,
				d.getTipoTercero()?:''
			   ,d.getTipoModificado()?:''
			   ,d.getRfcFinal()?:''
			   ,d.getIdFiscal()?:''
			   ,d.nacional?'':d.proveedor=='PROVEEDOR GLOBAL'?'':d.proveedor
			   ,d.nacional?'':d.pais?:''
			   ,d.nacional?'':d.nacionalidad?:''
			   ,d.nacional?e?.toPlainString():''
			   ,d.nacional?'':d.base.setScale(0, BigDecimal.ROUND_HALF_UP)?.toPlainString()
			   ,d.excento?d.excento.setScale(0, BigDecimal.ROUND_HALF_UP)?.toPlainString():''
			   ,d.nacional?d.base11.setScale(0, BigDecimal.ROUND_HALF_UP)?.toPlainString():''
			   ,d.nacional?d.ret1.setScale(0, BigDecimal.ROUND_HALF_UP)?.toPlainString():''
			   );
		  writer << line << "\n"
	   }
	   response.setHeader "Content-disposition", "attachment; filename=DIOT_${params.downloadFile}.txt"
	   response.contentType = 'text-plain'
	   response.outputStream << writer
	   response.outputStream.flush()
	  
   }
}

@ToString
@EqualsAndHashCode (includes=["proveedor"])
class Diot{
	
	String proveedor
	String rfc
	Boolean nacional
	BigDecimal base
	BigDecimal excento
	String tipo
	String pais
	String nacionalidad
	BigDecimal base11
	BigDecimal ret1
	
	
	def getTipoTercero(){
		if(proveedor=='PROVEEDOR GLOBAL')
			return '15'
		else if(nacional)
			return '04'
		else
			return '05'
	}
	
	def getRfcFinal(){
		if(proveedor=='PROVEEDOR GLOBAL')
			return ''
		else if(nacional)
			return rfc
		else
			return ''
	}
	
	def getIdFiscal(){
		if(!nacional){
			return rfc
		}else{
			return ''
		}
	}
	
	
	def getTipoModificado(){
		if(proveedor.startsWith("RSM BOGARIN"))
			return '03'
		else
			return tipo
	}

	
}
