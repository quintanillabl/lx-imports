databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1501609331525-1") {
		createTable(tableName: "cargo_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cargo_detPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "atraso", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "cantidad", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "cfdi_id", type: "bigint")

			column(name: "clave_prod_serv", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "varchar(300)") {
				constraints(nullable: "false")
			}

			column(name: "corte", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "varchar(200)") {
				constraints(nullable: "false")
			}

			column(name: "dias_pena", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "documento", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "importe", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "mismo_mes", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "numero_de_identificacion", type: "varchar(50)") {
				constraints(nullable: "false")
			}

			column(name: "pena_por_dia", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "saldo", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "tasa_cetes", type: "decimal(19,2)") {
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

			column(name: "vto", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "conceptos_idx", type: "integer")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-2") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "comentario", tableName: "cancelacion_de_cargo")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-3") {
		modifyDataType(columnName: "emisor", newDataType: "varchar(255)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-4") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "emisor", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-5") {
		modifyDataType(columnName: "receptor", newDataType: "varchar(255)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-6") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "receptor", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-7") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(500)", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-8") {
		addNotNullConstraint(columnDataType: "varchar(500)", columnName: "descripcion", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-9") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "descripcion", tableName: "cuenta_contable")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-10") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "comentario", tableName: "cxcnota_det")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-11") {
		modifyDataType(columnName: "fecha", newDataType: "date", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-12") {
		addNotNullConstraint(columnDataType: "date", columnName: "fecha", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-13") {
		addNotNullConstraint(columnDataType: "varchar(300)", columnName: "comentario", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-16") {
		createIndex(indexName: "pago_proveedor_id_uniq_1501609330886", tableName: "abono", unique: "true") {
			column(name: "pago_proveedor_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-17") {
		createIndex(indexName: "FK_9h1bdkp3m9pi8uecyff01f1k2", tableName: "cargo_det") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-18") {
		createIndex(indexName: "FK_motpax7sub85hb0vnfsdy6sfa", tableName: "cargo_det") {
			column(name: "venta_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-19") {
		createIndex(indexName: "poliza_det_id_uniq_1501609330907", tableName: "comprobante_extranjero", unique: "true") {
			column(name: "poliza_det_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-20") {
		createIndex(indexName: "poliza_det_id_uniq_1501609330908", tableName: "comprobante_nacional", unique: "true") {
			column(name: "poliza_det_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-21") {
		createIndex(indexName: "poliza_det_id_uniq_1501609330936", tableName: "transaccion_cheque", unique: "true") {
			column(name: "poliza_det_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-22") {
		createIndex(indexName: "poliza_det_id_uniq_1501609330936", tableName: "transaccion_transferencia", unique: "true") {
			column(name: "poliza_det_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-14") {
		addForeignKeyConstraint(baseColumnNames: "cfdi_id", baseTableName: "cargo_det", constraintName: "FK_9h1bdkp3m9pi8uecyff01f1k2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cfdi", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1501609331525-15") {
		addForeignKeyConstraint(baseColumnNames: "venta_id", baseTableName: "cargo_det", constraintName: "FK_motpax7sub85hb0vnfsdy6sfa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "venta", referencesUniqueColumn: "false")
	}
}
