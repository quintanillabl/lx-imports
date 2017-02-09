databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1440175812283-1") {
		createTable(tableName: "perfil") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "perfilPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "celular", type: "varchar(255)")

			column(name: "dash_inicial", type: "varchar(60)") {
				constraints(nullable: "false")
			}

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

	changeSet(author: "rcancino (generated)", id: "1440175812283-2") {
		createTable(tableName: "perfil_preferencias") {
			column(name: "perfil_id", type: "bigint")

			column(name: "preferencias_string", type: "varchar(255)")

			column(name: "preferencias_idx", type: "varchar(255)")

			column(name: "preferencias_elt", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440175812283-4") {
		createIndex(indexName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", tableName: "perfil") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440175812283-5") {
		createIndex(indexName: "usuario_id_uniq_1440175811238", tableName: "perfil", unique: "true") {
			column(name: "usuario_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440175812283-3") {
		addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "perfil", constraintName: "FK_qimyhrxv3rmjmv7cs5fi1ek85", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", referencesUniqueColumn: "false")
	}
}
