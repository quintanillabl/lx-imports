databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1440255167612-1") {
		createTable(tableName: "alert") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "alertPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-2") {
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

	changeSet(author: "rcancino (generated)", id: "1440255167612-3") {
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

	changeSet(author: "rcancino (generated)", id: "1440255167612-4") {
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

	changeSet(author: "rcancino (generated)", id: "1440255167612-5") {
		createTable(tableName: "mensaje") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mensajePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-6") {
		createTable(tableName: "nota") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "notaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-7") {
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

	changeSet(author: "rcancino (generated)", id: "1440255167612-8") {
		createTable(tableName: "perfil_preferencias") {
			column(name: "perfil_id", type: "bigint")

			column(name: "preferencias_string", type: "varchar(255)")

			column(name: "preferencias_idx", type: "varchar(255)")

			column(name: "preferencias_elt", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-9") {
		createTable(tableName: "tarea") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tareaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-10") {
		createTable(tableName: "usuario") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "usuarioPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "apellido_materno", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "apellido_paterno", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)")

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "nombres", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "numero_de_empleado", type: "integer")

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "puesto", type: "varchar(30)")

			column(name: "sucursal", type: "varchar(20)")

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-11") {
		createTable(tableName: "usuario_role") {
			column(name: "usuario_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-12") {
		addColumn(tableName: "banco") {
			column(name: "banco_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-13") {
		addColumn(tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-14") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cancelacion_de_cargo")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-15") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(355)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-16") {
		modifyDataType(columnName: "emisor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-17") {
		modifyDataType(columnName: "receptor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-18") {
		modifyDataType(columnName: "uuid", newDataType: "varchar(300)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-19") {
		modifyDataType(columnName: "descuento", newDataType: "decimal(19,2)", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-20") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "descuento", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-21") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-22") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "cuenta_contable")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-23") {
		modifyDataType(columnName: "provisionada", newDataType: "integer", tableName: "cuenta_por_pagar")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-24") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cxcnota_det")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-25") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-26") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-27") {
		modifyDataType(columnName: "fecha", newDataType: "date", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-28") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-29") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta_det")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-30") {
		addPrimaryKey(columnNames: "usuario_id, role_id", constraintName: "usuario_rolePK", tableName: "usuario_role")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-31") {
		dropForeignKeyConstraint(baseTableName: "cxcabono",  constraintName: "FKA67508E1302DA9BC")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-32") {
		dropForeignKeyConstraint(baseTableName: "user_role",  constraintName: "FK143BF46AF5916CE6")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-33") {
		dropForeignKeyConstraint(baseTableName: "user_role",  constraintName: "FK143BF46A9ABC30C6")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-34") {
		dropForeignKeyConstraint(baseTableName: "venta",  constraintName: "FK6AE6A4C302DA9BC")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-40") {
		dropIndex(indexName: "username", tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-41") {
		createIndex(indexName: "FK_ojbiy9td4xewidk3tfiq5143v", tableName: "banco") {
			column(name: "banco_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-42") {
		createIndex(indexName: "clave_uniq_1440255166691", tableName: "banco_sat", unique: "true") {
			column(name: "clave")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-43") {
		createIndex(indexName: "FK_98j7qng4c7yv8vqqy5ft8edpr", tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-44") {
		createIndex(indexName: "codigo_uniq_1440255166721", tableName: "cuenta_sat", unique: "true") {
			column(name: "codigo")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-45") {
		createIndex(indexName: "requisicion_id_uniq_1440255166734", tableName: "pago_proveedor", unique: "true") {
			column(name: "requisicion_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-46") {
		createIndex(indexName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", tableName: "perfil") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-47") {
		createIndex(indexName: "usuario_id_uniq_1440255166736", tableName: "perfil", unique: "true") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-48") {
		createIndex(indexName: "username_uniq_1440255166751", tableName: "usuario", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-49") {
		createIndex(indexName: "FK_55sbft3wldu0yr078kdq6hwxe", tableName: "usuario_role") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-50") {
		createIndex(indexName: "FK_qpqh5on1cqa0ktsitg2vhmirv", tableName: "usuario_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-51") {
		dropColumn(columnName: "cfd_id", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-52") {
		dropColumn(columnName: "disponible", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-53") {
		dropColumn(columnName: "cfd_id", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-54") {
		dropTable(tableName: "certificado")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-55") {
		dropTable(tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-56") {
		dropTable(tableName: "folio_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-57") {
		dropTable(tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-58") {
		dropTable(tableName: "user_role")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-35") {
		addForeignKeyConstraint(baseColumnNames: "banco_sat_id", baseTableName: "banco", constraintName: "FK_ojbiy9td4xewidk3tfiq5143v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "banco_sat", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-36") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_sat_id", baseTableName: "cuenta_contable", constraintName: "FK_98j7qng4c7yv8vqqy5ft8edpr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_sat", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-37") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "perfil", constraintName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-38") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "usuario_role", constraintName: "FK_qpqh5on1cqa0ktsitg2vhmirv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1440255167612-39") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "usuario_role", constraintName: "FK_55sbft3wldu0yr078kdq6hwxe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}

	
}
