databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1471448170606-1") {
		createTable(tableName: "sat_polizas_log") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sat_polizas_lPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "ejercicio", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "mes", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "tipo", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "rfc", type: "varchar(13)") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "numero_de_orden", type: "varchar(255)")

			column(name: "numero_de_tramite", type: "varchar(255)") 

			column(name: "comentario", type: "varchar(255)")

			column(name: "version_sat", type: "varchar(5)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "xml", type: "mediumblob") {
				constraints(nullable: "false")
			}

			column(name: "acuse", type: "mediumblob")

			column(name: "acuse_de_aceptacion", type: "mediumblob")
		}
	}
}
