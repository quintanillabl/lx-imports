package com.luxsoft.impapx

import groovy.sql.Sql;

class ConsultasController {
	
	def dataSource_importacion

    def index() {
		def db=new Sql(dataSource_importacion)
		def res=db.rows("select * from sw_sucursales")
		[rows:res]
	}
}
