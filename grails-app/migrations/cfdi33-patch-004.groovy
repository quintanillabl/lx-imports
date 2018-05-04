databaseChangeLog = {

	changeSet(author: "RUBEN (generated)", id: "1500315131314-1") {
		addColumn(tableName: "cxcabono") {
			column(name: "venta_relacionada_id", type: "bigint")
		}
	}
	
	changeSet(author: "RUBEN (generated)", id: "1500315131314-17") {
		createIndex(indexName: "FK_2itxf2ijy2vfnutxsdvc5oi2r", tableName: "cxcabono") {
			column(name: "venta_relacionada_id")
		}
	}
	
	changeSet(author: "RUBEN (generated)", id: "1500315131314-13") {
		addForeignKeyConstraint(baseColumnNames: "venta_relacionada_id", baseTableName: "cxcabono", constraintName: "FK_2itxf2ijy2vfnutxsdvc5oi2r", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "venta", referencesUniqueColumn: "false")
	}
}
