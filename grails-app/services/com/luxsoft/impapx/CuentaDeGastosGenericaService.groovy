package com.luxsoft.impapx



import org.springframework.util.Assert;

import com.luxsoft.impapx.cxp.CuentaDeGastosGenerica;

class CuentaDeGastosGenericaService {

	def agregarFactura(long cuentaDeGastosId,long facturaId) {
		
		def cuenta=CuentaDeGastosGenerica.get(cuentaDeGastosId)
		Assert.notNull(cuenta,"No existe la cuenta de gastos genérica: "+cuentaDeGastosId)
		def factura=FacturaDeGastos.get(facturaId)
		Assert.notNull(factura,"No existe la factura: "+facturaId)
		
		cuenta.addToFacturas(factura)
		cuenta.actualizarImportes()
		cuenta.save(failOnError:true)
		//println 'Cuenta: '+cuenta.total
		return cuenta
		
	}
	
	def eliminarFacturas(def cuentaDeGastosId,def facturas){
		def cuenta=CuentaDeGastosGenerica.findById(cuentaDeGastosId,[fetch:[facturas:'eager']])
		println 'Facturas asociadas: '+cuenta.facturas.size()
		
		facturas.each{
			def facturaId=it.toLong()
			def factura=FacturaDeGastos.get(facturaId)
			//def factura=cuenta.facturas.find{
				//it.id==facturaId
			//}
			//println 'Factura a eliminar: '+factura
			if(factura){
				//cuenta.removeFromFacturas(factura)
				def res=cuenta.facturas.remove(factura)
				println 'Eliminacion: '+res
				factura.cuentaGenerica=null
				factura.save()
				cuenta.save()
			}
		}
		cuenta.actualizarImportes()
		//cuenta.save(failOnError:true)
		return cuenta
		
	}
}
