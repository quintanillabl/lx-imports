package com.luxsoft.impapx
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
class ClaseController {
	
	def scaffold=true

    
}
