databaseChangeLog = {

	changeSet(author: "rcancino", id: "carga_inicial-001"){
		dropTable(cascadeConstraints:"true",tableName:'user_role')
		dropTable(cascadeConstraints:"true",tableName:'role')
		dropTable(cascadeConstraints:"true",tableName:'user')
	}

	changeSet(author: "rcancino", id: "carga-inicial-002") {
	 	dropForeignKeyConstraint(baseTableName: "cxcabono", baseTableSchemaName: "impapx2", constraintName: "FKA67508E1302DA9BC")
	 	dropForeignKeyConstraint(baseTableName: "venta", baseTableSchemaName: "impapx2", constraintName: "FK6AE6A4C302DA9BC")
	 	dropTable(cascadeConstraints:"true",tableName:'comprobante_fiscal')
	}

	

	
}
