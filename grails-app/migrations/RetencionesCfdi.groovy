databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1443792772136-1") {
		createTable(tableName: "cfdi_retenciones") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cfdi_retencioPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "ejercicio", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "emisor", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "emisor_curp", type: "varchar(255)")

			column(name: "emisor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "fecha_de_timbrado", type: "varchar(50)")

			// column(name: "folio", type: "varchar(255)") {
			// 	constraints(nullable: "false")
			// }

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "mes_final", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "mes_inicial", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "receptor", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "receptor_curp", type: "varchar(50)")

			column(name: "receptor_nacionalidad", type: "varchar(10)") {
				constraints(nullable: "false")
			}

			column(name: "receptor_registro_tributario", type: "varchar(20)")

			column(name: "receptor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "retencion_descripcion", type: "varchar(255)")

			column(name: "tipo_de_retencion_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "total", type: "decimal(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "total_excento", type: "decimal(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "total_gravado", type: "decimal(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "uuid", type: "varchar(50)")

			column(name: "xml", type: "mediumblob")

			column(name: "xml_name", type: "varchar(200)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-2") {
		createTable(tableName: "impuesto_retenido") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "impuesto_retePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "base_ret", type: "decimal(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "impuesto_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "monto_ret", type: "decimal(19,6)") {
				constraints(nullable: "false")
			}

			column(name: "retencion_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "tipo_pago_ret", type: "varchar(16)") {
				constraints(nullable: "false")
			}

			column(name: "impuestos_retenidos_idx", type: "integer")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-3") {
		createTable(tableName: "tipo_de_impuesto") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tipo_de_impuePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "clave", type: "varchar(2)") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-4") {
		createTable(tableName: "tipo_de_retencion") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tipo_de_retenPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "clave", type: "varchar(2)") {
				constraints(nullable: "false")
			}

			column(name: "descripcion", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-8") {
		createIndex(indexName: "FK_d7k4p5vcg1ll4j0m6xna9qeyx", tableName: "cfdi_retenciones") {
			column(name: "tipo_de_retencion_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-9") {
		createIndex(indexName: "FK_hgw1nybh6trrxffd8kmwchoa9", tableName: "impuesto_retenido") {
			column(name: "impuesto_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-10") {
		createIndex(indexName: "FK_mygrwmt6rev7scln8nvkedbee", tableName: "impuesto_retenido") {
			column(name: "retencion_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-11") {
		createIndex(indexName: "clave_uniq_1443792771510", tableName: "tipo_de_impuesto", unique: "true") {
			column(name: "clave")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-12") {
		createIndex(indexName: "clave_uniq_1443792771511", tableName: "tipo_de_retencion", unique: "true") {
			column(name: "clave")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-5") {
		addForeignKeyConstraint(baseColumnNames: "tipo_de_retencion_id", baseTableName: "cfdi_retenciones", constraintName: "FK_d7k4p5vcg1ll4j0m6xna9qeyx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tipo_de_retencion", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-6") {
		addForeignKeyConstraint(baseColumnNames: "impuesto_id", baseTableName: "impuesto_retenido", constraintName: "FK_hgw1nybh6trrxffd8kmwchoa9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tipo_de_impuesto", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1443792772136-7") {
		addForeignKeyConstraint(baseColumnNames: "retencion_id", baseTableName: "impuesto_retenido", constraintName: "FK_mygrwmt6rev7scln8nvkedbee", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cfdi_retenciones", referencesUniqueColumn: "false")
	}

	
}
