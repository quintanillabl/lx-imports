package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.impapx.cxp.ComprobanteFiscalException
import com.luxsoft.impapx.cxp.*
import com.luxsoft.impapx.*

@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class GastosDeImportacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def comprobanteFiscalService

    def index(Integer max) {
        def periodo=session.periodo
        
        def list=GastosDeImportacion.findAll(
            "from GastosDeImportacion c  where date(c.fecha) between ? and ? order by c.fecha desc",
            [periodo.fechaInicial,periodo.fechaFinal])
        
            
        [gastosDeImportacionInstanceList:list]
        
    }

    def show(GastosDeImportacion gastosDeImportacionInstance) {
        respond gastosDeImportacionInstance
    }

    def create() {
        respond new GastosDeImportacion(fecha:new Date(),vencimiento:new Date()+30)
    }

    @Transactional
    def save(GastosDeImportacion gastosDeImportacionInstance) {
        
        if (gastosDeImportacionInstance == null) {
            notFound()
            return
        }

        if (gastosDeImportacionInstance.hasErrors()) {
            respond gastosDeImportacionInstance.errors, view:'create'
            return
        }

        gastosDeImportacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'gastosDeImportacion.label', default: 'GastosDeImportacion'), gastosDeImportacionInstance.id])
                redirect gastosDeImportacionInstance
            }
            '*' { respond gastosDeImportacionInstance, [status: CREATED] }
        }
    }

    def edit(GastosDeImportacion gastosDeImportacionInstance) {
        respond gastosDeImportacionInstance
    }

    @Transactional
    def update(GastosDeImportacion gastosDeImportacionInstance) {
        if (gastosDeImportacionInstance == null) {
            notFound()
            return
        }

        if (gastosDeImportacionInstance.hasErrors()) {
            respond gastosDeImportacionInstance.errors, view:'edit'
            return
        }
        gastosDeImportacionInstance.save flush:true
        flash.message = message(code: 'default.updated.message', args: [message(code: 'GastosDeImportacion.label', default: 'GastosDeImportacion'), gastosDeImportacionInstance.id])
        redirect action:'edit',id:gastosDeImportacionInstance.id

    }

    @Transactional
    def delete(GastosDeImportacion gastosDeImportacionInstance) {
        if (gastosDeImportacionInstance == null) {
            notFound()
            return
        }
        gastosDeImportacionInstance.delete flush:true
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'GastosDeImportacion.label', default: 'GastosDeImportacion'), gastosDeImportacionInstance.id])
        redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'gastosDeImportacion.label', default: 'GastosDeImportacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def search(){
        def term='%'+params.term.trim()+'%'
        def query=GastosDeImportacion.where{
            (id.toString()=~term || documento=~term || proveedor.nombre=~term ) 
        }
        def cuentas=query.list(max:30, sort:"id",order:'desc')
        def cuentasList=cuentas.collect { cuenta ->
            def label="Id: ${cuenta.id} Docto:${cuenta.documento} ${cuenta.proveedor} ${cuenta.fecha.format('dd/MM/yyyy')} ${cuenta.total} "
            [id:cuenta.id,label:label,value:label]
        }
        render cuentasList as JSON
    }

    @Transactional
    def importarCfdi(GastosDeImportacion gastosDeImportacionInstance){

        def xml=request.getFile('xmlFile')
        if(xml==null){
            flash.message="Archivo XML no localizado"
            redirect(uri: request.getHeader('referer') )
            return
        }
        try {
            if(gastosDeImportacionInstance.id!=null){
                comprobanteFiscalService.actualizar(gastosDeImportacionInstance,xml)
                log.info 'CFDI actualizado para cxp: '+gastosDeImportacionInstance.id
                redirect action:'edit',id:gastosDeImportacionInstance.id
                return
            }else{
                try {
                    def cxp=comprobanteFiscalService.importar(xml,new GastosDeImportacion())
                    flash.message="Cuenta por pagar generada para el CFDI:  ${xml.getOriginalFilename()}"
                    redirect action:'edit',id:cxp.id
                    return
                }
                catch(ComprobanteExistenteException ex) {
                    log.info 'Cfdi ta importado: '+ex.comprobante.uuid
                    def cfdi = ex.comprobante
                    def cxp = cfdi.cxp
                    flash.message="CFDI YA IMPORTADO en la factura Id:${cxp.id} Docto:${cxp.documento}"+ex.message
                    if(cxp.instanceOf(GastosDeImportacion)){
                        redirect action:'show',id:cxp.id
                        return
                    }else if(cxp.instanceOf(FacturaDeGastos)){
                        redirect controller:'facturaDeGastos',action:'show',id:cxp.id
                        return
                    }
                }
            }
        }
        catch(ComprobanteFiscalException e) {
            flash.message="Errores en la importación"
            flash.error=e.message
            redirect action:'index'
        }
        
    }

    // @Transactional
    // def importarCfdi(){
    //     def xml=request.getFile('xmlFile')
    //     if(xml==null){
    //         flash.message="Archivo XML no localizado"
    //         redirect(uri: request.getHeader('referer') )
    //         return
    //     }
    //     try {
    //         def cxp=comprobanteFiscalService.importar(xml,new GastosDeImportacion())
    //         flash.message="Cuenta por pagar generada para el CFDI:  ${xml.getOriginalFilename()}"
    //         redirect action:'edit',id:cxp.id
    //     }
    //     catch(ComprobanteExistenteException ex) {
    //         // flash.message="Errores en la importación"
    //         // flash.error=e.message
    //         // redirect action:'index'
    //         log.info 'Cfdi ta importado: '+ex.comprobante.uuid
    //         def cfdi = ex.comprobante//ComprobanteFiscal.get(ex.comprobante.id)
    //         def cxp = cfdi.cxp
    //         flash.message="CFDI YA IMPORTADO en la factura Id:${cxp.id} Docto:${cxp.documento}"+ex.message
    //         if(cxp.instanceOf(GastosDeImportacion)){
    //             redirect action:'show',id:cxp.id
    //             return
    //         }else if(cxp.instanceOf(FacturaDeGastos)){
    //             redirect controller:'facturaDeGastos',action:'show',id:cxp.id
    //             return
    //         }
            
    //     }
        
    // }

   
}
