package com.luxsoft.cfdix.v33

import groovy.util.logging.Slf4j
import lx.cfdi.v33.CadenaBuilder33

import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import grails.util.Environment

import lx.cfdi.v33.Comprobante
import lx.cfdi.v33.CfdiUtils

@Slf4j
class CfdiCadenaBuilder33 {
    
    String xsltUrl = "http://www.sat.gob.mx/sitio_internet/cfd/3/cadenaoriginal_3_3/cadenaoriginal_3_3.xslt"

    private Transformer transformer;

    String build(Comprobante comprobante){
        println 'Generando cadena original para comprobante {}'+ comprobante.folio
        // Build transformer
        Transformer transformer = getTransformer()

        // Source
        StreamSource xmlSource = buildSource(comprobante)

        // Target
        Writer writer = new StringWriter()
        StreamResult target = new StreamResult(writer)

        transformer.transform(xmlSource, target)
        String cadena = writer.toString()
        println 'Cadena original generada: {}'+ cadena
        return cadena
    }
    
    Transformer getTransformer() {
        if (!this.transformer) {
            TransformerFactory factory=TransformerFactory.newInstance()
            StreamSource source
            if(Environment.current == Environment.DEVELOPMENT) {
                // source = new StreamSource(new File('/Users/rubencancino/dumps/xslt/cadenaoriginal_3_3.xslt'))
                source = new StreamSource(new File('/Users/ruben/dumps/xslt/cadenaoriginal_3_3.xslt'))
                // source = new StreamSource(new File('/Users/rcancino/dumps/xslt/cadenaoriginal_3_3.xslt'))
            } else {
                source = new StreamSource(new File('/home/xslt/cadenaoriginal_3_3.xslt'))
            }
            this.transformer = factory.newTransformer(source)
        }
        return this.transformer
    }

    StreamSource buildSource(Comprobante comprobante) {
        String xml = CfdiUtils.serialize(comprobante)
        Reader reader = new StringReader(xml)
        return  new StreamSource(reader)
    }

}

// old file
/*package com.luxsoft.cfdix.v33

import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

import lx.cfdi.v33.Comprobante
import lx.cfdi.v33.CfdiUtils


class CfdiCadenaBuilder33 {

	String xsltUrl = "http://www.sat.gob.mx/sitio_internet/cfd/3/cadenaoriginal_3_3/cadenaoriginal_3_3.xslt"

	String build(Comprobante comprobante){

		// Build transformer
		TransformerFactory factory=TransformerFactory.newInstance()
		StreamSource source	= new StreamSource(xsltUrl)
		Transformer transformer = factory.newTransformer(source)

		// Source
		String xml = CfdiUtils.serialize(comprobante)
		Reader reader = new StringReader(xml)
		StreamSource xmlSource = new StreamSource(reader)
		// Target
		Writer writer = new StringWriter()
		StreamResult target = new StreamResult(writer)

		transformer.transform(xmlSource, target)
		return writer.toString()

	}

}
*/