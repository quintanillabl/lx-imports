package com.luxsoft.impapx


import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMPRAS','TESORERIA','ADMIN')"])
class CuentaPorPagarController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect controller:'facturaDeImportacion',action:'index'
    }
	
	
}
