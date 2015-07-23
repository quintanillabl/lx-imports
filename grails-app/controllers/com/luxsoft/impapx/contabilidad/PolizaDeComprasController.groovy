package com.luxsoft.impapx.contabilidad

import org.hibernate.AssertionFailure;

import util.Rounding;

import com.luxsoft.impapx.CuentaDeGastos;
import com.luxsoft.impapx.CuentaPorPagar;
import com.luxsoft.impapx.Embarque;
import com.luxsoft.impapx.EmbarqueDet;
import com.luxsoft.impapx.FacturaDeImportacion;
import com.luxsoft.impapx.GastosDeImportacion;
import com.luxsoft.impapx.Pedimento;
import com.luxsoft.impapx.TipoDeCambio;

class PolizaDeComprasController {
	
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
		
		def polizas=Poliza.findAllByTipoAndFechaBetween('COMPRAS',periodo.inicio,periodo.fin,[sort:sort,order:order])
		[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
	}
	
	def generarPoliza(String fecha){
		Date dia=Date.parse("dd/MM/yyyy",fecha)
		
		params.dia=dia
		
		
		//Prepara la poliza
		Poliza poliza=new Poliza(tipo:'COMPRAS',folio:1, fecha:dia,descripcion:'Poliza '+dia.text(),partidas:[])
		
		//Collecciones usadas mas de una vez
		
		// Procesadores
		procearCuentaPorPagarMateriaPrima(poliza, dia)
		procearImpuestosAduanales(poliza, dia)
		proceaCuentaDeGastos(poliza, dia)
		
		 
		//Salvar la poliza
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		//poliza.cuadrar()
		//poliza.save(flush:true)
		poliza=polizaService.salvarPoliza(poliza)
		
		//println 'Poliza generada:'+ poliza
		redirect action: 'mostrarPoliza', params: [id:poliza.id]
		
		//render (view:'poliza' ,model:[poliza:poliza,partidas:poliza.partidas])
	}
	
