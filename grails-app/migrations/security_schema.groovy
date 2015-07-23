databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1437057552947-1") {
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

	changeSet(author: "rcancino (generated)", id: "1437057552947-2") {
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

	changeSet(author: "rcancino (generated)", id: "1437057552947-3") {
		createTable(tableName: "usuario_rol") {
			column(name: "usuario_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "rol_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-4") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cancelacion_de_cargo")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-5") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(355)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-6") {
		modifyDataType(columnName: "emisor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-7") {
		modifyDataType(columnName: "receptor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-8") {
		modifyDataType(columnName: "uuid", newDataType: "varchar(300)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-9") {
		modifyDataType(columnName: "descuento", newDataType: "decimal(19,2)", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-10") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "descuento", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-11") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-12") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "cuenta_contable")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-13") {
		modifyDataType(columnName: "provisionada", newDataType: "integer", tableName: "cuenta_por_pagar")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-14") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cxcnota_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-15") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "concepto", tableName: "movimiento_de_cuenta")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-16") {
		modifyDataType(columnName: "tipo_de_cambio", newDataType: "decimal(19,2)", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-17") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "tipo_de_cambio", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-18") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-19") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-20") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-21") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-22") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-23") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-24") {
		addPrimaryKey(columnNames: "usuario_id, rol_id", constraintName: "usuario_rolPK", tableName: "usuario_rol")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-25") {
		dropForeignKeyConstraint(baseTableName: "cxcabono", baseTableSchemaName: "lx_imports", constraintName: "FKA67508E1302DA9BC")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-26") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "lx_imports", constraintName: "FK143BF46AF5916CE6")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-27") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "lx_imports", constraintName: "FK143BF46A9ABC30C6")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-28") {
		dropForeignKeyConstraint(baseTableName: "venta", baseTableSchemaName: "lx_imports", constraintName: "FK6AE6A4C302DA9BC")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-31") {
		dropIndex(indexName: "authority_unique_1353003878645", tableName: "role")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-32") {
		dropIndex(indexName: "username_unique_1353003878706", tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-33") {
		createIndex(indexName: "authority_uniq_1437057551899", tableName: "rol", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-34") {
		createIndex(indexName: "username_uniq_1437057551907", tableName: "usuario", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-35") {
		createIndex(indexName: "FK_5gtipd65p6pda9ltx23lm68ge", tableName: "usuario_rol") {
			column(name: "rol_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-36") {
		createIndex(indexName: "FK_91qmacuyat735y6p88fsblnx5", tableName: "usuario_rol") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-37") {
		dropColumn(columnName: "cfd_id", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-38") {
		dropColumn(columnName: "cfd_id", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-39") {
		dropTable(tableName: "certificado")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-40") {
		dropTable(tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-41") {
		dropTable(tableName: "folio_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-42") {
		dropTable(tableName: "role")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-43") {
		dropTable(tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-44") {
		dropTable(tableName: "user_role")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-29") {
		addForeignKeyConstraint(baseColumnNames: "rol_id", baseTableName: "usuario_rol", constraintName: "FK_5gtipd65p6pda9ltx23lm68ge", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "rol", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1437057552947-30") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "usuario_rol", constraintName: "FK_91qmacuyat735y6p88fsblnx5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}
}
