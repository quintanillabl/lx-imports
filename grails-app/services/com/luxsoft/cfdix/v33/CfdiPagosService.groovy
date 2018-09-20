package com.luxsoft.cfdix.v33

import java.text.SimpleDateFormat

import grails.transaction.Transactional

import lx.cfdi.v33.Comprobante
import lx.cfdi.v33.pagos.PagosUtils

import com.luxsoft.cfdi.Cfdi
import com.luxsoft.impapx.cxc.CXCPago

import com.luxsoft.cfdix.v33.CfdiPagoBuilder

@Transactional
public class CfdiPagosService {

	final static SimpleDateFormat CFDI_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	def springSecurityService

	def generarCfdi(CXCPago cobro){
		log.info 'Generar CFDI cobro'
		assert cobro.cfdi == null," Ya se ha generado el comprobante de pago"
		def builder = new CfdiPagoBuilder()
		def comprobante = builder.build(cobro)

		def cfdi = new Cfdi()
		cfdi.tipo = 'PAGO'
		cfdi.tipoDeCfdi = 'P'
		cfdi.origen = cobro.id
		cfdi.serie = comprobante.serie
		cfdi.folio = comprobante.folio
		cfdi.fecha = CFDI_DATE_FORMAT.parse(comprobante.fecha)
		cfdi.emisor = comprobante.emisor.nombre
		cfdi.rfc = comprobante.emisor.rfc
		cfdi.receptor = comprobante.receptor.nombre
		cfdi.receptorRfc = comprobante.receptor.rfc
		cfdi.total = comprobante.total
		cfdi.versionCfdi = comprobante.version
		cfdi.importe = comprobante.subTotal
		cfdi.descuentos = 0.0
		cfdi.subtotal = comprobante.subTotal
		cfdi.impuesto = 0.0
		cfdi.xml = toXmlByteArray(comprobante)
		cfdi.setXmlName("${cfdi.receptorRfc}_${cfdi.serie}_${cfdi.folio}"+".xml")
		cfdi.save(failOnError:true)
		cobro.cfdi=cfdi
		cobro.save flush: true
		return cobro

	}

	def toXmlByteArray(Comprobante comprobante){
		ByteArrayOutputStream os = new ByteArrayOutputStream()
		PagosUtils.getMarshaller()
		.marshal(comprobante, os)
		return os.toByteArray()

	}
}