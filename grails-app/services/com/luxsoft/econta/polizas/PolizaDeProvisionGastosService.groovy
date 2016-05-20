package com.luxsoft.econta.polizas

import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.CuentaDeGastos
import com.luxsoft.impapx.CuentaPorPagar
import com.luxsoft.impapx.Embarque
import com.luxsoft.impapx.EmbarqueDet

import com.luxsoft.impapx.FacturaDeGastos
import com.luxsoft.impapx.Pedimento
import com.luxsoft.impapx.TipoDeCambio
import com.luxsoft.impapx.contabilidad.*
import grails.transaction.Transactional
import util.MonedaUtils
import util.Rounding

@Transactional
class PolizaDeProvisionGastosService extends ProcesadorService{

	def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de provisión de gastos "

        def dia = poliza.fecha
    	def inicio=dia.inicioDeMes()

    	def gastos=FacturaDeGastos.findAll("from FacturaDeGastos g where  date(g.fecha) between ? and ?",
    		[inicio,dia])

    	gastos=gastos.findAll { it.buscarSaldoAlCorte(dia) }

    	gastos.each{ gasto ->
    		def desc = "Prov F:${gasto.id} (${gasto.fecha}) ${gasto.proveedor.nombre} "
    		//cargoAGastos(poliza,gasto,desc)
    		//cargoAIvaPendienteDeAcreditar(poliza,gasto,desc)
    		//abonoAAcredoresDiversos(poliza,gasto,desc)
    	}
    	
    	poliza.descripcion="PROVISION ${dia.format('dd/MM/yyyy')}"

        //procearCuentaPorPagarMateriaPrima(poliza, dia)
        cuadrar(poliza)
        depurar(poliza)
        save poliza


    }

    def cargoAGastos(def poliza,def gasto,def descripcion){
		log.info 'Cargo a gastos'
		gasto.conceptos.each { det ->
			assert det.concepto,"Detalle del gasto sin cuenta contable ${gasto.id}"
			cargoA(poliza,
				det.concepto,
				det.importe,
				descripcion,
				'PROVISION',
				'F:'+gasto.id,
				gasto
			)
		}
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