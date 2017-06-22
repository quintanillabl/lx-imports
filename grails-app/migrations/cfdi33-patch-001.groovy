databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1498082860627-1") {
		addColumn(tableName: "cfdi") {
			column(name: "receptor_rfc", type: "varchar(13)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1498082860627-2") {
		addColumn(tableName: "cfdi") {
			column(name: "version_cfdi", type: "varchar(3)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1498082860627-3") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "version_cfdi", type: "varchar(10)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1498082860627-4") {
		addColumn(tableName: "empresa") {
			column(name: "regimen_clave_sat", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1498082860627-5") {
		addColumn(tableName: "empresa") {
			column(name: "version_de_cfdi", type: "varchar(3)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1498082860627-6") {
		addColumn(tableName: "producto") {
			column(name: "clave_prod_serv", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1498082860627-7") {
		addColumn(tableName: "producto") {
			column(name: "clave_unidad_sat", type: "varchar(255)")
		}
	}

	changeSet(author: "rcancino (generated)", id: "1498082860627-8") {
		addColumn(tableName: "producto") {
			column(name: "unidad_sat", type: "varchar(255)")
		}
	}
	

	
	
	
}
