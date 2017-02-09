databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1438440044257-1") {
		createTable(tableName: "rol") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-2") {
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

	changeSet(author: "rcancino (generated)", id: "1438440044257-3") {
		createTable(tableName: "usuario_rol") {
			column(name: "usuario_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "rol_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-4") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cancelacion_de_cargo")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-5") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(355)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-6") {
		modifyDataType(columnName: "emisor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-7") {
		modifyDataType(columnName: "receptor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-8") {
		modifyDataType(columnName: "uuid", newDataType: "varchar(300)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-9") {
		modifyDataType(columnName: "descuento", newDataType: "decimal(19,2)", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-10") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "descuento", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-11") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-12") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "cuenta_contable")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-13") {
		modifyDataType(columnName: "provisionada", newDataType: "integer", tableName: "cuenta_por_pagar")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-14") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cxcnota_det")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-15") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "concepto", tableName: "movimiento_de_cuenta")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-16") {
		modifyDataType(columnName: "tipo_de_cambio", newDataType: "decimal(19,2)", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-17") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", defaultNullValue:"1.0",columnName: "tipo_de_cambio", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-18") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-19") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-20") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-21") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-22") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-23") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta_det")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-24") {
		addPrimaryKey(columnNames: "usuario_id, rol_id", constraintName: "usuario_rolPK", tableName: "usuario_rol")
	}
	
	changeSet(author: "rcancino (generated)", id: "1438440044257-25") {
		dropForeignKeyConstraint(baseTableName: "cxcabono", baseTableSchemaName: "impapx2", constraintName: "FKA67508E1302DA9BC")

		
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-26") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "impapx2", constraintName: "FK143BF46AF5916CE6")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-27") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "impapx2", constraintName: "FK143BF46A9ABC30C6")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-28") {
		dropForeignKeyConstraint(baseTableName: "venta", baseTableSchemaName: "impapx2", constraintName: "FK6AE6A4C302DA9BC")
	}
	

	changeSet(author: "rcancino (generated)", id: "1438440044257-31") {
		dropIndex(indexName: "authority_unique_1353003878645", tableName: "role")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-32") {
		dropIndex(indexName: "username_unique_1353003878706", tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-33") {
		createIndex(indexName: "requisicion_id_uniq_1438440043568", tableName: "pago_proveedor", unique: "true") {
			column(name: "requisicion_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-34") {
		createIndex(indexName: "authority_uniq_1438440043578", tableName: "rol", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-35") {
		createIndex(indexName: "username_uniq_1438440043584", tableName: "usuario", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-36") {
		createIndex(indexName: "FK_5gtipd65p6pda9ltx23lm68ge", tableName: "usuario_rol") {
			column(name: "rol_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-37") {
		createIndex(indexName: "FK_91qmacuyat735y6p88fsblnx5", tableName: "usuario_rol") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-38") {
		dropColumn(columnName: "cfd_id", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-39") {
		dropColumn(columnName: "cfd_id", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-40") {
		dropTable(tableName: "certificado")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-41") {
		dropTable(tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-42") {
		dropTable(tableName: "folio_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-43") {
		dropTable(tableName: "role")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-44") {
		dropTable(tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-45") {
		dropTable(tableName: "user_role")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-29") {
		addForeignKeyConstraint(baseColumnNames: "rol_id", baseTableName: "usuario_rol", constraintName: "FK_5gtipd65p6pda9ltx23lm68ge", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "rol", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1438440044257-30") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "usuario_rol", constraintName: "FK_91qmacuyat735y6p88fsblnx5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}
}
