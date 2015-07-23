package com.luxsoft.impapx.contabilidad

import util.Rounding;

import com.luxsoft.impapx.EmbarqueDet;
import com.luxsoft.impapx.FacturaDeGastos;
import com.luxsoft.impapx.TipoDeCambio;
import com.luxsoft.impapx.tesoreria.Cheque;
import com.luxsoft.impapx.tesoreria.MovimientoDeCuenta;
import com.luxsoft.impapx.tesoreria.PagoProveedor;

class PolizaDeEgresosController {
	
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
		
		def polizas=Poliza.findAllByTipoAndFechaBetween('EGRESO',periodo.inicio,periodo.fin,[sort:sort,order:order])
		[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
	}
	
	def generarPoliza(String fecha){
		Date dia=Date.parse("dd/MM/yyyy",fecha)
		
		params.dia=dia
		
		println 'Generando poliza: '+params
		
		def pagos=PagoProveedor.findAll("from PagoProveedor p where date(p.egreso.fecha)=? and p.egreso.moneda='USD' and p.egreso.origen=? and p.requisicion.concepto='PAGO'"
			,[dia,'CXP'])
		
		pagos.each{ pago->
			
			
			//Prepara la poliza
			def fp=pago.egreso.tipo.substring(0,2)
			def egreso=pago.egreso
			
			def req=pago.requisicion
			def descripcion="$fp-$egreso.referenciaBancaria $req.proveedor (Proveedores extranjeros) id:$egreso.id"
			Poliza poliza=new Poliza(tipo:'EGRESO',folio:1, fecha:dia,descripcion:descripcion,partidas:[])
			
			
			def asiento='PAGO CXP'
			def pagoAcu=0
			req.partidas.each{ det->
				
				def fac=det.factura
				//println 'Procesando fac: '+det
				def tc=0
				
				//NUEVO
				//System.out.println("Localizando embarqueDet de factura:******************************************"+fac.id);
				def embarqueDet=EmbarqueDet.find("from EmbarqueDet x where x.factura=?",[fac])
				if(embarqueDet==null) 
					throw new RuntimeException("La factura ${fac} (${fac.proveedor.nombre}) no esta relacionada en algun embarque por lo tanto no se puede acceder al pedimento ")
				def pedimento=embarqueDet.pedimento
				
					
				if(!pedimento){
				
					def fechaTc=egreso.fecha-1
					def tipoDeCambioInstance=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=? and t.monedaFuente=?",[fechaTc,fac.moneda])
					if(!tipoDeCambioInstance)
						throw new RuntimeException("No existe el Tipo de cambio para:"+fechaTc.text())
					tc=tipoDeCambioInstance.factor
				}else if(pedimento.fecha>dia || (pedimento.fecha.toMonth()==dia.toMonth()) ){
					def fechaTc=egreso.fecha-1
					def tipoDeCambioInstance=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=? and t.monedaFuente=?",[fechaTc,fac.moneda])
					if(!tipoDeCambioInstance)
						throw new RuntimeException("No existe el Tipo de cambio para:"+fechaTc.text())
					tc=tipoDeCambioInstance.factor
				
				}else{
					def fechaTc=egreso.fecha.inicioDeMes()-2
					def tipoDeCambioInstance=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=? and t.monedaFuente=?",[fechaTc,fac.moneda])
					if(!tipoDeCambioInstance)
					throw new RuntimeException("No existe el Tipo de cambio para:"+fechaTc.text())
						tc=tipoDeCambioInstance.factor
							
				}
				
				def clave="201-$req.proveedor.subCuentaOperativa"
				if(pedimento ){
					if(egreso.fecha<pedimento.fecha || pedimento.fecha.toMonth()== egreso.fecha.toMonth() ){
						clave="111-$req.proveedor.subCuentaOperativa"
					}
				}else{
					clave="111-$req.proveedor.subCuentaOperativa"
				}
				
				
				def importeMN=Rounding.round(det.total*tc,2)
				def fechaDocto=fac.fecha.text()
				
				//Acumulamos el pago al tipo de cambio aplicado
				pagoAcu+=det.total*tc
				
				//Cargo a a proveedores
				
				
				poliza.addToPartidas(
						cuenta:CuentaContable.buscarPorClave(clave),
						debe:importeMN,
						haber:0.0,
						asiento:asiento,
						descripcion:"F Fac:$fechaDocto Imp:$det.total TC:$tc F.Ped:"+pedimento?.fecha?.text()+' F.Pag:'+egreso?.fecha?.text(),
						referencia:"$fac.documento"
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'FacturaDeImportacion'
						,origen:fac.id)
			}
			//Abono al banco
			if(!egreso.cuenta.cuentaContable)
				throw new RuntimeException("Cuenta de banco sin cuenta contable $egreso.cuenta")
			poliza.addToPartidas(
					cuenta:egreso.cuenta.cuentaContable,
					debe:0.0,
					haber:egreso.importe.abs()*egreso.tc,
					asiento:asiento,
					descripcion:"Pago prov "+egreso.importe.abs()+" * $egreso.tc",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
			
			//Diferencia cambiaria
			def dif=(egreso.importe.abs()*egreso.tc)-pagoAcu
			
			if(dif.abs()>0){
				def clave=dif<0?'701-0002':'705-0002'
				poliza.addToPartidas(
					cuenta:CuentaContable.buscarPorClave(clave),
					debe:dif>0?dif.abs():0.0,
					haber:dif<0?dif.abs():0.0,
					asiento:asiento,
					descripcion:"Pago a proveedor",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
			}
			
			//IETU
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("900-0002"),
				debe:egreso.importe.abs()*egreso.tc,
				haber:0.0,
				asiento:asiento,
				descripcion:"Pago prov "+egreso.importe.abs()+" * $egreso.tc",
				referencia:"$egreso.referenciaBancaria"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'MovimientoDeCuenta'
				,origen:egreso.id)
			
			poliza.addToPartidas(
				cuenta:CuentaContable.buscarPorClave("901-0002"),
				debe:0.0,
				haber:egreso.importe.abs()*egreso.tc,
				asiento:asiento,
				descripcion:"Pago prov "+egreso.importe.abs()+" * $egreso.tc",
				referencia:"$egreso.referenciaBancaria"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'MovimientoDeCuenta'
				,origen:egreso.id)
			
			//Salvar la poliza
			poliza.debe=poliza.partidas.sum (0.0,{it.debe})
			poliza.haber=poliza.partidas.sum(0.0,{it.haber})
			//poliza.folio=polizaService.nextFolio(poliza)
			//poliza.save(failOnError:true)
			poliza=polizaService.salvarPoliza(poliza)
		}
		registrarGastos(dia)
	    procesarAnticipos(dia)
		
		procesarAnticiposCompra(dia)
		procesarChequesCancelados(dia)
		procesarPagoChoferes(dia)
		redirect action: 'list'
	}
	
	
	private registrarGastos(Date dia){
		
		def pagosEx=PagoProveedor
			.findAll("from PagoProveedor p where date(p.egreso.fecha)=? and p.egreso.origen=? and p.requisicion.concepto in ('PAGO','PARCIALIDAD')"
			,[dia,'CXP'])
			
		def pagos=new HashSet()
		pagosEx.each{
		
			def req=it.requisicion
			req.partidas.each{ det ->
				if(det.factura && det.factura.instanceOf(FacturaDeGastos)){
					pagos.add(it)
					
				}
			}
			
		}
		
		pagos.each{ pago->
			
			//Prepara la poliza
			def fp=pago.egreso.tipo.substring(0,2)
			def egreso=pago.egreso
			
			def req=pago.requisicion
			def descripcion="$fp-$egreso.referenciaBancaria $req.proveedor ($req.comentario) id:$egreso.id"
			Poliza poliza=new Poliza(tipo:'EGRESO', fecha:dia,descripcion:descripcion,partidas:[])
			
			//def clave="201-$req.proveedor.subCuentaOperativa"
			def asiento='PAGO DE GASTOS'
			def pagoAcu=0
			def ietu=0.0
			req.partidas.each{ det->
				
				def fac=det.factura
				
				fac.conceptos.each{ c->
					
					def fechaFac=fac.fecha.text()
					
					ietu+=c.ietu
					
					Date fFactura=fac.fecha
					Date fPago=egreso.fecha
					
					if(c.tipo=='HONORARIOS AL CONSEJO ADMON'){
						//Cargo a gasto concepto
						poliza.addToPartidas(
							cuenta:c.concepto,
							debe:c.importe,
							haber:0.0,
							asiento:asiento,
							descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
							referencia:"$fac.documento"
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'FacturaDeGasto'
							,origen:fac.id)
						//Abono al ISR de Honorarios al Consejo
						poliza.addToPartidas(
							cuenta:CuentaContable.buscarPorClave("205-0006"),
							debe:0.0,
							haber:c.retensionIsr,
							asiento:asiento,
							descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
							referencia:"$fac.documento"
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'FacturaDeGasto'
							,origen:fac.id)
					}
					
					else if(fFactura.toMonth()==fPago.toMonth()){
						
						//Cargo a gasto concepto
						poliza.addToPartidas(
							cuenta:c.concepto,
							debe:c.importe,
							haber:0.0,
							asiento:asiento,
							descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
							referencia:"$fac.documento"
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'FacturaDeGasto'
							,origen:fac.id)
						//Cargo al iva de gasto
						poliza.addToPartidas(
							cuenta:CuentaContable.buscarPorClave("117-0001"),
							debe:c.impuesto,
							haber:0.0,
							asiento:asiento,
							descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
							referencia:"$fac.documento"
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'FacturaDeGasto'
							,origen:fac.id)
						
						if(c.retension>0){
							poliza.addToPartidas(
								cuenta:CuentaContable.buscarPorClave("117-0008"),
								debe:c.retension,
								haber:0.0,
								asiento:asiento,
								descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
								referencia:"$fac.documento"
								,fecha:poliza.fecha
								,tipo:poliza.tipo
								,entidad:'FacturaDeGasto'
								,origen:fac.id)
						}
						
						if(c.retensionIsr){
							poliza.addToPartidas(
								cuenta:CuentaContable.buscarPorClave("205-0006"),
								debe:c.retensionIsr,
								haber:0.0,
								asiento:asiento,
								descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
								referencia:"$fac.documento"
								,fecha:poliza.fecha
								,tipo:poliza.tipo
								,entidad:'FacturaDeGasto'
								,origen:fac.id)
						}
						
					//}else{ //Cancelamos la provision
					}else if(fFactura.toMonth()!=fPago.toMonth()){
						
						//Cargo a agredores diversos o cancelar la provision
					    def iva=c.impuesto
						def monto=c.total
						def cuenta=CuentaContable.buscarPorClave("203-V001")
						if(c.tipo=='SEGUROS Y FIANZAS'){
							cuenta=CuentaContable.buscarPorClave("203-$fac.proveedor.subCuentaOperativa")
						 monto=det.total
						 ietu=det.ietu
						 iva=det.impuestos
						 
							
						}
						
						poliza.addToPartidas(
						cuenta:cuenta,
						debe:monto,
						haber:0.0,
						asiento:asiento,
						descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
						referencia:"$fac.documento"
						,fecha:poliza.fecha
						,tipo:poliza.tipo
						,entidad:'FacturaDeGasto'
						,origen:fac.id)
						//Cargo al iva de gasto
						poliza.addToPartidas(						
							cuenta:CuentaContable.buscarPorClave("117-0001"),
							debe:iva,
							haber:0.0,
							asiento:asiento,
							descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
							referencia:"$fac.documento"
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'FacturaDeGasto'
							,origen:fac.id)
						//Abono al iva pendiente  de gasto
						poliza.addToPartidas(
							cuenta:CuentaContable.buscarPorClave("117-0005"),
							debe:0.0,
							haber:iva,
							asiento:asiento,
							descripcion:"Fac:$fac.documento ($fechaFac) $c.descripcion",
							referencia:"$fac.documento"
							,fecha:poliza.fecha
							,tipo:poliza.tipo
							,entidad:'FacturaDeGasto'
							,origen:fac.id)
					
					}
					
				}
				
			}
			
			//Abono al banco
			if(!egreso.cuenta.cuentaContable)
				throw new RuntimeException("Cuenta de banco sin cuenta contable $egreso.cuenta")
			poliza.addToPartidas(
					cuenta:egreso.cuenta.cuentaContable,
					debe:0.0,
					haber:egreso.importe.abs()*egreso.tc,
					asiento:asiento,
					descripcion:"$fp-$egreso.referenciaBancaria $req.proveedor",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
			
			def cuentaCargo=CuentaContable.buscarPorClave("900-0002")
			def cuentaAbono=CuentaContable.buscarPorClave("901-0002")
			def c=req.partidas[0]?.factura?.conceptos.iterator().next()
			if(c!=null && c.tipo=='SEGUROS Y FIANZAS'){
				 cuentaCargo=CuentaContable.buscarPorClave("900-0007")
				 cuentaAbono=CuentaContable.buscarPorClave("901-0007")
			 
				 
			}
			if(c!=null && c.tipo=='GASTOS'){
				cuentaCargo=CuentaContable.buscarPorClave("900-0003")
				cuentaAbono=CuentaContable.buscarPorClave("901-0003")
		   }
			
			if(c!=null && c.tipo=='SERVICIOS INDEPENDIENTES'){
				cuentaCargo=CuentaContable.buscarPorClave("900-0006")
				cuentaAbono=CuentaContable.buscarPorClave("901-0006")
			
		   }
			
			if(c!=null && c.tipo!='HONORARIOS AL CONSEJO ADMON'){
				//IETU
				poliza.addToPartidas(
					cuenta:cuentaCargo,
					debe:ietu,
					haber:0.0,
					asiento:asiento,
					descripcion:"$fp-$egreso.referenciaBancaria $req.proveedor",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
				
				poliza.addToPartidas(
					cuenta:cuentaAbono,
					debe:0.0,
					haber:ietu,
					asiento:asiento,
					descripcion:"$fp-$egreso.referenciaBancaria $req.proveedor",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
			}
			
			
			
			if(c!=null && c.tipo=='SEGUROS Y FIANZAS'){
				//Amorti de activo diferido
				
				poliza.addToPartidas(				
					cuenta:CuentaContable.buscarPorClave("600-0003"),
					debe:ietu,
					haber:0.0,
					asiento:asiento,
					descripcion:"$fp-$egreso.referenciaBancaria $req.proveedor",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
			
				poliza.addToPartidas(
					cuenta:CuentaContable.buscarPorClave("160-0001"),
					debe:0.0,
					haber:ietu,
					asiento:asiento,
					descripcion:"$fp-$egreso.referenciaBancaria $req.proveedor",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
		   }
			
			
			//Salvar la poliza
			poliza.debe=poliza.partidas.sum (0.0,{it.debe})
			poliza.haber=poliza.partidas.sum(0.0,{it.haber})
			//poliza.folio=polizaService.nextFolio(poliza)
			//poliza.save(failOnError:true)
			poliza=polizaService.salvarPoliza(poliza)
		}
		
	}
	
	private procesarAnticipos(def dia){
		//Asiento: Anticipos
		
		def anticipos=PagoProveedor.findAll("from PagoProveedor p where p.requisicion.concepto=? and date(p.egreso.fecha)=?",['ANTICIPO',dia])
		
		anticipos.each{ pago ->
			
			def fp=pago.egreso.tipo.substring(0,2)
			def egreso=pago.egreso
			
			def req=pago.requisicion
			def descripcion="$fp-$egreso.referenciaBancaria $req.proveedor ($req.concepto) id:$egreso.id"
			Poliza poliza=new Poliza(tipo:'EGRESO', fecha:dia,descripcion:descripcion,partidas:[])
			
			def clave="201-$req.proveedor.subCuentaOperativa"
			def asiento='ANTICIPO IMPORTACION'
			
			//Abono a bancos
			def cuentaDeBanco=pago.egreso.cuenta
			if(cuentaDeBanco.cuentaContable==null)
				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
			poliza.addToPartidas(
				cuenta:cuentaDeBanco.cuentaContable,
				debe:0.0,
				haber:pago.egreso.importe.abs()*pago.egreso.tc,
				asiento:asiento,
				descripcion:"$pago.egreso.cuenta ",
				referencia:"$pago.egreso.referenciaBancaria",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'PagoProveedor'
				,origen:pago.id)
			
			//Cargo a proveedor
			def proveedor=pago.requisicion.proveedor
			clave="109-$proveedor.subCuentaOperativa"
			def cuenta=CuentaContable.findByClave(clave)
			if(!cuenta) throw new RuntimeException("No existe la cuenta para el proveedor: "+proveedor.nombre+ 'Clave: '+clave)
			def requisicion=pago.requisicion
			requisicion.partidas.each{ reqDet ->
				poliza.addToPartidas(
					cuenta:cuenta,
					debe:reqDet.importe.abs()*pago.egreso.tc,
					haber:0.0,
					asiento:asiento,
					descripcion:"$pago.egreso.cuenta Ref:$reqDet.documento REq: $requisicion.id ",
					referencia:"$pago.egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'PagoProveedor'
					,origen:pago.id)
			}
			//Salvar la poliza
			poliza.debe=poliza.partidas.sum (0.0,{it.debe})
			poliza.haber=poliza.partidas.sum(0.0,{it.haber})
			//poliza.folio=polizaService.nextFolio(poliza)
			//poliza.save(failOnError:true)
			poliza=polizaService.salvarPoliza(poliza)
		}
	}
	
	private procesarAnticiposCompra(def dia){
		//Asiento: Anticipos
		
		def anticipos=PagoProveedor.findAll("from PagoProveedor p where p.requisicion.concepto=? and date(p.egreso.fecha)=?",['ANTICIPO_COMPRA',dia])
		
		anticipos.each{ pago ->
			
			def fp=pago.egreso.tipo.substring(0,2)
			def egreso=pago.egreso
			
			def req=pago.requisicion
			def descripcion="$fp-$egreso.referenciaBancaria $req.proveedor ($req.concepto) id:$egreso.id"
			Poliza poliza=new Poliza(tipo:'EGRESO', fecha:dia,descripcion:descripcion,partidas:[])
			
			def clave="201-$req.proveedor.subCuentaOperativa"
			def asiento='ANTICIPO_COMPRA'
			
			//Abono a bancos
			def cuentaDeBanco=pago.egreso.cuenta
			if(cuentaDeBanco.cuentaContable==null)
				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
			poliza.addToPartidas(
				cuenta:cuentaDeBanco.cuentaContable,
				debe:0.0,
				haber:pago.egreso.importe.abs()*pago.egreso.tc,
				asiento:asiento,
				descripcion:"$pago.egreso.cuenta "+egreso.importe.abs()+" * $egreso.tc",
				referencia:"$pago.egreso.referenciaBancaria",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'PagoProveedor'
				,origen:pago.id)
			
			//Cargo a proveedor
			def proveedor=pago.requisicion.proveedor
			clave="111-$proveedor.subCuentaOperativa"
			def cuenta=CuentaContable.findByClave(clave)
			if(!cuenta) throw new RuntimeException("No existe la cuenta para el proveedor: "+proveedor.nombre+ 'Clave: '+clave)
			def requisicion=pago.requisicion
			requisicion.partidas.each{ reqDet ->
				poliza.addToPartidas(
					cuenta:cuenta,
					debe:reqDet.importe.abs()*pago.egreso.tc,
					haber:0.0,
					asiento:asiento,
					descripcion:"$pago.egreso.cuenta Ref:$reqDet.documento REq: $requisicion.id "+egreso.importe.abs()+" * $egreso.tc",
					referencia:"$pago.egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'PagoProveedor'
					,origen:pago.id)				

				//IETU
				poliza.addToPartidas(
					cuenta:CuentaContable.buscarPorClave("900-0002"),
					debe:egreso.importe.abs()*egreso.tc,
					haber:0.0,
					asiento:asiento,
					descripcion:"Pago prov "+egreso.importe.abs()+" * $egreso.tc",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
				
				poliza.addToPartidas(
					cuenta:CuentaContable.buscarPorClave("901-0002"),
					debe:0.0,
					haber:egreso.importe.abs()*egreso.tc,
					asiento:asiento,
					descripcion:"Pago prov "+egreso.importe.abs()+" * $egreso.tc",
					referencia:"$egreso.referenciaBancaria"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'MovimientoDeCuenta'
					,origen:egreso.id)
		
			}
			
			
			//Salvar la poliza
			poliza.debe=poliza.partidas.sum (0.0,{it.debe})
			poliza.haber=poliza.partidas.sum(0.0,{it.haber})
			//poliza.folio=polizaService.nextFolio(poliza)
			//poliza.save(failOnError:true)
			poliza=polizaService.salvarPoliza(poliza)
		}
	}
	
	def private procesarChequesCancelados(Date dia){
		def cheques=Cheque.executeQuery("from Cheque c where date(c.egreso.fecha)=? and c.egreso.comentario='CANCELADO' ",[dia])
		cheques.each{pago ->
			
			
			def egreso=pago.egreso
			
			
			def descripcion="CH-$egreso.referenciaBancaria $egreso.comentario id:$egreso.id"
			Poliza poliza=new Poliza(tipo:'EGRESO', fecha:dia,descripcion:descripcion,partidas:[])
			
			poliza.debe=poliza.partidas.sum (0.0,{it.debe})
			poliza.haber=poliza.partidas.sum(0.0,{it.haber})
			poliza=polizaService.salvarPoliza(poliza)
		}
	}
	
	private procesarPagoChoferes(Date dia){
		def pagos=PagoProveedor
		.findAll("from PagoProveedor p where date(p.egreso.fecha)=? and p.egreso.origen=? and p.requisicion.concepto in ('FLETE')"
		,[dia,'CXP'])
		
		pagos.each{ pago ->
			
			def fp=pago.egreso.tipo.substring(0,2)
			def egreso=pago.egreso
			
			def req=pago.requisicion
			def descripcion="$fp-$egreso.referenciaBancaria $req.proveedor ($req.concepto) id:$egreso.id"
			Poliza poliza=new Poliza(tipo:'EGRESO', fecha:dia,descripcion:descripcion,partidas:[])
			def asiento='PAGO_FLETE'
			
			//Abono a bancos
			def cuentaDeBanco=pago.egreso.cuenta
			if(cuentaDeBanco.cuentaContable==null)
				throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
			poliza.addToPartidas(
				cuenta:cuentaDeBanco.cuentaContable,
				debe:0.0,
				haber:pago.egreso.importe.abs(),
				asiento:asiento,
				descripcion:"$pago.egreso.cuenta ",
				referencia:"$pago.egreso.referenciaBancaria",
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'PagoProveedor'
				,origen:pago.id)
			
			//Cargo a proveedor
			def proveedor=pago.requisicion.proveedor
			
			def clave="203-$proveedor.subCuentaOperativa"
			println 'Localizando cuenta operativa contable para Proveedor: '+proveedor
			def cuenta=CuentaContable.buscarPorClave(clave)
			def requisicion=pago.requisicion
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:pago.egreso.importe.abs(),
				haber:0.0,
				asiento:asiento,
				descripcion:"$pago.egreso.cuenta ",
				referencia:"$pago.egreso.referenciaBancaria"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'PagoProveedor'
				,origen:pago.id)
			
			//Salvar la poliza
			poliza.debe=poliza.partidas.sum (0.0,{it.debe})
			poliza.haber=poliza.partidas.sum(0.0,{it.haber})
			//poliza.folio=polizaService.nextFolio(poliza)
			//poliza.save(failOnError:true)
			poliza=polizaService.salvarPoliza(poliza)
		}
	}
	

	
	
	
}
