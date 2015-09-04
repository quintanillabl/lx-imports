package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.impapx.cxp.ComprobanteFiscalException


@Secured(["hasAnyRole('COMRAS','TESORERIA')"])
@Transactional(readOnly = true)
class FacturaDeGastosController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def facturaDeGastosService

     def comprobanteFiscalService

    def index(Integer max) {
        def periodo=session.periodo
        def list=FacturaDeGastos.findAll(
            "from FacturaDeGastos c  where date(c.fecha) between ? and ? order by c.lastUpdated desc",
            [periodo.fechaInicial,periodo.fechaFinal])
        [facturaDeGastosInstanceList:list]
    }

    def show(FacturaDeGastos facturaDeGastosInstance) {
        respond facturaDeGastosInstance
    }

    def create() {
        respond new FacturaDeGastos(fecha:new Date(),vencimiento:new Date()+30)
    }

    @Transactional
    def save(FacturaDeGastos facturaDeGastosInstance) {
        if (facturaDeGastosInstance == null) {
            notFound()
            return
        }
        if (facturaDeGastosInstance.hasErrors()) {
            respond facturaDeGastosInstance.errors, view:'create'
            return
        }
        facturaDeGastosInstance.save flush:true
        flash.message = "Factura/Gasto registrado ${facturaDeGastosInstance.id}"
        redirect action:'edit',id:facturaDeGastosInstance.id
    }

    def edit(FacturaDeGastos facturaDeGastosInstance) {
        respond facturaDeGastosInstance
    }

    @Transactional
    def update(FacturaDeGastos facturaDeGastosInstance) {
        if (facturaDeGastosInstance == null) {
            notFound()
            return
        }

        if (facturaDeGastosInstance.hasErrors()) {
            respond facturaDeGastosInstance.errors, view:'edit'
            return
        }

        facturaDeGastosInstance.save flush:true
        flash.message = message(code: 'default.updated.message', args: [message(code: 'FacturaDeGastos.label', default: 'FacturaDeGastos'), facturaDeGastosInstance.id])
        redirect facturaDeGastosInstance
    }

    @Transactional
    def delete(FacturaDeGastos facturaDeGastosInstance) {

        if (facturaDeGastosInstance == null) {
            notFound()
            return
        }

        facturaDeGastosInstance.delete flush:true
        flash.message = message(code: 'default.deleted.message', args: [message(code: 'FacturaDeGastos.label', default: 'FacturaDeGastos'), facturaDeGastosInstance.id])
        redirect action:"index", method:"GET"
    }

    protected void notFound() {
        flash.message = message(code: 'default.not.found.message', args: [message(code: 'facturaDeGastos.label', default: 'FacturaDeGastos'), params.id])
        redirect action: "index", method: "GET"
    }

    @Transactional
    def agregarPartida(ConceptoDeGastoCommand command){
        //log.info 'Concepto: '+command
        def concepto=command.toGasto()
        def fac=facturaDeGastosService.agregarPartida(command)
        redirect action:'edit', id:fac.id
    }

    @Transactional
    def eliminarConceptos(){
        log.debug 'Eliminando conceptos de gasto: '+params
        def data=[:]
        def fac = FacturaDeGastos.findById(params.facturaId,[fetch:[conceptos:'eager']])
        JSONArray jsonArray=JSON.parse(params.partidas);
        try {
            facturaDeGastosService.eliminarConceptos(fac,jsonArray)
            data.res='CONCEPTOS_ELIMINADOS'
        }
        catch (RuntimeException e) {
            
            log.error 'Error eliminando conceptos: '+ExceptionUtils.getRootCauseMessage(e)
            data.res="ERROR"
            data.error=ExceptionUtils.getRootCauseMessage(e)
        }
        flash.message="Concepto eliminado"
        render data as JSON
    }

    def consultarGasto(ConceptoDeGasto concepto){
        //println 'Consultando gasto:'+params
        render(template:"conceptoShowForm",bean:concepto)
        
    }

    def search(){
        def term='%'+params.term.trim()+'%'
        def query=FacturaDeGastos.where{
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
    def importarCfdi(FacturaDeGastos facturaDeGastosInstance){

        def xml=request.getFile('xmlFile')
        if(xml==null){
            flash.message="Archivo XML no localizado"
            redirect(uri: request.getHeader('referer') )
            return
        }
        try {
            if(facturaDeGastosInstance){
                
                comprobanteFiscalService.actualizar(facturaDeGastosInstance,xml)
                log.info 'CFDI actualizado para cxp: '+facturaDeGastosInstance.id
                //redirect action:'edit',id:facturaDeGastosInstance.id
                //redirect action:'edit', id:fac.id
                redirect action:'index'
                return
            }else{
                def cxp=comprobanteFiscalService.importar(xml,new FacturaDeGastos())
                flash.message="Cuenta por pagar generada para el CFDI:  ${xml.getOriginalFilename()}"
                redirect action:'edit',id:cxp.id
                return
            }
            
        }
        catch(ComprobanteFiscalException e) {
            flash.message="Errores en la importación"
            flash.error=e.message
            redirect action:'index'
        }
        
    }
}

import com.luxsoft.impapx.FacturaDeGastos
import com.luxsoft.impapx.contabilidad.CuentaContable
import com.luxsoft.impapx.cxp.ConceptoDeGasto
import groovy.transform.ToString


@ToString(includeNames=true,includePackage=false)
class ConceptoDeGastoCommand{

    Long factura
    CuentaContable concepto 
    String tipo='GASTOS'
    String descripcion
    BigDecimal importe=0
    BigDecimal impuestoTasa=16
    BigDecimal retensionTasa=0
    BigDecimal retensionIsrTasa=0
    BigDecimal descuento=0
    BigDecimal rembolso=0
    Date fechaRembolso
    BigDecimal otros=0
    String comentarioOtros

    static constraints={
        importFrom ConceptoDeGasto
    }

    ConceptoDeGasto toGasto(){
        ConceptoDeGasto gasto=new ConceptoDeGasto()
        gasto.properties=properties
        //gasto.ietu=0.0
        return gasto
    }
}
