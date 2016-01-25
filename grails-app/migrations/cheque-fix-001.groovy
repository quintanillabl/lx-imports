databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1453726779417-4") {
		dropNotNullConstraint(columnDataType: "bigint", columnName: "egreso_id", tableName: "cheque")
	}
	
}
