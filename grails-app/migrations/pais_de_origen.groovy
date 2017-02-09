databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1452624725302-1") {
		createTable(tableName: "pais_de_origen") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pais_de_origePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "nombre", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1452624725302-12") {
		createIndex(indexName: "nombre_uniq_1452624724494", tableName: "pais_de_origen", unique: "true") {
			column(name: "nombre")
		}
	}
}
