package com.luxsoft.cfdix.v33

import org.apache.commons.logging.LogFactory
import org.bouncycastle.util.encoders.Base64

import com.luxsoft.impapx.Empresa
import com.luxsoft.lx.utils.MonedaUtils
import com.luxsoft.impapx.cxp.Pago


import lx.cfdi.utils.DateUtils
import lx.cfdi.v33.ObjectFactory
import lx.cfdi.v33.Comprobante
import lx.cfdi.v33.Pagos

// Catalogos
import lx.cfdi.v33.CUsoCFDI
import lx.cfdi.v33.CMetodoPago
import lx.cfdi.v33.CTipoDeComprobante
import lx.cfdi.v33.CMoneda
import lx.cfdi.v33.CTipoFactor

/**
 * 
 */
class CfdiPagoBuilder {

	private static final log = LogFactory.getLog(this)

    private factory = new ObjectFactory()
	private Comprobante comprobante
    private Empresa empresa
    private Pago cobro
    private CfdiSellador33 sellador = new CfdiSellador33()

    def build(Pago cobro, String serie = 'PAGOS'){
        buildComprobante(cobro, serie)
        .buildEmisor()
        .buildReceptor()
        .buildConceptos()
        .buildCertificado()
        .buildPagos()
        .sellar()
        return comprobante
    }
    

	def buildComprobante(Pago cobro, String serie){
		log.info("Generando CFDI de comprobante de pago para Cobro: ${cobro.id}")

		this.comprobante = factory.createComprobante()
        this.cobro = cobro;
        this.empresa = cobro.empresa
        comprobante.version = "3.3"
        comprobante.tipoDeComprobante = CTipoDeComprobante.P
        comprobante.serie = serie
        comprobante.folio = cobro.id
        comprobante.setFecha(DateUtils.getCfdiDate(cobro.fecha))
        comprobante.moneda = CMoneda.MXN
        comprobante.lugarExpedicion = empresa.direccion.codigoPostal
        comprobante.subTotal = 0
        comprobante.moneda = CMoneda.XXX
        comprobante.total = 0
        return this
	}

    def buildEmisor(){
        /**** Emisor ****/
        Comprobante.Emisor emisor = factory.createComprobanteEmisor()
        emisor.rfc = empresa.rfc
        emisor.nombre = empresa.nombre
        emisor.regimenFiscal = empresa.regimenClaveSat ?:'601' 
        comprobante.emisor = emisor
        return this
    }

    def buildReceptor(){
        /** Receptor ***/
        Comprobante.Receptor receptor = factory.createComprobanteReceptor()
        receptor.rfc = this.cobro.cliente.rfc
        receptor.nombre = this.cobro.cliente.nombre
        receptor.usoCFDI = CUsoCFDI.P_01
        comprobante.receptor = receptor
        return this
    }

    

    def buildConceptos(){
        
        /** Conceptos ***/
        Comprobante.Conceptos conceptos = factory.createComprobanteConceptos()
        Comprobante.Conceptos.Concepto concepto = factory.createComprobanteConceptosConcepto()
        concepto.with {
            claveProdServ = "84111506" // Valor fijo
            cantidad = 1
            claveUnidad = 'ACT'
            descripcion = "Pago"
            valorUnitario = 0
            importe = 0
        }
        conceptos.concepto.add(concepto)
        this.comprobante.conceptos = conceptos
        return this
    }
    

    def buildCertificado(){
        comprobante.setNoCertificado(empresa.numeroDeCertificado)
        byte[] encodedCert=Base64.encode(empresa.getCertificado().getEncoded())
        comprobante.setCertificado(new String(encodedCert))
        return this

    }

    def buildPagos(){
        Pagos pagos = factory.createPagos()
        pagos.version = '1.0'
        
        Pagos.Pago pago = factory.createPagosPago()
        pago.fechaPago =  DateUtils.getCfdiDate(this.cobro.fecha)
        pago.formaDePagoP = getFormaDePago()
        pago.monedaP = CMoneda.MXN
        pago.monto = this.cobro.aplicado
        pago.numOperacion = this.cobro.referencia
        boolean varios = this.cobro.aplicaciones.size() > 1
        this.cobro.aplicaciones.each {
            Pagos.Pago.DoctoRelacionado docto = factory.createPagosPagoDoctoRelacionado()
            docto.idDocumento = '6E9122AA-7E57-49F4-A5D0-417ED2583A9C'
            docto.serie = it.cuentaPorCobrar.cfdi.serie
            docto.folio = it.cuentaPorCobrar.cfdi.folio
            docto.monedaDR = CMoneda.MXN
            docto.metodoDePagoDR = CMetodoPago.PPD
            docto.numParcialidad = 1
            docto.impSaldoAnt = it.cuentaPorCobrar.total
            //docto.impSaldoInsoluto = MonedaUtils.round(docto.impSaldoAnt - pago.monto)
            // if(varios){
            //     docto.impPagado = it.importe
            //     docto.impSaldoInsoluto = MonedaUtils.round(docto.impSaldoAnt - docto.impPagado)
            // }
            docto.impPagado = it.importe
            docto.impSaldoInsoluto = MonedaUtils.round(docto.impSaldoAnt - docto.impPagado)
            
            pago.doctoRelacionado.add(docto)
        }
        pagos.pago.add(pago)

        Comprobante.Complemento complemento = factory.createComprobanteComplemento()
        complemento.any.add(pagos)
        comprobante.complemento = complemento

        return this;
    }

    def sellar(){
        sellador.sellar(comprobante, this.empresa)
        return this
    }

    Comprobante getComprobante(){
        return this.comprobante
    }

    String getFormaDePago(){
        switch(this.cobro.formaDePago) {
            case FormaDePago.TRANSFERENCIA:
                return '03'
            case FormaDePago.CHEQUE:
                return '02'
            break
            default:
                return '99'
            break
        }
    }

    
	

}
