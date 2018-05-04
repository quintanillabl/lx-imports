databaseChangeLog = {

	changeSet(author: "RUBEN (generated)", id: "9500315131374-1") {
		addColumn(tableName: "cancelacion_de_cfdi") {
			column(name: "origen", type: "varchar(255)")
		}
	}
	
	changeSet(author: "RUBEN (generated)", id: "9500315131374-2") {
		addColumn(tableName: "cancelacion_de_cfdi") {
			column(name: "tipo", type: "varchar(10)")
		}
	}
	
	
	
}
