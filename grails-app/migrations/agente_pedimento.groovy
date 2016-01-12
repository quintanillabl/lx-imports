databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1452620024801-1") {
		addColumn(tableName: "pedimento") {
			column(name: "agente_aduanal", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1452620024801-2") {
		addColumn(tableName: "pedimento") {
			column(name: "pais_de_origen", type: "varchar(255)")
		}
	}
}
