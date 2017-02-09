package com.luxsoft.cfdi.retenciones



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import com.luxsoft.lx.core.*
import grails.converters.JSON
import groovy.xml.*
import com.luxsoft.lx.bi.ReportCommand
import com.luxsoft.impapx.Empresa
import com.luxsoft.impapx.*

@Transactional(readOnly = true)
@Secured(["hasRole('TESORERIA')"])
class CfdiRetencionesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def cfdiRetencionesService

    def reportService

    def index(Integer max) {
        params.max = Math.min(max ?: 100, 1000)
        respond CfdiRetenciones.list(params), model:[cfdiRetencionesInstanceCount: CfdiRetenciones.count()]
    }

    def show(CfdiRetenciones cfdiRetencionesInstance) {
        respond cfdiRetencionesInstance
    }

    def create() {
        
        //respond new RetencionesCommand(fecha:new Date(),empresa:session.empresa)
        [cfdiRetencionesInstance:new RetencionesCommand(
            fecha:new Date(),
            empresa:session.empresa,
            ejercicio:session.ejercicio,
            mesInicial:session.mes,
            mesFinal:session.mes)
        ]
    }

    @Transactional
    def save(RetencionesCommand command) {
        if (command == null) {
            notFound()
            return
        }
        command.empresa=session.empresa
        if (command.hasErrors()) {
            render view:'create',model:[cfdiRetencionesInstance:command]
            return
        }
        def cfdiRetencionesInstance=cfdiRetencionesService.save(command)
        flash.message = "Comprobante ${cfdiRetencionesInstance.id} CFDI de retención y pagos generado"
        redirect action:'edit',id:cfdiRetencionesInstance.id
    }

    def edit(CfdiRetenciones cfdiRetencionesInstance) {
        respond cfdiRetencionesInstance
    }

    @Transactional
    def update(CfdiRetenciones cfdiRetencionesInstance) {
        if (cfdiRetencionesInstance == null) {
            notFound()
            return
        }

        if (cfdiRetencionesInstance.hasErrors()) {
            respond cfdiRetencionesInstance.errors, view:'edit'
            return
        }
        
        cfdiRetencionesInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CfdiRetenciones.label', default: 'CfdiRetenciones'), cfdiRetencionesInstance.id])
                redirect cfdiRetencionesInstance
            }
            '*'{ respond cfdiRetencionesInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(CfdiRetenciones cfdiRetencionesInstance) {

        if (cfdiRetencionesInstance == null) {
            notFound()
            return
        }

        cfdiRetencionesInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CfdiRetenciones.label', default: 'CfdiRetenciones'), cfdiRetencionesInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cfdiRetenciones.label', default: 'CfdiRetenciones'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def buscarReceptores() {

        def list=Proveedor.findAllByNombreIlike("%"+params.term+"%",[max:40,sort:"nombre",order:"desc"])

        
        list=list.collect{ c->
            def nombre="$c.nombre"
            
            [
                label:nombre,
                value:nombre,
                nombre:nombre,
                rfc:c.rfc,
                nacional:c.nacional
            ]
        }
        def res=list as JSON
        
        render res
    }

    @Transactional
    def generarXml(CfdiRetenciones cfdiRetenciones){
        if (cfdiRetenciones == null) {
            notFound()
            return
        }
        if(cfdiRetenciones.uuid){
            flash.message="Documento ya timbrado no puede ser modificado"
            redirect action:'show',id:cfdiRetenciones.id
            return
        }
        cfdiRetenciones=cfdiRetencionesService.generarXml(cfdiRetenciones)
        flash.message="XML generado"
        redirect action:'edit',id:cfdiRetenciones.id
    }

    @Transactional
    def timbrar(CfdiRetenciones cfdiRetenciones){
        if (cfdiRetenciones == null) {
            notFound()
            return
        }
        if(cfdiRetenciones.uuid){
            flash.message="Documento ya timbrado no puede ser modificado"
            redirect action:'show',id:cfdiRetenciones.id
            return
        }
        
        // try {
        //     cfdiRetenciones=cfdiRetencionesService.timbrar(cfdiRetenciones)
        //     flash.message="Comprobante timbrado"
        //     redirect action:'edit',id:cfdiRetenciones.id
        // }
        // catch(Exception e) {
        //     e.printStackTrace()
        //     log.error e
        //     redirect action:'edit',id:cfdiRetenciones.id
        // }
        
        flash.message="Por el momento no se puede timbrar desde esta aplicación solicite a SISTEMAS el timbrado"
        redirect action:'edit',id:cfdiRetenciones.id
        
    }
    
    def agregarImpuesto(CfdiRetenciones cfdiRetencionesInstance){
        if (cfdiRetencionesInstance == null) {
            notFound()
            return
        }
        [cfdiRetencionesInstance:cfdiRetencionesInstance,impuestoRetenidoInstance:new ImpuestoRetenido(retencion:cfdiRetencionesInstance)]
    }

    @Transactional
    def salvarImpuesto(ImpuestoRetenido impuestoRetenidoInstance){
        println 'Agregando impuesto:'+ impuestoRetenidoInstance
        def cfdiRetenciones=impuestoRetenidoInstance.retencion
        cfdiRetenciones.addToImpuestosRetenidos(impuestoRetenidoInstance)
        cfdiRetenciones.save failOnError:true,flush:true
        flash.message="Retención agregada: "+impuestoRetenidoInstance
        redirect action:'edit',id:impuestoRetenidoInstance.retencion.id
    }

    @Transactional
    def eliminarImpuesto(ImpuestoRetenido impuestoRetenidoInstance){
        log.info 'Eliminando impuesto retenido: '+impuestoRetenidoInstance
        def cfdiRetenciones=impuestoRetenidoInstance.retencion
        cfdiRetenciones.removeFromImpuestosRetenidos(impuestoRetenidoInstance)
        cfdiRetenciones.save failOnError:true,flush:true
        flash.message="Retención de impuesto eliminado: "+impuestoRetenidoInstance
        redirect action:'edit',id:cfdiRetenciones.id
    }

    @Transactional
    def cargarXml(CfdiRetenciones cfdiRetencionesInstance){
        if (cfdiRetencionesInstance == null) {
            notFound()
            return
        }

        def xml=request.getFile('xmlFile')
        if(xml==null){
            flash.message="Archivo XML no localizado"
            redirect action:'index'
            return
        }

        File xmlFile = File.createTempFile(xml.getName(),".temp");
        xml.transferTo(xmlFile)
        cfdiRetencionesInstance=cfdiRetencionesService.cargarXml(cfdiRetencionesInstance,xmlFile)
        redirect action:'edit',id:cfdiRetencionesInstance.id
    }


    def mostrarXml(CfdiRetenciones cfdiRetencionesInstance){
        render(text:cfdiRetencionesInstance.toXml(), contentType: "text/xml", encoding: "UTF-8")
    }

    def descargarXml(CfdiRetenciones cfdiRetencionesInstance){
        log.info 'Descargando archivo xml: '+cfdiRetencionesInstance
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=${cfdiRetencionesInstance.xmlName}")
        response.outputStream << cfdiRetencionesInstance.xml
        return
    }

    def print(CfdiRetenciones cfdiRetencionesInstance){
        
        /*
        def modelData=cfdiRetencionesInstance.impuestosRetenidos.collect { cc ->
            def res=[TIPO_IMPUESTO:'IVA']
            return res
        }
        */
        def modelData=[]
        if(cfdiRetencionesInstance.impuestosRetenidos){
            modelData=cfdiRetencionesInstance.impuestosRetenidos.collect { cc ->
                def res=[TIPO_IMPUESTO:cc.impuesto.descripcion,
                         TIPO_PAGO:cc.tipoPagoRet,
                         BASE_RETENCION:cc.baseRet,
                         IMPUESTO_RETENIDO:cc.montoRet
                        ]
                return res
            }
        }else{
            modelData<<[TIPO_IMPUESTO:null]
        }
        

        def repParams=RetencionesPrintUtils.resolverParametros(cfdiRetencionesInstance)
        
        repParams.FECHA=cfdiRetencionesInstance.fecha.format("yyyy-MM-dd'T'HH:mm:ss")
        repParams.COMPANY=session.empresa.nombre

        def command=new ReportCommand()
        command.reportName="RetencionesPagosCFDI"
        command.empresa=session.empresa
        log.info 'Generando impreion de comprobante de retenciones y pagos con parameotros: '+repParams
        def stream=reportService.build(command,repParams,modelData)
        def file="ComprobanteDeRetencionPago_${cfdiRetencionesInstance.id}_${cfdiRetencionesInstance.uuid}"+'.'+command.formato.toLowerCase()
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
        
    }


}

import org.grails.databinding.BindingFormat
class RetencionesCommand{
    
    Empresa empresa
    
    @BindingFormat('dd/MM/yyyy')
    Date fecha
    
    String receptor

    String receptorRfc

    String receptorCurp

    Boolean nacional=true

    String registroTributario

    TipoDeRetencion tipoDeRetencion

    String retencionDescripcion

    Integer mesInicial

    Integer mesFinal

    Integer ejercicio

    BigDecimal total=0
    BigDecimal totalGravado=0
    BigDecimal totalExcento=0
    BigDecimal totalRetenido=0
    
    

    static constraints={
        importFrom CfdiRetenciones
        registroTributario nullable:true
        receptorCurp nullable:true
        mesInicial inList:[1,2,3,4,5,6,7,8,9,10,11,12]
        mesFinal inList:[1,2,3,4,5,6,7,8,9,10,11,12]
        ejercicio inList:(2004..2024) as List
        
    }

}


