package com.luxsoft.impapx.cxp

import com.luxsoft.impapx.*

class ComprobanteFiscalController {

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

}
