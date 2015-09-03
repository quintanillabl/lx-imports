databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1441301802590-1") {
		createTable(tableName: "alert") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "alertPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-2") {
		createTable(tableName: "audit_log") {
			column(name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "audit_logPK")
			}

			column(name: "actor", type: "varchar(255)")

			column(name: "class_name", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "event_name", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "new_value", type: "varchar(255)")

			column(name: "old_value", type: "varchar(255)")

			column(name: "persisted_object_id", type: "varchar(255)")

			column(name: "persisted_object_version", type: "bigint")

			column(name: "property_name", type: "varchar(255)")

			column(name: "uri", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-3") {
		createTable(tableName: "cancelacion_de_cfdi") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cancelacion_dPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "aka", type: "mediumblob") {
				constraints(nullable: "false")
			}

			column(name: "cfdi_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-4") {
		createTable(tableName: "comprobante_fiscal") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "comprobante_fPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "acuse", type: "mediumblob")

			column(name: "acuse_codigo_estatus", type: "varchar(100)")

			column(name: "acuse_estado", type: "varchar(100)")

			column(name: "cfdi", type: "mediumblob") {
				constraints(nullable: "false")
			}

			column(name: "cfdi_file_name", type: "varchar(200)")

			column(name: "cxp_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "emisor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "folio", type: "varchar(20)")

			column(name: "receptor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "serie", type: "varchar(20)")

			column(name: "total", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}

			column(name: "uuid", type: "varchar(40)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-5") {
		createTable(tableName: "mensaje") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mensajePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-6") {
		createTable(tableName: "nota") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "notaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-7") {
		createTable(tableName: "perfil") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "perfilPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "celular", type: "varchar(255)")

			column(name: "dash_inicial", type: "varchar(60)")

			column(name: "email", type: "varchar(255)")

			column(name: "foto", type: "mediumblob")

			column(name: "google", type: "varchar(255)")

			column(name: "telefono_casa", type: "varchar(255)")

			column(name: "twitter", type: "varchar(255)")

			column(name: "usuario_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-8") {
		createTable(tableName: "perfil_preferencias") {
			column(name: "perfil_id", type: "bigint")

			column(name: "preferencias_string", type: "varchar(255)")

			column(name: "preferencias_idx", type: "varchar(255)")

			column(name: "preferencias_elt", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-9") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-10") {
		createTable(tableName: "tarea") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tareaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-11") {
		createTable(tableName: "usuario_role") {
			column(name: "usuario_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-12") {
		addColumn(tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-13") {
		modifyDataType(columnName: "fecha", newDataType: "date", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-14") {
		addNotNullConstraint(columnDataType: "date", columnName: "fecha", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-15") {
		addPrimaryKey(columnNames: "usuario_id, role_id", constraintName: "usuario_rolePK", tableName: "usuario_role")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-16") {
		dropForeignKeyConstraint(baseTableName: "usuario_rol", baseTableSchemaName: "impapx2", constraintName: "FK_5gtipd65p6pda9ltx23lm68ge")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-17") {
		dropForeignKeyConstraint(baseTableName: "usuario_rol", baseTableSchemaName: "impapx2", constraintName: "FK_91qmacuyat735y6p88fsblnx5")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-24") {
		dropIndex(indexName: "authority_uniq_1438440043578", tableName: "rol")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-25") {
		createIndex(indexName: "FK_alib7rwpbtqfnrrkov7gg4rab", tableName: "cancelacion_de_cfdi") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-26") {
		createIndex(indexName: "cfdi_id_uniq_1441301801747", tableName: "cancelacion_de_cfdi", unique: "true") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-27") {
		createIndex(indexName: "FK_p7tcmwt402tlxp776j80p2qb", tableName: "comprobante_fiscal") {
			column(name: "cxp_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-28") {
		createIndex(indexName: "cxp_id_uniq_1441301801783", tableName: "comprobante_fiscal", unique: "true") {
			column(name: "cxp_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-29") {
		createIndex(indexName: "uuid_uniq_1441301801784", tableName: "comprobante_fiscal", unique: "true") {
			column(name: "uuid")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-30") {
		createIndex(indexName: "FK_98j7qng4c7yv8vqqy5ft8edpr", tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-31") {
		createIndex(indexName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", tableName: "perfil") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-32") {
		createIndex(indexName: "usuario_id_uniq_1441301801809", tableName: "perfil", unique: "true") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-33") {
		createIndex(indexName: "authority_uniq_1441301801820", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-34") {
		createIndex(indexName: "FK_55sbft3wldu0yr078kdq6hwxe", tableName: "usuario_role") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-35") {
		createIndex(indexName: "FK_qpqh5on1cqa0ktsitg2vhmirv", tableName: "usuario_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-36") {
		dropColumn(columnName: "entregado", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-37") {
		dropColumn(columnName: "disponible", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-38") {
		dropTable(tableName: "rol")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-39") {
		dropTable(tableName: "usuario_rol")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-18") {
		addForeignKeyConstraint(baseColumnNames: "cfdi_id", baseTableName: "cancelacion_de_cfdi", constraintName: "FK_alib7rwpbtqfnrrkov7gg4rab", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cfdi", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-19") {
		addForeignKeyConstraint(baseColumnNames: "cxp_id", baseTableName: "comprobante_fiscal", constraintName: "FK_p7tcmwt402tlxp776j80p2qb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-20") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_sat_id", baseTableName: "cuenta_contable", constraintName: "FK_98j7qng4c7yv8vqqy5ft8edpr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_sat", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-21") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "perfil", constraintName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-22") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "usuario_role", constraintName: "FK_qpqh5on1cqa0ktsitg2vhmirv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441301802590-23") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "usuario_role", constraintName: "FK_55sbft3wldu0yr078kdq6hwxe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}
}
