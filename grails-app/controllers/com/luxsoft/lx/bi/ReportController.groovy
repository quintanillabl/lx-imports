package com.luxsoft.lx.bi



import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable
import grails.validation.ValidationException
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.apache.commons.lang.WordUtils
import org.grails.databinding.BindingFormat
import groovy.sql.Sql


import util.Periodo
import groovy.transform.ToString
import com.luxsoft.impapx.*

// import pl.touk.excel.export.XlsxExporter
// import pl.touk.excel.export.getters.LongToDatePropertyGetter
// import pl.touk.excel.export.getters.MessageFromPropertyGetter

@Secured(["hasAnyRole('USUARIO','OPERADOR')"])
class ReportController {

	def jasperService

	def dataSource

	static defaultAction = "index"

	def index(){}

    def run(ReportCommand command){
    	if (command.hasErrors()) {
    	    throw new ValidationException('Errores de validacion en ReportCommand',command.errors)
    	}
    	log.info "Ejectuando reporte: "+command
    	def reportDef=new JasperReportDef(
    		name:command.reportName,
    		fileFormat:command.getJasperFormat(),
    		parameters:params
    		)
    	ByteArrayOutputStream  stream=jasperService.generateReport(reportDef)
		render(
			file: stream.toByteArray(), 
			contentType: command.getMimeType(),
			fileName:command.reportName)
    }

	

	protected void notFound() {
	    flash.message = "No localizo el reporte params: "+params
	    redirect action: params.actionName
	}
	
	
	
}

@ToString(includeNames=true,includePackage=false)
class ReportCommand{

	String reportName
	Empresa empresa
	String formato='PDF'

	static constraints={
		reportName nullable:false
		formato inList:['PDF','XLS','CSV']
	}

	String getMimeType(){
		return JasperExportFormat.determineFileFormat(formato).mimeTyp
	}

	def getJasperFormat(){
		return JasperExportFormat.determineFileFormat(formato)
	}

	def getDownloadFileName(){
		return "${reportName}_"+new Date().format('dd_MM_yyyy_hh_mm')+'.'+formato.toLowerCase()
	}

}

@Validateable 
class FechaCommand extends ReportCommand{
	
	@BindingFormat('dd/MM/yyyy')
	Date fecha=new Date()

	static constraints={
		fecha nullable:false
	}
}

@Validateable 
class PeriodoCommand extends ReportCommand{

	@BindingFormat('dd/MM/yyyy')
	Date fechaInicial=new Date()-30
	
	@BindingFormat('dd/MM/yyyy')
	Date fechaFinal=new Date()

	static constraints={
		fechaInicial nullable:false
		fechaFinal nullable:false
	}

	Periodo toPeriodo(){
		retur new Periodo(fechaInicial,fechaFinal)
	}
}

@Validateable
class PorClienteCommand extends ReportCommand{

	Cliente cliente
	@BindingFormat('dd/MM/yyyy')

	Date fechaInicial=new Date()-30
	@BindingFormat('dd/MM/yyyy')
	
	Date fechaFinal=new Date()
	

	static constraints={
		fechaInicial nullable:true
		fechaFinal nullable:true
	}
}


