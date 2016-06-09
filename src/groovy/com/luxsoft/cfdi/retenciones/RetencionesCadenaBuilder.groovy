package com.luxsoft.cfdi.retenciones


import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import javax.xml.bind.util.JAXBSource
import javax.xml.bind.*
import org.springframework.context.ResourceLoaderAware
import org.springframework.core.io.ResourceLoader
import org.apache.commons.logging.LogFactory


/**
 * Generador de cadena original
 * 
 * @author Ruben Cancino 
 *
 */
class RetencionesCadenaBuilder implements ResourceLoaderAware{
	
	private static final log=LogFactory.getLog(this)
	
	/**
	 * Genera la cadean original de un comprobante fiscal digital
	 * 
	 * @return La cadena original
	 */
	String generarCadena(Retenciones retenciones){
		
		def is=resourceLoader.getResource("WEB-INF/sat/retenciones.xslt").inputStream
		TransformerFactory factory=TransformerFactory.newInstance()
		StreamSource xslt=new StreamSource(is)
		Transformer transformer=factory.newTransformer(xslt)

		JAXBContext jc = JAXBContext.newInstance(Retenciones.class);
		JAXBSource source = new JAXBSource(jc, retenciones);

		ByteArrayOutputStream out=new ByteArrayOutputStream()
		StreamResult result = new StreamResult(out);

		transformer.transform(source, result);
		def res= out.toString("UTF-8")
		log.info 'Cadena original generada: '+res
		return res
		
	}
	
	ResourceLoader resourceLoader

	@Override
	public void setResourceLoader(ResourceLoader arg0) {
		resourceLoader=arg0
		
	}
	

}
