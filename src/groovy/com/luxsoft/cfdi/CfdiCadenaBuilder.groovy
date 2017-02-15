package com.luxsoft.cfdi

import mx.gob.sat.cfd.x3.ComprobanteDocument;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * Generador de cadena original
 * 
 * @author Ruben Cancino 
 *
 */
class CfdiCadenaBuilder implements ResourceLoaderAware{
	
	File xsltFile

	StreamSource source
	
	
	
	/**
	 * Genera la cadean original de un comprobante fiscal digital
	 * 
	 * @return La cadena original
	 */
	String generarCadenaOld(ComprobanteDocument document){
		TransformerFactory factory=TransformerFactory.newInstance()
		xsltFile=resourceLoader.getResource("WEB-INF/sat/cadenaoriginal_3_2.xslt").getFile()
		assert xsltFile.exists(),"No existe el archivo xslt para la cadena del sat: "+xsltFile.getPath()
		StreamSource source=new StreamSource(xsltFile);
		Transformer transformer=factory.newTransformer(source);
		Writer writer=new StringWriter();
		StreamResult out=new StreamResult(writer);
		Source so=new DOMSource(document.getDomNode());
		transformer.transform(so, out);
		return writer.toString();
		
	}

	String generarCadena(ComprobanteDocument document){
		TransformerFactory factory=TransformerFactory.newInstance()
		if(!source)
			source = new StreamSource("http://www.sat.gob.mx/sitio_internet/cfd/3/cadenaoriginal_3_2/cadenaoriginal_3_2.xslt")
		Transformer transformer=factory.newTransformer(source)
		Writer writer=new StringWriter()
		StreamResult out=new StreamResult(writer)
		Source so=new DOMSource(document.getDomNode())
		transformer.transform(so, out)
		return writer.toString()
	}
	
	ResourceLoader resourceLoader

	@Override
	public void setResourceLoader(ResourceLoader arg0) {
		resourceLoader=arg0
		
	}

}
