package com.luxsoft.impapx.contabilidad

import grails.validation.ValidationException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationListener;

import com.luxsoft.impapx.cxp.NotaDeCredito;
import com.luxsoft.impapx.tesoreria.SaldoDeCuenta;

class PolizaService implements ApplicationListener<PolizaUpdateEvent>{
	
	def saldoPorCuentaContableService
	
	def salvarPolizaDiario(Poliza poliza){
		if(poliza.tipo=='DIARIO'){
			def found=Poliza.findByTipoAndDescripcionAndFecha(poliza.tipo,poliza.descripcion,poliza.fecha)
			if(found){
				poliza.folio=found.folio
				found.delete(flush:true)
			}else{
				poliza.folio=nextFolio(poliza)
			}
			poliza.cuadrar()
			poliza.save(failOnError:true)
			return poliza
		}else{
			throw new RuntimeException("No es poliza de diario")
		}
	}
	
	def salvarPoliza(Poliza poliza){
		if(poliza.tipo=='DIARIO'){
			throw new RuntimeException("Poliza de diario no se puede salvar por este metodo")
		}
		def found=Poliza.findByTipoAndFecha(poliza.tipo,poliza.fecha)
		if(poliza.tipo=='EGRESO'){
			found=Poliza.findByTipoAndDescripcionAndFecha(poliza.tipo,poliza.descripcion,poliza.fecha)
		}
		if(found){
			poliza.folio=found.folio
			found.delete(flush:true)
		}else{
			poliza.folio=nextFolio(poliza)
		}
		poliza.cuadrar()
		poliza.save(failOnError:true)
		return poliza
	}
	
	def nextFolio(Poliza poliza){
		def year=poliza.fecha.toYear()
		def mes=poliza.fecha.toMonth()
		def max=Poliza.executeQuery(
			"select max(p.folio) from Poliza p where p.tipo=? and year(p.fecha)=? and month(p.fecha)=?"
			,[poliza.tipo,year,mes])
		def folio=max[0]?:0
		folio+=1
	}

    def crearPolizaGenerica(Poliza poliza) {
		def year=poliza.fecha.toYear()
		def mes=poliza.fecha.toMonth()
		def max=Poliza.executeQuery(
			"select max(p.folio) from Poliza p where p.tipo='GENERICA' and year(p.fecha)=? and month(p.fecha)=?"
			,[year,mes])
		def folio=max[0]?:0
		folio+=1 
		poliza.debe=0.0
		poliza.haber=0.0
		poliza.folio=folio
		try {
			poliza.save(failOnError:true)
			return poliza
		} catch (ValidationException e) {
			return poliza
		}
    }
	
	def agregarPartida(long polizaId, def params){
		
		//println 'Agregando partida: '+params+ 'A poliza: '+polizaId
		//def poliza=Poliza.findById(polizaId,[fetch:[partidas:'eager']])
		def poliza=Poliza.get(polizaId)
		params.id=null
		
		def det=new PolizaDet(params)
		det.fecha=poliza.fecha
		det.tipo=poliza.tipo
		det.debe?:0.0
		det.haber?:0.0
		
		poliza.addToPartidas(cuenta:det.cuenta,
					debe:det.debe,
					haber:det.haber,
					asiento:det.asiento,
					descripcion:det.descripcion,
					referencia:det.referencia
					,fecha:poliza.fecha
					,tipo:poliza.tipo)
		poliza.cuadrar()
		//poliza.save(failOnError:true)
		/*
		try {
			poliza.save(failOnError:true)
			return poliza
		} catch (ValidationException e) {
			e.printStackTrace()
			return poliza
		}*/
	}
	
	def eliminarPartidas(Poliza poliza,def partidas) {
		partidas.each {
			def det=PolizaDet.get(it.toLong())
			//println 'Eliminando partida de poliza: '+det
			if(det){
				poliza.removeFromPartidas(det)
				//poliza.cuadrar()
				poliza=poliza.save(failOnError:true)
				//poliza=salvarPoliza(poliza)
			}
		}
	}

	@Override
	public void onApplicationEvent(PolizaUpdateEvent event) {
		//println "Evento en poliza: calculando saldos de cuentas contables para la poliza: "+event.source
		actualizarSaldos(event.source)
	}
	
	def actualizarSaldos(def poliza){
		if(poliza.tipo=='CIERRE_ANUAL'){
			return
		}
		int year=poliza.fecha.toYear()
		int mes=poliza.fecha.toMonth()
		def cuentas=PolizaDet.executeQuery("select distinct(d.cuenta) from PolizaDet d where d.poliza=?",[poliza])
		
		
		cuentas.each{ c->
			
			saldoPorCuentaContableService.actualizarSaldo(year, mes, c)
			
		}
	}
}
