package com.luxsoft.cfdix.v33

import net.sf.jasperreports.engine.JRExporterParameter
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.engine.export.JRPdfExporterParameter
import net.sf.jasperreports.export.PdfExporterConfiguration


import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.core.io.Resource

import com.luxsoft.nomina.NominaAsimilado
import lx.cfdi.v33.nomina.Nomina

import java.io.ByteArrayInputStream

import com.luxsoft.cfdi.Cfdi

import com.luxsoft.cfdix.v33.V33CfdiUtils

class Cfdi33NominaPrintService {


	def imprimir(NominaAsimilado ne, def params = [:]){
		/*
		Cfdi cfdi = ne.cfdi
		assert cfdi, 'Debe generar el cfdi para la nomina asimilado ' + ne.id
		assert cfdi.uuid, 'No se ha timbrado el cfdi: ' + ne.cfdi.id

		def comprobante = V33CfdiUtils.toComprobante(cfdi)
		Nomina nomina = getComplemento(cfdi)
		def modelData = []
		registrarDeducciones(nomina, modelData)
		registrarPercepciones(nomina, modelData)
		registrarOtrosPagos(nomina, modelData)

		modelData.sort{
			it.clave
		}

		def repParams = ParamsUtils.getParametros(cfdi.comprobante, nomina, ne)
		params.FECHA = comprobante.fecha.getTime().format("yyyy-MM-dd'T'HH:mm:ss")
		params << repParams

		params['RECIBO_NOMINA'=]ne.id as String
		params[PdfExporterConfiguration.PROPERTY_PDF_JAVASCRIPT]="this.print();"

		def reportDef=new JasperReportDef(
			name: 'NominaDigitalCFDI'
			,fileFormat: JasperExportFormat.PDF_FORMAT
			,reportData: modelData,
			,parameters: params
			)
		Resource resource = reportDef.getReport()
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(reportDef.reportData)
		JasperPrint print= JasperFillManager.fillReport(JasperCompileManager.compileReport(resource.inputStream)
			, reportDef.parameters
			, jrBeanCollectionDataSource)
		ByteArrayOutputStream  pdfStream = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, pdfStream); // your output goes here
		exporter.exportReport();

		return pdfStream
		*/
	}
	
	/*
	def registrarDeducciones(Nomina nomina, def modelData){
		def deducciones = nomina?.deducciones?.deduccionArray
		deducciones.each { cc ->
			def res=[
				'GRUPO':cc.tipoDeduccion.toString(),
				'CLAVE':cc.clave,
				'DESCRIPCION':cc.concepto,
				'IMPORTE_GRAVADO': 0.0,
				'IMPORTE_EXENTO': cc.importe,
				'CONCEPTO':'D'
			 ]
			modelData << res
		}
	}

	def registrarPercepciones(Nomina nomina, def modelData){
		def percepciones=nomina.percepciones.percepcionArray
		percepciones.each{ cc->
			def res = [
				'GRUPO':cc.tipoPercepcion.toString(),
				'CLAVE':cc.clave,
				'DESCRIPCION':cc.concepto,
				'IMPORTE_GRAVADO':cc.importeGravado,
				'IMPORTE_EXENTO':cc.importeExento,
				'CONCEPTO':'P'
			 ]
			modelData<<res
		}
	}

	def registrarOtrosPagos(Nomina nomina, def modelData){
		if( nomina.otrosPagos) {
			def otrosPagos=nomina.otrosPagos.otroPagoArray
			otrosPagos.each{ cc->
				def res=[
					'GRUPO':cc.tipoOtroPago.toString(),
					'CLAVE':cc.clave,
					'DESCRIPCION':cc.concepto,
					'IMPORTE_GRAVADO':cc.importe,
					'IMPORTE_EXENTO':cc.importe,
					'CONCEPTO':'P'
				 ]
				modelData<<res
			}
		}
	}
	*/
}