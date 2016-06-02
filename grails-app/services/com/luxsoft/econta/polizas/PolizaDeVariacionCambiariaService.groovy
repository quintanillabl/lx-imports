package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import org.apache.commons.lang.time.DateUtils
import com.luxsoft.cfdi.Cfdi
import com.luxsoft.impapx.FacturaDeGastos
import com.luxsoft.impapx.FacturaDeImportacion
import com.luxsoft.impapx.Pedimento
import com.luxsoft.impapx.TipoDeCambio
import com.luxsoft.impapx.Venta
import com.luxsoft.impapx.VentaDet
import com.luxsoft.impapx.cxc.CXCAplicacion
import com.luxsoft.impapx.cxc.CXCNota
import com.luxsoft.impapx.cxp.Aplicacion
import com.luxsoft.impapx.cxp.NotaDeCredito
import com.luxsoft.impapx.tesoreria.Comision
import com.luxsoft.impapx.tesoreria.CompraDeMoneda
import com.luxsoft.impapx.tesoreria.Inversion
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta
import com.luxsoft.impapx.tesoreria.PagoProveedor
import com.luxsoft.impapx.tesoreria.SaldoDeCuenta
import com.luxsoft.impapx.tesoreria.Traspaso
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*

@Transactional
class PolizaDeVariacionCambiariaService extends ProcesadorService{

    def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de variación cambiaria "

        def dia = poliza.fecha

