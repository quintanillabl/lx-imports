package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

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

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'GastosDeImportacion.label', default: 'GastosDeImportacion'), gastosDeImportacionInstance.id])
                redirect gastosDeImportacionInstance
            }
            '*'{ respond gastosDeImportacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(GastosDeImportacion gastosDeImportacionInstance) {

        if (gastosDeImportacionInstance == null) {
            notFound()
            return
        }

        gastosDeImportacionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'GastosDeImportacion.label', default: 'GastosDeImportacion'), gastosDeImportacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
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

    def importarCfdi(){
        def xml=request.getFile('xmlFile')
        
        if(xml==null){
            flash.message="Archivo XML no localizado"
            redirect(uri: request.getHeader('referer') )
            return
        }
        def cxp=comprobanteFiscalService.importar(xml,new GastosDeImportacion())
        flash.message="Cuenta por pagar generada para el CFDI:  ${xml.getName()}"
        redirect action:'edit',id:cxp.id
    }
}
