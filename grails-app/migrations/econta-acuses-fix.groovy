databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1471384214981-1") {
		addColumn(tableName: "sat_balanza_log") {
			column(name: "acuse_de_aceptacion", type: "mediumblob")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1471384214981-2") {
		addColumn(tableName: "sat_catalogo_log") {
			column(name: "acuse_de_aceptacion", type: "mediumblob")
		}
	}
}
