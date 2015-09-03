databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1441291324756-1") {
		createTable(tableName: "alert") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "alertPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-2") {
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

	changeSet(author: "rcancino (generated)", id: "1441291324756-3") {
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

	changeSet(author: "rcancino (generated)", id: "1441291324756-4") {
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

	changeSet(author: "rcancino (generated)", id: "1441291324756-5") {
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

	changeSet(author: "rcancino (generated)", id: "1441291324756-6") {
		createTable(tableName: "mensaje") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mensajePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-7") {
		createTable(tableName: "nota") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "notaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-8") {
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

	changeSet(author: "rcancino (generated)", id: "1441291324756-9") {
		createTable(tableName: "perfil_preferencias") {
			column(name: "perfil_id", type: "bigint")

			column(name: "preferencias_string", type: "varchar(255)")

			column(name: "preferencias_idx", type: "varchar(255)")

			column(name: "preferencias_elt", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-10") {
		createTable(tableName: "tarea") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tareaPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-11") {
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

	changeSet(author: "rcancino (generated)", id: "1441291324756-12") {
		createTable(tableName: "usuario_role") {
			column(name: "usuario_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-13") {
		addColumn(tableName: "banco") {
			column(name: "banco_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-14") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "acuse", type: "mediumblob")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-15") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "acuse_codigo_estatus", type: "varchar(100)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-16") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "acuse_estado", type: "varchar(100)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-17") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "cfdi", type: "mediumblob") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-18") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "cfdi_file_name", type: "varchar(200)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-19") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "cxp_id", type: "bigint") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-20") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "emisor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-21") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "receptor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-22") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "uuid", type: "varchar(40)") {
				constraints(nullable: "false", unique: "true")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-23") {
		addColumn(tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id", type: "bigint")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-24") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cancelacion_de_cargo")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-25") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(355)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-26") {
		modifyDataType(columnName: "emisor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-27") {
		modifyDataType(columnName: "receptor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-28") {
		modifyDataType(columnName: "uuid", newDataType: "varchar(300)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-29") {
		modifyDataType(columnName: "descuento", newDataType: "decimal(19,2)", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-30") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "descuento", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-31") {
		modifyDataType(columnName: "folio", newDataType: "varchar(20)", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-32") {
		dropNotNullConstraint(columnDataType: "varchar(20)", columnName: "folio", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-33") {
		modifyDataType(columnName: "total", newDataType: "decimal(19,2)", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-34") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "total", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-35") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-36") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "cuenta_contable")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-37") {
		modifyDataType(columnName: "provisionada", newDataType: "integer", tableName: "cuenta_por_pagar")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-38") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cxcnota_det")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-39") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "concepto", tableName: "movimiento_de_cuenta")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-40") {
		modifyDataType(columnName: "tipo_de_cambio", newDataType: "decimal(19,2)", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-41") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "tipo_de_cambio", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-42") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-43") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-44") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-45") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-46") {
		modifyDataType(columnName: "fecha", newDataType: "date", tableName: "tipo_de_cambio")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-47") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-48") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta_det")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-49") {
		addPrimaryKey(columnNames: "usuario_id, role_id", constraintName: "usuario_rolePK", tableName: "usuario_role")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-50") {
		dropForeignKeyConstraint(baseTableName: "cxcabono", baseTableSchemaName: "impapx2", constraintName: "FKA67508E1302DA9BC")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-51") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "impapx2", constraintName: "FK143BF46AF5916CE6")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-52") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "impapx2", constraintName: "FK143BF46A9ABC30C6")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-53") {
		dropForeignKeyConstraint(baseTableName: "venta", baseTableSchemaName: "impapx2", constraintName: "FK6AE6A4C302DA9BC")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-61") {
		dropIndex(indexName: "username_unique_1353003878706", tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-62") {
		createIndex(indexName: "FK_ojbiy9td4xewidk3tfiq5143v", tableName: "banco") {
			column(name: "banco_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-63") {
		createIndex(indexName: "clave_uniq_1441291323835", tableName: "banco_sat", unique: "true") {
			column(name: "clave")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-64") {
		createIndex(indexName: "FK_alib7rwpbtqfnrrkov7gg4rab", tableName: "cancelacion_de_cfdi") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-65") {
		createIndex(indexName: "cfdi_id_uniq_1441291323837", tableName: "cancelacion_de_cfdi", unique: "true") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-66") {
		createIndex(indexName: "FK_p7tcmwt402tlxp776j80p2qb", tableName: "comprobante_fiscal") {
			column(name: "cxp_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-67") {
		createIndex(indexName: "cxp_id_uniq_1441291323854", tableName: "comprobante_fiscal", unique: "true") {
			column(name: "cxp_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-68") {
		createIndex(indexName: "uuid_uniq_1441291323855", tableName: "comprobante_fiscal", unique: "true") {
			column(name: "uuid")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-69") {
		createIndex(indexName: "FK_98j7qng4c7yv8vqqy5ft8edpr", tableName: "cuenta_contable") {
			column(name: "cuenta_sat_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-70") {
		createIndex(indexName: "codigo_uniq_1441291323866", tableName: "cuenta_sat", unique: "true") {
			column(name: "codigo")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-71") {
		createIndex(indexName: "requisicion_id_uniq_1441291323880", tableName: "pago_proveedor", unique: "true") {
			column(name: "requisicion_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-72") {
		createIndex(indexName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", tableName: "perfil") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-73") {
		createIndex(indexName: "usuario_id_uniq_1441291323882", tableName: "perfil", unique: "true") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-74") {
		createIndex(indexName: "username_uniq_1441291323898", tableName: "usuario", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-75") {
		createIndex(indexName: "FK_55sbft3wldu0yr078kdq6hwxe", tableName: "usuario_role") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-76") {
		createIndex(indexName: "FK_qpqh5on1cqa0ktsitg2vhmirv", tableName: "usuario_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-77") {
		dropColumn(columnName: "entregado", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-78") {
		dropColumn(columnName: "aduana", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-79") {
		dropColumn(columnName: "ano_aprobacion", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-80") {
		dropColumn(columnName: "comentario", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-81") {
		dropColumn(columnName: "estado", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-82") {
		dropColumn(columnName: "fecha", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-83") {
		dropColumn(columnName: "fecha_pedimento", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-84") {
		dropColumn(columnName: "impuesto", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-85") {
		dropColumn(columnName: "numero_de_aprobacion", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-86") {
		dropColumn(columnName: "numero_de_certificado", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-87") {
		dropColumn(columnName: "origen", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-88") {
		dropColumn(columnName: "pedimento", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-89") {
		dropColumn(columnName: "rfc", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-90") {
		dropColumn(columnName: "tipo", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-91") {
		dropColumn(columnName: "tipo_cfd", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-92") {
		dropColumn(columnName: "xml_path", tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-93") {
		dropColumn(columnName: "cfd_id", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-94") {
		dropColumn(columnName: "disponible", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-95") {
		dropColumn(columnName: "cfd_id", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-96") {
		dropTable(tableName: "certificado")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-97") {
		dropTable(tableName: "folio_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-98") {
		dropTable(tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-99") {
		dropTable(tableName: "user_role")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-54") {
		addForeignKeyConstraint(baseColumnNames: "banco_sat_id", baseTableName: "banco", constraintName: "FK_ojbiy9td4xewidk3tfiq5143v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "banco_sat", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-55") {
		addForeignKeyConstraint(baseColumnNames: "cfdi_id", baseTableName: "cancelacion_de_cfdi", constraintName: "FK_alib7rwpbtqfnrrkov7gg4rab", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cfdi", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-56") {
		addForeignKeyConstraint(baseColumnNames: "cxp_id", baseTableName: "comprobante_fiscal", constraintName: "FK_p7tcmwt402tlxp776j80p2qb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-57") {
		addForeignKeyConstraint(baseColumnNames: "cuenta_sat_id", baseTableName: "cuenta_contable", constraintName: "FK_98j7qng4c7yv8vqqy5ft8edpr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_sat", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-58") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "perfil", constraintName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-59") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "usuario_role", constraintName: "FK_qpqh5on1cqa0ktsitg2vhmirv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1441291324756-60") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "usuario_role", constraintName: "FK_55sbft3wldu0yr078kdq6hwxe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}
}
