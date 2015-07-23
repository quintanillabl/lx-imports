package com.luxsoft.cfdi

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.XmlDateTime;

import util.MonedaUtils;

import com.luxsoft.impapx.Empresa;
import com.luxsoft.impapx.Venta;
import com.luxsoft.impapx.cxc.CXCNota;

import mx.gob.sat.cfd.x3.ComprobanteDocument
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Conceptos.Concepto.CuentaPredial;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Emisor;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos.Traslados;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Impuestos.Traslados.Traslado;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.Receptor;
import mx.gob.sat.cfd.x3.ComprobanteDocument.Comprobante.TipoDeComprobante;
import mx.gob.sat.cfd.x3.TInformacionAduanera;

class CfdiConverters {
	
	static Cfdi toCfdi(def source,Empresa empresa){
		if(source instanceof Venta){
			def cfdi=fromVenta(source,empresa)
			if(source.tipo=='NOTA_DE_CARGO')
				cfdi.tipo='CAR'
			return cfdi	
		}
		else if(source instanceof CXCNota)
			return fromNota(source, empresa)
	}
	
	static Cfdi fromVenta(Venta source,Empresa empresa){
		def cfdi=new Cfdi(
			tipo:'FAC'
			,tipoDeCfdi:'I'
			,fecha:source.fecha
			,origen:source.id.toString()
			,emisor:empresa.nombre
			,receptor:source.cliente.nombre
			,rfc:source.cliente.rfc
			,importe:source.importe
			,descuentos:source.descuentos
			,subtotal:source.subtotal
			,impuesto:source.impuestos
			,total:source.total
			)
		return cfdi
	}
	
	
	
	static ComprobanteDocument toComprobante(def source,Empresa empresa){
		if(source instanceof Venta)
			return toComprobanteFromVenta(source, empresa)
		else if(source instanceof CXCNota)
			return toComprobanteFromNota(source, empresa)
		
	}
	
