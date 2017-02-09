package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class CompraController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def filterPaneService

    def compraService

    def index(Integer max) {
        def periodo=session.periodo
        def list=Compra.findAll(
            "from Compra c  where date(c.fecha) between ? and ? order by c.fecha desc",
            [periodo.fechaInicial,periodo.fechaFinal])
        [compraInstanceList:list]
    }

    

    def show(Compra compraInstance) {
        respond compraInstance
    }

    def create() {
        respond new Compra(fecha:new Date())
    }

    @Transactional
    def save(Compra compraInstance) {
        if (compraInstance == null) {
            notFound()
            return
        }

        if (compraInstance.hasErrors()) {
            respond compraInstance.errors, view:'create'
            return
        }

        compraInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'compra.label', default: 'Compra'), compraInstance.id])
                redirect compraInstance
            }
            '*' { respond compraInstance, [status: CREATED] }
        }
    }

    def edit(Compra compraInstance) {
        respond compraInstance
    }

    @Transactional
    def update(Compra compraInstance) {
        if (compraInstance == null) {
            notFound()
            return
        }

        if (compraInstance.hasErrors()) {
            respond compraInstance.errors, view:'edit'
            return
        }

        compraInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Compra.label', default: 'Compra'), compraInstance.id])
                redirect compraInstance
            }
            '*'{ respond compraInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Compra compraInstance) {

        if (compraInstance == null) {
            notFound()
            return
        }

        compraInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Compra.label', default: 'Compra'), compraInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'compra.label', default: 'Compra'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def search(){
        def term='%'+params.term.trim()+'%'
        def query=Compra.where{
            (folio=~term || proveedor.nombre=~term || comentario=~term) 
        }
        def compras=query.list(max:30, sort:"folio",order:'desc')

        def comprasList=compras.collect { compra ->
            def label="${compra.folio} ${compra.proveedor} ${compra.fecha.format('dd/MM/yyyy')} ${compra.total} (${compra.moneda})"
            [id:compra.id,label:label,value:label]
        }
        render comprasList as JSON
    }

    @Transactional
    def agregarPartida(PartidaDeCompraCommand command){
        log.info "Agregando "+command
        def compra=compraService.agregarPartida(command.compra,command.producto,command.cantidad)
        flash.message="Partida agregada a la compra ${compra.id}"
        redirect action:'edit',id:compra.id
    }

    @Transactional
    def eliminarPartida(){
        def data=[:]
        def compraInstance = Compra.findById(params.compraId,[fetch:[partidas:'eager']])
        JSONArray jsonArray=JSON.parse(params.partidas);
        //println 'Partidas a eliminar: '+jsonArray
        try {
            jsonArray.each {
                def det=CompraDet.get(it.toLong())
                
                if(det){
                    
                    compraInstance.removeFromPartidas(det)
                    //println 'Eliminando : '+det+ 'res: '+compraInstance.partidas.size()
                }
            }
            compraInstance.save(failOnError:true)
            data.res='PARTIDAS_ELIMINADAS'
        }
        catch (RuntimeException e) {
            e.printStackTrace()
            data.res="ERROR"
            data.error=ExceptionUtils.getRootCauseMessage(e)
        }
        render data as JSON
    }
}

class PartidaDeCompraCommand{
    Compra compra
    Producto producto
    BigDecimal cantidad

    String toString(){
        "Partida de compra ${compra.id} Producto:${producto}  Cantidad:${cantidad}"
    }

}


