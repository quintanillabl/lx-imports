package com.luxsoft.sx4.sec

class Perfil {

	String email
	String telefonoCasa
	String celular
	String twitter
	String google
	byte[] foto

	Map preferencias

	String dashInicial

	static belongsTo = [usuario: Usuario]

	static hasMany = [preferencias: String]

    static constraints = {
    	email nullable:true,email:true
    	foto nullable:true, maxSize:(1024 * 512)  // 50kb para almacenar el xml
    	dashInicial nullable:true,maxSize:60
    	celular nullable:true
    	telefonoCasa nullable:true
    	twitter nullable:true
    	google nullable:true
    }
}
