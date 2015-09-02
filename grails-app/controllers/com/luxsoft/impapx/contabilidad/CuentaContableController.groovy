package com.luxsoft.impapx.contabilidad



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
@Transactional(readOnly = true)
class CuentaContableController extends ContabilidadController{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def index(Integer max) {
        params.sort='clave'
        params.order="asc"
        respond CuentaContable.list(params)
    }

    def show(CuentaContable cuentaContableInstance) {
        respond cuentaContableInstance
    }

    def create() {
        respond new CuentaContable(params)
    }

    @Transactional
    def save(CuentaContable cuentaContableInstance) {
        if (cuentaContableInstance == null) {
            notFound()
            return
        }
        if (cuentaContableInstance.hasErrors()) {
            respond cuentaContableInstance.errors, view:'create'
            return
        }
        cuentaContableInstance.save flush:true
        flash.message = "Cuenta ${cuentaContableInstance.clave} registrada"
        redirect cuentaContableInstance
    }

    def edit(CuentaContable cuentaContableInstance) {
        respond cuentaContableInstance
    }

    @Transactional
    def update(CuentaContable cuentaContableInstance) {
    	

        if (cuentaContableInstance == null) {
            notFound()
            return
        }

        if (cuentaContableInstance.hasErrors()) {
            respond cuentaContableInstance.errors, view:'edit'
            return
        }

        cuentaContableInstance.save failOnError:true,flush:true
        flash.message = "Cuenta ${cuentaContableInstance.id} actualizada"
		redirect cuentaContableInstance
    }

    @Transactional
    def delete(CuentaContable cuentaContableInstance) {

        if (cuentaContableInstance == null) {
            notFound()
            return
        }

        cuentaContableInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CuentaContable.label', default: 'CuentaContable'), cuentaContableInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def agregarSubCuenta(CuentaContable cuenta,String clave,String descripcion){
    	println "Agregando subcuenta $clave $descripcion a cuenta: ${cuenta}"
    	redirect action:'edit', id:cuenta.id
    }

    @Secured(["hasAnyRole('CONTABILIDAD','TESORERIA','COMPRAS','GASTOS')"])
    def cuentasDeDetalleJSONList(){
        def cuentas=CuentaContable.findAllByClaveIlikeAndDetalle(params.term+"%",true,[max:100,sort:"clave",order:"desc"])
        
        def cuentasList=cuentas.collect { it ->
            def desc="$it.clave  $it.descripcion"
            [id:it.id,label:it.toString(),value:it.toString()]
        }
        //println 'Cuentas: '+cuentasList
        render cuentasList as JSON
    }
}