        def finDeMes=dia.finDeMes().clearTime()
    	if(dia.clearTime()!=finDeMes)
    		return "Esta póliza sólo se puede ejcutar el fin de mes"
        procesarVariacionCambiariaBancos(poliza,dia)
        cuadrar(poliza)
		depurar(poliza)
		save poliza
    }

    private procesarVariacionCambiariaBancos(def poliza,def dia){
    	
    	
    	def asiento="VARIACION CAMBIARIA BANCOS"
    	
    	def saldos=SaldoDeCuenta.executeQuery("from SaldoDeCuenta s where s.year=? and s.mes=? and s.cuenta.moneda!='MXN'",[dia.toYear(),dia.toMonth()])
    	
    	saldos.each{ saldo->
    		def fini=dia.inicioDeMes()-2
    		def tipoDeCambioIni=TipoDeCambio.findByFecha(fini)
    		if(!tipoDeCambioIni){
    			throw new RuntimeException("No existe el tipo de cambio registrado para el $fini")
    		}
    		def saldoInicialMN=saldo.saldoInicial*tipoDeCambioIni.factor
    		def movimientos=MovimientoDeCuenta.executeQuery(
    			"select sum(m.importe*m.tc) ,sum(m.importe) from MovimientoDeCuenta m where m.cuenta=? and  date(m.fecha) between ? and ? "
    			,[saldo.cuenta,dia.inicioDeMes(),dia.finDeMes()] )
    		
    		def movMN=movimientos[0][0]?:0.0
    		def mov=movimientos[0][1]?:0.0
    		
    		def saldoFinal=saldo.saldoInicial+mov
    		
    		def tipoDeCambio=TipoDeCambio.findByFecha(dia-1)
    		if(!tipoDeCambio){
    			throw new RuntimeException("No existe el tipo de cambio registrado para el $dia")
    		}
    		def saldoFinalMNActualizado=saldoFinal*tipoDeCambio.factor
    		def saldoFinalMN=saldoInicialMN+movMN
    		
    		def diferencia=saldoFinalMNActualizado-saldoFinalMN
    		
    		
    		def periodo=dia.asPeriodoText()
    		
    		
    		poliza.addToPartidas(
    			cuenta:saldo.cuenta.cuentaContable,
    			debe:diferencia>0?diferencia.abs():0.0,
    			haber:diferencia<0?diferencia.abs():0.0,
    			asiento:asiento,
    			descripcion:"Variacion cambiaria $periodo ",
    			referencia:"$saldo.cuenta.id",
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'NA'
    			,origen:0)
    		
    		def clave=diferencia>0?"702-0002":"701-0002"
    		poliza.addToPartidas(
    			cuenta:CuentaContable.buscarPorClave(clave),
    			debe:diferencia<0?diferencia.abs():0.0,
    			haber:diferencia>0?diferencia.abs():0.0,
    			asiento:asiento,
    			descripcion:"Variacion cambiaria $periodo ",
    			referencia:"$saldo.cuenta.id",
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'NA'
    			,origen:0)
    		
    	}
    	
    }
    
    private procesarVariacionCambiariaProveedores(def poliza,def dia){
    	
    	def finDeMes=dia.finDeMes().clearTime()
    	if(dia.clearTime()!=finDeMes)
    		return
    	def asiento="VARIACION CAMBIARIA PROVEEDORES"
    	
    	def facturasList=FacturaDeImportacion
    //		.executeQuery("from FacturaDeImportacion f where date(f.fecha)<=? and f.total-(select sum(x.total) from Aplicacion x where x.factura=f and date(x.fecha)<=?) >0 order by f.fecha",
    		.executeQuery("from FacturaDeImportacion f where date(f.fecha)<=? order by f.fecha",
    		[dia])
    	
    	def facturas=facturasList.findAll{ fac->
    		def pagosAplic=Aplicacion.executeQuery("select sum(a.total) from Aplicacion a where a.factura=? and date(a.fecha)<=?",[fac,dia])//[0][0]?:0.0
    		//println 'Pagos aplicados: '+pagosAplic
    		def pagos=pagosAplic[0]?:0.0
    		def saldo=fac.total-pagos
    		fac.saldoAlCorte=saldo
    		return saldo>0
    	}
    	
    	Map facturasPorProveedor=facturas.groupBy({
    		
    		it.proveedor
    		})
    	
    	facturasPorProveedor.entrySet().each{
    		def proveedor=it.key
    		//println 'Procesando diferencia cambiara para proveedor: '+proveedor+ " CtaOper: "+proveedor.subCuentaOperativa
    		def facs=it.value
    		
    		def saldo=0.0
    		def saldoActualizado=0.0
    		def tcProvAnterior=TipoDeCambio.findByFecha(dia.inicioDeMes()-2)
    		def tcCorte=TipoDeCambio.findByFecha(dia.finDeMes()-1)
    		
    		facs.each{ fac->
    			
    			
    			
    			def pedimentos=Pedimento.executeQuery("select det.pedimento from EmbarqueDet det where det.factura=?",[fac])
    			if(!pedimentos.isEmpty()){
    				def pedimento=pedimentos[0]
    				def fecha=pedimento.fecha
    				def tc=pedimento.tipoDeCambio
    				if(fecha.toMonth()!=dia.toMonth() ){
    					tc=tcProvAnterior.factor
    				}
    				saldo+=fac.saldoAlCorte*tc
    				saldoActualizado+=fac.saldoAlCorte*tcCorte.factor
    				//println " $proveedor, $fac.documento, $fac.saldoAlCorte, $tc, $tcCorte.factor "
    			}
    		}
    		
    		def diferencia=saldo-saldoActualizado
    		def periodo=dia.asPeriodoText()
    		if(diferencia.abs()>0){
    			poliza.addToPartidas(
    				cuenta:CuentaContable.buscarPorClave("201-${proveedor.subCuentaOperativa}"),
    				debe:diferencia>0?diferencia.abs():0.0,
    				haber:diferencia<0?diferencia.abs():0.0,
    				asiento:asiento,
    				descripcion:"Variacion cambiaria $periodo ",
    				referencia:"$proveedor.id",
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'NA'
    				,origen:0)
    			
    			def clave=diferencia>0?"702-0002":"701-0002"
    			poliza.addToPartidas(
    				cuenta:CuentaContable.buscarPorClave(clave),
    				debe:diferencia<0?diferencia.abs():0.0,
    				haber:diferencia>0?diferencia.abs():0.0,
    				asiento:asiento,
    				descripcion:"Variacion cambiaria $periodo ",
    				referencia:"$proveedor.id",
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'NA'
    				,origen:0)
    		}		
    		
    	}
    	
    }
}
