package com.luxsoft.impapx.tesoreria

import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.CuentaBancaria;
import com.luxsoft.impapx.cxc.CXCPago;
import com.luxsoft.impapx.cxp.Anticipo;
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('TESORERIA')"])
class MovimientoDeCuentaController {

    

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def beforeInterceptor = {
    	if(!session.periodoTesoreria){
    		session.periodoTesoreria=new Date()
    	}
	}

	def cambiarPeriodo(){
		def fecha=params.date('fecha', 'dd/MM/yyyy')
		session.periodoTesoreria=fecha
		redirect(uri: request.getHeader('referer') )
	}

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        def periodo=session.periodoTesoreria
        def tipo=params.tipo?:'TODOS'
        def list=[]
        def count=0
        if(tipo=='TODOS'){
        	//list=MovimientoDeCuenta.list(params)
        	list=MovimientoDeCuenta.findAllByFechaBetween(periodo.inicioDeMes(),periodo.finDeMes(),params)
        	count=MovimientoDeCuenta.countByFechaBetween(periodo.inicioDeMes(),periodo.finDeMes())
        }else{

        	boolean ingreso=tipo=='DEPOSITOS'
        	list=MovimientoDeCuenta.findAllByFechaBetweenAndIngreso(periodo.inicioDeMes(),periodo.finDeMes(),ingreso,params)
        	count=MovimientoDeCuenta.countByFechaBetweenAndIngreso(periodo.inicioDeMes(),periodo.finDeMes(),ingreso)
        }
        respond list, model:[movimientoDeCuentaInstanceCount: count,tipo:tipo]
    }
    
	
	def cobros() {
		params.max = Math.min(params.max ? params.int('max') : 100, 1000)
		[CXCPagoInstanceList: CXCPago.list(params), CXCPagoInstanceTotal: CXCPago.count()]
	}
	
	

	def depositar(){
		def movimientoDeCuentaInstance=new MovimientoDeCuenta(fecha:new Date(),ingreso:true,tc:1.00)
		def anticipos=Anticipo.findAll(
			"from Anticipo a where  a.sobrante is null and a.requisicion.concepto=? and a.total-a.requisicion.total>0"
			,['ANTICIPO'])
		render view:'create',model:[movimientoDeCuentaInstance: movimientoDeCuentaInstance,
			conceptos:Conceptos.INGRESOS,
			anticipos:anticipos]
	}
	
	def retirar(){
		def movimientoDeCuentaInstance=new MovimientoDeCuenta(fecha:new Date(),ingreso:false,tc:1.00)
		def anticipos=Anticipo.findAll(
			"from Anticipo a where  a.sobrante is null and a.requisicion.concepto=? and a.total-a.requisicion.total>0"
			,['ANTICIPO'])
		render view:'create',model:[movimientoDeCuentaInstance: movimientoDeCuentaInstance,
			conceptos:Conceptos.EGRESOS,
			anticipos:anticipos]
	}

	def save(MovimientoDeCuentaCommand command){
		log.debug("Salvando movimiento de cuenta: "+command)
		if (command == null) {
		    notFound()
		    return
		}
		if (command.hasErrors()) {
		    render view:'create',model:[movimientoDeCuentaInstance:command,conceptos:command.ingreso?Conceptos.INGRESOS:Conceptos.EGRESOS]
		    return
		}

		def movimiento=command.toMovimiento()
		movimiento=movimiento.save flush:true ,failOnError:true
		flash.message = "Movimiento ${movimiento.id} registrado "
		if(command.anticipo){
			def anticipo=command.anticipo
			anticipo.sobrante=movimiento
			anticipo.save flush:true,failOnError:true
			flash.message+=" Con anticipo ${anticipo.id}"
		}
		
		redirect action:'index',id:movimiento.id,params:[tipo:movimiento.ingreso?'DEPOSITOS':'RETIROS']
	}

    def create() {
		switch (request.method) {
		case 'GET':
		redirect action:'depositar'
		return
			// //println 'Alta de movimiento :'+params.tipo
			// params.fecha=new Date()
   //      	[movimientoDeCuentaInstance: new MovimientoDeCuenta(params),conceptos:params.conceptos]
			// break
		case 'POST':
			println 'Generando movimiento: '+params
			
	        def movimientoDeCuentaInstance = new MovimientoDeCuenta(params)
			def cuenta=CuentaBancaria.get(params.cuenta.id)
			movimientoDeCuentaInstance.cuenta=cuenta
			movimientoDeCuentaInstance.moneda=cuenta.moneda
			movimientoDeCuentaInstance.origen='TESORERIA'
			movimientoDeCuentaInstance.tipo='TRANSFERENCIA'
			if(movimientoDeCuentaInstance.ingreso){
				movimientoDeCuentaInstance.importe=movimientoDeCuentaInstance.importe.abs()
			}else{
				movimientoDeCuentaInstance.importe=movimientoDeCuentaInstance.importe.abs()*-1
			}
			movimientoDeCuentaInstance.concepto="$movimientoDeCuentaInstance.concepto $movimientoDeCuentaInstance.comentario"
			
	        if (!movimientoDeCuentaInstance.save(flush: true)) {
	            //render view: 'index', model: [movimientoDeCuentaInstance: movimientoDeCuentaInstance]
	            redirect view:'index', params:[tipo:movimientoDeCuenta.ingreso?'DEPOSITOS':'RETIROS']
	            return
	        }
			
			if(params.anticipoId){
				def anticipo=Anticipo.get(params.long('anticipoId'))
				anticipo.sobrante=movimientoDeCuentaInstance
				anticipo.save()
			}

			flash.message = message(code: 'default.created.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), movimientoDeCuentaInstance.id])
	        redirect action: 'show', id: movimientoDeCuentaInstance.id
			break
		}
    }

    def show() {
        def movimientoDeCuentaInstance = MovimientoDeCuenta.get(params.id)
        if (!movimientoDeCuentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
            redirect action: 'list'
            return
        }

        [movimientoDeCuentaInstance: movimientoDeCuentaInstance]
    }

    def edit() {
		redirect action:'show',model:params
    }

    def delete() {
        def movimientoDeCuentaInstance = MovimientoDeCuenta.get(params.id)
        if (!movimientoDeCuentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
            redirect action: 'list'
            return
        }

        try {
            movimientoDeCuentaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta'), params.id])
            redirect action: 'show', id: params.id
        }
       
    }

    def search(){
        def term='%'+params.term.trim()+'%'
        def query=MovimientoDeCuenta.where{
            (id.toString()=~term || cuenta.numero=~term || cuenta.banco.nombre=~term || referenciaBancaria=~term || importe.toString()=~term) 
        }
        def rows=query.list(max:30, sort:"id",order:'desc')

        def res=rows.collect { mov ->
            def label=" ${mov.cuenta.numero} (${mov.cuenta.banco.nombre}) ${mov.concepto} ${mov.fecha.format('dd/MM/yyyy')} ${mov.importe} (${mov.moneda}) ${mov.referenciaBancaria}"
            [id:mov.id,label:label,value:label]
        }
        render res as JSON
    }
	
	
}

import org.grails.databinding.BindingFormat
import com.luxsoft.impapx.contabilidad.CuentaContable
import groovy.transform.ToString
import com.luxsoft.impapx.cxp.Anticipo

@ToString(includeNames=true,includePackage=false)
class MovimientoDeCuentaCommand {

	CuentaBancaria cuenta

	@BindingFormat('dd/MM/yyyy')
	Date fecha

	BigDecimal tc

	BigDecimal importe

	String concepto
	

	String comentario

	String referenciaBancaria

	boolean ingreso

	CuentaContable cuentaDeudora

	Anticipo anticipo

	static constraints={
	    importFrom MovimientoDeCuenta
	    importe min:1.0
	    anticipo nullable:true
	}

	MovimientoDeCuenta toMovimiento(){
	    def m=new MovimientoDeCuenta()
	    m.properties=properties
	    m.origen='TESORERIA'
		m.tipo='TRANSFERENCIA'
		m.concepto="$concepto $comentario"
		if(ingreso){
			m.importe=m.importe.abs()
		}else{
			m.importe=m.importe.abs()*-1
		}
		m.moneda=m.cuenta.moneda
	    return m
	}

}

