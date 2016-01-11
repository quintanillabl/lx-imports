databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "changelog") {
		// TODO add changes and preconditions here
	}

	//include file: 'changelog-001.groovy'

	/* Impementacion de IMPAP

	include file: 'changelog-002.groovy'
	include file: 'changelog-003.groovy'
	include file: 'changelog-004.groovy'
	include file: 'changelog-005.groovy'
	include file: 'changelog_006.groovy'
	include file: 'changelog_007.groovy'
	include file: 'changelog_008.groovy'
	include file: 'changelog_009.groovy'
	include file: 'changelog_010.groovy'
	include file: 'changelog_011.groovy'
	*/
	
	//include file: 'paper-ini.groovy'
	include file: 'CfdiGastosModel.groovy'
	include file: 'CfdiGastosModel_log2.groovy'
	include file: 'CancelacionDeCfdi.groovy'
	include file: 'CompraDet_log1.groovy'


	include file: 'CfdiGastosModel_log3.groovy'

	include file: 'agencia-aduanal-prov.groovy'
}
