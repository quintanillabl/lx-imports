package com.luxsoft.impapx

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('USUARIO')"])
class HomeController {

    def index() { }

    def homeDashboard(){
    }
}
