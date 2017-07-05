package com.luxsoft.cfdix.v33

import grails.transaction.Transactional
import java.text.SimpleDateFormat

import com.luxsoft.cfdi.Cfdi
import lx.cfdi.v33.Comprobante
import lx.cfdi.v33.CfdiUtils
import com.luxsoft.impapx.Venta
import com.luxsoft.impapx.Empresa
import com.luxsoft.nomina.NominaAsimilado

@Transactional
public class CfdiV33Service {

	def springSecurityService

	final static SimpleDateFormat CFDI_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	def generar(Venta venta){
		
		CfdiBuilder33  builder = new CfdiBuilder33()
		CfdiSellador33 sellador = new CfdiSellador33()
		Comprobante comprobante = builder.build(venta, 'FAC')
		def empresa = Empresa.first()
		comprobante = sellador.sellar(comprobante, empresa)

		def cfdi = new Cfdi()
		cfdi.tipo = "FACTURA"
		cfdi.tipoDeCfdi = 'I'
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

		cfdi.origen = venta.id
		cfdi.xml = CfdiUtils.toXmlByteArray(comprobante)
		cfdi.setXmlName("$cfdi.receptorRfc-${'CFDIV33'}-$cfdi.serie-$cfdi.folio"+".xml")
		cfdi.save(failOnError:true)
		return cfdi

	}

	def generarCfdiNomina(NominaAsimilado ne){

		Cfdi33NominaBuilder builder = new Cfdi33NominaBuilder()
		CfdiSellador33 sellador = new CfdiSellador33()
		def empresa = Empresa.first()

		Comprobante comprobante = builder.build(venta, 'NOMINA12')
		comprobante = sellador.sellar(comprobante, empresa)

		def cfdi=new Cfdi(
			tipo: 'CRE',
			tipoDeCfdi: 'N' ,
			fecha: ne.fecha,
			serie: comprobante.serie,
			folio: comprobante.folio,
			origen: ne.id.toString(),
			emisor: comprobante.getEmisor().nombre,
			receptor: comprobante.receptor.nombre,
			rfc: comprobante.receptor.rfc,
			importe: comprobante.total,
			descuentos: 0,
			subtotal: comprobante.subTotal,
			impuesto: 0.0,
			total: comprobante.total,
		)

		cfdi.xml = CfdiUtils.toXmlByteArray(comprobante)
		cfdi.setXmlName("$cfdi.receptorRfc-${'CFDIV33'}-$cfdi.serie-$cfdi.folio"+".xml")
		cfdi.save(failOnError:true)
		return cfdi
	}


	def timbrar(Cfdi cfdi){
		
	}

	def toXml(Comprobante comprobante){
		return CfdiUtils.serialize(comprobante)
	}

}