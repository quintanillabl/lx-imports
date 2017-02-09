databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1471540492649-1") {
		addColumn(tableName: "pago_proveedor") {
			column(name: "banco_destino_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1471540492649-2") {
		addColumn(tableName: "pago_proveedor") {
			column(name: "cuenta_destino", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1471540492649-4") {
		createIndex(indexName: "FK_r0trannrgf0cjyrsfhr6uhqj8", tableName: "pago_proveedor") {
			column(name: "banco_destino_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1471540492649-3") {
		addForeignKeyConstraint(baseColumnNames: "banco_destino_id", baseTableName: "pago_proveedor", constraintName: "FK_r0trannrgf0cjyrsfhr6uhqj8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "banco_sat", referencesUniqueColumn: "false")
	}
}
