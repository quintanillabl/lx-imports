package com.luxsoft.impapx


import org.codehaus.groovy.grails.web.json.JSONArray
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured


@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class ProveedorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = 1000
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        respond Proveedor.list(params), model:[proveedorInstanceCount: Proveedor.count()]
    }

    def show(Proveedor proveedorInstance) {
        respond proveedorInstance
    }

    def create() {
        respond new Proveedor(params)
    }

    @Transactional
    def save(Proveedor proveedorInstance) {
        if (proveedorInstance == null) {
            notFound()
            return
        }

        if (proveedorInstance.hasErrors()) {
            respond proveedorInstance.errors, view:'create'
            return
        }

        proveedorInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'proveedor.label', default: 'Proveedor'), proveedorInstance.id])
                redirect proveedorInstance
            }
            '*' { respond proveedorInstance, [status: CREATED] }
        }
    }

    def edit(Proveedor proveedorInstance) {
        def tab = params.tab?:'propiedades'
        respond proveedorInstance,model:[tab:tab]
    }

    @Transactional
    def update(Proveedor proveedorInstance) {
        if (proveedorInstance == null) {
            notFound()
            return
        }

        if (proveedorInstance.hasErrors()) {
            respond proveedorInstance.errors, view:'edit'
            return
        }

        proveedorInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Proveedor.label', default: 'Proveedor'), proveedorInstance.id])
                redirect proveedorInstance
            }
            '*'{ respond proveedorInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Proveedor proveedorInstance) {

        if (proveedorInstance == null) {
            notFound()
            return
        }

        proveedorInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Proveedor.label', default: 'Proveedor'), proveedorInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'proveedor.label', default: 'Proveedor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def proveedoresJSONList(){
        def proveedores=Proveedor.findAllByNombreIlike(params.term+"%",[max:10,sort:"nombre",order:"desc"])
        def proveedoresList=proveedores.collect { prov ->
            [id:prov.id,label:prov.nombre,value:prov.nombre]
        }
        render proveedoresList as JSON
    }
    
    def proveedoresGastosJSONList(){
        def proveedores=Proveedor.findAllByNombreIlikeAndNacional(params.term+"%",true,[max:10,sort:"nombre",order:"desc"])
        def proveedoresList=proveedores.collect { prov ->
            [id:prov.id,label:prov.nombre,value:prov.nombre]
        }
        //def jsonResult=[provs:proveedoresList]
        render proveedoresList as JSON
    }

    def productosAsignablesJSONList(){
        def c=Producto.createCriteria()
        def productos=c.list{
            or{
                ilike("clave",params.term+'%')
                ilike("descripcion",'%'+params.term+'%')
            }
            order 'clave','asc'
            maxResults(15)
        }
        def productosList=productos.collect {
            [id:it.clave,label:it.toString(),value:it.toString(),descripcion:it.descripcion]
        }
        render productosList as JSON
    }

    def localisarPrecio(long proveedorId,long productoId){
        println 'Localizando precio: '+params
        def provProducto=ProveedorProducto.find{
            proveedor.id==proveedorId && producto.id==productoId
        }
        if(provProducto==null)
            provProducto=new ProveedorProducto(costoUnitario:0)
        render provProducto as JSON
    }
    
     @Transactional
    def actualizarCostoUnitarioEnProductos(){
        println 'Actualizar costo unitario de productos params:'+params
        JSONArray jsonArray=JSON.parse(params.partidas);
        jsonArray.each{
            def det=ProveedorProducto.get(it.toLong())
            det.costoUnitario=params.costoUnitario.toBigDecimal()
        }
        def res=[costoUnitario:params.costoUnitario]
        render res as JSON
    }
    
    def eliminarProductos(){
        println 'Eliminando proveedorProductos params:'+params
        JSONArray jsonArray=JSON.parse(params.partidas);
        jsonArray.each{
            def det=ProveedorProducto.get(it.toLong())
            //def proveedor=det.proveedor
            //proveedor.removeFromProductos(det)
            det.delete(flush:true)
            
        }
        def res=[eliminados:jsonArray.length()]
        render res as JSON
    }
    
    def selectorDeProductos(long id){
        Proveedor p=Proveedor.get(id)
        def hql="from Producto p where p not in (select x.producto from ProveedorProducto x where x.proveedor=?) order by p.linea.nombre,p.marca.nombre,p.clave"
        def res=Producto.findAll(hql,[p])
        [productos:res,productosTotal:res.size(),proveedor:p]
    }

     @Transactional
    def registrarProductos(){
        def data=[:]
        def proveedor = Proveedor.findById(params.proveedorId,[fetch:[productos:'eager']])
        JSONArray jsonArray=JSON.parse(params.partidas);
        jsonArray.each{
            def prod=Producto.get(it.toLong())
            proveedor.addToProductos(producto:prod,costoUnitario:0.0,gramos:prod.gramos)
        }
        proveedor.save(failOnError:true)
        flash.message="Nuevos productos asignados "
        render data as JSON
    }

    @Transactional
    def agregarAgenteAduanal(Proveedor proveedorInstance){
        def agente = params.agente
        log.info 'Agregando agente aduanal: '+agente
        flash.message="Agente asignado: "+agente
        proveedorInstance.agentes.add(agente)
        proveedorInstance.save flush:true
        forward action:'edit',id:proveedorInstance.id,params:[tab:'agentes']

    }

    @Transactional
    def eliminarAgenteAduanal(Proveedor proveedorInstance){
        def agente = params.agente
        log.info 'Eliminando agente aduanal: '+agente
        flash.message="Agente eliminado: "+agente
        proveedorInstance.agentes.remove(agente)
        proveedorInstance.save failOnError:true,flush:true
        forward action:'edit',id:proveedorInstance.id,params:[tab:'agentes']

    }

    def buscarAgentesAduanales(Proveedor proveedorInstance){
        def data =  []
        proveedorInstance.agentes.each{
            data.add([nombre:it])
        }
        render data as JSON
    }

}
