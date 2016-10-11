package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.*

@Transactional
class PolizaDeComisionesBancariasGastoService extends ProcesadorService{
    
    void procesar(def poliza){
        
        def fecha=poliza.fecha
        def gastos = FacturaDeGastos.where {concepto == 'COMISIONES_BANCARIAS' && gastoPorComprobat && fecha == fecha }.list().each { gasto ->
            def desc = "Comisión F:${gasto.folio} (${gasto.fecha}) ${gasto.proveedor.nombre} "
            //Cargo a gastos
            cargoAGastos(poliza,gasto,desc,'COMISION_GASTO')
            //Abono a deudores
            abonoDeudores(poliza,gasto,desc,'COMISION_GASTO')
        }
        log.info "Generando poliza de comisiones bancarias (Gasto) $empresa ${fecha.text()} "
        poliza.concepto="Comisiones bancarias  (Gasto) ${fecha.format('dd/MMMM/yyyy')}"
    }

    def cargoAGastos(def poliza,def gasto,def descripcion,def asiento){
        log.info 'Cargo a gastos'
        gasto.partidas.each { det ->
            assert det.cuentaContable,"Detalle del gasto sin cuenta contable ${gasto.id}"
            def cuenta = det.cuentaContable
            def referencia = 'F:'+gasto.folio
            cargoA(poliza,cuenta,det.importe,descripcion,asiento,referencia,gasto)
        }
    }
    
    def abonoDeudores(def poliza,def gasto,def descripcion,def asiento){
        
        assert gasto.proveedor.subCuentaOperativa, "No existe la subCuenta operativa para el proveedor: $gasto.proveedor"
        def cuenta = CuentaContable.buscarPorClave(poliza.empresa,'107-' + gasto.proveedor.subCuentaOperativa)
        assert cuenta, 'No existe cuenta acredora ya sea para el proveedor o la generica provedores diversos'
        def referencia = 'F:'+gasto.folio
        //cargoA(poliza,cuenta,det.importe,descripcion,asiento,referencia,gasto)
        abonoA(poliza,cuenta,gasto.subTotal,descripcion,asiento,referencia,gasto)
    }

    def cargoA(def poliza,def cuenta,def importe,def descripcion,def asiento,def referencia,def entidad){
        def det=new PolizaDet(
            cuenta:cuenta,
            concepto:cuenta.descripcion,
            debe:importe.abs(),
            haber:0.0,
            descripcion:StringUtils.substring(descripcion,0,255),
            asiento:asiento,
            referencia:referencia,
            origen:entidad.id.toString(),
            entidad:entidad.class.toString()
        )
        addComplemento(det,entidad)
        poliza.addToPartidas(det)
        return det;
    }

    def abonoA(def poliza,def cuenta,def importe,def descripcion,def asiento,def referencia,def entidad){
        def det=new PolizaDet(
            cuenta:cuenta,
            concepto:cuenta.descripcion,
            debe:0.0,
            haber:importe.abs(),
            descripcion:StringUtils.substring(descripcion,0,255),
            asiento:asiento,
            referencia:referencia,
            origen:entidad.id.toString(),
            entidad:entidad.class.toString()
        )
        addComplemento(det,entidad)
        poliza.addToPartidas(det)
        return det
    }

    def addComplemento(def polizaDet, def gasto){
        log.info("Agregando complenento de comprobante nacional para gasto: $gasto  UUID:$gasto.uuid")
        if(gasto.uuid){
            def comprobante = new ComprobanteExtranjero(
                polizaDet:polizaDet,
                numFacExt: cxp.documento,
                montoTotal: cxp.total,
                moneda: cxp.moneda.getCurrencyCode(),
                tipCamb: cxp.tc
            )
            polizaDet.comprobanteExtranjero = comprobante
        }

    }
   
 
}
