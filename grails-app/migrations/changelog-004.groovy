databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1438442357109-1") {
		createTable(tableName: "banco_sat") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "banco_satPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "clave", type: "varchar(20)") {
				constraints(nullable: "false")
			}

			column(name: "nombre_corto", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "razon_social", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438442357109-2") {
		createTable(tableName: "cuenta_sat") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cuenta_satPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "codigo", type: "varchar(20)") {
				constraints(nullable: "false")
			}

			column(name: "nivel", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "tipo", type: "varchar(100)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438442357109-3") {
		addColumn(tableName: "banco") {
			column(name: "banco_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438442357109-5") {
		createIndex(indexName: "FK_ojbiy9td4xewidk3tfiq5143v", tableName: "banco") {
			column(name: "banco_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438442357109-6") {
		createIndex(indexName: "clave_uniq_1438442356362", tableName: "banco_sat", unique: "true") {
			column(name: "clave")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438442357109-7") {
		createIndex(indexName: "codigo_uniq_1438442356392", tableName: "cuenta_sat", unique: "true") {
			column(name: "codigo")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438442357109-4") {
		addForeignKeyConstraint(baseColumnNames: "banco_sat_id", baseTableName: "banco", constraintName: "FK_ojbiy9td4xewidk3tfiq5143v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "banco_sat", referencesUniqueColumn: "false")
	}
}
