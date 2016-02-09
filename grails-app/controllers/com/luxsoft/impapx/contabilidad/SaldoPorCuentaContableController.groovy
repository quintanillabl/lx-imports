	package com.luxsoft.impapx.contabilidad

import grails.converters.JSON

import java.text.NumberFormat;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('CONTABILIDAD')"])
class SaldoPorCuentaContableController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
	
	def saldoPorCuentaContableService

 	def index() {
		def periodo=session.periodoContable
		def saldos=SaldoPorCuentaContable
			.findAll("from SaldoPorCuentaContable c where c.cuenta.detalle=? and c.year=? and c.mes=? order by c.cuenta.clave"
			,[false,periodo.ejercicio,periodo.mes])
		[saldoPorCuentaContableInstanceList: saldos, saldoPorCuentaContableInstanceTotal: saldos.size()]
	}

    def show() {
        def saldoPorCuentaContableInstance = SaldoPorCuentaContable.get(params.id)
        if (!saldoPorCuentaContableInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'saldoPorCuentaContable.label', default: 'SaldoPorCuentaContable'), params.id])
            redirect action: 'list'
            return
        }

        [saldoPorCuentaContableInstance: saldoPorCuentaContableInstance]
    }

    def delete() {
        def saldoPorCuentaContableInstance = SaldoPorCuentaContable.get(params.id)
        if (!saldoPorCuentaContableInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'saldoPorCuentaContable.label', default: 'SaldoPorCuentaContable'), params.id])
            redirect action: 'list'
            return
        }

        try {
            saldoPorCuentaContableInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'saldoPorCuentaContable.label', default: 'SaldoPorCuentaContable'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'saldoPorCuentaContable.label', default: 'SaldoPorCuentaContable'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def actualizarSaldos(){
		def periodo=session.periodoContable
		saldoPorCuentaContableService.actualizarSaldos(periodo.ejercicio, periodo.mes)
		redirect action:'index'
	}
	
	def subcuentas(long id){
		if(!session.periodoContable){
			PeriodoContable periodo=new PeriodoContable()
			periodo.actualizarConFecha()
			session.periodoContable=periodo
		}
		def sal=SaldoPorCuentaContable.get(id)
		def saldos=SaldoPorCuentaContable
			.findAll("from SaldoPorCuentaContable c where c.cuenta.padre=? and c.year=? and c.mes=? order by c.cuenta.clave asc"
			,[sal.cuenta,sal.year,sal.mes])
		[saldo:sal,saldoPorCuentaContableInstanceList: saldos
			, saldoPorCuentaContableInstanceTotal: saldos.size(),]
	}
	
	def auxiliar(long id,int year,int month){
		println 'Auxiliar contable: '+params
		def saldo=SaldoPorCuentaContable.get(id)
		def dets=PolizaDet.findAll("from PolizaDet d where d.cuenta=? and date(d.fecha) between ? and ?"
			,[saldo.cuenta,saldo.fecha.inicioDeMes(),saldo.fecha.finDeMes()])
		def saldoPadre=SaldoPorCuentaContable.get(params.saldoPadre)
		[saldo:saldo,partidas:dets,saldoPadre:saldoPadre]
	}
	
	def imprimirAuxiliarContable(){
		println 'Imprimiento auxiliar'+params
		
		def saldo=SaldoPorCuentaContable.get(params.long('id'))
		
		if(!saldo)
			throw new RuntimeException("No existe saldo : "+id)
			
		def dets=PolizaDet.findAll("from PolizaDet d where d.cuenta=? and date(d.fecha) between ? and ? order by d.fecha"
			,[saldo.cuenta,saldo.fecha.inicioDeMes(),saldo.fecha.finDeMes()])
		
		def saldoPadre=SaldoPorCuentaContable.get(saldo.cuenta.padre.id)
		
		def acumulado=0.0
		def modelData=dets.collect { det ->
			acumulado=(acumulado+(det.debe-det.haber))
			def res=[
			'Poliza':det.poliza.folio.toLong()
			,'Tipo':det.poliza.tipo
			,'Fecha':det.poliza.fecha
			,'Descripcion':det.descripcion
			,'Debe':det.debe
			 ,'Haber':det.haber
			 ,'Asiento':det.asiento
			 ,'Acumulado':acumulado
			 ]
			return res
		}
		NumberFormat nf=NumberFormat.getNumberInstance()
		def repParams=[CUENTA:saldo.cuenta.clave
			,FECHA_INI:saldo.fecha.inicioDeMes().text()
			,FECHA_FIN:saldo.fecha.finDeMes().text()
			,INICIAL:nf.format(saldo.saldoInicial)
			,CARGOS:nf.format(saldo.debe)
			,ABONOS:nf.format(saldo.haber)
			,SALDO:nf.format(saldo.saldoFinal)
			,YEAR:saldo.year.toString()
			,MES:saldo.mes.toString()
			,CONCEPTO:saldo.cuenta.descripcion
			]
		repParams<<params
		println 'Ejecutando reporte params:'+params+'\n Registros: '+modelData
		chain(controller:'jasper',action:'index',model:[data:modelData],params:repParams)
		
	}
	
	def reclasificarCuenta(){
		println 'Reclasificando cuentas para :'+params
		def data=[:]
		def saldo=SaldoPorCuentaContable.get(params.long('saldoId'))
		def destino=CuentaContable.get(params.long('destinoId'))
		JSONArray partidas=JSON.parse(params.partidas);
		
		try {
			saldoPorCuentaContableService.reclasificarMovimientos(saldo, destino, partidas)
			data.res='PARTIDAS_RECLASIFICADAS'
		}
		catch (RuntimeException e) {
			e.printStackTrace()
			data.res="ERROR"
			data.error=ExceptionUtils.getRootCauseMessage(e)
		}
		render data as JSON
	}
	
	def cierreAnual(){
		
		def periodo = session.periodoContable
		
		println 'Presentando cierre del periodo:'+periodo.ejercicio
		
		def sort=params.sort?:'fecha'
		def order=params.order?:'desc'
		
		def saldos=SaldoPorCuentaContable.findAll(
			"from SaldoPorCuentaContable c where c.cuenta.detalle=? and c.year=? and c.mes=? order by c.cuenta.clave"
			,[false,periodo.ejercicio,13])
		[saldoPorCuentaContableInstanceList: saldos
			, saldoPorCuentaContableInstanceTotal: saldos.size()
			,periodoCierre:periodoCierre]
	}
	
	def generarCierreAnual(){
		log.info 'Generando cierre anual: '+session.periodoContable
		saldoPorCuentaContableService.cierreAnual(session.periodoContable)
		flash.message = "Saldos para el cierre anual generados"
		redirect action:'index'
		
	}
	
	def actualizarCierreAnual(){
		saldoPorCuentaContableService.actualizarCierreAnual(session.periodoContable.ejercicio)
		redirect action:'index'
	}
}
