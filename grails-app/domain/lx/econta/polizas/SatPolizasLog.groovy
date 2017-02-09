package lx.econta.polizas

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode


@ToString(includeNames=true,includePackage=false)
@EqualsAndHashCode(includes='id,ejercicio,mes')
class SatPolizasLog {

    String versionSat='1.1'

	Integer ejercicio

    Integer mes

	String rfc

	String nombre

	Tipo tipo

	String numeroDeOrden

	String numeroDeTramite
	

    String comentario

    byte[] acuse

    byte[] acuseDeAceptacion

	byte[] xml

    Date dateCreated

    Date lastUpdated

    static constraints = {
    	versionSat size:3..5
    	rfc minSize:12,maxSize:13 //,matches:"[A-ZÑ&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z0-9]?[A-Z0-9]?[0-9A-Z]?"
    	ejercicio inList:[2015,2016,2017,2018,2019,2020]
    	mes  inList:[1,2,3,4,5,6,7,8,9,10,11,12,13]
    	numeroDeOrden nullable:true
    	numeroDeTramite nullable:true
        xml maxSize:(1024 * 1024)  // 50kb para almacenar el xml
        acuse nullable: true, maxSize:(1024 * 512)  // 50kb para almacenar el acuse
        comentario nullable: true
        acuseDeAceptacion nullable: true, maxSize:(1024 * 512)  // 50kb para almacenar el acuse
    }

    static mapping = {
    	//tipo enumType: 'ordinal'
    }

    Polizas asPolizas(){
        Polizas polizas = PolizasUtils.read(xml)
        return polizas
    }

    String asXmlString(){
        StringWriter writer = new StringWriter()
        def polizas = asPolizas()
        PolizasUtils.marshall(polizas,writer)
        return writer.toString()
    }
}

enum Tipo {
	AF("Acto de fiscalizacion"),
	FC("Fiscalización compulza"),
	DE("Devolución"),
	CO("Compensación")

	final String descripcion

	private Tipo(String descripcion){
		this.descripcion = descripcion
	}

	public String value() {
		return name();
	}

	public String getDescripcion(){
		return descripcion;
	}

	String toString(){
		return "$descripcion ("+value()+")"
	}

}
