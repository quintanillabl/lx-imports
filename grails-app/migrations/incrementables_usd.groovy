databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1452813356854-1") {
		addColumn(tableName: "embarque_det") {
			column(name: "incrementables_usd", type: "decimal(19,2)")
		}
		grailsChange {
            change {
                sql.execute("UPDATE embarque_det SET incrementables_usd = 0")
            }
            rollback {
            }
        }

		addNotNullConstraint(tableName: "embarque_det", columnDataType: "decimal(19,2)",columnName: "incrementables_usd")
	}

	
}
