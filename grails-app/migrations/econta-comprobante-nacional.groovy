databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1471612493699-1") {
		createTable(tableName: "comprobante_nacional") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "transaccion_cPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
			column(name: "rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}
			column(name: "uuidcfdi", type: "varchar(255)") {
				constraints(nullable: "false")
			}
			column(name: "moneda", type: "varchar(255)") {
				constraints(nullable: "false")
			}
			column(name: "tip_camb", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "monto_total", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "poliza_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1471612493699-4") {
		createIndex(indexName: "FK_pwuwxq0ey6htrnrfxswrsprcs", tableName: "comprobante_nacional") {
			column(name: "poliza_det_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1471612493699-3") {
		addForeignKeyConstraint(baseColumnNames: "poliza_det_id", baseTableName: "comprobante_nacional", constraintName: "FK_pwuwxq0ey6htrnrfxswrsprcs", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "poliza_det", referencesUniqueColumn: "false")
	}
}
