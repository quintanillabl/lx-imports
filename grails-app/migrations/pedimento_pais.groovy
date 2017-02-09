databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1452635812033-1") {
		addColumn(tableName: "pedimento") {
			column(name: "pais_de_origen_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1452635812033-13") {
		createIndex(indexName: "FK_si62rf29l2tvyxvcntsg0svbe", tableName: "pedimento") {
			column(name: "pais_de_origen_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1452635812033-14") {
		dropColumn(columnName: "pais_de_origen", tableName: "pedimento")
	}

	changeSet(author: "rcancino (generated)", id: "1452635812033-11") {
		addForeignKeyConstraint(baseColumnNames: "pais_de_origen_id", baseTableName: "pedimento", constraintName: "FK_si62rf29l2tvyxvcntsg0svbe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pais_de_origen", referencesUniqueColumn: "false")
	}
}
