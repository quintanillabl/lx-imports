package com.luxsoft.cfdix.v32

import java.text.SimpleDateFormat
import java.text.MessageFormat

import com.luxsoft.cfdi.Cfdi
import com.luxsoft.cfdi.ImporteALetra
import lx.cfdi.v32.Comprobante
import lx.cfdi.v32.TUbicacion
import com.luxsoft.cfdix.CfdiTimbre

import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType


class V32PdfGenerator {

	final static SimpleDateFormat CFDI_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	static getReportData(Cfdi cfdi){
		Comprobante comprobante = V32CfdiUtils.toComprobante(cfdi)
		
		def conceptos = comprobante.conceptos.concepto
		def modelData=conceptos.collect { cc ->
			def res=[
				'cantidad' : cc.getCantidad(),
		        'NoIdentificacion' : cc.getNoIdentificacion(),
				'descripcion' : cc.getDescripcion(),
				'unidad':cc.getUnidad(),
				'ValorUnitario':cc.getValorUnitario(),
				'Importe':cc.getImporte()
			]
			if(cc.cuentaPredial){
				res.CUENTA_PREDIAL=cc.cuentaPredial.numero
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
		params["SERIE"] = comprobante.getSerie()
		params["FOLIO"] = comprobante.getFolio()
		params["NUM_CERTIFICADO"] = comprobante.getNoCertificado()
		params["SELLO_DIGITAL"] = comprobante.getSello()
		params["RECEPTOR_NOMBRE"] = comprobante.getReceptor().getNombre() 
		params["RECEPTOR_RFC"] = comprobante.getReceptor().getRfc()
		params["FECHA"] = comprobante.getFecha().toGregorianCalendar().getTime()
		params["IMPORTE"] = comprobante.getSubTotal() as String
		params["IVA"] = (comprobante.getImpuestos().getTotalImpuestosTrasladados()?: 0.0) as String
		params["TOTAL"] = comprobante.getTotal() as String
		params["RECEPTOR_DIRECCION"] = format(comprobante.getReceptor().getDomicilio())

		params.put("NUM_CTA_PAGO", comprobante.getNumCtaPago());
		params.put("METODO_PAGO", 		comprobante.getMetodoDePago());
		params.put("FORMA_PAGO", 		comprobante.getFormaDePago());
		params.put("IMP_CON_LETRA", 	ImporteALetra.aLetra(comprobante.getTotal()));
		params['FORMA_DE_PAGO']=comprobante.formaDePago
		params['PINT_IVA']='16 '
		params["DESCUENTOS"] = comprobante.getDescuento() as String
		if(comprobante.getReceptor().rfc=='XAXX010101000'){
			params["IMPORTE"] = comprobante.getTotal() as String
		}
		def emisor=comprobante.getEmisor();
		params.put("EMISOR_NOMBRE", 	emisor.getNombre());
		params.put("EMISOR_RFC", 		emisor.getRfc())
		
		params["EMISOR_DIRECCION"] = format(emisor.domicilioFiscal)
		params["REGIMEN"] = comprobante.getEmisor().getRegimenFiscal().collect{it.regimen}.join(',')
		
		if (emisor.getExpedidoEn() != null){
			def expedido=emisor.getExpedidoEn()
			params["EXPEDIDO_DIRECCION"] = format(expedido)
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
		}
		params.FECHA = cfdi.fecha.format("yyyy-MM-dd'T'HH:mm:ss")
		//params.IMPORTE = params.IMPORTE as String
		//params.IVA=params.IVA as String
		//params.TOTAL=params.TOTAL as String
		//params.DESCUENTOS=params.DESCUENTOS as String
		return params;
	}

	static String format(def d){
		return """${d.calle}, ${d.noExterior}, ${d.noInterior?:''}, ${d.colonia},${d.codigoPostal}, ${d.municipio}, ${d.localidad},${d.estado}, ${d.pais} """;
	}

	public static  generarQR(Cfdi cfdi) {
		String pattern="?re=${0}&rr={1}&tt={2,number,##########.######}&id,{3}"
		String qq=MessageFormat.format(pattern, cfdi.emisorRfc,cfdi.receptorRfc,cfdi.total,cfdi.uuid)
		File file=QRCode.from(qq).to(ImageType.GIF).withSize(250, 250).file()
		return file.toURI().toURL()
		
	}

}