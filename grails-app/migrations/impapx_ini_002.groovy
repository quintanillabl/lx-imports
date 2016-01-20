databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1453302056503-1") {
		createTable(tableName: "alert") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "alertPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-2") {
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

	changeSet(author: "rcancino (generated)", id: "1453302056503-3") {
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

	changeSet(author: "rcancino (generated)", id: "1453302056503-4") {
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

	changeSet(author: "rcancino (generated)", id: "1453302056503-5") {
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

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "emisor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "fecha", type: "date") {
				constraints(nullable: "false")
			}

			column(name: "folio", type: "varchar(20)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

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

	changeSet(author: "rcancino (generated)", id: "1453302056503-6") {
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

	changeSet(author: "rcancino (generated)", id: "1453302056503-7") {
		createTable(tableName: "mensaje") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mensajePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-8") {
		createTable(tableName: "nota") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "notaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-9") {
		createTable(tableName: "pais_de_origen") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pais_de_origePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-10") {
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

	changeSet(author: "rcancino (generated)", id: "1453302056503-11") {
		createTable(tableName: "perfil_preferencias") {
			column(name: "perfil_id", type: "bigint")

			column(name: "preferencias_string", type: "varchar(255)")

			column(name: "preferencias_idx", type: "varchar(255)")

			column(name: "preferencias_elt", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-12") {
		createTable(tableName: "proveedor_agentes") {
			column(name: "proveedor_id", type: "bigint")

			column(name: "agentes_string", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-13") {
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

	changeSet(author: "rcancino (generated)", id: "1453302056503-14") {
		createTable(tableName: "tarea") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tareaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-15") {
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

	changeSet(author: "rcancino (generated)", id: "1453302056503-16") {
		createTable(tableName: "usuario_role") {
			column(name: "usuario_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-17") {
		addColumn(tableName: "banco") {
			column(name: "banco_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-18") {
		addColumn(tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-19") {
		addColumn(tableName: "pedimento") {
			column(name: "agente_aduanal", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-20") {
		addColumn(tableName: "pedimento") {
			column(name: "contra_prestacion", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-21") {
		addColumn(tableName: "pedimento") {
			column(name: "incrementables", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-22") {
		addColumn(tableName: "pedimento") {
			column(name: "pais_de_origen_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-23") {
		addColumn(tableName: "proveedor") {
			column(name: "agencia_aduanal", type: "bit") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-24") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cancelacion_de_cargo")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-25") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(355)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-26") {
		modifyDataType(columnName: "emisor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-27") {
		modifyDataType(columnName: "receptor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-28") {
		modifyDataType(columnName: "uuid", newDataType: "varchar(300)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-29") {
		modifyDataType(columnName: "descuento", newDataType: "decimal(19,2)", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-30") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "descuento", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-31") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-32") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "cuenta_contable")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-33") {
		modifyDataType(columnName: "provisionada", newDataType: "integer", tableName: "cuenta_por_pagar")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-34") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cxcnota_det")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-35") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "concepto", tableName: "movimiento_de_cuenta")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-36") {
		modifyDataType(columnName: "tipo_de_cambio", newDataType: "decimal(19,2)", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-37") {
		grailsChange {
            change {
                sql.execute("UPDATE pago_proveedor SET tipo_de_cambio = 1.0")
            }
            rollback {
            }
        }
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "tipo_de_cambio", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-38") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-39") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-40") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-41") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-42") {
		modifyDataType(columnName: "fecha", newDataType: "date", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-43") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-44") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta_det")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-45") {
		addPrimaryKey(columnNames: "usuario_id, role_id", constraintName: "usuario_rolePK", tableName: "usuario_role")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-55") {
		dropIndex(indexName: "FKA67508E1302DA9BC", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-56") {
		dropIndex(indexName: "FK6AE6A4C302DA9BC", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-57") {
		createIndex(indexName: "pago_proveedor_id_uniq_1453302055794", tableName: "abono", unique: "true") {
			column(name: "pago_proveedor_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-58") {
		createIndex(indexName: "FK_ojbiy9td4xewidk3tfiq5143v", tableName: "banco") {
			column(name: "banco_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-59") {
		createIndex(indexName: "clave_uniq_1453302055803", tableName: "banco_sat", unique: "true") {
			column(name: "clave")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-60") {
		createIndex(indexName: "FK_alib7rwpbtqfnrrkov7gg4rab", tableName: "cancelacion_de_cfdi") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-61") {
		createIndex(indexName: "cfdi_id_uniq_1453302055805", tableName: "cancelacion_de_cfdi", unique: "true") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-62") {
		createIndex(indexName: "FK_p7tcmwt402tlxp776j80p2qb", tableName: "comprobante_fiscal") {
			column(name: "cxp_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-63") {
		createIndex(indexName: "cxp_id_uniq_1453302055826", tableName: "comprobante_fiscal", unique: "true") {
			column(name: "cxp_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-64") {
		createIndex(indexName: "uuid_uniq_1453302055827", tableName: "comprobante_fiscal", unique: "true") {
			column(name: "uuid")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-65") {
		createIndex(indexName: "FK_98j7qng4c7yv8vqqy5ft8edpr", tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-66") {
		createIndex(indexName: "codigo_uniq_1453302055839", tableName: "cuenta_sat", unique: "true") {
			column(name: "codigo")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-67") {
		createIndex(indexName: "requisicion_id_uniq_1453302055853", tableName: "pago_proveedor", unique: "true") {
			column(name: "requisicion_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-68") {
		createIndex(indexName: "nombre_uniq_1453302055854", tableName: "pais_de_origen", unique: "true") {
			column(name: "nombre")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-69") {
		createIndex(indexName: "FK_si62rf29l2tvyxvcntsg0svbe", tableName: "pedimento") {
			column(name: "pais_de_origen_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-70") {
		createIndex(indexName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", tableName: "perfil") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-71") {
		createIndex(indexName: "usuario_id_uniq_1453302055857", tableName: "perfil", unique: "true") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-72") {
		createIndex(indexName: "FK_sy2xb512760vlw7sr1e6fjcnt", tableName: "proveedor_agentes") {
			column(name: "proveedor_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-73") {
		createIndex(indexName: "authority_uniq_1453302055868", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-74") {
		createIndex(indexName: "username_uniq_1453302055875", tableName: "usuario", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-75") {
		createIndex(indexName: "FK_55sbft3wldu0yr078kdq6hwxe", tableName: "usuario_role") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-76") {
		createIndex(indexName: "FK_qpqh5on1cqa0ktsitg2vhmirv", tableName: "usuario_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-77") {
		dropColumn(columnName: "entregado", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-78") {
		dropColumn(columnName: "cfd_id", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-79") {
		dropColumn(columnName: "disponible", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-80") {
		dropColumn(columnName: "cfd_id", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-81") {
		dropTable(tableName: "certificado")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-82") {
		dropTable(tableName: "folio_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-46") {
		addForeignKeyConstraint(baseColumnNames: "banco_sat_id", baseTableName: "banco", constraintName: "FK_ojbiy9td4xewidk3tfiq5143v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "banco_sat", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-47") {
		addForeignKeyConstraint(baseColumnNames: "cfdi_id", baseTableName: "cancelacion_de_cfdi", constraintName: "FK_alib7rwpbtqfnrrkov7gg4rab", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cfdi", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-48") {
		addForeignKeyConstraint(baseColumnNames: "cxp_id", baseTableName: "comprobante_fiscal", constraintName: "FK_p7tcmwt402tlxp776j80p2qb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-49") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_sat_id", baseTableName: "cuenta_contable", constraintName: "FK_98j7qng4c7yv8vqqy5ft8edpr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_sat", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-50") {
		addForeignKeyConstraint(baseColumnNames: "pais_de_origen_id", baseTableName: "pedimento", constraintName: "FK_si62rf29l2tvyxvcntsg0svbe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "pais_de_origen", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-51") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "perfil", constraintName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-52") {
		addForeignKeyConstraint(baseColumnNames: "proveedor_id", baseTableName: "proveedor_agentes", constraintName: "FK_sy2xb512760vlw7sr1e6fjcnt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "proveedor", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-53") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "usuario_role", constraintName: "FK_qpqh5on1cqa0ktsitg2vhmirv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1453302056503-54") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "usuario_role", constraintName: "FK_55sbft3wldu0yr078kdq6hwxe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}
}
