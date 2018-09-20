package com.luxsoft.cfdix.v33

import java.text.SimpleDateFormat
import java.text.MessageFormat

import com.luxsoft.cfdi.Cfdi
import com.luxsoft.cfdi.ImporteALetra
import lx.cfdi.v33.Comprobante

import com.luxsoft.cfdix.CfdiTimbre

import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType
import com.luxsoft.cfdix.v33.CfdiCadenaBuilder33

class ReciboDePagoPdfGenerator {

	final static SimpleDateFormat CFDI_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

	final static CfdiCadenaBuilder33 cadenaBuilder = new CfdiCadenaBuilder33()

	static getReportData(Cfdi cfdi){

		Comprobante comprobante = V33CfdiUtils.toComprobante(cfdi)
		def pagos = comprobante.complemento.any[0]
		def relacionados = pagos.pago.doctoRelacionado

		def modelData = relacionados.get(0).collect {  cc ->

            def res=[
                    'IdDocumento': cc.idDocumento,
                    'Serie' : cc.serie,
                    'Folio': cc.folio,
                    'MonedaDR': cc.monedaDR.toString(),
                    'MetodoDePagoDR': cc.metodoDePagoDR.toString(),
                    'NumParcialidad': cc.numParcialidad.toString(),
                    'ImpPagado': cc.impPagado,
                    'ImpSaldoAnt': cc.impSaldoAnt,
                    'ImpSaldoInsoluto': cc.impSaldoInsoluto,
            ]
            return res
        }

        def data = [:]
        def params = getParametros(cfdi, comprobante)

        params['FECHA_PAGO'] = pagos.pago.get(0).fechaPago
        params['FORMA_DE_PAGO'] = pagos.pago.get(0).formaDePagoP
        params['MONEDAP'] = pagos.pago.get(0).monedaP.toString()
        params['MONTO'] = pagos.pago.get(0).monto
        params['NUM_OPERACION'] = pagos.pago.get(0).numOperacion
        params['SUBTOTAL'] = comprobante.subTotal.toString()
        params["IMP_CON_LETRA"] = ImporteALetra.aLetra(pagos.pago.get(0).monto)
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
		params["LUGAR_EXPEDICION"] = comprobante.lugarExpedicion
		params["TIPO_DE_COMPROBANTE"] = "P (Pago)"
		
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