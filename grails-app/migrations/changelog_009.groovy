databaseChangeLog = {

	changeSet(author:"rcancino",id:"modificacion-security-1"){
		dropTable(cascadeConstraints:"true",tableName:'usuario_rol')
		dropTable(cascadeConstraints:"true",tableName:'rol')
		dropTable(cascadeConstraints:"true",tableName:'usuario')
	}


	changeSet(author: "rcancino (generated)", id: "1440166546960-1") {
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

	changeSet(author: "rcancino (generated)", id: "1440166546960-2") {
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

	changeSet(author: "rcancino (generated)", id: "1440166546960-3") {
		createTable(tableName: "usuario_role") {
			column(name: "usuario_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440166546960-4") {
		addPrimaryKey(columnNames: "usuario_id, role_id", constraintName: "usuario_rolePK", tableName: "usuario_role")
	}

	changeSet(author: "rcancino (generated)", id: "1440166546960-7") {
		createIndex(indexName: "authority_uniq_1440166546223", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440166546960-8") {
		createIndex(indexName: "username_uniq_1440166546229", tableName: "usuario", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440166546960-9") {
		createIndex(indexName: "FK_55sbft3wldu0yr078kdq6hwxe", tableName: "usuario_role") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440166546960-10") {
		createIndex(indexName: "FK_qpqh5on1cqa0ktsitg2vhmirv", tableName: "usuario_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440166546960-5") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "usuario_role", constraintName: "FK_qpqh5on1cqa0ktsitg2vhmirv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "rcancino (generated)", id: "1440166546960-6") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "usuario_role", constraintName: "FK_55sbft3wldu0yr078kdq6hwxe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}
}
