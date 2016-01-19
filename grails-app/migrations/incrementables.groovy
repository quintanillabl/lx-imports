databaseChangeLog = {

	changeSet(author: "RUBEN (generated)", id: "1453142679736-1") {
		addColumn(tableName: "pedimento") {
			column(name: "contra_prestacion", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}
		}
		grailsChange {
            change {
                sql.execute("UPDATE pedimento SET contra_prestacion = 0.0")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "pedimento", columnDataType: "decimal(19,2)",columnName: "contra_prestacion")

	}

	changeSet(author: "RUBEN (generated)", id: "1453142679736-2") {
		addColumn(tableName: "pedimento") {
			column(name: "incrementables", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}
		}
		grailsChange {
            change {
                sql.execute("UPDATE pedimento SET incrementables = 0.0")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "pedimento", columnDataType: "decimal(19,2)",columnName: "incrementables")
	}

	
}
