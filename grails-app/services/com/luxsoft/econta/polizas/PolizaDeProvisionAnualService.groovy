package com.luxsoft.econta.polizas

import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.CuentaDeGastos
import com.luxsoft.impapx.CuentaPorPagar
import com.luxsoft.impapx.Embarque
import com.luxsoft.impapx.EmbarqueDet
import com.luxsoft.impapx.FacturaDeImportacion
import com.luxsoft.impapx.GastosDeImportacion
import com.luxsoft.impapx.Pedimento
import com.luxsoft.impapx.TipoDeCambio
import com.luxsoft.impapx.contabilidad.*
import grails.transaction.Transactional
import util.MonedaUtils
import util.Rounding

@Transactional
class PolizaDeProvisionAnualService extends ProcesadorService{

	def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de provisión anual de compras "
        def dia = poliza.fecha
        procearCuentaPorPagarMateriaPrima(poliza, dia)
        cuadrar(poliza)
        depurar(poliza)
        save poliza
    }

	private procearCuentaPorPagarMateriaPrima(def poliza , def dia){
		
		def asiento="Provision "+dia.toYear()
		
		
		def facturas=FacturaDeImportacion.findAll("from FacturaDeImportacion f where f.provisionada=?",[dia.toYear()])
		
		facturas.each{ factura->
			
			//println entry
			def fechaF=factura.fecha.text()
			
			// 1. Cargo al inventario
			def cuenta=CuentaContable.buscarPorClave('115-0003')
			def fechaTc=factura.fecha-1
			def tipoDeCambioInstance=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=? and t.monedaFuente=?",[fechaTc,factura.moneda])
			def valor=factura.importe*tipoDeCambioInstance.factor
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:valor,
				haber:0.0,
				asiento:asiento,
				descripcion:"$factura.proveedor ($fechaF) $factura.importe T.C:$tipoDeCambioInstance.factor",
				referencia:"$factura.documento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CuentaPorPagar'
				,origen:factura.id)
			
			def clave="201-$factura.proveedor.subCuentaOperativa"
			cuenta=CuentaContable.buscarPorClave(clave)
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:0.0,
				haber:valor,
				asiento:asiento,
				descripcion:"$factura.proveedor ($fechaF) $factura.importe T.C:$tipoDeCambioInstance.factor",
				referencia:"$factura.documento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CuentaPorPagar'
				,origen:factura.id)
		}
	}

}