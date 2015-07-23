package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class ProductoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        respond Producto.list(params), model:[productoInstanceCount: Producto.count()]
    }

    def show(Producto productoInstance) {
        respond productoInstance
    }

    def create() {
        respond new Producto(params)
    }

    @Transactional
    def save(Producto productoInstance) {
        if (productoInstance == null) {
            notFound()
            return
        }

        if (productoInstance.hasErrors()) {
            respond productoInstance.errors, view:'create'
            return
        }

        productoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'producto.label', default: 'Producto'), productoInstance.id])
                redirect productoInstance
            }
            '*' { respond productoInstance, [status: CREATED] }
        }
    }

    def edit(Producto productoInstance) {
        respond productoInstance
    }

    @Transactional
    def update(Producto productoInstance) {
        if (productoInstance == null) {
            notFound()
            return
        }

        if (productoInstance.hasErrors()) {
            respond productoInstance.errors, view:'edit'
            return
        }

        productoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Producto.label', default: 'Producto'), productoInstance.id])
                redirect productoInstance
            }
            '*'{ respond productoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Producto productoInstance) {

        if (productoInstance == null) {
            notFound()
            return
        }

        productoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Producto.label', default: 'Producto'), productoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'producto.label', default: 'Producto'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def listAsJSON(){
        log.info 'Soliciando productos JSON'+params
        def productos=Producto.list()
        def dataToRender = [:]
        dataToRender.sEcho = params.sEcho
        dataToRender.aaData=[]

        dataToRender.iTotalRecords=productos.size
        dataToRender.iTotalDisplayRecords = dataToRender.iTotalRecords
        productos.each{ prod ->
            dataToRender.aaData <<[
                prod.id
                ,prod.clave
                ,prod.descripcion
                ,prod.unidad.clave
                ,prod.kilos
                ,prod.gramos
                ,prod.m2
                ,prod.linea.nombre
                ,prod?.marca?.nombre
                ,prod?.clase?.nombre
                ]
        }
        render dataToRender as JSON
    }
        
    def productosJSONList(){
        //def productos=Producto.findAllByClaveIlikeOrDescripcionLike(params.term+"%",[max:50,sort:"clave",order:"desc"])
        def term='%'+params.term.trim()+'%'
        def query=Producto.where{
            //(clave=~term || descripcion=~term ) && suspendido==false
            (clave=~term || descripcion=~term ) 
        }
        def productos=query.list(max:30, sort:"descripcion")
        def productosList=productos.collect { prov ->
            [id:prov.id,label:prov.toString(),value:prov.toString(),precioCredito:prov.precioCredito]
        }
        //def jsonResult=[provs:proveedoresList]
        render productosList as JSON
    }
}

class ProductoSeachCommand{
    String clave
    String descripcion
    Linea linea
}
