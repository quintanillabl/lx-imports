package com.luxsoft.impapx

import grails.rest.RestfulController
import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMPRAS','TESORERIA')"])
@Transactional(readOnly = true)
class RequisicionRestController extends RestfulController{

	static responseFormats = ['json']

    RequisicionRestController(){
    	super(Requisicion)
    }

    def index(Integer max) {
    	println 'Buscando requisiciones REST '+Requisicion.count()
    	println 'Parametros: '+params
        // params.max = Math.min(max ?: 20, 100)
        // params.sort=params.sort?:'lastUpdated'
        // params.order='desc'

        params.max = 10
        params.sort=params.sort?:'lastUpdated'
        params.order=params.order?:'desc'

        respond Requisicion.list(params), model:[totalCount: Requisicion.count()]
    }
}
