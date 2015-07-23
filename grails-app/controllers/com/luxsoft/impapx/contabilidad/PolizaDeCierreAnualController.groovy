package com.luxsoft.impapx.contabilidad

class PolizaDeCierreAnualController {

    def polizaService

   def index() {
	   redirect action: 'list', params: params
    }
	 
	def mostrarPoliza(long id){
		def poliza=Poliza.findById(id,[fetch:[partidas:'eager']])
		render (view:'/poliza/poliza2' ,model:[poliza:poliza,partidas:poliza.partidas])
	}
	 
	def list() {
		if(!session.periodoContable){
			PeriodoContable periodo=new PeriodoContable()
			periodo.actualizarConFecha()
			session.periodoContable=periodo
		}
		PeriodoContable periodo=session.periodoContable
		def sort=params.sort?:'fecha'
		def order=params.order?:'desc'
		
		def polizas=Poliza.findAllByTipoAndDescripcionLikeAndFechaBetween('CIERRE_ANUAL'
			,'CIERRE ANUAL %'+periodo.year
			,periodo.inicio,periodo.fin,[sort:sort,order:order])
		[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
	}
	
	def generarSaldos(){
		
	}
	
	def generarPoliza(String fecha){
		Date dia=Date.parse("dd/MM/yyyy",fecha)
		
		params.dia=dia
		
		//Prepara la poliza
		Poliza poliza=new Poliza(tipo:'GENERICA',folio:1, fecha:dia,descripcion:'CIERRE ANUAL '+dia.toYear(),partidas:[])
		// Procesadores
		generar(poliza, dia)
		cancelacionIETU(poliza, dia)
		//Salvar la poliza
		poliza.tipo='CIERRE_ANUAL'
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		
		poliza=polizaService.salvarPoliza(poliza)
		redirect action: 'mostrarPoliza', params: [id:poliza.id]
	}
	
	def generar(def poliza , def dia){
		println 'Generando poliza de cierre anual: '+dia
		def asiento="CIERRE ANUAL "+dia.toYear()
		def saldos=SaldoPorCuentaContable
			.findAll("from SaldoPorCuentaContable s where s.year=? and s.mes=13 and s.cuenta.deResultado=true and s.cuenta.padre!=null and s.cuenta.clave not like ?"
				,[dia.toYear(),'90%'])
		BigDecimal cargos=0
		BigDecimal abonos=0	
		saldos.each {saldo->
			BigDecimal imp=saldo.saldoInicial
			if(imp>0){
				poliza.addToPartidas(
					cuenta:saldo.cuenta,
					debe:0.0,
					haber:imp,
					asiento:asiento,
					descripcion:asiento,
					referencia:"",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'SaldoPorCuetaContable'
					,origen:saldo.id)
				abonos+=imp
			}else if(imp<0){
			poliza.addToPartidas(
				cuenta:saldo.cuenta,
				debe:imp.abs(),
				haber:0.0,
				asiento:asiento,
				descripcion:asiento,
				referencia:"",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'SaldoPorCuetaContable'
				,origen:saldo.id)
				cargos+=imp.abs()
			}
			
		}
		def resultado=cargos-abonos
		
		CuentaContable cuenta=CuentaContable.buscarPorClave("304-0013")
		if(resultado){
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:0.0,
				haber:resultado,
				asiento:asiento,
				descripcion:asiento,
				referencia:"",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'SaldoPorCuetaContable'
				,origen:cuenta.id)
		}else{
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:resultado.abs(),
				haber:0.0,
				asiento:asiento,
				descripcion:asiento,
				referencia:"",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'SaldoPorCuetaContable'
				,origen:cuenta.id)
		}
		
	}
	
	def cancelacionIETU(def poliza , def dia){
		println 'Generando poliza de cierre anual: '+dia
		def asiento="CANCELACION IETU "+dia.toYear()
		def saldos=SaldoPorCuentaContable
			.findAll("from SaldoPorCuentaContable s where s.year=? and s.mes=13 and s.cuenta.deResultado=true and s.cuenta.padre!=null and s.cuenta.clave like ?"
				,[dia.toYear(),'90%'])
		BigDecimal cargos=0
		BigDecimal abonos=0
		saldos.each {saldo->
			BigDecimal imp=saldo.saldoInicial
			if(imp>0){
				poliza.addToPartidas(
					cuenta:saldo.cuenta,
					debe:0.0,
					haber:imp,
					asiento:asiento,
					descripcion:asiento,
					referencia:"",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'SaldoPorCuetaContable'
					,origen:saldo.id)
				abonos+=imp
			}else if(imp<0){
			poliza.addToPartidas(
				cuenta:saldo.cuenta,
				debe:imp.abs(),
				haber:0.0,
				asiento:asiento,
				descripcion:asiento,
				referencia:"",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'SaldoPorCuetaContable'
				,origen:saldo.id)
				cargos+=imp.abs()
			}
			
		}
		
		
	}
	
}
