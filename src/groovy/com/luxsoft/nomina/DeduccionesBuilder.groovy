package com.luxsoft.nomina

import mx.gob.sat.nomina12.NominaDocument.Nomina
import mx.gob.sat.nomina12.NominaDocument.Nomina.Deducciones
import mx.gob.sat.nomina12.NominaDocument.Nomina.Deducciones.Deduccion

import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CTipoDeduccion



class DeduccionesBuilder {

	def build(Nomina nomina, NominaAsimilado nominaEmpleado){
		
		
		Deducciones ded=nomina.addNewDeducciones()

		def deducciones = nominaEmpleado.getDeducciones()
		//ded.totalOtrasDeducciones = 1.0 //nominaEmpleado.getDeducciones()
		
		def isr = nominaEmpleado.getDeducciones()
		ded.totalImpuestosRetenidos = isr
		
		Deduccion dd=ded.addNewDeduccion()
		def clave = '002'
		dd.setTipoDeduccion(CTipoDeduccion.Enum.forString(clave))
		dd.setClave('ISR')
		dd.setConcepto('ISR')
		dd.setImporte(isr)

		return ded
	}

	

	

}