databaseChangeLog = {

	
	changeSet(author: "rcancino (generated)", id: "1454096644365-5") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "asiento", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1454096644365-6") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "descripcion", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1454096644365-7") {
		modifyDataType(columnName: "origen", newDataType: "varchar(255)", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1454096644365-8") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "referencia", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1454096644365-14") {
		dropColumn(columnName: "fecha", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1454096644365-15") {
		dropColumn(columnName: "tipo", tableName: "poliza_det")
	}
}
