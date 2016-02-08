package com.luxsoft.lx.contabilidad

import grails.validation.Validateable
import groovy.transform.ToString
import com.luxsoft.utils.Periodo


@ToString(includes='ejercicio,mes',includeNames=true,includePackage=false)
@Validateable(nullable=true)
class PeriodoContable implements Serializable{
	
	Integer ejercicio=Periodo.currentYear()
    Integer mes=Periodo.currentMes()

    static constraints={
        ejercicio inList:(2014..2018)
        mes inList:(1..13)
    }

    String toString(){
    	return "$ejercicio - $mes"
    }

    Periodo toPeriodo(){
        return Periodo.getPeriodoEnUnMes(mes-1,ejercicio)
    }


	
}