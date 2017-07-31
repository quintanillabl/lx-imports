databaseChangeLog = {

	changeSet(author: "RUBEN (generated)", id: "1500309585536-1") {
		addColumn(tableName: "cxcabono") {
			column(name: "uso_cfdi", type: "varchar(3)")
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1500309585536-2") {
		addColumn(tableName: "cxcnota_det") {
			column(name: "clave_prod_serv", type: "varchar(255)")
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1500309585536-3") {
		addColumn(tableName: "cxcnota_det") {
			column(name: "clave_unidad_sat", type: "varchar(255)")
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1500309585536-4") {
		addColumn(tableName: "cxcnota_det") {
			column(name: "unidad_sat", type: "varchar(255)")
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1500309585536-5") {
		addColumn(tableName: "venta") {
			column(name: "uso_cfdi", type: "varchar(3)")
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1500309585536-14") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "comentario", tableName: "cxcnota_det")
	}
}