	static ComprobanteDocument toComprobanteFromVenta(Venta source,Empresa empresa){
		println "Generando ComprobanteDocument para $source.tipo $source.id"
		final ComprobanteDocument document=ComprobanteDocument.Factory.newInstance()
		final Comprobante comprobante=document.addNewComprobante()
		CFDIUtils.depurar(document)
		comprobante.setVersion("3.2")
		def fecha=new Date()
		//def fecha=Date.parse('dd/MM/yyy hh:mm:ss','31/03/2014 20:'+new Date().format('mm:ss'))
		comprobante.setFecha(CFDIUtils.toXmlDate(fecha).getCalendarValue())
		comprobante.setFormaDePago("PAGO EN UNA SOLA EXHIBICION")
		comprobante.setMetodoDePago(source.formaDePago)
		comprobante.setMoneda(source.moneda.getCurrencyCode())
		comprobante.setTipoCambio(source.tc.toString())
		comprobante.setDescuento(source.descuentos)
		comprobante.setTipoDeComprobante(TipoDeComprobante.INGRESO)
		comprobante.setLugarExpedicion(empresa.direccion.pais)
		//comprobante.addNewEmisor()
		Emisor emisor=CFDIUtils.registrarEmisor(comprobante, empresa)
		Receptor receptor=CFDIUtils.registrarReceptor(comprobante, source.cliente)
		comprobante.setTotal(source.total)
		comprobante.setSubTotal(source.importe)
		comprobante.setNoCertificado(empresa.numeroDeCertificado)
		
		Impuestos impuestos=comprobante.addNewImpuestos();
		String rfc=comprobante.getReceptor().getRfc();
		
		//Facturacion a clientes extranjero
		if(rfc=="XEXX010101000"){
			comprobante.setSubTotal(source.importe);
			comprobante.setTotal(source.subtotal);
		}else if(rfc=="XAXX010101000" || StringUtils.isBlank(rfc)){
			comprobante.setSubTotal(source.importe*(1+MonedaUtils.IVA));
			comprobante.setDescuento(source.descuentos*(1+MonedaUtils.IVA));
			comprobante.setTotal(source.total);
		}else{
			impuestos.setTotalImpuestosTrasladados(source.impuestos);
			Traslados traslados=impuestos.addNewTraslados();
			Traslado traslado=traslados.addNewTraslado();
			traslado.setImpuesto(Traslado.Impuesto.IVA);
			traslado.setImporte(source.impuestos);
			traslado.setTasa(MonedaUtils.IVA*100);
		}
		
		
		Conceptos conceptos=comprobante.addNewConceptos()
		
		if(source.tipo=='NOTA_DE_CARGO'){
			Concepto c=conceptos.addNewConcepto();
			c.setCantidad(1.00);
			c.setUnidad("NO APLICA");
			c.setNoIdentificacion('CARGO');
			c.setDescripcion(source.comentario);
			c.setValorUnitario(source.importe);
			c.setImporte(source.importe);
		}
		
		source.partidas.each {det->
			
			Concepto c=conceptos.addNewConcepto()
			c.setCantidad(det.getCantidadEnUnidad())
			c.setUnidad(det.producto.unidad.nombre)
			c.setNoIdentificacion(det.producto.clave)
			String desc = det.producto.descripcion
			if(StringUtils.isNotBlank(det.getComentario()))
				desc = (new StringBuilder(String.valueOf(desc))).append(StringUtils.stripToEmpty(det.comentario)).toString()
			desc = StringUtils.abbreviate(desc, 250)
			c.setDescripcion(desc)
			c.setValorUnitario(det.precio)
			c.setImporte(det.importe)
			
			def pedimento=det.embarque?.pedimento
			if(pedimento){
				TInformacionAduanera aduana=c.addNewInformacionAduanera()
				aduana.setAduana(det.embarque.embarque.aduana.nombre)
				aduana.setNumero(pedimento.pedimento)
				Calendar cal=Calendar.getInstance()
				cal.setTime(pedimento.fecha)
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				XmlDateTime xmlDateTime = XmlDateTime.Factory.newInstance();
				xmlDateTime.setStringValue(df.format(cal.getTime()));
				aduana.setFecha(xmlDateTime.getCalendarValue());
			}
			
			
		}
		println 'ComprobanteDocument generado: '+document
		return document
	}
	
	
	static Cfdi fromNota(CXCNota source,Empresa empresa){
		def cfdi=new Cfdi(
			tipo:'CRE'
			,tipoDeCfdi:'E'
			,fecha:source.fecha
			,origen:source.id.toString()
			,emisor:empresa.nombre
			,receptor:source.cliente.nombre
			,rfc:source.cliente.rfc
			,importe:source.importe
			,descuentos:0
			,subtotal:source.importe
			,impuesto:source.impuesto
			,total:source.total
			)
		return cfdi
	}
	
	
	static ComprobanteDocument toComprobanteFromNota(CXCNota source,Empresa empresa){
		final ComprobanteDocument document=ComprobanteDocument.Factory.newInstance()
		final Comprobante comprobante=document.addNewComprobante()
		CFDIUtils.depurar(document)
		comprobante.setVersion("3.2")
		comprobante.setFecha(CFDIUtils.toXmlDate(new Date()).getCalendarValue())
		comprobante.setFormaDePago("PAGO EN UNA SOLA EXHIBICION")
		comprobante.setMetodoDePago("NO IDENTIFICADO")
		comprobante.setMoneda(source.moneda.getCurrencyCode())
		comprobante.setTipoCambio(source.tc.toString())
		
		comprobante.setTipoDeComprobante(TipoDeComprobante.EGRESO)
		comprobante.setLugarExpedicion(empresa.direccion.pais)
		
		Emisor emisor=CFDIUtils.registrarEmisor(comprobante, empresa)
		
		Receptor receptor=CFDIUtils.registrarReceptor(comprobante, source.cliente)
		
		comprobante.setTotal(source.total)
		comprobante.setSubTotal(source.importe)
		comprobante.setNoCertificado(empresa.numeroDeCertificado)
		
		Impuestos impuestos=comprobante.addNewImpuestos();
		String rfc=comprobante.getReceptor().getRfc();
		
		//Facturacion a clientes extranjero
		if(rfc=="XEXX010101000"){
			comprobante.setSubTotal(source.importe);
			comprobante.setTotal(source.subtotal);
		}else if(rfc=="XAXX010101000" || StringUtils.isBlank(rfc)){
			comprobante.setSubTotal(source.importe*(1+MonedaUtils.IVA));
			//comprobante.setDescuento(source.descuentos*(1+MonedaUtils.IVA));
			comprobante.setTotal(source.total);
		}else{
			impuestos.setTotalImpuestosTrasladados(source.impuesto);
			Traslados traslados=impuestos.addNewTraslados();
			Traslado traslado=traslados.addNewTraslado();
			traslado.setImpuesto(Traslado.Impuesto.IVA);
			traslado.setImporte(source.impuesto);
			traslado.setTasa(MonedaUtils.IVA*100);
		}
		
		
		Conceptos conceptos=comprobante.addNewConceptos()
		
		source.partidas.each {det->
			
			Concepto c=conceptos.addNewConcepto()
			c.setCantidad(det.getCantidad());
			c.setUnidad(det.unidad);
			c.setNoIdentificacion(det.numeroDeIdentificacion);
			
			String desc = det.descripcion;
			c.setDescripcion(desc);
			c.setValorUnitario(det.valorUnitario);
			c.setImporte(det.importe);
			
			
		}
		return document 
	}
	

}
