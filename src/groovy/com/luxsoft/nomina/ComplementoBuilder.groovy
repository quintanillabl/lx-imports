package com.luxsoft.nomina

import java.text.SimpleDateFormat
import java.math.RoundingMode

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject
import org.apache.xmlbeans.XmlOptions
import org.apache.xmlbeans.XmlValidationError
import org.apache.xmlbeans.XmlDateTime
import org.apache.xmlbeans.XmlDate

import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CTipoNomina
import mx.gob.sat.sitioInternet.cfd.catalogos.CEstado
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CTipoNomina
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CTipoRegimen
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CTipoJornada
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CTipoContrato
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CRiesgoPuesto
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CPeriodicidadPago
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CTipoPercepcion
import mx.gob.sat.sitioInternet.cfd.catalogos.nomina.CBanco

import mx.gob.sat.nomina12.NominaDocument
import mx.gob.sat.nomina12.NominaDocument.Nomina
import mx.gob.sat.nomina12.NominaDocument.Nomina.Emisor

import com.luxsoft.nomina.Asimilado
import com.luxsoft.impapx.Empresa

import com.luxsoft.nomina.NominaAsimilado

class ComplementoBuilder {

	Nomina build(NominaAsimilado nominaEmpleado, def empresa){

		NominaDocument nominaDocto=NominaDocument.Factory.newInstance()
		Nomina nomina=nominaDocto.addNewNomina()
		nomina.version = '1.2'
		nomina.tipoNomina = CTipoNomina.E
		nomina.setNumDiasPagados(1)
		nomina.fechaPago = NominaUtils.toISO8601(nominaEmpleado.pago)
		nomina.fechaInicialPago = NominaUtils.toISO8601(nominaEmpleado.pago)
		nomina.fechaFinalPago = NominaUtils.toISO8601(nominaEmpleado.pago)
		
		//registrarEmisor(nomina, empresa)
		
		registrarReceptor(nomina, nominaEmpleado)
		
		nomina.setTotalPercepciones(nominaEmpleado.percepciones)
		nomina.setTotalDeducciones(nominaEmpleado.deducciones)
		
		return nomina
	}

	

	def registrarEmisor(Nomina nomina, Empresa empresa) {
		Emisor emisor = nomina.addNewEmisor()
		//emisor.setRegistroPatronal(empresa.registroPatronal)
	}

	def registrarReceptor(Nomina nomina, NominaAsimilado nominaEmpleado){
		
		Asimilado empleado = nominaEmpleado.asimilado

		Nomina.Receptor receptor = nomina.addNewReceptor()
        receptor.curp = empleado.curp
        receptor.setTipoContrato(CTipoContrato.X_99)  
        receptor.tipoRegimen = CTipoRegimen.Enum.forString('09') 
        receptor.numEmpleado = empleado.id.toString()
        
        receptor.setPeriodicidadPago(CPeriodicidadPago.X_05)
        
        if(empleado.formaDePago == 'TRANSFERENCIA'){
        	def bancoClave = empleado.bancoSat.clave.toString().padLeft(3,'0')
			receptor.setBanco(CBanco.Enum.forString(bancoClave))
			receptor.cuentaBancaria = new BigInteger(empleado.clabe ?: empleado.numeroDeCuenta)	
        }

        receptor.setClaveEntFed(CEstado.DIF) 
	}

	
}