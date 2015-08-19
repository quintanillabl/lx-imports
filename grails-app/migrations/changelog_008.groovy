databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1439816137325-1") {
		addColumn(tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1439816137325-3") {
		createIndex(indexName: "FK_98j7qng4c7yv8vqqy5ft8edpr", tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1439816137325-2") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_sat_id", baseTableName: "cuenta_contable", constraintName: "FK_98j7qng4c7yv8vqqy5ft8edpr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_sat", referencesUniqueColumn: "false")
	}
}
