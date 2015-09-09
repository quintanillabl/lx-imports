package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.*
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasAnyRole('USUARIO','COMPRAS','TESORERIA','CONTABILIDAD')"])
class ComprobanteFiscalController {

    def comprobanteFiscalService

    def index() { }

    
    def importarCfdi(){
        def xml=request.getFile('xmlFile')
        
        if(xml==null){
            flash.message="Archivo XML no localizado"
            redirect(uri: request.getHeader('referer') )
            return
        }

        def cxp=comprobanteFiscalService.importar(xml)
        def controller='cuentaPorPagar'
        if(cxp.instanceOf(GastosDeImportacion)){
            controller='gastosDeImportacion'
        }else if(cxp.instanceOf(FacturaDeGastos)){
        	controller='facturaDeGastos'
        }
        flash.message="Cuenta por pagar generada para el CFDI:  ${xml.getName()}"
        redirect controller:controller, action:'edit',id:cxp.id
    }
    

    def validar(ComprobanteFiscal cf){
        cf=comprobanteFiscalService.validar(cf)
        redirect(uri: request.getHeader('referer') )

    }

    def mostrarAcuse(ComprobanteFiscal cf){
        def acuse=comprobanteFiscalService.toAcuse(cf.acuse)
        def xml=comprobanteFiscalService.toXml(acuse)
        render(text: xml, contentType: "text/xml", encoding: "UTF-8")
    }

    def mostrarCfdi(ComprobanteFiscal cf){
        def xml=comprobanteFiscalService.getCfdiXml(cf)
        render(text: xml, contentType: "text/xml", encoding: "UTF-8")
    }

    def descargarCfdi(ComprobanteFiscal cf){
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment; filename=\"$cf.cfdiFileName\"")
        ByteArrayInputStream is=new ByteArrayInputStream(cf.cfdi)
        response.outputStream << is
        
    }

}
