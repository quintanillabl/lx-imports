databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1471540492699-1") {
		addColumn(tableName: "pago_proveedor") {
			column(name: "banco_destino_ext", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1471540492699-2") {
		addColumn(tableName: "banco") {
			column(name: "nacional", type: "bit")
		}
		grailsChange {
            change {
                sql.execute("UPDATE banco SET nacional = true")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "banco", columnDataType: "bit",columnName: "nacional")
	}

	changeSet(author: "rcancino (generated)", id: "1471540492699-3") {
		addColumn(tableName: "pago_proveedor") {
			column(name: "referencia", type: "varchar(255)")
		}
	}
	
}
