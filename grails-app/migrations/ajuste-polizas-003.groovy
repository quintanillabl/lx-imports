databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1453928104915-1") {
		addColumn(tableName: "procesador_de_poliza") {
			column(name: "sub_tipo", type: "varchar(50)") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453928104915-9") {
		dropIndex(indexName: "nombre_uniq_1453925795776", tableName: "procesador_de_poliza")
	}

	
	changeSet(author: "rcancino (generated)", id: "1453928104915-11") {
		createIndex(indexName: "sub_tipo_uniq_1453928104237", tableName: "procesador_de_poliza", unique: "true") {
			column(name: "sub_tipo")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453928104915-13") {
		dropColumn(columnName: "nombre", tableName: "procesador_de_poliza")
	}
}
