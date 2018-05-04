databaseChangeLog = {

	changeSet(author: "RUBEN (generated)", id: "1499969711638-1") {
		createTable(tableName: "producto_sat") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "producto_satPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "clave_prod_serv", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1499969711638-2") {
		addColumn(tableName: "producto") {
			column(name: "producto_sat_id", type: "bigint")
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1499969711638-3") {
		addColumn(tableName: "unidad") {
			column(name: "clave_unidad_sat", type: "varchar(255)")
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1499969711638-4") {
		addColumn(tableName: "unidad") {
			column(name: "unidad_sat", type: "varchar(255)")
		}
	}

	
	changeSet(author: "RUBEN (generated)", id: "1499969711638-21") {
		createIndex(indexName: "FK_jbougbbxgvxku68k238dyq8cs", tableName: "producto") {
			column(name: "producto_sat_id")
		}
	}

	changeSet(author: "RUBEN (generated)", id: "1499969711638-22") {
		createIndex(indexName: "clave_prod_serv_uniq_1499969706027", tableName: "producto_sat", unique: "true") {
			column(name: "clave_prod_serv")
		}
	}

	
	changeSet(author: "RUBEN (generated)", id: "1499969711638-25") {
		dropColumn(columnName: "clave_prod_serv", tableName: "producto")
	}

	changeSet(author: "RUBEN (generated)", id: "1499969711638-26") {
		dropColumn(columnName: "clave_unidad_sat", tableName: "producto")
	}

	changeSet(author: "RUBEN (generated)", id: "1499969711638-27") {
		dropColumn(columnName: "unidad_sat", tableName: "producto")
	}

	changeSet(author: "RUBEN (generated)", id: "1499969711638-17") {
		addForeignKeyConstraint(baseColumnNames: "producto_sat_id", baseTableName: "producto", constraintName: "FK_jbougbbxgvxku68k238dyq8cs", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "producto_sat", referencesUniqueColumn: "false")
	}
	
}
