package com.luxsoft.nomina

import org.bouncycastle.util.encoders.Base64

import mx.gob.sat.cfd.x3.ComprobanteDocument
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Complemento
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Emisor
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Receptor
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.TipoDeComprobante

import com.luxsoft.cfdi.CFDIUtils
import com.luxsoft.impapx.Empresa 




class NominaCfdiBuilder {

	ComplementoBuilder complementoBuilder = new ComplementoBuilder()

	ComprobanteDocument build(NominaAsimilado nominaEmpleado){
		ComprobanteDocument document=ComprobanteDocument.Factory.newInstance()
		Comprobante comprobante=document.addNewComprobante()

		def empresa = Empresa.first()
		def empleado = nominaEmpleado.asimilado
		

		init(comprobante, empresa)

		def complementoNomina = complementoBuilder.build(nominaEmpleado, empresa)
		
		new PercepcionesBuilder().build(complementoNomina, nominaEmpleado) 
		new DeduccionesBuilder().build(complementoNomina, nominaEmpleado)
		
		registrarEmisor(comprobante, empresa)
		registrarReceptor(comprobante, empleado)
		registrarConceptos(comprobante, complementoNomina)
		registrarTotales(comprobante, complementoNomina)
		
		// Impuestos
		comprobante.addNewImpuestos()
		anexarComplemento(comprobante, complementoNomina)
		NominaUtils.depurar(document)

		return document
	}

	private  init(Comprobante comprobante, def empresa){
		Date fecha = new Date()
		comprobante.version = '3.2'
		comprobante.serie = 'NOMINA_CFDI'
		comprobante.setFecha(CFDIUtils.toXmlDate(fecha).getCalendarValue())
		comprobante.setFormaDePago("PAGO EN UNA SOLA EXHIBICION")
		comprobante.setMetodoDePago('NA')
		comprobante.setMoneda(Currency.getInstance(new Locale("es","mx")).currencyCode)
		comprobante.setTipoCambio("1")
		comprobante.setTipoDeComprobante(TipoDeComprobante.EGRESO)
		comprobante.setLugarExpedicion(empresa.direccion.codigoPostal)
		comprobante.setNoCertificado(empresa.numeroDeCertificado)
		byte[] encodedCert=Base64.encode(empresa.getCertificado().getEncoded())
		comprobante.setCertificado(new String(encodedCert))
		return this
	}

	private registrarEmisor(Comprobante comprobante, def empresa){
		Emisor emisor=comprobante.addNewEmisor()
		emisor.setRfc(empresa.rfc)
		emisor.setNombre(empresa.nombre)
		ComprobanteDocument.Comprobante.Emisor.RegimenFiscal regimenFiscal  = emisor.addNewRegimenFiscal()
        regimenFiscal.setRegimen('601')  // Magic number  sacar de empresa por lo pronto
        return this
	}

	private registrarReceptor(Comprobante comprobante, def empleado){
		Receptor receptor=comprobante.addNewReceptor()
		receptor.setNombre(empleado.nombre)
		receptor.setRfc(empleado.rfc)
		return this
	}

	private registrarConceptos(Comprobante comprobante, def complementoNomina) {
		Conceptos conceptos=comprobante.addNewConceptos()
		Concepto c=conceptos.addNewConcepto();
		c.setCantidad(1);
		c.setUnidad("ACT");
		c.setDescripcion('Pago de nómina');
		def totalPercepciones = complementoNomina.totalPercepciones?:0.0
		def totalOtrosPagos = complementoNomina.totalOtrosPagos?:0.0
		c.setValorUnitario(totalPercepciones + totalOtrosPagos);
		c.setImporte(totalPercepciones + totalOtrosPagos);
		return this
	}

	private registrarTotales(Comprobante comprobante, def complementoNomina){
		def totalPercepciones = complementoNomina.totalPercepciones?:0.0
		def totalOtrosPagos = complementoNomina.totalOtrosPagos?:0.0
		def totalDeducciones = complementoNomina.totalDeducciones?:0.0
		comprobante.setSubTotal(totalPercepciones + totalOtrosPagos)
		comprobante.setDescuento(totalDeducciones)
        comprobante.setTotal(totalPercepciones + totalOtrosPagos - totalDeducciones)
        return this
	}
	

	private anexarComplemento(Comprobante comprobante, def nomina){
		Complemento complemento=comprobante.addNewComplemento()
        def cursor=complemento.newCursor()
        cursor.toEndToken()
        def cn=nomina.newCursor()
        cn.moveXml(cursor)
	}



	
}
