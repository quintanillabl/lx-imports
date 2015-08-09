package com.luxsoft.impapx.tesoreria

import grails.transaction.Transactional
import com.luxsoft.impapx.CuentaBancaria

@Transactional
class SaldoDeCuentaService {

    def actualizarSaldos(Date periodo) {
    	def cuentas=CuentaBancaria.list()
    	def saldos=[]
    	cuentas.each{
    		saldos<<actualizarSaldo(it,periodo)
    	}
    	return saldos
    }

    def actualizarSaldo(CuentaBancaria cuenta,Date periodo){
    	
    	def fechaIni=periodo.inicioDeMes()
    	def fechaFin=periodo.finDeMes()
    	def month=periodo.toMonth()
    	def year=periodo.toYear()
    	log.info("Actualiando saldo para cuenta ${cuenta} periodo:${periodo.asPeriodoText()}")
    	
    	def saldoFinalMesAnterior=MovimientoDeCuenta
    		.executeQuery("select sum(x.importe) from MovimientoDeCuenta x where x.cuenta=? and date(fecha) < ?",
    			[cuenta,fechaIni])[0]?:00
    	def hql="select sum(x.importe) from MovimientoDeCuenta x where x.cuenta=? and date(x.fecha) between ? and ? and ingreso=?"
    	def ingresos=MovimientoDeCuenta.executeQuery(hql,[cuenta,fechaIni,fechaFin,true])[0]?:00
    	def egresos=MovimientoDeCuenta.executeQuery(hql,[cuenta,fechaIni,fechaFin,false])[0]?:00
    	
    	def saldo=SaldoDeCuenta.findOrCreateByCuentaAndYearAndMes(cuenta,year,month)
    	saldo.saldoInicial=saldoFinalMesAnterior
    	saldo.ingresos=ingresos
    	saldo.egresos=egresos
    	saldo.saldoFinal=saldoFinalMesAnterior+(ingresos+egresos)
    	saldo.tc=0
    	saldo.saldoFinalMN=0.0
    	saldo.save(flush:true)
    }

    def actualizarSaldo(MovimientoDeCuenta movimiento){
    	return actualizarSaldo(movimiento.cuenta,movimiento.fecha)
    }
}
