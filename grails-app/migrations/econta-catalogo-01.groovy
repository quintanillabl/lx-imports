databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1470349376129-1") {
		createTable(tableName: "sat_catalogo_log") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sat_catalogo_PK")
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

			column(name: "rfc", type: "varchar(13)") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "comentario", type: "varchar(255)")

			column(name: "enviado", type: "datetime")

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
		}
	}
}
