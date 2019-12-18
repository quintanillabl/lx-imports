package com.luxsoft.cfdix.v33

import org.apache.commons.logging.LogFactory
import org.bouncycastle.util.encoders.Base64

import com.luxsoft.impapx.Empresa
import com.luxsoft.impapx.cxc.CXCNota
import com.luxsoft.cfdi.Cfdi


import com.luxsoft.lx.utils.MonedaUtils
import lx.cfdi.utils.DateUtils
import lx.cfdi.v33.ObjectFactory
import lx.cfdi.v33.Comprobante

// Catalogos
import lx.cfdi.v33.CUsoCFDI
import lx.cfdi.v33.CMetodoPago
import lx.cfdi.v33.CTipoDeComprobante
import lx.cfdi.v33.CMoneda
import lx.cfdi.v33.CTipoFactor
import lx.cfdi.v33.*

/**
 * TODO: Parametrizar el regimenFiscal de
 */
class CfdiNotaDeCreditoBuilder33 {

	private static final log=LogFactory.getLog(this)

    private factory = new ObjectFactory();
	private Comprobante comprobante;
    private Empresa empresa
    private CXCNota nota;

    def build(CXCNota nota, String serie = 'CRE'){
        buildComprobante(nota, serie)
        .buildEmisor()
        .buildReceptor()
        .buildFormaDePago()
        .buildConceptos()
        .buildImpuestos()
        .buildTotales()
        .buildCertificado()
        //.buildRelacionados()
        .buildRelacionados2()
        // println CfdiUtils.serialize(comprobante)
        return comprobante
    }
    

	def buildComprobante(CXCNota nota, String serie){
		log.info("Generando CFDI 3.3 para nota de credito: ${nota.id}")
        this.empresa = Empresa.first()
		this.comprobante = factory.createComprobante()
        this.nota = nota;
        
        comprobante.version = "3.3"
        comprobante.tipoDeComprobante = CTipoDeComprobante.E
        comprobante.serie = serie
        comprobante.folio = nota.id
        comprobante.setFecha(DateUtils.getCfdiDate(new Date()))
        buildMoneda()
        comprobante.lugarExpedicion = empresa.direccion.codigoPostal
        return this
	}

    def buildMoneda(){
        if(nota.moneda.equals(MonedaUtils.PESOS)){
            comprobante.moneda = CMoneda.MXN
        } else {
            comprobante.moneda = CMoneda.valueOf(nota.moneda.toString())
            comprobante.tipoCampo = nota.tc
        }
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
        receptor.rfc = nota.cliente.rfc
        receptor.nombre = nota.cliente.nombre
        
        switch(nota.usoCfdi) {
            case 'G01':
                receptor.usoCFDI = CUsoCFDI.G_01
                break
            case 'G02':
                receptor.usoCFDI = CUsoCFDI.G_02
                break
            case 'G03':
                receptor.usoCFDI = CUsoCFDI.G_03
                break
            default:
                receptor.usoCFDI = CUsoCFDI.G_02
            break
        }
        comprobante.receptor = receptor
        return this
    }

    def buildFormaDePago(){
        comprobante.formaPago = '99'
        comprobante.condicionesDePago = 'Credito 30 Días'
        comprobante.metodoPago = CMetodoPago.PUE
        return this
    }

