package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.Venta
import com.luxsoft.cfdi.*


@Transactional
class PolizaDeFacturacionService extends ProcesadorService{

    def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de facturación "

        def dia = poliza.fecha

        def facturas=[]
        def servicios=[]
        
        def cfdis=Cfdi.findAll("from Cfdi c where date(c.fecha)=? and c.tipo=? and c.origen!='CANCELACION' "
        	,[dia,'FAC'])
        log.info "Procesando ${cfdis.size()} del ${dia.text()}"
        
        cfdis.each {
        	Venta venta=Venta.get(it.origen)
        	assert venta,"Debe existir la venta para el cfdi: "+cfdi
        	if(!venta.clase || venta.clase=='IMPORTACION')
        		facturas.add(venta)
        	if(venta.clase=='generica' || venta.clase == 'GENERICA')
        		servicios.add(venta)
        }
        
        facturacion(poliza,facturas)

        procesarServiciosservicios(poliza,servicios)
        procesarNotasDeCargoCxC poliza,poliza.fecha
        cuadrar(poliza)
		depurar(poliza)
		save poliza
    }

    private facturacion(def poliza,def facturas){

    	def dia = poliza.fecha
    	
    	def asiento="FACTURACION"
    	
    	facturas.each{ fac->
    		
    		//Cargo a clientes
    		def clave="105-$fac.cliente.subCuentaOperativa"
    		def cuenta=CuentaContable.findByClave(clave)
    		//println 'Cuenta localizada: '+cuenta
    		if(!cuenta) throw new RuntimeException("No existe la cuenta para el cliente: "+fac.cliente+ 'Clave: '+clave)
    		
    		poliza.addToPartidas(
    				cuenta:cuenta,
    				debe:fac.total,
    				haber:0.0,
    				asiento:asiento,
    				descripcion:"$fac.facturaFolio $fac.fechaFactura ",
    				referencia:"$fac.facturaFolio"
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
    			descripcion:"$fac.facturaFolio $fac.fechaFactura ",
    			referencia:"$fac.factura"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Venta'
    			,origen:fac.id)
    		
    		//Abono a iva por trasladar
    		poliza.addToPartidas(
    			cuenta:CuentaContable.findByClave('209-0001'),
    			debe:0.0,
    			haber:fac.impuestos,
    			asiento:asiento,
    			descripcion:"$fac.facturaFolio $fac.fechaFactura ",
    			referencia:"$fac.factura"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Venta'
    			,origen:fac.id)
    			
    			
    	}
    }

    private procesarServiciosservicios(def poliza,def servicios){
    	def dia = poliza.fecha
    	
    	def asientos="PRESTACION SERVICIOS"
    	servicios.each{ srv->
    		
    		//Cargo a clientes
    		def clave="105-$srv.cliente.subCuentaOperativa"
    		def cuenta=CuentaContable.findByClave(clave)
    		//println 'Cuenta localizada: '+cuenta
    		if(!cuenta) throw new RuntimeException("No existe la cuenta para el cliente: "+srv.cliente+ 'Clave: '+clave)
    		poliza.addToPartidas(
    				cuenta:cuenta,
    				debe:srv.total,
    				haber:0.0,
    				asiento:asientos,
    				//descripcion:"Fecha:$srv.fechaFactura $srv.cliente.nombre",
                    descripcion:"$srv.facturaFolio $srv.fechaFactura ",
    				referencia:"$srv.factura"
    				,fecha:poliza.fecha
    				,tipo:poliza.tipo
    				,entidad:'Venta'
    				,origen:srv.id)
    		
    		//Abono a ventas
    		clave="401-$srv.cliente.subCuentaOperativa"
    		cuenta=CuentaContable.findByClave(clave)
    		if(!cuenta) throw new RuntimeException("No existe la cuenta para el cliente: "+srv.cliente+ 'Clave: '+clave)
    		poliza.addToPartidas(
    			cuenta:cuenta,
    			debe:0.0,
    			haber:srv.importe,
    			asiento:asientos,
    			//descripcion:"Fecha:$srv.fechaFactura $srv.cliente.nombre",
                descripcion:"$srv.facturaFolio $srv.fechaFactura ",
    			referencia:"$srv.factura"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Venta'
    			,origen:srv.id)
    		
    		//Abono a iva por trasladar
    		poliza.addToPartidas(
    			cuenta:CuentaContable.findByClave('209-0001'),
    			debe:0.0,
    			haber:srv.impuestos,
    			asiento:asientos,
    			//descripcion:"Fecha:$srv.fechaFactura $srv.cliente.nombre",
                descripcion:"$srv.facturaFolio $srv.fechaFactura ",
    			referencia:"$srv.factura"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Venta'
    			,origen:srv.id)
    			
    			
    	}
    }

    private procesarNotasDeCargoCxC(def poliza,def dia){
        def asiento='NOTAS DE CARGO CXC'
        
        def notas=Venta.findAll ("from Venta v where v.tipo='NOTA_DE_CARGO' and date(v.fecha)=?",[dia])
        
        notas.each{ nota ->
            
            //Cargo a cliente
            
            def clave="105-$nota.cliente.subCuentaOperativa"
            poliza.addToPartidas(
                cuenta:CuentaContable.buscarPorClave(clave),
                debe:nota.total,
                haber:0.0,
                asiento:asiento,
                //descripcion:"$nota.factura  $nota.cliente ",
                descripcion:"$nota.facturaFolio $nota.fechaFactura ",
                referencia:"$nota.factura",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Venta'
                ,origen:nota.id)
            
            //Abono a productos financieros
            clave="702-0003"
            poliza.addToPartidas(
                cuenta:CuentaContable.buscarPorClave(clave),
                debe:0.0,
                haber:nota.importe,
                asiento:asiento,
                descripcion:"$nota.facturaFolio $nota.fechaFactura ",
                referencia:"$nota.factura",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Venta'
                ,origen:nota.id)
            
            //Abono a IVA Pendiente por trasladar (productos financieros)
            clave="209-0001"
            poliza.addToPartidas(
                cuenta:CuentaContable.buscarPorClave(clave),
                debe:0.0,
                haber:nota.impuestos,
                asiento:asiento,
                descripcion:"$nota.facturaFolio $nota.fechaFactura ",
                referencia:"$nota.factura",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Venta'
                ,origen:nota.id)
            
        }
    }

    
}
