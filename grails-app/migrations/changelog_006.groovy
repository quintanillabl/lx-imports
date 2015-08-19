databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1439653998207-1") {
		modifyDataType(columnName: "fecha", newDataType: "date", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1439653998207-2") {
		addNotNullConstraint(columnDataType: "date", columnName: "fecha", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1439653998207-3") {
		dropColumn(columnName: "disponible", tableName: "cxcabono")
	}
}
