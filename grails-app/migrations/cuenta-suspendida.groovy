databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1452352365149-6") {
		addColumn(tableName: "cuenta_contable") {
			column(name: "suspendida", type: "bit")
		}
		grailsChange {
            change {
                sql.execute("UPDATE cuenta_contable SET suspendida = false")
            }
            rollback {
            }
        }

		addNotNullConstraint(tableName: "cuenta_contable", columnDataType: "bit",columnName: "suspendida")
	}

	
}
