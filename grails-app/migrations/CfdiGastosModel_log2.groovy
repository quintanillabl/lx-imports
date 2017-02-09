databaseChangeLog = {

	changeSet(author: "rcancino (generated)", id: "1441032004443-1") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "emisor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441032004443-2") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "receptor_rfc", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "rcancino (generated)", id: "1441032004443-3") {
		addColumn(tableName: "comprobante_fiscal") {
			column(name: "total", type: "decimal(19,2)") {
				constraints(nullable: "false")
			}
		}
	}
	
}
