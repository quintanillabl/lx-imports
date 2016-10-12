package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils

import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.*

@Transactional
class PolizaDeComisionesBancariasGastoService extends ProcesadorService{

    def procesar(Poliza poliza){
        def fecha=poliza.fecha
        log.info "Generando poliza de comisiones bancarias (Gasto)  ${fecha.text()} "
        poliza.descripcion="Comisiones bancarias  (Gasto) ${fecha.format('dd/MMMM/yyyy')}"
        
        def gastos = FacturaDeGastos.where {concepto == 'COMISIONES_BANCARIAS' && gastoPorComprobat && fecha == fecha }.list().each { gasto ->
            
            def desc = "Comisión F:${gasto.documento} (${gasto.fecha}) ${gasto.proveedor.nombre} "
            //Cargo a gastos
            cargoAGastos(poliza,gasto,desc,'COMISION_GASTO')
            
            //Abono a deudores
            abonoDeudores(poliza,gasto,desc,'COMISION_GASTO')
        }
        
        
    }
    

    def cargoAGastos(def poliza,def gasto,def descripcion,def asiento){
        log.info 'Cargo a gastos'
        gasto.conceptos.each { det ->
            assert det.concepto,"Detalle del gasto sin cuenta contable ${gasto.id}"
            def cuenta = det.concepto
            def referencia = 'F:'+gasto.documento
            cargoA(poliza,cuenta,det.importe,descripcion,asiento,referencia,gasto)
        }
    }
    
    def abonoDeudores(def poliza,def gasto,def descripcion,def asiento){
        
        assert gasto.proveedor.subCuentaOperativa, "No existe la subCuenta operativa para el proveedor: $gasto.proveedor"
        def cuenta = CuentaContable.buscarPorClave('107-' + gasto.proveedor.subCuentaOperativa)
        assert cuenta, 'No existe cuenta acredora ya sea para el proveedor o la generica provedores diversos'
        def referencia = 'F:'+gasto.documento
        //cargoA(poliza,cuenta,det.importe,descripcion,asiento,referencia,gasto)
        abonoA(poliza,cuenta,gasto.importe,descripcion,asiento,referencia,gasto)
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
            entidad:entidad.class.getSimpleName()
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
            entidad:entidad.class.getSimpleName()
        )
        addComplemento(det,entidad)
        poliza.addToPartidas(det)
        return det
    }

    def addComplemento(def polizaDet, def cxp){
        if(cxp?.comprobante){
            log.info("Agregando complenento de comprobante nacional para cxp: $cxp.documento  UUID:${cxp.comprobante?.uuid}")
            def cfdi = cxp.comprobante
            def comprobante = new ComprobanteNacional(
              polizaDet:polizaDet,
              uuidcfdi:cfdi.uuid,
              rfc: cfdi.emisorRfc,
              montoTotal: cfdi.total,
              moneda: cxp.moneda.getCurrencyCode(),
              tipCamb: cxp.tc
            )
            polizaDet.comprobanteNacional = comprobante
        }

    }
   
 
}
