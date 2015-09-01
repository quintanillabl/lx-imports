package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import grails.plugin.springsecurity.annotation.Secured


@Secured(["hasAnyRole('COMPRAS','TESORERIA')"])
@Transactional(readOnly = true)
class RequisicionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def requisicionService

    def reportService


    def index(Integer max) {

        def periodo=session.periodo
        def tipo=params.tipo?:'TODAS'
        def hql=""
        
        if(tipo=="PAGADAS"){
            hql="from Requisicion c  where c.pagoProveedor.id!=null and date(c.fecha) between ? and ? order by c.fecha desc"
            
        }

        if(tipo=='PENDIENTES'){
            hql="from Requisicion c   where c.pagoProveedor=null and date(c.fecha) between ? and ? order by c.fecha desc"
        }

        if(tipo=='TODAS'){
            hql="from Requisicion c  where date(c.fecha) between ? and ? order by date(c.lastUpdated) desc"
        }

        def list=Requisicion.findAll(hql,[periodo.fechaInicial,periodo.fechaFinal])
        [requisicionInstanceList:list,tipo:tipo]
    }

    def show(Requisicion requisicionInstance) {
        respond requisicionInstance
    }

    def create() {
        respond new Requisicion(fecha:new Date())
    }

    @Transactional
    def save(Requisicion requisicionInstance) {
        if (requisicionInstance == null) {
            notFound()
            return
        }
        if (requisicionInstance.hasErrors()) {
            respond requisicionInstance.errors, view:'create'
            return
        }
        if(requisicionInstance.concepto.startsWith('ANTICIPO')){
            def embarque=Embarque.get(params.long('embarque.id'))
            requisicionService.generarAnticipo(requisicionInstance, embarque)
            flash.message = message(code: 'default.created.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), requisicionInstance.id])
            redirect action: 'edit', id: requisicionInstance.id
            return
        }


        requisicionInstance.save flush:true
        flash.message = message(code: 'default.created.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), requisicionInstance.id])
        redirect action:'edit',id:requisicionInstance.id
        
    }

    def edit(Requisicion requisicionInstance) {
        respond requisicionInstance
    }

    @Transactional
    def update(Requisicion requisicionInstance) {
        if (requisicionInstance == null) {
            notFound()
            return
        }

        if (requisicionInstance.hasErrors()) {
            respond requisicionInstance.errors, view:'edit'
            return
        }
        requisicionInstance.save flush:true
        flash.message = message(code: 'default.updated.message', args: [message(code: 'Requisicion.label', default: 'Requisicion'), requisicionInstance.id])
        redirect action:'edit',id:requisicionInstance.id
        
    }

    @Transactional
    def delete(Requisicion requisicionInstance) {

        if (requisicionInstance == null) {
            notFound()
            return
        }

        requisicionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Requisicion.label', default: 'Requisicion'), requisicionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'requisicion.label', default: 'Requisicion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Transactional
    def selectorDeFacturas(Requisicion requisicion){
        def hql="from CuentaPorPagar p where p.proveedor=?   and p.total-p.requisitado>0 and moneda=? and total-pagosAplicados>0 order by p.fecha,p.documento "
        def res=CuentaPorPagar.findAll(hql,[requisicion.proveedor,requisicion.moneda])
        [requisicionInstance:requisicion,cuentaPorPagarInstanceList:res,cuentasPorPagarTotal:res.size()]
    }

    @Transactional
    def asignarFacturas(){
        def dataToRender=[:]
        JSONArray jsonArray=JSON.parse(params.partidas);
        def requisicion= Requisicion.findById(params.requisicionId,[fetch:[partidas:'select']])
        requisicionService.asignarFacturas(requisicion, jsonArray)
        dataToRender.requisicionId=requisicion.id
        render dataToRender as JSON
    }

    @Transactional
    def eliminarPartidas(){
        def dataToRender=[:]
        def requisicion= Requisicion.findById(params.requisicionId,[fetch:[partidas:'select']])
        JSONArray jsonArray=JSON.parse(params.partidas);
        requisicionService.eliminarPartidas(requisicion,jsonArray)
        dataToRender.requisicionId=requisicion.id
        render dataToRender as JSON
    }

    def createPartida(Requisicion requisicion){
        println 'Agregando partida: '+params
         render(template:"/requisicionDet/createForm",model:[requisicion:requisicion,requisicionDetInstance:new RequisicionDet()])
    }

    @Transactional
    def savePartida(RequisicionDetCommand command) {
        if (command == null) {
            notFound()
            return
        }
        
        if (command.hasErrors()) {
            flash.message="Errores de validacion al tratar de insertar partida"
            redirect action:'edit',id:command.requisicion.id
            return
        }
        
        def requisicion=command.requisicion
        def det=command.toRequisicionDet()
        requisicionService.agregarPartida(requisicion.id, det)
        redirect action: 'edit', id: requisicion.id
    }

    def embarquesDisponiblesJSONList(){
        println 'Embarques disponibles para anticipo: '+params
        def embarques=Embarque.findAll(
            "from Embarque e where e.id=? "
            ,[params.long('term')],[max:10])
        def embarquesList=embarques.collect { row ->
            def label=row.toString()
            [id:row.id,label:label,value:label]
        }
        println 'Embarques registrados: '+embarquesList.size()
        render embarquesList as JSON
    }

    def print(Requisicion requisicion){
        def command=reportService.buildCommand(session.empresa,'Requisicion')
        params.COMPANY=session.empresa.nombre
        params.ID=requisicion.id
        params.MONEDA=requisicion.moneda.toString()
        def stream=reportService.build(command,params)
        def file="Requisicion_${requisicion.id}.pdf"
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
    }
}

import org.grails.databinding.BindingFormat
import grails.validation.Validateable

@Validateable
class RequisicionDetCommand {

    Requisicion requisicion

    String documento

    @BindingFormat('dd/MM/yyyy')
    Date fechaDocumento

    BigDecimal totalDocumento=0

    Embarque embarque

    BigDecimal total

    static constraints={
        importFrom RequisicionDet
        requisicion nullable:false
        
    }

    RequisicionDet toRequisicionDet(){
        def r=new RequisicionDet()
        r.documento=documento
        r.fechaDocumento=fechaDocumento
        r.totalDocumento=totalDocumento
        r.embarque=embarque
        r.total=total
        return r
    }
}
