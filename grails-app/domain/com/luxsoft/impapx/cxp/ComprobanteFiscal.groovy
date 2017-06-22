package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.CuentaPorPagar
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='uuid')
@ToString(excludes='cfdi,acues',includeNames=true,includePackage=false)
class ComprobanteFiscal {

	static auditable = true	

	String versionCfdi
	byte[] cfdi
	String cfdiFileName
	String uuid
	String serie
	String folio
	Date fecha

	String emisorRfc
	String receptorRfc
	BigDecimal total

	byte[] acuse
	String acuseEstado
	String acuseCodigoEstatus
	CuentaPorPagar cxp

	Date dateCreated
	Date lastUpdated

    static constraints = {
    	uuid maxSize:40,unique:true
    	serie nullable:true,maxSize:20
    	folio nullable:true,maxSize:20
    	cfdiFileName nullable:true,maxSize:200
		cfdi maxSize:(1024 * 512)  // 50kb para almacenar el xml
		acuse nullable:true,maxSize:(1024*256)
		acuseEstado nullable:true,maxSize:100
		acuseCodigoEstatus nullable:true,maxSize:100
		versionCfdi nullable:true,maxSize:10
    }

    static mapping ={
		fecha type:'date'
		
	}
    
}
