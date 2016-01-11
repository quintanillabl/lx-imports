package com.luxsoft.impapx

import grails.plugin.springsecurity.annotation.Secured
import groovy.sql.Sql;

@Secured(["hasRole('USUARIO')"])
class ConsultasController {
	
	def dataSource_importacion

    def index() {
		def db=new Sql(dataSource_importacion)
		def res=db.rows("select * from sw_sucursales")
		[rows:res]
	}

	def embarques(Integer max){
		params.max = Math.min(max ?: 40, 100)
		params.sort=params.sort?:'lastUpdated'
		params.order='desc'
		def periodo=session.periodoEmbarques
		def list=Embarque.findAll(
		    "from Embarque e  where date(e.dateCreated) between ? and ? order by e.lastUpdated desc",
		    [periodo.fechaInicial,periodo.fechaFinal])
		[embarqueInstanceList:list]
	}
}
