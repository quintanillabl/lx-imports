databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1441807227536-1") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "date_created", type: "datetime")
		}
		grailsChange {
            change {
                sql.execute("UPDATE comprobante_fiscal SET date_created = NOW()")
            }
            rollback {
            }
        }
		
        addNotNullConstraint(tableName: "comprobante_fiscal", columnDataType: "datetime",columnName: "date_created")
	}
	

	changeSet(author: "rcancino (generated)", id: "1441807227536-2") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "last_updated", type: "datetime")
		}
		grailsChange {
            change {
                sql.execute("UPDATE comprobante_fiscal SET last_updated = NOW()")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "comprobante_fiscal", columnDataType: "datetime",columnName: "last_updated")
	}

	changeSet(author: "rcancino (generated)", id: "1441807227536-3") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "fecha", type: "date")
		}
		grailsChange {
            change {
                sql.execute("UPDATE comprobante_fiscal SET fecha = NOW()")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "comprobante_fiscal",columnDataType: "date", columnName: "fecha")

	}
	
}
