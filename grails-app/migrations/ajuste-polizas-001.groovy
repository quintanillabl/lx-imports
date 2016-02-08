databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1453907988718-1") {
		addColumn(tableName: "poliza") {
			column(name: "cierre", type: "datetime")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453907988718-2") {
		addColumn(tableName: "poliza") {
			column(name: "ejercicio", type: "integer") 
		}
		grailsChange {
            change {
                sql.execute("UPDATE poliza SET ejercicio = year(fecha)")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "poliza", columnDataType: "integer",columnName: "ejercicio")
	}

	changeSet(author: "rcancino (generated)", id: "1453907988718-4") {
		addColumn(tableName: "poliza") {
			column(name: "mes", type: "integer") 
		}
		grailsChange {
            change {
                sql.execute("UPDATE poliza SET mes = month(fecha)")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "poliza", columnDataType: "integer",columnName: "mes")
	}

	changeSet(author: "rcancino (generated)", id: "1453907988718-3") {
		addColumn(tableName: "poliza") {
			column(name: "manual", type: "bit")
		}
		grailsChange {
            change {
                sql.execute("UPDATE poliza SET manual = false")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "poliza", columnDataType: "bit",columnName: "manual")
	}

	

	changeSet(author: "rcancino (generated)", id: "1453907988718-5") {
		addColumn(tableName: "poliza") {
			column(name: "sub_tipo", type: "varchar(30)") 
		}
		grailsChange {
            change {
                sql.execute("UPDATE poliza SET sub_tipo = tipo")
            }
            rollback {
            }
        }
        addNotNullConstraint(tableName: "poliza", columnDataType: "varchar(30)",columnName: "sub_tipo")
	}

	changeSet(author: "rcancino (generated)", id: "1453907988718-13") {
		modifyDataType(columnName: "fecha", newDataType: "date", tableName: "poliza")
	}

	
}
