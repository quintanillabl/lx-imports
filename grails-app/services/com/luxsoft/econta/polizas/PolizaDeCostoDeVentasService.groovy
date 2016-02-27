package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.Venta
import com.luxsoft.cfdi.*


@Transactional
class PolizaDeCostoDeVentasService extends ProcesadorService {

    def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de costo de ventas ${poliza.fecha.text()}"

        def dia = poliza.fecha
        log.info "Polia de costo de ventas"
        procesarCostoDeVentas poliza
        
    }

    private procesarCostoDeVentas(def poliza){
    	def dia = poliza.fecha
    	
    	def facturas=[]
    	def servicios=[]
    	
    	def cfdis=Cfdi.findAll("from Cfdi c where date(c.fecha)=? and c.tipo=? and c.origen!='CANCELACION' ",[dia,'FAC'])
    	
    	cfdis.each {
    		Venta venta=Venta.get(it.origen)
    		assert venta,"Debe existir la venta para el cfdi: "+cfdi
    		if(!venta.clase || venta.clase=='IMPORTACION')
    			facturas.add(venta)
    	}

    	// Asiento COSTO DE VENTA
    	def asiento='COSTO DE VENTA'
    	facturas.each{fac->
    		def costoNeto=fac.partidas.sum(0.0,{it.embarque.costoBruto})
    		def embarque=fac.partidas.embarque.embarque.id			
    		
    		def pedimento=fac.partidas[0].embarque?.pedimento?.pedimento
    		
    		
    		//Abono al inventario
    		poliza.addToPartidas(
    			cuenta:CuentaContable.buscarPorClave('115-0001'),
    			debe:0.0,
    			haber:costoNeto,
    			asiento:asiento,
    			descripcion:"Fecha:$fac.fechaFactura $fac.cliente.nombre Pedimento:$pedimento",
    			referencia:"$fac.factura"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Venta'
    			,origen:fac.id)
    		
    		//Cargo a costo de venta
    		poliza.addToPartidas(
    			cuenta:CuentaContable.buscarPorClave('501-0001'),
    			debe:costoNeto,
    			haber:0.0,
    			asiento:asiento,
    			descripcion:"Fecha:$fac.fechaFactura $fac.cliente.nombre Pedimento:$pedimento",
    			referencia:"$fac.factura"
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Venta'
    			,origen:fac.id)
    		
    	}
    }
}
