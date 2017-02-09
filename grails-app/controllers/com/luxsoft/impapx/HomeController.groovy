package com.luxsoft.impapx

import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.utils.Periodo

@Secured(["hasRole('USUARIO')"])
class HomeController {

    def index() {
    	redirect action:'homeDashboard'
    }

    def homeDashboard(){
    }

    def cambiarPeriodo(Periodo periodo){
        session.periodo=periodo
        redirect(uri: request.getHeader('referer') )
    }
}
