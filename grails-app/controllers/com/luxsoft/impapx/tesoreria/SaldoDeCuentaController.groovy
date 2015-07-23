package com.luxsoft.impapx.tesoreria

import java.text.NumberFormat;

import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.CuentaBancaria;

class SaldoDeCuentaController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }
	
	def cambiarPeriodo(String fecha){
		println 'Cambiando periodo: '+fecha
		def periodo=fecha?Date.parse("dd/MM/yyyy",fecha):new Date()
		session.periodo=periodo
		redirect action:'list'
	}

    def list() {
		log.info('Saldo de cuentas')
		
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		if(!session.periodo){
			session.periodo=new Date()
		}
		
		//def periodo=params.periodo?Date.parse("dd/MM/yyyy",params.periodo):new Date()
		def periodo=session.periodo
		def rango=periodo.asPeriodoText()
		
		def saldoDeCuentasList=SaldoDeCuenta.findAllByYearAndMes(periodo.toYear(),periodo.toMonth())
        [saldoDeCuentaInstanceList: saldoDeCuentasList, saldoDeCuentaInstanceTotal: saldoDeCuentasList.size(),periodo:periodo,rango:rango]
    }
   

    def show() {
        def saldoDeCuentaInstance = SaldoDeCuenta.get(params.id)
        if (!saldoDeCuentaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'saldoDeCuenta.label', default: 'SaldoDeCuenta'), params.id])
            redirect action: 'list'
            return
        }
        [saldoDeCuentaInstance: saldoDeCuentaInstance]
    }
	
	def actualizarSaldos(){
		//def periodo=params.periodo?Date.parse(params.periodo):new Date()
		println("Actualizando saldos al: "+session.periodo)
		Date periodo=session.periodo
		//Date periodo=Date.parse("dd/MM/yyyy", params.periodo).inicioDeMes()
		//Date periodo=params.periodo
		//Obtenemos el saldo al inicio del mes
		def month=periodo.toMonth()
		def year=periodo.toYear()
		log.info("Calculando saldos para el periodo: $month - $year")
		println "Calculando saldos para el periodo: $month - $year"
		
		
		def fechaIni=periodo.inicioDeMes()
		def fechaFin=periodo.finDeMes()
		
		//Obtenemos una lista de las cuentas
		def cuentas=CuentaBancaria.list()
		cuentas.each{
			
			//Calculand el saldo inicial
			//println 'Calculando el saldo final al: '+fechaIni
			def saldoFinalMesAnterior=MovimientoDeCuenta.executeQuery("select sum(x.importe) from MovimientoDeCuenta x where x.cuenta=? and date(fecha) < ?"
				,[it,fechaIni])[0]?:00
			//Calculando los movimientos del mes
			def hql="select sum(x.importe) from MovimientoDeCuenta x where x.cuenta=? and date(x.fecha) between ? and ? and ingreso=?"
			def ingresos=MovimientoDeCuenta.executeQuery(hql,[it,fechaIni,fechaFin,true])[0]?:00
			def egresos=MovimientoDeCuenta.executeQuery(hql,[it,fechaIni,fechaFin,false])[0]?:00
			//def ingresos=res[0]?:0.0
			//println "$it  Saldi Inicial:$saldoFinal  Ingresos: $ingresos  Egresos: $egresos"
			
			def saldo=SaldoDeCuenta.findOrCreateByCuentaAndYearAndMes(it,year,month)
			saldo.saldoInicial=saldoFinalMesAnterior
			saldo.ingresos=ingresos
			saldo.egresos=egresos
			saldo.saldoFinal=saldoFinalMesAnterior+(ingresos+egresos)
			saldo.tc=0
			saldo.saldoFinalMN=0.0
			saldo.save(flush:true)
		}
		flash.message='Saldos actualizados al: '+periodo.text()
		redirect (action:'list',params:params) 
	}
	
	def detalleDeMovimientos(long id,String fecha){
		def cuenta=CuentaBancaria.get(id)
		
		if(!cuenta)
			throw new RuntimeException("No existe la cuenta: "+id)
		if(fecha)
			session.periodo=Date.parse("dd/MM/yyyy", fecha)
		//def periodo=Date.parse("dd/MM/yyyy", params.periodo).inicioDeMes()
		def periodo=session.periodo
		def saldo=SaldoDeCuenta.findOrCreateByCuentaAndYearAndMes(cuenta,periodo.toYear(),periodo.toMonth())
		println "Detalle de movientos Cuenta: $cuenta  Saldo:$saldo Periodo:$periodo "
		if(saldo.id==null){
			flash.message="No registro de saldo final para la cuenta: $cuenta en el periodo:$periodo"
		}
		//def movimientos=MovimientoDeCuenta.findAllByCuentaAndFechaBetween(saldo.cuenta,periodo.inicioDeMes(),periodo.finDeMes(),sort:'fecha','id')
		def movimientos=MovimientoDeCuenta.findAll("from MovimientoDeCuenta where date(fecha) between ? and ? and cuenta=? order by fecha,id ",[periodo.inicioDeMes(),periodo.finDeMes(),cuenta])
		[saldoDeCuenta:saldo,movimientos:movimientos,periodo:periodo]
	}
	
	def imprimirEstadoDeCuenta(){
		
		def cuenta=CuentaBancaria.get(params.cuentaId)
		
		if(!cuenta)
			throw new RuntimeException("No existe la cuenta: "+id)
		println 'Periodo: '+session.periodo+ ' Tipo: '+session.periodo.class
		//def periodo=Date.parse("dd/MM/yyyy", session.periodo)
		def periodo=session.periodo
		
		println "Generando estado de cuenta para:$cuenta  al:$periodo"
		
		def fechaIni=periodo.inicioDeMes()
		def fechaFin=periodo.finDeMes()
		
		def saldoInicial=MovimientoDeCuenta.executeQuery("select sum(x.importe) from MovimientoDeCuenta x where x.cuenta=? and date(fecha) < ?"
			,[cuenta,fechaIni])[0]?:0.0
		//Calculando los movimientos del mes
		//def hql="select sum(x.importe) from MovimientoDeCuenta x where x.cuenta=? and date(x.fecha) between ? and ? and ingreso=?  "
		def ingresos=MovimientoDeCuenta.executeQuery("select sum(x.importe) from MovimientoDeCuenta x where x.cuenta=? and date(x.fecha) between ? and ? and importe>0  "
			,[cuenta,fechaIni,fechaFin])[0]?:00
		def egresos=MovimientoDeCuenta.executeQuery("select sum(x.importe) from MovimientoDeCuenta x where x.cuenta=? and date(x.fecha) between ? and ? and importe<0  "
			,[cuenta,fechaIni,fechaFin])[0]?:00
		def saldoFinal=saldoInicial+(ingresos+egresos)
		
		//def movimientos=MovimientoDeCuenta.findAllByCuentaAndFechaBetween(cuenta,fechaIni,fechaFin,[sort:('fecha')])
		def movimientos=MovimientoDeCuenta.findAll("from MovimientoDeCuenta where date(fecha) between ? and ? and cuenta=? order by fecha,id ",[periodo.inicioDeMes(),periodo.finDeMes(),cuenta])
		def modelData=movimientos.collect { mov ->
			
			def res=[
			//'FOLIO':mov.id
			'FECHA':mov.fecha.format("dd"),
			 'CONCEPTO':mov.concepto
			 ,'TIPO':mov.tipo
			,'INGRESO':mov.importe>0?mov.importe.abs():0.0
			,'EGRESO':mov.importe<0?mov.importe.abs():0.0
			 ,'COMENTARIO':mov.comentario
			 ,'REFERENCIA':mov.referenciaBancaria
			 ,INI:saldoInicial
			 ]
			return res
		}
		NumberFormat nf=NumberFormat.getNumberInstance()
		def repParams=[CUENTA:cuenta.toString()
			,FECHA_INI:fechaIni.text()
			,FECHA_FIN:fechaFin.text()
			,SALDO_INI:nf.format(saldoInicial)
			,SALDO_FIN:nf.format(saldoFinal)
			,INGRESOS:nf.format(ingresos.abs())
			,EGRESOS:nf.format(egresos.abs())
			]
		//params<<repParams
		repParams<<params
		println 'Ejecutando reporte params:'+repParams+'\n Registros: '+modelData
		chain(controller:'jasper',action:'index',model:[data:modelData],params:repParams)
		
	}

}
