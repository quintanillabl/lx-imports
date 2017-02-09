databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1440741050607-1") {
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

			column(name: "folio", type: "varchar(20)")

			column(name: "serie", type: "varchar(20)")

			column(name: "uuid", type: "varchar(40)") {
				constraints(nullable: "false")
			}
		}
	}

	
	changeSet(author: "rcancino (generated)", id: "1440741050607-12") {
		createIndex(indexName: "FK_p7tcmwt402tlxp776j80p2qb", tableName: "comprobante_fiscal") {
			column(name: "cxp_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440741050607-13") {
		createIndex(indexName: "cxp_id_uniq_1440741049582", tableName: "comprobante_fiscal", unique: "true") {
			column(name: "cxp_id")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440741050607-14") {
		createIndex(indexName: "uuid_uniq_1440741049583", tableName: "comprobante_fiscal", unique: "true") {
			column(name: "uuid")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1440741050607-11") {
		addForeignKeyConstraint(baseColumnNames: "cxp_id", baseTableName: "comprobante_fiscal", constraintName: "FK_p7tcmwt402tlxp776j80p2qb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "cuenta_por_pagar", referencesUniqueColumn: "false")
	}
}