	private procearCuentaPorPagarMateriaPrima(def poliza , def dia){
		
		def asiento="Cuenta por pagar"
		
		def embarques=EmbarqueDet.findAll ("from EmbarqueDet e where e.factura is not null and date(e.pedimento.fecha)=?",[dia])
		//println 'Embarques a procesar: '+embarques.size()
		def data=[:]
		
		embarques.each{it->
			
			data[it.factura]=it
		}
		
		data.each{ entry->
			
			//println entry
			def factura=entry.key
			def embarqueDet=entry.value
			def pedimento=embarqueDet.pedimento
			def fechaF=factura.fecha.text()		
			def peds=EmbarqueDet.executeQuery('select d.pedimento.pedimento from EmbarqueDet d where d.factura=?',[factura]) as Set	
			def pedimentos=peds.join(',')	
			
			//Verificar si la factura es provisionada
			int periodoAnterior=dia.toYear()-1
			if(periodoAnterior==factura.provisionada){
				
				asiento="Cancelacion de Provision"
				
				//Cancelar provision
				// 1. Abono al inventario
				def cuenta=CuentaContable.buscarPorClave('119-0003')
				def fechaTc=factura.fecha-1
				def tipoDeCambioInstance=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=? and t.monedaFuente=?",[fechaTc,factura.moneda])
				assert tipoDeCambioInstance,"Debe existir tipo de cambio para el :"+fechaTc.text()
				def tipoCambioPed=EmbarqueDet.executeQuery('select d.pedimento.tipoDeCambio from EmbarqueDet d where d.factura=?',[factura]).get(0)
				
				def valorFactTC=factura.importe*tipoDeCambioInstance.factor
				def valorProvTC=factura.importe*tipoCambioPed
				def variacionCambiaria=valorFactTC-valorProvTC
				poliza.addToPartidas(
					cuenta:cuenta,
					debe:0.0,
					haber:valorFactTC,
					asiento:asiento,
					descripcion:"Canc provision $factura.proveedor ($fechaF) $factura.importe T.C:$tipoDeCambioInstance.factor",
					referencia:"$factura.documento",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'CuentaPorPagar'
					,origen:factura.id)
				// Cargo a proveedor
				def clave="201-$factura.proveedor.subCuentaOperativa"
				cuenta=CuentaContable.buscarPorClave(clave)
				poliza.addToPartidas(
					cuenta:cuenta,
					debe:valorProvTC,
					haber:0.0,
					asiento:asiento,
					descripcion:"Canc provision $factura.proveedor ($fechaF) $factura.importe T.C:$tipoCambioPed",
					referencia:"$factura.documento",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'CuentaPorPagar'
					,origen:factura.id)
				if(variacionCambiaria>0){
					cuenta=CuentaContable.buscarPorClave("705-0002")
					poliza.addToPartidas(
						cuenta:cuenta,
						debe:variacionCambiaria,
						haber:0.0,
						asiento:asiento,
						descripcion:"Canc prov variacion $factura.proveedor ($fechaF) ",
						referencia:"$factura.documento",
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'CuentaPorPagar'
						,origen:factura.id)
				}
				if(variacionCambiaria<0){
					cuenta=CuentaContable.buscarPorClave("701-0002")
					poliza.addToPartidas(
						cuenta:cuenta,
						debe:0.0,
						haber:variacionCambiaria.abs(),
						asiento:asiento,
						descripcion:"Canc prov variacion $factura.proveedor ($fechaF)",
						referencia:"$factura.documento",
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'CuentaPorPagar'
						,origen:factura.id)
				}
			}
			
			asiento="Cuenta por pagar"
			
			// 1. Cargo al inventario
			def cuenta=CuentaContable.findByClave('119-0001')
			if(cuenta==null){
				throw new RuntimeException("No existe la cuenta 119-0001")
			}
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:factura.importe*pedimento.tipoDeCambio,
				haber:0.0,
				asiento:asiento,
				descripcion:"$factura.proveedor ($fechaF) $factura.importe  Pedimentos:$pedimentos ",
				//descripcion:"$factura.proveedor ($fechaF) $factura.importe  T.C. $pedimento.tipoDeCambio Ped:$pedimento.pedimento ",
				referencia:"$factura.documento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CuentaPorPagar'
				,origen:factura.id)
			
			def clave="201-$factura.proveedor.subCuentaOperativa"
			cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $factura.proveedor")
			}
			
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:0.0,
				haber:factura.importe*pedimento.tipoDeCambio,
				asiento:asiento,
				//descripcion:"$factura.proveedor ($fechaF) $factura.importe  T.C. $pedimento.tipoDeCambio Ped:$pedimento.pedimento ",
				descripcion:"$factura.proveedor ($fechaF) $factura.importe  Pedimentos:$pedimentos ",
				referencia:"$factura.documento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CuentaPorPagar'
				,origen:factura.id)
			
		}
		
	}
	
	private procearImpuestosAduanales(def poliza , def dia){
		
		def asiento="IMPUESTOS ADUANALES"
		def pedimentos=Pedimento.findAll ("from Pedimento p where date(p.fecha)=?",[dia])	
		
		pedimentos.each{pedimento->
			
			def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
			def provImp=provImportacion.join(',')
			
			//println 'Procesando pedimento: '+pedimento
			//1 Cargo a DTA
			def clave="501-0003"
			def cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
			}
			
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:pedimento.dta,
				haber:0.0,
				asiento:asiento,
				descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg Prov:$provImp",
				referencia:"$pedimento.pedimento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Pedimento'
				,origen:pedimento.id)
			
		}
		
		pedimentos.each{pedimento->
			
			def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
			def provImp=provImportacion.join(',')
			
			//2 Cargo a Prevalidacion
			def clave="501-0004"
			def cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
			}
			def imp=pedimento.prevalidacion*(1+(pedimento.impuestoTasa/100))
			imp=Rounding.round(imp, 0)
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:imp,
				haber:0.0,
				asiento:asiento,
				descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg Prov:$provImp",
				referencia:"$pedimento.pedimento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Pedimento'
				,origen:pedimento.id)
			
		}
		
		pedimentos.each{pedimento->
			
			def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
			def provImp=provImportacion.join(',')
			
			//2 Cargo a Prevalidacion
			def clave="117-0003"
			def cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
			}
			def incrementableImp=pedimento.incrementables
			//incrementableImp=incrementableImp*pedimento.tipoDeCambio//*(pedimento.impuestoTasa/100)*pedimento.tipoDeCambio
			//pedimento.tipoDeCambio
			
			def imp2=pedimento.impuestoMateriaPrima+((pedimento.dta+pedimento.arancel+incrementableImp)*(pedimento.impuestoTasa/100))
			//def imp2=((pedimento.impuestoMateriaPrima))
			imp2=Rounding.round(imp2, 0)			
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:imp2,
				haber:0.0,
				asiento:asiento,
				descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg Prov:$provImp",
				referencia:"$pedimento.pedimento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Pedimento'
				,origen:pedimento.id)
			/*
			clave="117-0002"
			cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
			}
			incrementableImp=pedimento.incrementables
			
			imp2=((incrementableImp)*(pedimento.impuestoTasa/100))
			
			imp2=Rounding.round(imp2, 0)
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:0.0,
				haber:imp2,
				asiento:asiento,
				descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg ",
				referencia:"$pedimento.pedimento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Pedimento'
				,origen:pedimento.id)*/
		}
		
		pedimentos.each{pedimento->
			
			def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
			def provImp=provImportacion.join(',')
			
			//6 Cargo a Arancel
			def clave="501-0005"
			def cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
			}
			
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:pedimento.arancel,
				haber:0.0,
				asiento:asiento,
				descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg Prov:$provImp",
				referencia:"$pedimento.pedimento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Pedimento'
				,origen:pedimento.id)
			
			if(pedimento.incrementable1){
				clave="501-0007"
				cuenta=CuentaContable.findByClave(clave)
				if(cuenta==null){
					throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
				}
				
				poliza.addToPartidas(
					cuenta:cuenta,
					debe:Rounding.round(pedimento.incrementable1.importe*pedimento.tipoDeCambio,0),
					haber:0.0,
					asiento:asiento,
					descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg Prov:$provImp",
					referencia:"$pedimento.pedimento",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'Pedimento'
					,origen:pedimento.id)
			}
			
			//Abono deudores diversos
	
			def impuesto=pedimento.calcularImpuestoDinamico()
			def incrementableImp=pedimento.incrementables
			//def imp2=pedimento.impuestoMateriaPrima+((pedimento.dta+pedimento.arancel+incrementableImp)*(pedimento.impuestoTasa/100))
			def imp2=((incrementableImp)*(pedimento.impuestoTasa/100))
			imp2=Rounding.round(imp2, 0)
			
			
			/*
			if(pedimento.incrementable1){
				incrementableImp=pedimento.incrementable1.importe?:0.0
				incrementableImp=incrementableImp*(1+(pedimento.impuestoTasa/100))*pedimento.tipoDeCambio
				incrementableImp=Rounding.round(incrementableImp, 0)
			}
			*/
			
			
			clave="109-"+pedimento.proveedor.subCuentaOperativa
			cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta  $clave")
			}
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:0.0,
				haber:impuesto+imp2,
				asiento:asiento,
				descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg Prov:$provImp",
				referencia:"$pedimento.pedimento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Pedimento'
				,origen:pedimento.id)
			
		}
		
		pedimentos.each{pedimento->
			def ietu=pedimento.dta+pedimento.arancel+(pedimento.prevalidacion*(1+(pedimento.impuestoTasa/100)))
			ietu=Rounding.round(ietu, 0)
			
			def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
			def provImp=provImportacion.join(',')
			
			//2 Cargo a Prevalidacion
			def clave="900-0004"
			def cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
			}
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:ietu,
				haber:0.0,
				asiento:asiento,
				descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg Prov:$provImp",
				referencia:"$pedimento.pedimento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Pedimento'
				,origen:pedimento.id)
			
			clave="901-0004"
			cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null){
				throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
			}
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:0.0,
				haber:ietu,
				asiento:asiento,
				descripcion:"Ped:$pedimento.pedimento $pedimento.fecha Ref:$pedimento.referenciacg Prov:$provImp",
				referencia:"$pedimento.pedimento",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Pedimento'
				,origen:pedimento.id)
			
		}
		
	}
	
	
	private proceaCuentaDeGastos(def poliza, def dia){
		
		def asiento="CUENTA DE GASTOS"
		
		def gastos=CuentaDeGastos.findAll ("from CuentaDeGastos c where date(c.fecha)=?",[dia])
		
		gastos.each{ cg->
			
			// Registrar incrementables
			def incrementable=cg.facturas.sum(0.0,{it.incrementable?it.importe*it.tc:0.0})
			incrementable=Rounding.round(incrementable, 2)
			if(incrementable>0){
				def fac1=cg.facturas.find {it.incrementable}
				poliza.addToPartidas(
					cuenta:CuentaContable.buscarPorClave("501-0007"),
					debe:incrementable,
					haber:0.0,
					asiento:asiento,
					descripcion:"$cg.proveedor.nombre Ref:$cg.referencia T.C. $fac1.tc ",
					referencia:"$cg.referencia",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'CuentaDeGastos'
					,origen:cg.id)
			}
			
			//1.Cargo a cuenta de gastos
			def clave="600-H001"
			def cuenta=CuentaContable.findByClave(clave)
			def importe=cg.facturas.sum(0.0,{!it.incrementable?it.importe:0.0})
			importe=Rounding.round(importe, 2)
			if(cuenta==null)
				throw new RuntimeException("No existe  la cuenta con clave: $clave")
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:importe,
				haber:0.0,
				asiento:asiento,
				descripcion:"$cg.proveedor.nombre Ref:$cg.referencia $cg.fecha ",
				referencia:"$cg.referencia",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'CuentaDeGastos'
				,origen:cg.id)
			
			
			//2 Cargo a IVA al 11 de cuenta de gastos
			def impuesto11=cg.facturas.sum(0.0,{it.tasaDeImpuesto==11.0?it.impuestos*it.tc:0.0})
			impuesto11=Rounding.round(impuesto11, 2)
			if(impuesto11>0){
				clave="117-0002"
				cuenta=CuentaContable.findByClave(clave)
				if(cuenta==null)
					throw new RuntimeException("No existe  la cuenta con clave: $clave")
				poliza.addToPartidas(
					cuenta:cuenta,
					debe:impuesto11,
					haber:0.0,
					asiento:asiento,
					descripcion:"$cg.proveedor.nombre Ref:$cg.referencia",
					referencia:"$cg.referencia",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'CuentaDeGastos'
					,origen:cg.id)
			}
			
			//2 Cargo a IVA al 11 de cuenta de gastos
			def impuesto16=cg.facturas.sum(0.0,{it.tasaDeImpuesto==16.0?it.impuestos*it.tc:0.0})
			impuesto16=Rounding.round(impuesto16, 2)
			if(impuesto16>0){
				clave="117-0001"
				cuenta=CuentaContable.findByClave(clave)
				if(cuenta==null)
					throw new RuntimeException("No existe  la cuenta con clave: $clave")
				poliza.addToPartidas(
					cuenta:cuenta,
					debe:impuesto16,
					haber:0.0,
					asiento:asiento,
					descripcion:"$cg.proveedor.nombre Ref:$cg.referencia",
					referencia:"$cg.referencia",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'CuentaDeGastos'
					,origen:cg.id)
			}
			
			//3 Abono a Deudores
			clave="109-$cg.proveedor.subCuentaOperativa"
			cuenta=CuentaContable.findByClave(clave)
			if(cuenta==null)
				throw new RuntimeException("No existe  la cuenta  con clave: $clave para el proveedor: $cg.proveedor.nombre")
				poliza.addToPartidas(
					cuenta:cuenta,
					debe:0.0,
					haber:cg.total,
					asiento:asiento,
					descripcion:"$cg.proveedor.nombre Ref:$cg.referencia $cg.fecha ",
					referencia:"$cg.referencia",
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'CuentaDeGastos'
					,origen:cg.id)
				
				def ietuCg=cg.facturas.sum(0.0,{
					it.incrementable?0.0:it.importe*it.tc}
				)
				ietuCg=Rounding.round(ietuCg, 2)
				clave="900-0004"
				cuenta=CuentaContable.findByClave(clave)
				if(cuenta==null)
					throw new RuntimeException("No existe  la cuenta  con clave: $clave ")
					poliza.addToPartidas(
						cuenta:cuenta,
						debe:ietuCg,
						haber:0.0,
						asiento:asiento,
						descripcion:"$cg.proveedor.nombre Ref:$cg.referencia $cg.fecha ",
						referencia:"$cg.referencia",
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'CuentaDeGastos'
						,origen:cg.id)
					
					clave="901-0004"
					cuenta=CuentaContable.findByClave(clave)
					if(cuenta==null)
						throw new RuntimeException("No existe  la cuenta  con clave: $clave ")
						poliza.addToPartidas(
							cuenta:cuenta,
							debe:0.0,
							haber:ietuCg,
							asiento:asiento,
							descripcion:"$cg.proveedor.nombre Ref:$cg.referencia $cg.fecha ",
							referencia:"$cg.referencia",
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'CuentaDeGastos'
							,origen:cg.id)
				
				def ietuIncre=cg.facturas.sum(0.0,{
					it.incrementable?it.importe*it.tc:0.0}
				)
				ietuIncre=Rounding.round(ietuIncre, 2)
				if(ietuIncre>0){
					clave="900-0002"
					cuenta=CuentaContable.findByClave(clave)
					if(cuenta==null)
						throw new RuntimeException("No existe  la cuenta  con clave: $clave ")
						poliza.addToPartidas(
							cuenta:cuenta,
							debe:ietuIncre,
							haber:0.0,
							asiento:asiento,
							descripcion:"$cg.proveedor.nombre Ref:$cg.referencia $cg.fecha ",
							referencia:"$cg.referencia",
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'CuentaDeGastos'
							,origen:cg.id)
						
						clave="901-0002"
						cuenta=CuentaContable.findByClave(clave)
						if(cuenta==null)
							throw new RuntimeException("No existe  la cuenta  con clave: $clave ")
							poliza.addToPartidas(
								cuenta:cuenta,
								debe:0.0,
								haber:ietuIncre,
								asiento:asiento,
								descripcion:"$cg.proveedor.nombre Ref:$cg.referencia $cg.fecha ",
								referencia:"$cg.referencia",
								,fecha:poliza.fecha
								,tipo:poliza.tipo
								,entidad:'CuentaDeGastos'
								,origen:cg.id)
				}
		}
	}
	
}
