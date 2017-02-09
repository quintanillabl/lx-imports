databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1452352965148-1") {
		addColumn(tableName: "proveedor") {
			column(name: "agencia_aduanal", type: "bit")
		}
		grailsChange {
            change {
                sql.execute("UPDATE proveedor SET agencia_aduanal = false")
            }
            rollback {
            }
        }

		addNotNullConstraint(tableName: "proveedor", columnDataType: "bit",columnName: "agencia_aduanal")
	}

	
}
