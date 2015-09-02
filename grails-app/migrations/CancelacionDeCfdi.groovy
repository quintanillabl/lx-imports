databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1441209972524-1") {
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
	
	changeSet(author: "rcancino (generated)", id: "1441209972524-12") {
		createIndex(indexName: "FK_alib7rwpbtqfnrrkov7gg4rab", tableName: "cancelacion_de_cfdi") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441209972524-13") {
		createIndex(indexName: "cfdi_id_uniq_1441209971557", tableName: "cancelacion_de_cfdi", unique: "true") {
			column(name: "cfdi_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441209972524-11") {
		addForeignKeyConstraint(baseColumnNames: "cfdi_id", baseTableName: "cancelacion_de_cfdi", constraintName: "FK_alib7rwpbtqfnrrkov7gg4rab", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cfdi", referencesUniqueColumn: "false")
	}
}
