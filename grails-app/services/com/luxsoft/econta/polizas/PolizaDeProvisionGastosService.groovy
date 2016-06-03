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

import static com.luxsoft.econta.polizas.PolizaUtils.*

@Transactional
class PolizaDeProvisionGastosService extends ProcesadorService{

	def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de provisión de gastos "

        def dia = poliza.fecha
    	def inicio=dia.inicioDeMes()

    	def gastos=FacturaDeGastos.findAll("from FacturaDeGastos g where  date(g.fecha) between ? and ? ",
    		[inicio,dia])

    	gastos=gastos.findAll { it.buscarSaldoAlCorte(dia) }

    	gastos.each{ gasto ->
    		def flete = gasto.conceptos.find{ it.concepto.clave.startsWith('600-F')}
    		if(!flete){
    			def desc = "Prov F:${gasto.documento} (${gasto.fecha.text()}) ${gasto.proveedor.nombre} "
    			cargoAGastos(poliza,gasto,desc)
    			cargoAIvaPendienteDeAcreditar(poliza,gasto,desc)
    			abonoAAcredoresDiversos(poliza,gasto,desc)
    		}
    		
    	}
    	
    	poliza.descripcion="PROVISION ${dia.format('dd/MM/yyyy')}"
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
				'F:'+gasto.documento,
				gasto
			)
		}
	}

	def cargoAIvaPendienteDeAcreditar(def poliza,def gasto,def descripcion){
		log.info 'Cargo a iva pendiente de acreditar'
		cargoA(poliza,
			CuentaContable.buscarPorClave('119-0001'),
			gasto.impuestos,
			descripcion,
			'PROVISION',
			'F:'+gasto.documento,
			gasto
		)
	}

	def abonoAAcredoresDiversos(def poliza,def gasto,def descripcion){
		log.info 'Abono a acredores diversos'
		def proveedor = gasto.proveedor
		//assert proveedor.subCuentaOperativa, 'No existe la subCuentaOperativa del proveedor:  '+proveedor
		def cta = CuentaContable.findByClave('205-'+proveedor.subCuentaOperativa)
		if(cta == null){
			cta = CuentaContable.findByClave('205-V001')
		}
		abonoA(poliza,
			cta,
			gasto.total,
			descripcion,
			'PROVISION',
			'F:'+gasto.documento,
			gasto
		)
	}

	

}