
import grails.plugin.springsecurity.SpringSecurityUtils



navigation={
	user{
		
		catalogos(){
			lineas(controller:'linea',action:'index')
			marcas(controller:'marca',action:'index')
			clases(controller:'clase',action:'index')
			unidades(controller:'unidad',action:'index')
			productos(controller:'producto',action:'index')
			proveedores(controller:'proveedor',action:'index')
			clientes(controller:'cliente',action:'index')
			aduana(controller:'aduana',action:'index')
		}
		operaciones(){
			compras(controller:'compra',action:'index')
			embarques(controller:'embarque',action:'index')
			pedimentos(controller:'pedimento',action:'index')
			cuentasDeGastos(controller:'cuentaDeGastos',action:'index')
			cuentasPorPagar(controller:'cuentaPorPagar',action:'index'){
				importaciones()
				gastosDeImportacion()
				otrosGastos()
				requisiciones()
				pagos()
				notasDeCredito()
				detallesDeFactura()
				cuentaDeGastos()
			}
			distribucion(controller:'distribucion',action:'index')
			ventas(controller:'venta',action:'index')
			cfdis(controller:'cfdi',action:'index')
			notasDeCargo(controller:'notaDeCargo',action:'index')
			cuentasPorCobrar(controller:'cuentaPorCobrar',action:'index')
			anticipo(controller:'anticipo',action:'index')
		}
		
		contabilidad(){
			cuentasContables(controller:'cuentaContable',action:'index',titleText:'Cuentas')
			saldos(controller:'saldoPorCuentaContable',action:'index')
			foliosCFDI(controller:'cfdiFolio',action:'index')
			polizas(controller:'poliza',action:'index')
			balanza(controller:'poliza',action:'index')
			reportes(controller:'contabilidad',action:'index')
			cierreAnual(controller:'poliza',action:'cierreAnual')
			contabilidadElectronica(controller:'contabilidadElectronica',action:'index')
			diot(controller:'diot',action:'index',titleText:'DIOT')
		}
		
		tesoreria(){
			tipoDeCambio(controller:'tipoDeCambio',actio:'index')
			bancos(controller:'banco',action:'index')
			cuentas(controller:'cuentaBancaria',action:'index')
			proveedores(controller:'proveedor',action:'index')
			cuentaPorPagar(controller:'cuentaPorPagar',action:'index',titleText:'C x P')
			movimientos(controller:'movimientoDeCuenta',action:'index')
		}
		
	}
	
}