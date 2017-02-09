package com.luxsoft.cfdi.retenciones

import java.text.SimpleDateFormat

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils

import com.edicom.ediwinws.cfdi.client.CfdiClient
import com.edicom.ediwinws.cfdi.utils.ZipUtils

import com.luxsoft.cfdi.CfdiException
import com.luxsoft.impapx.Empresa

class RetencionesTimbrador {
	
	ZipUtils zipUtils=new ZipUtils()
	CfdiClient cfdiClient=new CfdiClient()
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	def timbradoDePrueba
	
	//private static final log=LogFactory.getLog(this)
	
	CfdiRetenciones timbrar(CfdiRetenciones cfdi){
		try {
			println 'Usando cliente edicom: '+cfdiClient

			def empresa=Empresa.findByRfc(cfdi.emisorRfc)
			
			//assert empresa.usuarioPac,"Debe registrar un usuario para el servicio del PAC "
			//assert empresa.passwordPac,"Debe registrar un password para el servicio del PAC "
			//, 
			String user="PAP830101CR3"
			String password="yqjvqfofb"
			
			String nombre=cfdi.xmlName
			byte[] xml=cfdi.xml
			assert xml,'El cfdi esta mal generado no contiene datos xml'
			assert nombre,"El cfdi esta mal generado no define nombre de archivo xmlName"
			byte[] zipFile=zipUtils.comprimeArchivo(nombre, xml)
			
			byte[] res
			if(timbradoDePrueba){
				println 'Timbrando de prueba: '+cfdi
				res = cfdiClient.getCfdiRetencionesTest(user, password, zipFile)
				//res=cfdiClient.getCfdiRetencionesTest(user, password, zipFile)
			}else{
				println 'Timbrando real de: '+cfdi
				//res=cfdiClient.getCfdiRetenciones(user, password, zipFile)
				res = cfdiClient.getCfdiRetencionesTest(user, password, zipFile)
			}
			cfdi.xmlName=entry.getKey()
			cfdi.xml=entry.getValue()
			/*
			cfdi.loadComprobante()
			cfdi.timbreFiscal=new TimbreFiscal(cfdi.getComprobante())
			cfdi.uuid=cfdi.timbreFiscal.UUID
			cfdi.timbrado=df.parse(cfdi.timbreFiscal.FechaTimbrado)
			*/
			cfdi.save(failOnError:true)
			return cfdi
			
		}
		catch(Exception e) {
			String msg="Imposible timbrar cfdi $cfdi.id Error: "+ExceptionUtils.getMessage(e)
			throw new CfdiException(message:msg,cfdi:cfdi)
		}
		
		
		
	}

	

}
