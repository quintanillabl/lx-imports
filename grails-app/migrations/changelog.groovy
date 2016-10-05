databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "changelog") {
		// TODO add changes and preconditions here
	}
	
	/*
	include file: 'CfdiGastosModel.groovy'
	include file: 'CfdiGastosModel_log2.groovy'
	include file: 'CancelacionDeCfdi.groovy'
	include file: 'CompraDet_log1.groovy'


	include file: 'CfdiGastosModel_log3.groovy'

	include file: 'agencia-aduanal-prov.groovy'

	include file: 'agentes_aduanales.groovy'

	include file: 'agente_pedimento.groovy'

	include file: 'pais_de_origen.groovy'

	include file: 'pedimento_pais.groovy'

	include file: 'incrementables_usd.groovy'

	include file: 'incrementables.groovy'
	*/
	
	/* IMPLEMENTACION DE IMPAP
	include file: 'impapx_ini.groovy'
	include file: 'impapx_ini_002.groovy'
	*/

	include file: 'cheque-fix-001.groovy'

	include file: 'ajuste-polizas-001.groovy'

	include file: 'ajuste-polizas-002.groovy'

	include file: 'ajuste-polizas-003.groovy'

	include file: 'poliza-det-fix.groovy'

	include file: 'RetencionesCfdi.groovy'
	
	include file: 'econta-catalogo-01.groovy'

	include file: 'econta-balanza-01.groovy'
	
	include file: 'cuenta-suspendida.groovy'

	include file: 'econta-acuses-fix.groovy'

	include file: 'econta-polizas-01.groovy'

	include file: 'econta-pago-proveedor-transferencia-fix.groovy'
	include file: 'econta-pago-proveedor-transferencia-fix2.groovy'

	include file: 'econta-comprobante-nacional.groovy'

	include file: 'econta-comprobante-extranjero.groovy'
}
