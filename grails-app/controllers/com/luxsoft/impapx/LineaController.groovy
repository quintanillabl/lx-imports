package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('COMPRAS','ADMIN')"])
@Transactional(readOnly = true)
class LineaController {

    static scaffold = true
}
