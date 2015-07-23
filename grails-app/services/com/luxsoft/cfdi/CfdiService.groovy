package com.luxsoft.cfdi

import grails.util.Environment;

import java.util.List;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlValidationError;

import mx.gob.sat.cfd.x3.ComprobanteDocument;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.InitializingBean

import com.edicom.ediwinws.cfdi.client.CfdiClient;
import com.edicom.ediwinws.cfdi.utils.Base64Util;
import com.edicom.ediwinws.service.cfdi.CancelaResponse;
import com.luxsoft.impapx.Empresa;
import com.luxsoft.impapx.Venta;
import com.luxsoft.impapx.cxc.CXCNota;

class CfdiService implements InitializingBean{

	def grailsApplication
	
	def cfdiSellador
	
	
	def cfdiTimbrador
	
    def Cfdi generarCfdi(def source) {
		
		def empresa=Empresa.last()
		assert empresa,"Debe existir la empresa"
		
		def serie=null
		if(source instanceof Venta){
			if(source.tipo=='NOTA_DE_CARGO')
				serie='CAR'
			else
				serie='FAC'
		}
		if(source instanceof CXCNota)
			serie='CRE'
			
			
		def cfdiFolio=CfdiFolio.findByEmisorAndSerie(empresa.rfc,serie)
		assert cfdiFolio," Debe registrar folio de  para la serie "+serie
		def folio=cfdiFolio.next()
		
		println "Generando CFDI folio:$folio  Serie:$serie y rfc:$empresa.rfc  Para entidad: $source.tipo"
		def cfdi=CfdiConverters.toCfdi(source,empresa)
		cfdi.serie=serie
		cfdi.folio=folio
		
		def ComprobanteDocument document=CfdiConverters.toComprobante(source, empresa)
		assert document," Debe existir la conversion a ComprobanteDocument de la entidad: "+source.class
		Comprobante comprobante=document.getComprobante()
		comprobante.serie=serie
		comprobante.folio=folio
		
		//comprobante.setSello(cfdiSellador.sellar(CFDIUtils.leerLlavePrivada(empresa),document))
		comprobante.setSello(cfdiSellador.sellar(empresa.privateKey,document))
		byte[] encodedCert=Base64.encode(empresa.getCertificado().getEncoded())
		comprobante.setCertificado(new String(encodedCert))
		
		XmlOptions options = new XmlOptions()
		options.setCharacterEncoding("UTF-8")
		options.put( XmlOptions.SAVE_INNER )
		options.put( XmlOptions.SAVE_PRETTY_PRINT )
		options.put( XmlOptions.SAVE_AGGRESSIVE_NAMESPACES )
		options.put( XmlOptions.SAVE_USE_DEFAULT_NAMESPACE )
		options.put(XmlOptions.SAVE_NAMESPACES_FIRST)
		ByteArrayOutputStream os=new ByteArrayOutputStream()
		document.save(os, options)
		
		cfdi.setXml(os.toByteArray())
		cfdi.setXmlName("$cfdi.rfc-$cfdi.serie-$cfdi.folio"+".xml")
		
		validarDocumento(document)		
		cfdi.save(failOnError:true)
		if(cfdiTimbrador==null){
			//cfdiTimbrador=new CfdiTimbrador(timbradoDePrueba:false)
			println 'Algo anda mal el timbrador no puede ser nulo'
		}
		cfdi=cfdiTimbrador.timbrar(cfdi,"PAP830101CR3", "yqjvqfofb")
		return cfdi
    }
	
	void validarDocumento(ComprobanteDocument document) {
		List<XmlValidationError> errores=findErrors(document);
		if(errores.size()>0){
			StringBuffer buff=new StringBuffer();
			for(XmlValidationError e:errores){
				buff.append(e.getMessage()+"\n");
			}
			throw new CfdiException(message:"Datos para generar el comprobante fiscal (CFD) incorrectos "+buff.toString());
		}
	}
	
	List findErrors(final XmlObject node){
		final XmlOptions options=new XmlOptions();
		final List errors=new ArrayList();
		options.setErrorListener(errors);
		node.validate(options);
		return errors;
		
	}
	
	def Cfdi cancelar(Cfdi cfdi){
		
		println 'Mandando cancelar CFDI: '+cfdi.uuid
		
		
		def rfc=cfdi.getComprobante().emisor.rfc
		assert cfdi.getTimbrado(),"Debe estar timbrado: "+cfdi
		Empresa empresa=Empresa.findByRfc(rfc)
		assert empresa,"Debe existir la empresa con rfc: "+rfc
		/*
		File cert=new File("web-app/cfd/00001000000202323568.cer")
		cert.getBytes()
		empresa.certificadoDigital=cert.getBytes()
	
		File pk=new File("web-app/cfd/impap2012.key")
		empresa.llavePrivada=pk.getBytes()
		
		File pfx=new File("web-app/cfd/certificadoimpap.pfx")
		empresa.certificadoDigitalPfx=pfx.getBytes()
		*/
		
		//def uuidList=new String[1]{cfdi.uuid}
		def  uuidList=[cfdi.uuid] as String[]
		File dir=new File(System.properties['user.home'])
		assert dir.exists(),'Debe existir el directorio: '+dir
		assert dir.isDirectory()
		
		Environment.executeForCurrentEnvironment {
			production{
				CfdiClient client=new CfdiClient()
				CancelaResponse res=client.cancelCfdi(
							"PAP830101CR3"
							,"yqjvqfofb"
							, empresa.getRfc()
							, uuidList
							, empresa.getCertificadoDigitalPfx()
							, empresa.getPasswordPfx())
				
				String text=Base64.decode(res.getText())
				byte[] aka=Base64.decode(res.getAck())
				String name=cfdi.emisor+'-'+cfdi.serie+'-'+cfdi.folio
				
				File akaFile=new File(dir,name+'_CANCELACION_AKA.xml')
				akaFile.setText(new String(aka))
				
				File file1=new File(dir,name+'_CANCELACION_RES.txt')
				file1.setText(new String(text))
			}
			development{
				println 'Entorno de desarrollo no se manda cancelar con el PAC...'
			}
		}
		
		
		
		cfdi.comentario="CANCELADO ORIGEN: "+cfdi.origen
		cfdi.origen='CANCELACION'
		cfdi.save(flush:true)
		
		return cfdi
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(cfdiTimbrador==null){
			println 'Error timbrador no detectado'
			println 'Env: '+Environment.getCurrent().getName()
		}
		 
	}
}


