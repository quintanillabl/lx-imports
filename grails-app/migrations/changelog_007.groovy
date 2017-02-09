databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1439743073749-1") {
		createTable(tableName: "audit_log") {
			column(name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "audit_logPK")
			}

			column(name: "actor", type: "varchar(255)")

			column(name: "class_name", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "event_name", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "new_value", type: "varchar(255)")

			column(name: "old_value", type: "varchar(255)")

			column(name: "persisted_object_id", type: "varchar(255)")

			column(name: "persisted_object_version", type: "bigint")

			column(name: "property_name", type: "varchar(255)")

			column(name: "uri", type: "varchar(255)")
		}
	}
}
