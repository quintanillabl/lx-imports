databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1440177298026-1") {
		dropNotNullConstraint(columnDataType: "varchar(60)", columnName: "dash_inicial", tableName: "perfil")
	}
}
