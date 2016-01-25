package com.luxsoft.impapx

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class CuentaBancariaController {
    static scaffold = true
}
