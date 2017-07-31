package com.luxsoft.cfdix.v33

import java.text.SimpleDateFormat
import java.text.MessageFormat

import com.luxsoft.cfdi.Cfdi
import com.luxsoft.cfdi.ImporteALetra
import lx.cfdi.v33.Comprobante

import com.luxsoft.cfdix.CfdiTimbre

import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType


class V33PdfGenerator {

	final static SimpleDateFormat CFDI_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	static getReportData(Cfdi cfdi){

		Comprobante comprobante = V33CfdiUtils.toComprobante(cfdi)
		
		def conceptos = comprobante.conceptos.concepto

		def modelData=conceptos.collect { cc ->
			def traslado = cc.impuestos.traslados.traslado[0]
			def res=[
				'cantidad' : cc.getCantidad(),
		        'NoIdentificacion' : cc.noIdentificacion,
				'descripcion' : cc.descripcion,
				'unidad': cc.unidad,
				'ValorUnitario':cc.valorUnitario,
				'Importe':cc.importe,
				'ClaveProdServ': cc.claveProdServ,
				'ClaveUnidad': cc.claveUnidad,
				'Descuento': cc.descuento?: '0.0',
				'Impuesto': traslado.impuesto.toString(),
				'TasaOCuota': traslado.tasaOCuota.toString(),
				'TipoFactor': traslado.tipoFactor.value().toString(),
				'Base': traslado.base,
				'TrasladoImporte': traslado.importe

			]
			if(cc.cuentaPredial){
				res.CUENTA_PREDIAL=cc.cuentaPredial.numero
			}
			if(cc.informacionAduanera.size() > 0 ){
				def data = cc.informacionAduanera.collect {it.numeroPedimento}.join(',')
				res.PEDIMENTO = data
			}
			return res
		}
		def params = getParametros(cfdi, comprobante)

		def data = [:]
		data['CONCEPTOS'] = modelData
		data['PARAMETROS'] = params
		return data
	}

	static getParametros(Cfdi cfdi, Comprobante comprobante){
		
		def params=[:]
		params["VERSION"] = comprobante.version
		params["SERIE"] = comprobante.getSerie()
		params["FOLIO"] = comprobante.getFolio()
		params["NUM_CERTIFICADO"] = comprobante.getNoCertificado()
		params["SELLO_DIGITAL"] = comprobante.getSello()
		params["RECEPTOR_NOMBRE"] = comprobante.getReceptor().getNombre() 
		params["RECEPTOR_RFC"] = comprobante.getReceptor().getRfc()
		params["IMPORTE"] = comprobante.getSubTotal() as String
		params["IVA"] = (comprobante?.getImpuestos()?.getTotalImpuestosTrasladados()?: 0.0) as String
		params["TOTAL"] = comprobante.getTotal() as String
		params["RECEPTOR_DIRECCION"] = 'ND'

		
		params.put("METODO_PAGO", 		comprobante.metodoPago.toString());
		params.put("FORMA_PAGO", 		comprobante.formaPago);
		params.put("IMP_CON_LETRA", 	ImporteALetra.aLetra(comprobante.getTotal()));
		params['FORMA_DE_PAGO']=comprobante.formaPago
		params['PINT_IVA']='16 '
		params["DESCUENTOS"] = comprobante.getDescuento() as String
		params['CONDICIONES_PAGO'] = comprobante.condicionesDePago
		params['UsoCFDI'] = comprobante.receptor.usoCFDI.value().toString()
		params['Moneda'] = comprobante.moneda.value().toString()

		if(comprobante.getReceptor().rfc=='XAXX010101000'){
			params["IMPORTE"] = comprobante.getTotal() as String
		}
		def emisor=comprobante.getEmisor();
		params.put("EMISOR_NOMBRE", 	emisor.getNombre());
		params.put("EMISOR_RFC", 		emisor.getRfc())
		
		params["EMISOR_DIRECCION"] = ' '
		params["REGIMEN"] = comprobante.emisor.regimenFiscal
		//params["EXPEDIDO_DIRECCION"] = comprobante.lugarExpedicion
		params["LUGAR_EXPEDICION"] = comprobante.lugarExpedicion

		def relacionados = comprobante.cfdiRelacionados
		if(relacionados){
			params.put('RelacionUUID',relacionados.cfdiRelacionado.get(0).UUID)
		} 
		
		if(cfdi.uuid!=null){
			def img = generarQR(cfdi)
			params.put("QR_CODE",img);
			CfdiTimbre timbre = new CfdiTimbre(cfdi)
			params.put("FECHA_TIMBRADO", timbre.fechaTimbrado);
			params.put("FOLIO_FISCAL", timbre.uuid);
			params.put("SELLO_DIGITAL_SAT", timbre.selloSAT);
			params.put("CERTIFICADO_SAT", timbre.noCertificadoSAT);
			params.put("CADENA_ORIGINAL_SAT", timbre.cadenaOriginal());
			params.put("RfcProvCertif", timbre.rfcProvCertif)
		}
		params.FECHA = comprobante.fecha
		return params;
	}

	

	static String format(def d){
		return """${d.calle}, ${d.noExterior}, ${d.noInterior?:''}, ${d.colonia},${d.codigoPostal}, ${d.municipio}, ${d.localidad},${d.estado}, ${d.pais} """;
	}

	public static  generarQR(Cfdi cfdi) {
		String pattern="?re=${0}&rr={1}&tt={2,number,##########.######}&id,{3}"
		String qq=MessageFormat.format(pattern, cfdi.rfc,cfdi.receptorRfc,cfdi.total,cfdi.uuid)
		File file=QRCode.from(qq).to(ImageType.GIF).withSize(250, 250).file()
		return file.toURI().toURL()
		
	}

}