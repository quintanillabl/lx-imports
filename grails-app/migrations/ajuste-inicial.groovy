databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1437935325815-1") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cancelacion_de_cargo")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-2") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(355)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-3") {
		modifyDataType(columnName: "emisor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-4") {
		modifyDataType(columnName: "receptor", newDataType: "varchar(600)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-5") {
		modifyDataType(columnName: "uuid", newDataType: "varchar(300)", tableName: "cfdi")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-6") {
		modifyDataType(columnName: "descuento", newDataType: "decimal(19,2)", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-7") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "descuento", tableName: "compra_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-8") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "concepto_de_gasto")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-9") {
		modifyDataType(columnName: "descripcion", newDataType: "varchar(300)", tableName: "cuenta_contable")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-10") {
		modifyDataType(columnName: "provisionada", newDataType: "integer", tableName: "cuenta_por_pagar")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-11") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "cxcnota_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-12") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "concepto", tableName: "movimiento_de_cuenta")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-13") {
		modifyDataType(columnName: "tipo_de_cambio", newDataType: "decimal(19,2)", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-14") {
		addNotNullConstraint(columnDataType: "decimal(19,2)", columnName: "tipo_de_cambio", tableName: "pago_proveedor")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-15") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-16") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-17") {
		modifyDataType(columnName: "tipo", newDataType: "varchar(12)", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-18") {
		addNotNullConstraint(columnDataType: "varchar(12)", columnName: "tipo", tableName: "poliza_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-19") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-20") {
		modifyDataType(columnName: "comentario", newDataType: "varchar(300)", tableName: "venta_det")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-21") {
		dropForeignKeyConstraint(baseTableName: "cxcabono", baseTableSchemaName: "lx_imports", constraintName: "FKA67508E1302DA9BC")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-22") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "lx_imports", constraintName: "FK143BF46AF5916CE6")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-23") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "lx_imports", constraintName: "FK143BF46A9ABC30C6")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-24") {
		dropForeignKeyConstraint(baseTableName: "venta", baseTableSchemaName: "lx_imports", constraintName: "FK6AE6A4C302DA9BC")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-25") {
		dropIndex(indexName: "authority_unique_1353003878645", tableName: "role")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-26") {
		dropIndex(indexName: "username_unique_1353003878706", tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-27") {
		createIndex(indexName: "FK_91qmacuyat735y6p88fsblnx5", tableName: "usuario_rol") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-28") {
		dropColumn(columnName: "cfd_id", tableName: "cxcabono")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-29") {
		dropColumn(columnName: "cfd_id", tableName: "venta")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-30") {
		dropTable(tableName: "certificado")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-31") {
		dropTable(tableName: "comprobante_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-32") {
		dropTable(tableName: "folio_fiscal")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-33") {
		dropTable(tableName: "role")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-34") {
		dropTable(tableName: "user")
	}

	changeSet(author: "rcancino (generated)", id: "1437935325815-35") {
		dropTable(tableName: "user_role")
	}
}
