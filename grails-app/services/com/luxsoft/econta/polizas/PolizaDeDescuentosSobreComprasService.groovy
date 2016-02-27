package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.Venta
import com.luxsoft.impapx.cxp.NotaDeCredito
import com.luxsoft.cfdi.*

@Transactional
class PolizaDeDescuentosSobreComprasService extends ProcesadorService{

    def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de descuentos sobre compras ${poliza.fecha.text()}"

        def dia = poliza.fecha
        procesarDescuentosCxP poliza,dia
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
}
