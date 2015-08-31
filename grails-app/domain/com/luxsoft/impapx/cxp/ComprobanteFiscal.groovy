package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.CuentaPorPagar

class ComprobanteFiscal {

	byte[] cfdi
	String cfdiFileName
	String uuid
	String serie
	String folio

	String emisorRfc
	String receptorRfc
	BigDecimal total

	byte[] acuse
	String acuseEstado
	String acuseCodigoEstatus
	CuentaPorPagar cxp

	

    static constraints = {
    	uuid maxSize:40,unique:true
    	serie nullable:true,maxSize:20
    	folio nullable:true,maxSize:20
    	cfdiFileName nullable:true,maxSize:200
		cfdi maxSize:(1024 * 512)  // 50kb para almacenar el xml
		acuse nullable:true,maxSize:(1024*256)
		acuseEstado nullable:true,maxSize:100
		acuseCodigoEstatus nullable:true,maxSize:100
    }


    
}
