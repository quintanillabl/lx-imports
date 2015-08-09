package com.luxsoft.impapx.tesoreria
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class BancoController {
    static scaffold = true
}
