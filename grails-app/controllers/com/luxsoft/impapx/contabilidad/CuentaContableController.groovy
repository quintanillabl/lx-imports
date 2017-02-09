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
        //respond CuentaContable.list(params)
        def list = CuentaContable.where {padre == null}.list(params)
        respond list
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
        cuentaContableInstance.clave+='-0000'
        cuentaContableInstance.save flush:true
        flash.message = "Cuenta ${cuentaContableInstance.clave} registrada"
        redirect action:'edit',id:cuentaContableInstance.id
        
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

    @Transactional
    def agregarSubCuenta(String clave,String descripcion,Boolean detalle){
        CuentaContable cuenta = CuentaContable.get(params.padre)
    	log.info "Agregando subcuenta $clave $descripcion a cuenta: ${cuenta}"
        log.info params
        
        def subCuenta = new CuentaContable(clave:clave,descripcion:descripcion,detalle:detalle)
        subCuenta.padre= cuenta
        //subCuenta.clave=cuenta.clave+'-'+subCuenta.clave.padLeft(4,'0')
        subCuenta.clave=cuenta.clave.split('-')[0]+'-'+subCuenta.clave.padLeft(4,'0')
        subCuenta.tipo=cuenta.tipo
        subCuenta.subTipo=cuenta.subTipo
        subCuenta.naturaleza=cuenta.naturaleza
        subCuenta.deResultado=cuenta.deResultado
        cuenta.addToSubCuentas(subCuenta)
        
        cuenta.save failOnError:true,flush:true
        redirect action:'edit', id:subCuenta.id
        //redirect action:'edit' ,id:cuenta.id //
        
       
    }

    // def saveSubCuenta(CuentaContable subCuenta){
    //     def cuenta=CuentaContable.get(params.cuentaId)
    //     assert cuenta,'No existe la cuenta de padre'
    //     assert subCuenta.padre==null,'La sub cuenta no debe tener padre'
    //     subCuenta.validate(['clave','descripcion','cuentaSat','detalle'])
    //     if (subCuenta.hasErrors()) {
    //         render view:'createSubCuenta',model:[subCuenta:subCuenta]
    //         return
    //     }
    //     cuenta=cuentaContableService.agregarSubCuenta(cuenta,subCuenta)
    //     flash.message="Sub cuenta $subCuenta registrada "
    //     redirect action:'show',params:[id:cuenta.id]
        

    // }

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

    @Secured(["hasAnyRole('CONTABILIDAD','TESORERIA','COMPRAS','GASTOS')"])
    def getCuentasDeDetalleJSON() {
        
        def term=params.term+'%'
        log.info 'Buscando cuenta: '+term
        def args=[term,term.toLowerCase()]
        def params=[max:30,sort:"clave",order:"desc"]
        def hql="from CuentaContable c where  c.detalle=true and ( c.clave like ? or lower(c.descripcion) like ?) "
        def list=CuentaContable.findAll(hql,args,params)
        
        list=list.collect{ c->
            def nombre="$c.clave $c.descripcion"
            
            [id:c.id,
            label:nombre,
            value:nombre]
        }
        def res=list as JSON
        render res
    }
}
