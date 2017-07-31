package com.luxsoft.cfdi

import groovy.transform.ToString;

import mx.gob.sat.cfd.x3.ComprobanteDocument
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante
import java.io.ByteArrayInputStream


@ToString(includeNames=true,includes="id,serie,folio,tipo,fecha,uuid")
class Cfdi {
	
	
	String serie
	String tipo
	String tipoDeCfdi
	Date fecha
	String folio
	String uuid
	Date timbrado
	String emisor
	String receptor
	String rfc
	BigDecimal importe
	BigDecimal descuentos
	BigDecimal subtotal
	BigDecimal impuesto
	BigDecimal total
	String comentario
	
	String cadenaOriginal
	String origen
	String xmlName
	byte[] xml
	String url
	
	String versionCfdi = '3.2'
	String receptorRfc
	
	Date dateCreated
	Date lastUpdated

	ComprobanteDocument comprobanteDocument
	TimbreFiscal timbreFiscal

    static constraints = {
		serie blannk:false,maxSize:15 
		tipo inList:['FACTURA','NOTA_CREDITO','NOTA_CARGO','PAGO','FAC','CRE','CAR']
		fecha nullable:false
		folio blank:false,maxSize:20
		uuid nullable:true
		timbrado(nullable:true)
		emisor blank:false
		receptor blank:false
		rfc blank:false,maxSize:13
		xmlName nullable:true,maxSize:200
		xml maxSize:(1024 * 512)  // 50kb para almacenar el xml
		cadenaOriginal maxSize:1024*64, nullable:true //@Column(name="CADENA_ORIGINAL",length=1048576,nullable=true)
		origen blank:false,maxSize:255
		tipoDeCfdi inList:['I','E','N']
		comentario nullable:true
		url nullable:true,url:true
		cancelacion nullable:true
		versionCfdi inList: ['3.2','3.3']
		receptorRfc blank:false,maxSize:13
    }

    static hasOne = [cancelacion: CancelacionDeCfdi]
	
	static transients = ['comprobanteDocument','timbreFiscal']
	
	Comprobante getComprobante(){
		getComprobanteDocument().getComprobante()
	}
	
	public ComprobanteDocument getComprobanteDocument(){
		if(this.comprobanteDocument==null){
			loadComprobante()
		}
		return this.comprobanteDocument
	}
	
	void loadComprobante(){
		ByteArrayInputStream is=new ByteArrayInputStream(getXml())
		this.comprobanteDocument=ComprobanteDocument.Factory.parse(is)
		
		
	}
	
	
	String toString(){
		return "($emisor) Id:$id  Tipo:$tipo Serie:$serie Folio:$folio  UUID:$uuid xmlName:$xmlName"
	}
	
}
