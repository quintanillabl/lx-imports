databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1439474960505-1",context:'tipoDeCambio') {
		modifyDataType(columnName: "fecha", newDataType: "date", tableName: "tipo_de_cambio")
	}
}
