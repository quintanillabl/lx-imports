package com.luxsoft.impapx

class Distribucion {
	
	Embarque embarque
	Date fecha
	int contenedores
	String comentario
	
	List partidas
	
	static hasMany = [partidas:DistribucionDet]
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		fecha(nullable:false)
		comentario(nullable:true,maxSize:250)
    }
	
	static mapping = {
		//partidas lazy:false
		//embarque fetch:'join'
		partidas cascade: "all-delete-orphan"
	}
	
}
