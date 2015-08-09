databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1438441269716-1") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "comentario", tableName: "cancelacion_de_cargo")
	}

	changeSet(author: "rcancino (generated)", id: "1438441269716-2") {
		addNotNullConstraint(columnDataType: "varchar(600)", columnName: "emisor", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1438441269716-3") {
		addNotNullConstraint(columnDataType: "varchar(600)", columnName: "receptor", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1438441269716-4") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "descripcion", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1438441269716-5") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "descripcion", tableName: "cuenta_contable")
	}

	changeSet(author: "rcancino (generated)", id: "1438441269716-6") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "comentario", tableName: "cxcnota_det")
	}

	changeSet(author: "rcancino (generated)", id: "1438441269716-7") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "comentario", tableName: "venta")
	}
}
