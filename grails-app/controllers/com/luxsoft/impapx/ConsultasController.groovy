package com.luxsoft.impapx

import grails.plugin.springsecurity.annotation.Secured
import groovy.sql.Sql;
import com.luxsoft.utils.Periodo
import org.grails.databinding.BindingFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import com.luxsoft.lx.bi.*


@Secured(["hasRole('USUARIO')"])
class ConsultasController {
	
	def dataSource_importacion

	def reportService

    def index() {
		def db=new Sql(dataSource_importacion)
		def res=db.rows("select * from sw_sucursales")
		[rows:res]
	}

	

	def proveedores(){
		params.max = 1000
		params.sort=params.sort?:'lastUpdated'
		params.order='desc'
		respond Proveedor.list(params)
	}
	def proveedor(Proveedor proveedorInstance){
		//def pendientes = CuentaPorPagar.where {proveedor==proveedorInstance && (total-pagosAplicados)>0}.list()

		def inicio = Date.parse('dd/MM/yyyy','30/09/2015')
		def pendientes = CuentaPorPagar
			.findAll("from CuentaPorPagar c where c.proveedor=? and (c.total-c.pagosAplicados)>0 and date(c.fecha)>? order by c.vencimiento ",[proveedorInstance,inicio])
		respond proveedorInstance,model:[pendientes:pendientes]
	}

	def estadoDeCuentaProveedor(EstadoDeCuentaProveedor command){
        println 'Empresa: '+session.empresa
        command.empresa=session.empresa
        def stream=reportService.build(command,[
            PROVEEDOR:command.proveedor.id as String,
            FECHA:command.fechaCorte,
            COMPANY:session.empresa.nombre])
        def file='EstadoDeCuentaCXP_'+command.proveedor.id+'_'+new Date().format('mmss')+'.'+command.formato.toLowerCase()
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
    }

    def show(CuentaPorPagar cxp){
    	if(cxp.instanceOf(FacturaDeImportacion)){
    		redirect action:'show',controller:'facturaDeImportacion',id:cxp.id
    		return
    	}else if(cxp.instanceOf(FacturaDeGastos)){
    		redirect action:'show',controller:'facturaDeGastos',id:cxp.id
    		return
    	}else if(cxp.instanceOf(GastosDeImportacion)){
    		redirect action:'show',controller:'gastosDeImportacion',id:cxp.id
    		return
    	}
    	redirect action:'proveedor',id:cxp.id
    }
}

class EstadoDeCuentaProveedor extends ReportCommand{

	Proveedor proveedor

	@BindingFormat('dd/MM/yyyy')
	Date fechaCorte

	EstadoDeCuentaProveedor(){
		this.reportName='EstadoDeCuentaCXP'
	}

}
