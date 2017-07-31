databaseChangeLog = {

	changeSet(author: "RUBEN (generated)", id: "1501540067079-1") {
		createTable(tableName: "cargo_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cargo_detPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "cantidad", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "varchar(300)") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "varchar(200)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "numero_de_identificacion", type: "varchar(50)") {
				constraints(nullable: "false")
			}

			column(name: "unidad", type: "varchar(100)") {
				constraints(nullable: "false")
			}

			column(name: "valor_unitario", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "venta_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "conceptos_idx", type: "integer")
		}
	}
	

	changeSet(author: "RUBEN (generated)", id: "1501540067079-16") {
		createIndex(indexName: "FK_motpax7sub85hb0vnfsdy6sfa", tableName: "cargo_det") {
			column(name: "venta_id")
		}
	}


	changeSet(author: "RUBEN (generated)", id: "1501540067079-14") {
		addForeignKeyConstraint(baseColumnNames: "venta_id", baseTableName: "cargo_det", constraintName: "FK_motpax7sub85hb0vnfsdy6sfa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "venta", referencesUniqueColumn: "false")
	}
}
