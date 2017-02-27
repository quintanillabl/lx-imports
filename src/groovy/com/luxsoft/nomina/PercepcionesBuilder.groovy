package com.luxsoft.nomina

import mx.gob.sat.nomina12.NominaDocument.Nomina
import mx.gob.sat.nomina12.NominaDocument.Nomina.Percepciones
import mx.gob.sat.nomina12.NominaDocument.Nomina.Percepciones.Percepcion
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CTipoPercepcion



class PercepcionesBuilder {

	def build(Nomina nomina, NominaAsimilado nominaEmpleado){
		
		// Percepciones
		Percepciones per=nomina.addNewPercepciones()
		
		per.totalSueldos = nominaEmpleado.percepciones
		per.totalGravado = nominaEmpleado.percepciones
		per.totalExento = 0.0
		
		Percepcion pp=per.addNewPercepcion()
		def clave = '046' // Ingresos asimilados a salarios
		pp.setTipoPercepcion(CTipoPercepcion.Enum.forString(clave))
		pp.setClave('P046')  
		pp.setConcepto(nominaEmpleado.concepto)
		pp.setImporteGravado(nominaEmpleado.percepciones)
		pp.setImporteExento(0.0)
		return per
	}


}