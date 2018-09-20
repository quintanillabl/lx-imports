databaseChangeLog = {

	changeSet(author: "rubencancino (generated)", id: "1537465555189-1") {
		addColumn(tableName: "cxcabono") {
			column(name: "cfdi_id", type: "bigint")
		}
	}

	changeSet(author: "rubencancino (generated)", id: "1537465555189-7") {
		createIndex(indexName: "FK_9vynnudyinfp0wotj5de0bcw4", tableName: "cxcabono") {
			column(name: "cfdi_id")
		}
	}
	

	changeSet(author: "rubencancino (generated)", id: "1537465555189-5") {
		addForeignKeyConstraint(baseColumnNames: "cfdi_id", baseTableName: "cxcabono", constraintName: "FK_9vynnudyinfp0wotj5de0bcw4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cfdi", referencesUniqueColumn: "false")
	}
}