    def buildConceptos(){
        /** Conceptos ***/
        Comprobante.Conceptos conceptos = factory.createComprobanteConceptos()
        this.nota.partidas.each { det ->
            
            Comprobante.Conceptos.Concepto concepto = factory.createComprobanteConceptosConcepto()
            concepto.with { 
                assert det.claveProdServ, "No hay una claveProdServ definida para el concepto ${det.descripcion} SE REQUIERE PARA EL CFDI 3.3"
                assert det.claveUnidadSat, "No hay una claveUnidadSat definida para el concepto ${det.descripcion} SE REQUIERE PARA EL CFDI 3.3"
                assert det.unidadSat, "No hay una unidadSat definida para el concepto ${det.descripcion} SE REQUIERE PARA EL CFDI 3.3"
                claveProdServ = det.claveProdServ
                noIdentificacion = det.numeroDeIdentificacion
                cantidad = det.cantidad
                claveUnidad = det.claveUnidadSat
                unidad = det.unidadSat
                descripcion = det.descripcion
                valorUnitario = det.valorUnitario
                importe = det.importe
                // Impuestos del concepto
                concepto.impuestos = factory.createComprobanteConceptosConceptoImpuestos()
                concepto.impuestos.traslados = factory.createComprobanteConceptosConceptoImpuestosTraslados()
                Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado1 
                traslado1 = factory.createComprobanteConceptosConceptoImpuestosTrasladosTraslado()
                traslado1.base =  det.importe
                traslado1.impuesto = '002'
                traslado1.tipoFactor = CTipoFactor.TASA
                traslado1.tasaOCuota = '0.160000'
                traslado1.importe = MonedaUtils.round(det.importe * 0.16)
                concepto.impuestos.traslados.traslado.add(traslado1)
                conceptos.concepto.add(concepto)
                comprobante.conceptos = conceptos
            }
        }
        return this
    }

    def buildImpuestos(){
        /** Impuestos **/
        Comprobante.Impuestos impuestos = factory.createComprobanteImpuestos()
        impuestos.setTotalImpuestosTrasladados(nota.impuesto)

        Comprobante.Impuestos.Traslados traslados = factory.createComprobanteImpuestosTraslados()
        Comprobante.Impuestos.Traslados.Traslado traslado = factory.createComprobanteImpuestosTrasladosTraslado()
        traslado.impuesto = '002'
        traslado.tipoFactor = CTipoFactor.TASA
        traslado.tasaOCuota = '0.160000'
        traslado.importe = nota.impuesto
        traslados.traslado.add(traslado)
        impuestos.traslados = traslados
        comprobante.setImpuestos(impuestos)
        return this
    }

    def buildTotales(){
        comprobante.subTotal = nota.importe
        comprobante.total = nota.total
        return this
    }

    def buildCertificado(){
        comprobante.setNoCertificado(empresa.numeroDeCertificado)
        byte[] encodedCert=Base64.encode(empresa.getCertificado().getEncoded())
        comprobante.setCertificado(new String(encodedCert))
        return this

    }

    def buildRelacionados(){
        Comprobante.CfdiRelacionados relacionados = factory.createComprobanteCfdiRelacionados()
        relacionados.tipoRelacion = '01'
        Comprobante.CfdiRelacionados.CfdiRelacionado relacionado = factory.createComprobanteCfdiRelacionadosCfdiRelacionado()
        def venta = nota.ventaRelacionada
        assert venta, 'Se requiere relacionar el documento con una factura o nota de cargo existente'
        def serie = venta.tipo =='VENTA' ? 'FAC' : 'CAR'
        def cfdi = Cfdi.findBySerieAndOrigen(serie,venta.id)
        relacionado.UUID = cfdi.uuid
        relacionados.cfdiRelacionado.add(relacionado) // .add(relacionado)

        comprobante.cfdiRelacionados = relacionados
        return this
    }

    def buildRelacionados2(){
        Comprobante.CfdiRelacionados relacionados = factory.createComprobanteCfdiRelacionados()
        relacionados.tipoRelacion = '01'

        def det = nota.partidas[0]
        def rows = det.comentario.split(',')
        rows.each {
            Comprobante.CfdiRelacionados.CfdiRelacionado relacionado = factory.createComprobanteCfdiRelacionadosCfdiRelacionado()
            relacionado.UUID = it
            relacionados.cfdiRelacionado.add(relacionado) // .add(relacionado)
        }
        comprobante.cfdiRelacionados = relacionados
        return this
    }

    Comprobante getComprobante(){
        return this.comprobante
    }

    
	

}
