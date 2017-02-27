package com.luxsoft.nomina

import org.grails.databinding.BindingFormat

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode

import com.luxsoft.cfdi.Cfdi


@ToString(includes='tipo,ejercicio,periodicidad,folio,formaDePago',includeNames=true,includePackage=false)
@EqualsAndHashCode(includes='id')
class NominaAsimilado {

	Asimilado asimilado

	String formaDePago

	@BindingFormat('dd/MM/yyyy')
	Date fecha 

	@BindingFormat('dd/MM/yyyy')
	Date pago // fecha de pago aproximado

	BigDecimal percepciones

	BigDecimal deducciones

	String concepto

	Cfdi cfdi

	Date dateCreated
	Date lastUpdated


    static constraints = {
    	formaDePago inList: ['TRANSFERENCIA','CHEQUE', 'EFECTIVO']
    	cfdi nullable: true
    }

    static hasMany = [partidas: NominaAsimiladoDet]

    static transients = ['deducciones']

    static mapping = {
    	partidas cascade: "all-delete-orphan"
    }

    BigDecimal getDeducciones() {
		partidas.sum 0.0,{
			return it.tipo=='DEDUCCION' ? it.importeExcento:0.0
		}
	}
}
