databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "changelog") {
		// TODO add changes and preconditions here
	}

	include file: 'cfdi33-patch-001.groovy'

	//include file: 'cfdi33.patch-002.groovy'

	include file: 'cfdi33-patch-002.groovy'

	include file: 'cfdi33-patch-003.groovy'

	include file: 'cfdi33-patch-004.groovy'

  include file: 'cfdi33-patch-006.groovy'

	
}
