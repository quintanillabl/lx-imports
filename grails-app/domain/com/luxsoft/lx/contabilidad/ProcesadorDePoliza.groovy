package com.luxsoft.lx.contabilidad

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode


@ToString(includes='subTipo,service',includeNames=true,includePackage=false)
@EqualsAndHashCode(includes='subTipo')
class ProcesadorDePoliza {

	
    String tipo = 'DIARIO'
    String subTipo 
	String descripcion
	String service
    String label
    int orden = 0


    static constraints = {
    	subTipo maxSize:50,unique:true
    	descripcion nullable:true
    	service nullable:true,maxSize:100
        tipo(inList:['INGRESO','EGRESO','DIARIO'])
    }
    
    def beforeInsert() {
        subTipo = subTipo.toUpperCase()
        if(!label)
            label=subTipo.capitalize()
        if(!descripcion)
            descripcion=subTipo.capitalize()
    }

    def beforeUpdate() {
        subTipo = subTipo.toUpperCase()
        if(!label)
            label=subTipo.capitalize()
        if(!descripcion)
            descripcion=subTipo.capitalize()

    }

    
}



