databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1452554941728-1") {
		createTable(tableName: "proveedor_agentes") {
			column(name: "proveedor_id", type: "bigint")

			column(name: "agentes_string", type: "varchar(255)")
		}
	}
	
	changeSet(author: "rcancino (generated)", id: "1452554941728-13") {
		createIndex(indexName: "FK_sy2xb512760vlw7sr1e6fjcnt", tableName: "proveedor_agentes") {
			column(name: "proveedor_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1452554941728-11") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "proveedor_agentes", constraintName: "FK_sy2xb512760vlw7sr1e6fjcnt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "proveedor", referencesUniqueColumn: "false")
	}
}
