package com.luxsoft.cfdix.v33

import grails.transaction.Transactional
import java.text.SimpleDateFormat

import com.luxsoft.cfdi.Cfdi
import lx.cfdi.v33.Comprobante
import lx.cfdi.v33.CfdiUtils
import lx.cfdi.v33.nomina.NominaUtils
import com.luxsoft.impapx.Venta
import com.luxsoft.impapx.Empresa
import com.luxsoft.nomina.NominaAsimilado
import com.luxsoft.impapx.cxc.CXCNota
import com.luxsoft.cfdi.CfdiFolio

@Transactional
public class CfdiV33Service {

	def springSecurityService

	final static SimpleDateFormat CFDI_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	def generar(def source){
		if(source instanceof Venta){
			return generarFactura(source)
		} else {
			return generarNotaDeCredito(source)
		}
	}

	def generarFactura(Venta venta){

		println "Generando Factura " +venta
		
		CfdiBuilder33  builder = new CfdiBuilder33()
		CfdiSellador33 sellador = new CfdiSellador33()
		
		def serie = venta.tipo =='VENTA' ? 'FAC' : 'CAR'
		Comprobante comprobante = builder.build(venta, serie)
		def empresa = Empresa.first()
		def cfdiFolio=CfdiFolio.findByEmisorAndSerie(empresa.rfc,serie)
		comprobante.folio = cfdiFolio.next()
		comprobante = sellador.sellar(comprobante, empresa)

			println "Voy a generar el cfdi"
		def cfdi = new Cfdi()
		cfdi.tipo = serie
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
		println "Voy a generar el xml del cfdi"
		cfdi.xml = CfdiUtils.toXmlByteArray(comprobante)
		cfdi.setXmlName("$cfdi.receptorRfc-${'CFDIV33'}-$cfdi.serie-$cfdi.folio"+".xml")
		println "Voy a salvar el xml del cfdi " +cfdi
		cfdi.save(failOnError:true)
		cfdiFolio.save(flush:true)
		return cfdi

	}

	def generarNotaDeCredito(CXCNota nota){
		
		CfdiNotaDeCreditoBuilder33  builder = new CfdiNotaDeCreditoBuilder33()
		CfdiSellador33 sellador = new CfdiSellador33()
		def empresa = Empresa.first()

		def serie = 'CRE'

		Comprobante comprobante = builder.build(nota, serie)
		def cfdiFolio=CfdiFolio.findByEmisorAndSerie(empresa.rfc,serie)
		comprobante.folio = cfdiFolio.next()

		comprobante = sellador.sellar(comprobante, empresa)

		def cfdi = new Cfdi()
		cfdi.tipo = serie
		cfdi.tipoDeCfdi = 'E'
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

		cfdi.origen = nota.id
		cfdi.xml = CfdiUtils.toXmlByteArray(comprobante)
		cfdi.setXmlName("$cfdi.receptorRfc-${'CFDIV33'}-$cfdi.serie-$cfdi.folio"+".xml")
		cfdi.save(failOnError:true)
		cfdiFolio.save(flush:true)
		return cfdi

	}

	def generarCfdiNomina(NominaAsimilado ne){

		Cfdi33NominaBuilder builder = new Cfdi33NominaBuilder()
		CfdiSellador33 sellador = new CfdiSellador33()
		def empresa = Empresa.first()

		def serie = 'FACTURA'
		Comprobante comprobante = builder.build(ne, serie)
		// def cfdiFolio = CfdiFolio.findByEmisorAndSerie(empresa.rfc,serie)
		// comprobante.folio = cfdiFolio.next()

		comprobante = sellador.sellar(comprobante, empresa)
		println 'Receptor: ' + comprobante.receptor.nombre
		def cfdi=new Cfdi(
			tipo: serie,
			tipoDeCfdi: 'N' ,
			fecha: CFDI_DATE_FORMAT.parse(comprobante.fecha),
			serie: comprobante.serie,
			folio: comprobante.folio,
			origen: ne.id.toString(),
			emisor: empresa.nombre,
			receptor: comprobante.receptor.nombre,
			receptorRfc: comprobante.receptor.rfc,
			rfc: comprobante.emisor.rfc,
			importe: comprobante.total,
			descuentos: 0,
			subtotal: comprobante.subTotal,
			impuesto: 0.0,
			total: comprobante.total
		)

		cfdi.xml = NominaUtils.toXmlByteArray(comprobante)
		cfdi.setXmlName("$cfdi.receptorRfc-${'CFDIV33'}-$cfdi.serie-$cfdi.folio"+".xml")
		cfdi.save(failOnError:true)
		ne.cfdi = cfdi
		// cfdiFolio.save(flush:true)
		return cfdi
	}


	def timbrar(Cfdi cfdi){
		
	}

	def toXml(Comprobante comprobante){
		return CfdiUtils.serialize(comprobante)
	}

}