package com.luxsoft.cfdix.v32

import javax.xml.bind.JAXBContext
import java.text.SimpleDateFormat
import org.apache.commons.lang.builder.ToStringStyle
import org.apache.commons.lang.builder.ToStringBuilder


import com.luxsoft.cfdi.Cfdi
import lx.cfdi.v32.Comprobante
import com.luxsoft.cfdi.v32.CfdiUtils

class V32CfdiUtils {

	final static SimpleDateFormat CFDI_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	public static Comprobante toComprobante(Cfdi cfdi){
		JAXBContext context = JAXBContext.newInstance(Comprobante.class)
		def unmarshaller = context.createUnmarshaller()
		Comprobante comprobante = (Comprobante)unmarshaller
			.unmarshal(new ByteArrayInputStream(cfdi.xml))
		return comprobante
	}

}