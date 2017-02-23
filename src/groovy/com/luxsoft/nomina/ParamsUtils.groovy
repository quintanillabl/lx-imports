package com.luxsoft.nomina


import org.apache.commons.lang.StringUtils

import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Emisor
import mx.gob.sat.cfd.x3.TUbicacion

import mx.gob.sat.nomina12.NominaDocument
import mx.gob.sat.nomina12.NominaDocument.Nomina

import com.luxsoft.cfdi.QRCodeUtils
import com.luxsoft.cfdi.TimbreFiscal
import com.luxsoft.cfdi.ImporteALetra
import com.luxsoft.lx.utils.*



class ParamsUtils {

	static getParametros(Comprobante comprobante, Nomina nomina, NominaAsimilado nominaPorEmpleado){
		
		def parametros=[:]
		Emisor emisor=comprobante.getEmisor();
		parametros.put("EMISOR_NOMBRE", 	emisor.getNombre())
		parametros.put("EMISOR_RFC", 		emisor.getRfc())
		parametros.put("EMISOR_DIRECCION", comprobante.lugarExpedicion)
		parametros.put("EXPEDIDO_DIRECCION", comprobante.lugarExpedicion)
		parametros.put("REGIMEN",comprobante.getEmisor().getRegimenFiscalArray(0).regimen)
		parametros.put("METODO_DE_PAGO", 		comprobante.getMetodoDePago())
		
		// Datos tomados del Comprobante fiscal digital XML
		parametros.put("NOMBRE", 			comprobante.getReceptor().getNombre()) //Recibir como Parametro
		parametros.put("RFC", 				comprobante.getReceptor().getRfc())
		parametros['NUMERO_EMPLEADO'] = nomina.receptor.numEmpleado
		parametros['NUMERO_IMSS'] = nomina.receptor.numSeguridadSocial
		parametros['CURP'] = nomina.receptor.curp
		parametros['REGIMEN_TRABAJADOR'] = nomina.receptor.tipoRegimen.toString()
		parametros['EMISOR_RFC']=comprobante.emisor.rfc
		parametros['PERIOCIDAD_PAGO'] = nomina.receptor.periodicidadPago.toString()
		parametros.put("SERIE", 			comprobante.serie)
		parametros.put("FOLIO", 			comprobante.folio)
		parametros.put("NUM_CERTIFICADO", 	comprobante.getNoCertificado())
		parametros.put("SELLO_DIGITAL", 	comprobante.getSello())
		parametros.put("IMP_CON_LETRA", 	ImporteALetra.aLetra(comprobante.getTotal()))
		//parametros['REGISTRO_PATRONAL'] = nomina.emisor.registroPatronal
		parametros.put("NFISCAL", 			comprobante.getSerie() + " - " +comprobante.getFolio())
		
		//parametros['RIESGO_PUESTO'] = ''+nomina.receptor.riesgoPuesto
		parametros['TIPO_JORNADA'] = nomina.receptor.tipoJornada.toString()
		
		parametros['FECHA_INGRESO_LABORAL'] = nomina.receptor.fechaInicioRelLaboral?.format("yyyy-MM-dd")
		parametros['ANTIGUEDAD'] = '' + nomina.receptor.antigüedad
		parametros['TIPO_CONTRATO'] = nomina.receptor.tipoContrato.toString()
		
		parametros['FECHA_INICIAL']=nomina.fechaInicialPago?.format("yyyy-MM-dd")
		parametros['FECHA_FINAL']=nomina.fechaFinalPago?.format("yyyy-MM-dd")
		
		parametros['CLABE'] = nomina.receptor.cuentaBancaria?.toString()
		parametros['BANCO'] = nominaPorEmpleado.asimilado?.bancoSat?.clave
		parametros['TOTAL'] = comprobante.getTotal() //as String
		parametros['COMENTARIO_NOM'] = 'Nómina'
		def img = QRCodeUtils.generarQR(comprobante)
		parametros.put("QR_CODE",img)
		
		TimbreFiscal timbre = new TimbreFiscal(comprobante)
		parametros.put("FECHA_TIMBRADO", timbre.FechaTimbrado)
		parametros.put("FOLIO_FISCAL", timbre.UUID)
		parametros.put("SELLO_DIGITAL_SAT", timbre.selloSAT)
		parametros.put("CERTIFICADO_SAT", timbre.noCertificadoSAT)
		parametros.put("CADENA_ORIGINAL_SAT", timbre.cadenaOriginal())
		
		//parametros['SALARIO_DIARIO_BASE']=nomina.receptor.salarioBaseCotApor as String
		//parametros['SALARIO_DIARIO_INTEGRADO']=nomina.receptor.salarioDiarioIntegrado as String
		
		parametros['TIPO_NOMINA']=nomina.tipoNomina.toString()
		//parametros['SUCURSAL']=nominaPorEmpleado.empleado.perfil.ubicacion.clave
		//parametros['PUESTO'] = nomina.receptor.puesto
		//parametros['DEPARTAMENTO']=nomina.receptor.departamento
		parametros['FECHA']= comprobante.fecha.getTime().format("yyyy-MM-dd'T'HH:mm:ss")
		//parametros['SUB_EMPLEO_APLIC'] = nominaPorEmpleado.subsidioEmpleoAplicado
		
		
		return parametros
	}
}