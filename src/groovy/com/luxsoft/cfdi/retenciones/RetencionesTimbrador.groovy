package com.luxsoft.cfdi.retenciones

import java.text.SimpleDateFormat

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils

import com.edicom.ediwinws.cfdi.client.CfdiClient
import com.edicom.ediwinws.cfdi.utils.ZipUtils

import org.apache.commons.logging.LogFactory
import com.luxsoft.impapx.Empresa

class RetencionesTimbrador {
	
	ZipUtils zipUtils=new ZipUtils()
	CfdiClient cfdiClient=new CfdiClient()
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	def timbradoDePrueba
	
	private static final log=LogFactory.getLog(this)
	
	CfdiRetenciones timbrar(CfdiRetenciones cfdi){
		log.info 'Usando cliente edicom: '+cfdiClient
		def empresa=Empresa.findByRfc(cfdi.emisorRfc)
		
		assert empresa.usuarioPac,"Debe registrar un usuario para el servicio del PAC "
		assert empresa.passwordPac,"Debe registrar un password para el servicio del PAC "
		String user=empresa.usuarioPac
		String password=empresa.passwordPac
		
		String nombre=cfdi.xmlName
		byte[] xml=cfdi.xml
		assert xml,'El cfdi esta mal generado no contiene datos xml'
		assert nombre,"El cfdi esta mal generado no define nombre de archivo xmlName"
		byte[] zipFile=zipUtils.comprimeArchivo(nombre, xml)
		
		byte[] res
		if(timbradoDePrueba){
			log.info 'Timbrando de prueba: '+cfdi
			cfdiClient.getCfdiRetencionesTest(user, password, zipFile)
			//res=cfdiClient.getCfdiRetencionesTest(user, password, zipFile)
		}else{
			log.debug 'Timbrando real de: '+cfdi
			res=cfdiClient.getCfdi(user, password, zipFile)
		}
		cfdi.save(failOnError:true)
		return cfdi
		
	}

	

}
