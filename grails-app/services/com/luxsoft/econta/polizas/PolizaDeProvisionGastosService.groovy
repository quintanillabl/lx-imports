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

    	def gastos=FacturaDeGastos.findAll("from FacturaDeGastos g where  date(g.fecha) = ? and g.concepto != 'COMISIONES_BANCARIAS'",
    		[dia])

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
		//log.info 'Cargo a gastos'
		gasto.conceptos.each { det ->
			assert det.concepto,"Detalle del gasto sin cuenta contable ${gasto.id}"
			def polizaDet = cargoA(poliza,
				det.concepto,
				det.importe*gasto.tc,
				descripcion,
				'PROVISION',
				'F:'+gasto.documento,
				gasto
			)
			agregarComplemento(polizaDet,gasto)
		}
	}

	def cargoAIvaPendienteDeAcreditar(def poliza,def gasto,def descripcion){
		//log.info 'Cargo a iva pendiente de acreditar'
		def polizaDet = cargoA(poliza,
			CuentaContable.buscarPorClave('119-0001'),
			gasto.impuestos,
			descripcion,
			'PROVISION',
			'F:'+gasto.documento,
			gasto
		)
		agregarComplemento(polizaDet,gasto)
	}

	def abonoAAcredoresDiversos(def poliza,def gasto,def descripcion){
		//log.info 'Abono a acredores diversos'
		def proveedor = gasto.proveedor
		//assert proveedor.subCuentaOperativa, 'No existe la subCuentaOperativa del proveedor:  '+proveedor
		def cta = CuentaContable.findByClave('205-'+proveedor.subCuentaOperativa)
		if(cta == null){
			cta = CuentaContable.findByClave('205-V001')
		}
		def polizaDet = abonoA(poliza,
			cta,
			gasto.total*gasto.tc,
			descripcion,
			'PROVISION',
			'F:'+gasto.documento,
			gasto
		)
		agregarComplemento(polizaDet,gasto)

		if(gasto.retensionIsr){
			def polizaDetRet = abonoA(poliza,
			CuentaContable.buscarPorClave('216-0002'),
			gasto.retensionIsr,
			descripcion,
			'PROVISION',
			'F:'+gasto.documento,
			gasto)
			agregarComplemento(polizaDetRet,gasto)

		}
	}

	def agregarComplemento(def polizaDet, def cxp){
		
		if(cxp?.comprobante){
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
		}else {
			log.info(" CxP $cxp.id (${cxp.class.getSimpleName()}) sin CFDI no se puede generar complemento")
		}
	    
	}

	

}