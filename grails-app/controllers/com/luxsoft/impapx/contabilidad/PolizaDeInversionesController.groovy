package com.luxsoft.impapx.contabilidad

class PolizaDeInversionesController {

    def index() {
	   redirect action: 'list', params: params
    }
	 
	def mostrarPoliza(long id){
		def poliza=Poliza.findById(id,[fetch:[partidas:'eager']])
		render (view:'poliza' ,model:[poliza:poliza,partidas:poliza.partidas])
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
		
		def polizas=Poliza.findAllByTipoAndFechaBetween('INVERSIONES',periodo.inicio,periodo.fin,[sort:sort,order:order])
		[polizaInstanceList: polizas, polizaInstanceTotal: polizas.size()]
	}
	
	def generarPoliza(String fecha){
		Date dia=Date.parse("dd/MM/yyyy",fecha)
		
		params.dia=dia
		
		println 'Generando poliza: '+params
		
		//Prepara la poliza
		Poliza poliza=new Poliza(tipo:'DIARIO',folio:1, fecha:dia,descripcion:'Poliza '+dia.text(),partidas:[])
		
		//Collecciones usadas mas de una vez
		def facturas=Venta.findAll("from Venta v  where date(v.cfd.fecha)=? and v.tipo=?",[dia,'VENTA'])
		
		// Procesadores
		procesarFacturacion(poliza, dia, facturas)
		
		//Salvar la poliza
		poliza.debe=poliza.partidas.sum (0.0,{it.debe})
		poliza.haber=poliza.partidas.sum(0.0,{it.haber})
		poliza.save(failOnError:true)
		redirect action: 'mostrarPoliza', params: [id:poliza.id]
	}
	
	private procesarFacturacion(def poliza,def dia,def facturas){
		
		def asiento="FACTURACION"
		facturas.each{ fac->
			
			//Cargo a clientes
			def clave="106-$fac.cliente.subCuentaOperativa"
			def cuenta=CuentaContable.findByClave(clave)
			println 'Cuenta localizada: '+cuenta
			if(!cuenta) throw new RuntimeException("No existe la cuenta para el cliente: "+fac.cliente+ 'Clave: '+clave)
			poliza.addToPartidas(
					cuenta:cuenta,
					debe:fac.total,
					haber:0.0,
					asiento:asiento,
					descripcion:"Fecha:$fac.cfd.fecha $fac.cliente.nombre",
					referencia:"$fac.cfd.folio"
					,fecha:poliza.fecha
					,tipo:poliza.tipo
					,entidad:'Venta'
					,origen:fac.id)
			
			//Abono a ventas
			clave="401-$fac.cliente.subCuentaOperativa"
			cuenta=CuentaContable.findByClave(clave)
			if(!cuenta) throw new RuntimeException("No existe la cuenta para el cliente: "+fac.cliente+ 'Clave: '+clave)
			poliza.addToPartidas(
				cuenta:cuenta,
				debe:0.0,
				haber:fac.importe,
				asiento:asiento,
				descripcion:"Fecha:$fac.cfd.fecha $fac.cliente.nombre",
				referencia:"$fac.cfd.folio"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Venta'
				,origen:fac.id)
			
			//Abono a iva por trasladar
			poliza.addToPartidas(
				cuenta:CuentaContable.findByClave('206-0002'),
				debe:0.0,
				haber:fac.impuestos,
				asiento:asiento,
				descripcion:"Fecha:$fac.cfd.fecha $fac.cliente.nombre",
				referencia:"$fac.cfd.folio"
				,fecha:poliza.fecha
				,tipo:poliza.tipo
				,entidad:'Venta'
				,origen:fac.id)
				
				
		}
	}
}
