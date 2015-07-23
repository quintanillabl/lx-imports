package com.luxsoft.lx.bi

import grails.validation.ValidationException
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef


// import pl.touk.excel.export.XlsxExporter
// import pl.touk.excel.export.getters.LongToDatePropertyGetter
// import pl.touk.excel.export.getters.MessageFromPropertyGetter
import grails.transaction.Transactional


class ReportService {

	static transactional = false

	def jasperService	
	

    ByteArrayOutputStream build(ReportCommand command,Map params){
    	if (command.hasErrors()) {
    	    throw new ValidationException('Errores de validacion en ReportCommand',command.errors)
    	}
    	def reportDef=new JasperReportDef(
    		name:command.reportName,
    		fileFormat:command.getJasperFormat(),
    		parameters:params
    		)
    	ByteArrayOutputStream  stream=jasperService.generateReport(reportDef)
		return stream
    }

}
