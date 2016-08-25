package lx.econta.balanzas

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode


@ToString(includeNames=true,includePackage=false)
@EqualsAndHashCode(includes='id,ejercicio,mes')
class SatBalanzaLog {

	String versionSat='1.1'

	Integer ejercicio

    Integer mes

	String rfc

	String nombre

	String tipo

	Date fechaModBal

    String comentario

    Date enviado

    byte[] acuse

    byte[] acuseDeAceptacion

	byte[] xml

    Date dateCreated

    Date lastUpdated

    static constraints = {
    	versionSat size:3..5
    	rfc minSize:12,maxSize:13 //,matches:"[A-ZÑ&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z0-9]?[A-Z0-9]?[0-9A-Z]?"
    	ejercicio range:2015..2020
    	mes range:1..13
    	tipo inList:['NORMAL','COMPLEMENTARIA']
        xml maxSize:(1024 * 1024)  // 50kb para almacenar el xml
        acuse nullable: true, maxSize:(1024 * 512)  // 50kb para almacenar el acuse
        comentario nullable: true
        enviado nullable: true
        fechaModBal nullable:true
        acuseDeAceptacion nullable: true, maxSize:(1024 * 512)  // 50kb para almacenar el acuse
    }

    Balanza asBalanza(){
        Balanza balanza = (Balanza)BalanzaUtils
            .getContext()
            .createUnmarshaller()
            .unmarshal(new ByteArrayInputStream(this.xml))
        return balanza
    }

    String asXmlString(){
        StringWriter writer = new StringWriter()
        def balanza = asBalanza()
        BalanzaUtils.marshall(balanza,writer)
        return writer.toString()
    }


    
}
