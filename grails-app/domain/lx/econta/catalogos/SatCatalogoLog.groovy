package lx.econta.catalogos

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode


@ToString(includeNames=true,includePackage=false)
@EqualsAndHashCode(includes='id,ejercicio,mes')
class SatCatalogoLog {

	String versionSat='1.1'

	Integer ejercicio

    Mes mes

	String rfc

	String nombre

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
        xml maxSize:(1024 * 1024)  // 50kb para almacenar el xml
        acuse nullable: true, maxSize:(1024 * 512)  // 50kb para almacenar el acuse
        comentario nullable: true
        enviado nullable: true
        acuseDeAceptacion nullable: true, maxSize:(1024 * 512)  // 50kb para almacenar el acuse
        
    }

    static mapping = {
    	mes enumType: 'ordinal'
    }

    static enum Mes {
    	ENE(1),
    	FEB(2),
    	MAR(3),
    	ABR(4),
    	MAY(5),
    	JUN(6),
    	JUL(7),
    	AGO(8),
    	SEP(9),
    	OCT(10),
    	NOV(11),
    	DIC(12)

    	final int id

    	private Mes(int id){
    		this.id = id
    	}

    	public String value() {
    		return name();
    	}

    	public int getId(){
    		return id;
    	}


    	static Map mesesMap = [1:ENE, 2:FEB, 3:MAR, 4:ABR, 5:MAY, 6:JUN, 7:JUL, 8:AGO, 9:SEP, 10:OCT, 11:NOV, 12:DIC]

    	public static Mes fromValue(Integer v){
    		if(v < 1 || v > 12)
    			throw new IllegalArgumentException("El numero de mes debe ser 1 a 12 ")
    		return mesesMap[v]
    	}
    }

    Catalogo asCatalogo(){
    	Catalogo catalogo = (Catalogo)CatalogoUtils
			.getContext()
			.createUnmarshaller()
			.unmarshal(new ByteArrayInputStream(this.xml))
		return catalogo
    }

    String asXmlString(){
    	StringWriter writer = new StringWriter()
    	def catalogo = asCatalogo()
    	CatalogoUtils.marshall(catalogo,writer)
    	return writer.toString()
    }

}
