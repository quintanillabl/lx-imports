package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*

import com.luxsoft.impapx.cxc.CXCAplicacion
import com.luxsoft.impapx.cxc.CXCNota
import com.luxsoft.cfdi.*


@Transactional
class PolizaDeNotaDeCreditoService extends ProcesadorService{

    def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de notas de credito "

        def dia = poliza.fecha
        procesarNotasDeCreditoCxC poliza,dia
        procesarComplementos(poliza)
    }

    private procesarNotasDeCreditoCxC(def poliza,def dia){
    	def asiento='NOTAS DE CREDITO CXC'
    	
    	def notas=CXCNota.findAll ("from CXCNota a where date(a.fecha)=?",[dia])
    	notas.each{nota ->

             if(nota.getCfdi()){

            //Abono a cliente
            //def importe=nota.total
            def clave="105-$nota.cliente.subCuentaOperativa"
            poliza.addToPartidas(
                cuenta:CuentaContable.buscarPorClave(clave),
                debe:0.0,
                haber:nota.total,
                asiento:asiento,
                descripcion:"CRE-$nota.cfdi  ${nota.fecha.text()} ",
                referencia:"$nota.comprobanteFiscal",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CXCNota'
                ,origen:nota.id)
            //Cargo a descuentos y rebajas
            clave="402-$nota.cliente.subCuentaOperativa"
            poliza.addToPartidas(
                cuenta:CuentaContable.buscarPorClave(clave),
                debe:nota.importe,
                haber:0.0,
                asiento:asiento,
                descripcion:"CRE-$nota.cfdi  ${nota.fecha.text()} ",
                referencia:"$nota.comprobanteFiscal",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CXCNota'
                ,origen:nota.id)
            //Cargo a IVA Pendiente por trasladar (descuentos y rebajas)
            clave="209-0001"
            poliza.addToPartidas(
                cuenta:CuentaContable.buscarPorClave(clave),
                debe:nota.impuesto,
                haber:0.0,
                asiento:asiento,
                descripcion:"CRE-$nota.cfdi  ${nota.fecha.text()} ",
                referencia:"$nota.comprobanteFiscal",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CXCNota'
                ,origen:nota.id)
             }
    		
    		
    	}
    }

    def procesarComplementos(def poliza){
        poliza.partidas.each { polizaDet ->
            def serie = 'CRE'
            def row = polizaDet.origen

            

            def cfdi = Cfdi.where {serie == serie && origen == row && origen!='CANCELACION'}.find()

           if(cfdi){
            def comprobante = new ComprobanteNacional(
                  polizaDet:polizaDet,
                  uuidcfdi:cfdi.uuid,
                  rfc: cfdi.rfc,
                  montoTotal: cfdi.total
            )
            polizaDet.comprobanteNacional = comprobante

           }


            
        }
    }
}
