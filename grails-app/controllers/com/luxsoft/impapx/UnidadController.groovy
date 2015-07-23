package com.luxsoft.impapx
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
class UnidadController {
    static scaffold = true
}
