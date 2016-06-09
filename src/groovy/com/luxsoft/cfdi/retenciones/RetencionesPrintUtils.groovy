package com.luxsoft.cfdi.retenciones


import java.text.MessageFormat
import java.text.DecimalFormat
import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.ImageIcon

import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.commons.logging.LogFactory
import groovy.xml.*

import com.luxsoft.cfdi.ImporteALetra



class RetencionesPrintUtils {

	private static final log=LogFactory.getLog(this)
	
	static resolverParametros(CfdiRetenciones retenciones){

		DecimalFormat df = new DecimalFormat("###,###.##");
		
		ByteArrayInputStream is=new ByteArrayInputStream(retenciones.xml)
		def xml=new XmlSlurper().parse(is)

		def xmlRet=xml.attributes()
		def xmlPeriodo=xml.breadthFirst().find { it.name() == 'Periodo'}.attributes()
		def xmlTotal=xml.breadthFirst().find { it.name() == 'Totales'}.attributes()


		def parametros=[:]
		parametros["IMP_CON_LETRA"]=ImporteALetra.aLetra(retenciones.getTotal())
		parametros["OPERACION"]=df.format(retenciones.getTotal())
		parametros["MES_FINAL"]=xmlPeriodo['MesIni']
		parametros["MES_INICIAL"]=xmlPeriodo['MesFin']
		parametros["EJERCICIO"]=xmlPeriodo['Ejerc']
		parametros["CADENA_ORIGINAL"]=""
		parametros["SELLO_DIGITAL"]=xmlRet['Sello']
		parametros["CLAVE_RET"]=xmlRet['CveRetenc']
		parametros["RECEPTOR_NOMBRE"]=retenciones.receptor  
		parametros["RECEPTOR_CURP"]=retenciones.receptorCurp
		parametros["RECEPTOR_RFC"]=retenciones.receptorRfc
		parametros["EMISOR_NOMBRE"]=retenciones.emisor  
		parametros["EMISOR_RFC"]=retenciones.emisorRfc
		parametros["DESCRIPCION_RET"]=retenciones.tipoDeRetencion.descripcion
		parametros["FOLIO_FISCAL"]=retenciones.uuid
		parametros["FECHA"]=xmlRet['FechaExp']
		parametros["FOLIO"]=xmlRet['FolioInt']
		
		//parametros["OPERACION"]
		parametros["GRAVADO"]=df.format(retenciones.totalGravado)
		parametros["EXENTO"]=df.format(retenciones.totalExcento)
		parametros["RETENIDO"]=df.format(retenciones.totalRetenido)
		//parametros["GRUPO"]=
		//parametros["DESTINATARIO"]
		
		parametros["CERTIFICADO"]=xmlRet['Cert']
		//parametros["CUENTA_BANCARIA"]=
		//parametros["TITULAR_CUENTA"]
		parametros["EMPRESA_LOGO"]
		
		
		//Especiales para CFDI
		if(retenciones.uuid!=null){
			
			//println 'Imagen generada: '+img
			def img=generarQR(retenciones)
			//println 'Imagen generada: '+img
			parametros.put("QR_CODE",img);
			
			
			def timbre=xml.breadthFirst().find { it.name() == 'TimbreFiscalDigital'}.attributes()

			parametros.put("FECHA_TIMBRADO", timbre['FechaTimbrado']);
			parametros.put("FOLIO_FISCAL", timbre['UUID']);
			parametros.put("SELLO_DIGITAL_SAT", timbre.selloCFD);
			parametros.put("CERTIFICADO_SAT", timbre.noCertificadoSAT);
			
		}
		
		return parametros;
	}


	static  generarQR(def retenciones) {
		try {
			String pattern="?re={0}&rr={1}&tt={2,number,##########.######}&id,{3}"
			String qq=MessageFormat.format(
				pattern, 
				retenciones.emisorRfc,
				retenciones.receptorRfc,
				retenciones.total,
				retenciones.uuid)
			log.debug 'Generando QCode para: '+qq
			File file=QRCode.from(qq).to(ImageType.GIF).withSize(250, 250).file()
			return file.toURI().toURL()
		} catch (Exception e) {
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(e),e);
		}
		
	}

	

}
