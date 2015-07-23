package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.lang.StringUtils

@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class FacturaDeImportacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def reportService

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        respond FacturaDeImportacion.list(params), model:[facturaDeImportacionInstanceCount: FacturaDeImportacion.count()]
    }

    def show(FacturaDeImportacion facturaDeImportacionInstance) {
        respond facturaDeImportacionInstance
    }

    def create() {
        respond new FacturaDeImportacion(params)
    }

    @Transactional
    def save(FacturaDeImportacion facturaDeImportacionInstance) {
        if (facturaDeImportacionInstance == null) {
            notFound()
            return
        }

        if (facturaDeImportacionInstance.hasErrors()) {
            respond facturaDeImportacionInstance.errors, view:'create'
            return
        }

        facturaDeImportacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'facturaDeImportacion.label', default: 'FacturaDeImportacion'), facturaDeImportacionInstance.id])
                redirect facturaDeImportacionInstance
            }
            '*' { respond facturaDeImportacionInstance, [status: CREATED] }
        }
    }

    def edit(FacturaDeImportacion facturaDeImportacionInstance) {
        respond facturaDeImportacionInstance
    }

    @Transactional
    def update(FacturaDeImportacion facturaDeImportacionInstance) {
        if (facturaDeImportacionInstance == null) {
            notFound()
            return
        }

        if (facturaDeImportacionInstance.hasErrors()) {
            respond facturaDeImportacionInstance.errors, view:'edit'
            return
        }

        facturaDeImportacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'FacturaDeImportacion.label', default: 'FacturaDeImportacion'), facturaDeImportacionInstance.id])
                redirect facturaDeImportacionInstance
            }
            '*'{ respond facturaDeImportacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(FacturaDeImportacion facturaDeImportacionInstance) {

        if (facturaDeImportacionInstance == null) {
            notFound()
            return
        }

        facturaDeImportacionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'FacturaDeImportacion.label', default: 'FacturaDeImportacion'), facturaDeImportacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'facturaDeImportacion.label', default: 'FacturaDeImportacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def programacionDePagos(){
    	
    	params.max = Math.min(params.max ? params.int('max') : 200, 1000)
    	params.sort='id'
    	params.order= "desc"
    	if(!params.fechaInicial)
    		params.fechaInicial=new Date()
    	if(!params.fechaFinal)
    		params.fechaFinal=new Date()+7
    	
    	// FacturasPorPeriodoCommand cmd=new FacturasPorPeriodoCommand()
    	// cmd.properties=params
    	// println 'Periodo: '+cmd+' Proveedor: '+cmd?.proveedor?.id
    	
    	def facturas=[]
    	if(StringUtils.isNotBlank(params.proveedor)){
    		Proveedor p=Proveedor.get(params.long('proveedor.id'))
    		cmd.proveedor=p
    		facturas=FacturaDeImportacion.findAllByProveedorAndVencimientoBetween(p,params.fechaInicial,params.fechaFinal,params)
    	}
    	else{
    		facturas=FacturaDeImportacion.findAllByVencimientoBetween(params.fechaInicial,params.fechaFinal,params)
    	}
    	flash.message='Facturas: '+facturas.size()
    	[facturaDeImportacionInstanceList: facturas,facturaDeImportacionInstanceCount: facturas.size()]
    }

    def imprimirProgramacionDePagos(){
    	if(!params.fechaInicial)
    		params.fechaInicial=new Date()
    	if(!params.fechaFinal)
    		params.fechaFinal=new Date()+7

    	def command=new com.luxsoft.lx.bi.ReportCommand()
    	command.reportName="ProgramacionDePago"
    	command.empresa=session.empresa
    	def repParams=[
    		EMPRESA:session.empresa.nombre,
    	    FECHA_INI:params.fechaInicial.format('dd/MM/yyyy'),
    	    FECHA_FIN:params.fechaFinal.format('dd/MM/yyyy'),
    	    PROVEEDOR:'%'
    	]
    	def stream=reportService.build(command,repParams)
    	def file="ProgramacionDePagos_"+new Date().format('mmss')+'.'+command.formato.toLowerCase()
    	render(
    	    file: stream.toByteArray(), 
    	    contentType: 'application/pdf',
    	    fileName:file)
    }
}

class FacturasPorPeriodoCommand{
	
	Date fechaInicial=new Date()-90
	Date fechaFinal=new Date()
	Proveedor proveedor
	
	String toString(){
		return fechaInicial.text() +' al '+fechaFinal.text() 
	}
	
}
