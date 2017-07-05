package com.luxsoft.cfdix.v33

import org.apache.commons.logging.LogFactory
import org.bouncycastle.util.encoders.Base64

import com.luxsoft.impapx.Empresa
import com.luxsoft.nomina.NominaAsimilado
import com.luxsoft.nomina.Asimilado

import com.luxsoft.lx.utils.MonedaUtils
import lx.cfdi.utils.DateUtils
import lx.cfdi.v33.ObjectFactory
import lx.cfdi.v33.Comprobante
import lx.cfdi.v33.nomina.Nomina
import lx.cfdi.v33.nomina.CTipoNomina
import lx.cfdi.v33.nomina.NominaUtils


// Catalogos
import lx.cfdi.v33.CUsoCFDI
import lx.cfdi.v33.CMetodoPago
import lx.cfdi.v33.CTipoDeComprobante
import lx.cfdi.v33.CMoneda
import lx.cfdi.v33.CTipoFactor


class Cfdi33NominaBuilder {

	private static final log=LogFactory.getLog(this)

    private factory = new ObjectFactory();
	private Comprobante comprobante;
    private Empresa empresa
    private NominaAsimilado nominaAsimilado;

    def build(NominaAsimilado nominaAsimilado, String serie = 'NOMINA_CFDI'){
        this.nominaAsimilado = nominaAsimilado
        this.empresa = Empresa.first()

        buildComprobante(serie)
        .buildComplemento()
        /*
        .buildEmisor()
        .buildReceptor()
        .buildFormaDePago()
        .buildConceptos()
        .buildImpuestos()
        .buildTotales()
        .buildCertificado()
        */
        return comprobante
    }

    def buildComplemento(){
        Nomina nomina = factory.createNomina()
        nomina.version = '1.0'
        nomina.tipoNomina = CTipoNomina.E
        nomina.numDiasPagados = 1
        nomina.fechaPago = NominaUtils.toDate(nominaAsimilado.pago)
        nomina.fechaInicialPago = NominaUtils.toDate(nominaAsimilado.pago)
        nomina.fechaFinalPago = NominaUtils.toDate(nominaAsimilado.pago)

        // Emisor
        Asimilado empleado = nominaEmpleado.asimilado
        Nomina.Receptor receptor = factory.createNominaReceptor()
        receptor.curp = empleado.curp
        receptor.tipoContrato = '99'  
        receptor.tipoRegimen = '09'
        receptor.numEmpleado = empleado.id.toString()
        receptor.periodicidadPago = '99'
        receptor.setClaveEntFed(CEstado.DIF)
        nomina.receptor = receptor

        Comprobante.Complemento complemento = factory.createComprobanteComplemento()
        complemento.any.add(nomina)
        this.comprobante.complemento = complemento
        return this
    }
    

	def buildComprobante(String serie){

        log.info("Generando CFDI 3.3 para nomina de asimilado: ${this.nominaAsimilado.id}")
        this.empresa = Empresa.first()
		this.comprobante = factory.createComprobante()

        comprobante.version = "3.3"
        comprobante.tipoDeComprobante = CTipoDeComprobante.N
        comprobante.serie = serie
        comprobante.folio = nominaAsimilado.id.toString()
        comprobante.setFecha(DateUtils.getCfdiDate(nominaAsimilado.fecha))
        comprobante.moneda = CMoneda.MXN
        comprobante.lugarExpedicion = empresa.direccion.codigoPostal
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
        receptor.rfc = nomina.asimilado.rfc
        receptor.nombre = nomia.asimilado.nombre
        receptor.usoCFDI = CUsoCFDI.P01 
        comprobante.receptor = receptor
        return this
    }

    def buildFormaDePago(){
        comprobante.formaPago = '99'
        //comprobante.condicionesDePago = 'Credito 30 días'
        comprobante.metodoPago = CMetodoPago.PPD
        return this
    }

    def buildConceptos(){
        /** Conceptos ***/
        Comprobante.Conceptos conceptos = factory.createComprobanteConceptos()
        Comprobante.Conceptos.Concepto concepto = factory.createComprobanteConceptosConcepto()
        concepto.with { 
            claveProdServ = "84111505" 
            noIdentificacion = "84111505" 
            cantidad = 1
            claveUnidad = 'ACT'
            unidad = 'ACT'
            descripcion = 'Pago de nómina'
            
            def totalPercepciones = nominaEmpleado.percepciones?:0.0
            def totalOtrosPagos = 0.0
            valorUnitario = totalPercepciones + totalOtrosPagos
            importe = totalPercepciones + totalOtrosPagos

            conceptos.concepto.add(concepto)
            
        }
        comprobante.conceptos = conceptos
        
        return this
    }

    def buildTotales(){
        def totalPercepciones = nominaEmpleado.percepciones?:0.0
        def totalOtrosPagos = 0.0
        def totalDeducciones = nominaEmpleado.deducciones?: 0.0
        comprobante.subTotal = totalPercepciones + totalOtrosPagos
        comprobante.descuento = totalDeducciones
        comprobante.total = totalPercepciones + totalOtrosPagos - totalDeducciones
        return this
    }
    

    def buildCertificado(){
        comprobante.setNoCertificado(empresa.numeroDeCertificado)
        byte[] encodedCert=Base64.encode(empresa.getCertificado().getEncoded())
        comprobante.setCertificado(new String(encodedCert))
        return this

    }

    Comprobante getComprobante(){
        return this.comprobante
    }

    
	

}
