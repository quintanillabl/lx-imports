package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.Venta
import com.luxsoft.impapx.cxp.NotaDeCredito
import com.luxsoft.cfdi.*
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.cxp.*

@Transactional
class PolizaDeDescuentosSobreComprasService extends ProcesadorService{

    def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de descuentos sobre compras "

        def dia = poliza.fecha
        procesarDescuentosCxP poliza,dia
        procesarComplementos(poliza)
    }

    private procesarDescuentosCxP(def poliza,def dia){
    	def asiento='DESCUENTOS_CXP'
    	
    	def notas=NotaDeCredito.findAll ("from NotaDeCredito a where date(a.fecha)=?",[dia])
    	notas.each{nota ->
    		
    		
    		def importe=nota.total*nota.tc
    		def clave="702-$nota.proveedor.subCuentaOperativa"
    		poliza.addToPartidas(
    			cuenta:CuentaContable.buscarPorClave(clave),
    			debe:0.0,
    			haber:importe,
    			asiento:asiento,
    			descripcion:"NC: $nota.documento $nota.concepto  $nota.total * $nota.tc",
    			referencia:"$nota.documento",
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'NotaDeCredito'
    			,origen:nota.id)
    		
    		clave="201-$nota.proveedor.subCuentaOperativa"
    		poliza.addToPartidas(
    			cuenta:CuentaContable.buscarPorClave(clave),
    			debe:importe,
    			haber:0.0,
    			asiento:asiento,
    			descripcion:"NC: $nota.documento $nota.concepto  $nota.total * $nota.tc",
    			referencia:"$nota.documento",
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'NotaDeCredito'
    			,origen:nota.id)
    		
    	}
    }

    def procesarComplementos(def poliza){

        poliza.partidas.each {polizaDet ->

            if(polizaDet.entidad == 'NotaDeCredito'){
                def nota = NotaDeCredito.get(polizaDet.origen)
                def comprobante = new ComprobanteExtranjero(
                    polizaDet:polizaDet,
                    numFacExt: nota.documento,
                    taxId: nota.proveedor.rfc,
                    montoTotal: nota.total,
                    moneda: nota.moneda.getCurrencyCode(),
                    tipCamb: nota.tc
                )
                polizaDet.comprobanteExtranjero = comprobante
            }
        }
    }
}
