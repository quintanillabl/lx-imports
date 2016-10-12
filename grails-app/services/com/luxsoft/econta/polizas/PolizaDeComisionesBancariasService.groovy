package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*

@Transactional
class PolizaDeComisionesBancariasService extends ProcesadorService {

    def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de comsiones bancarias "

        def dia = poliza.fecha
        
        comisionesBancarias poliza
        
    }

    private comisionesBancarias(def poliza){

    	def dia = poliza.fecha

    	def asiento="COMISIONES BANCARIAS"
    	
    	def comisiones=Comision.findAll("from Comision m where date(m.fecha)=?",[dia])
    	
    	comisiones.each{comision ->
    		
    		// 1. Cargo a Deudores
    		// def cuenta=CuentaContable.findByClave('600-0013')
    		// if(cuenta==null)
    		// 	throw new RuntimeException("No existe la cuenta contable 600-0013")
            //def cuenta = CuentaContable.buscarPorClave('107-V001')
            def cuenta = CuentaContable.buscarPorClave('107-' + comision.cuenta.subCuentaOperativa)
            assert cuenta, 'No existe cuenta acredora ya sea para el proveedor o la generica provedores diversos'     
    		poliza.addToPartidas(
    			cuenta:cuenta,
    			debe:comision.comision.abs()*comision.tc,
    			haber:0.0,
    			asiento:asiento,
    			descripcion:"Comision $comision.cuenta ",
    			referencia:"$comision.referenciaBancaria",
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Comision'
    			,origen:comision.id)
    		
    		// 2. Cargo a IVA acreditable
    		cuenta=CuentaContable.findByClave('118-0001')
    		if(cuenta==null)
    			throw new RuntimeException("No existe la cuenta contable 118-0001")
    		poliza.addToPartidas(
    			cuenta:cuenta,
    			debe:comision.impuesto.abs()*comision.tc,
    			haber:0.0,
    			asiento:asiento,
    			descripcion:" IVA comision $comision.cuenta ",
    			referencia:"$comision.referenciaBancaria",
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Comision'
    			,origen:comision.id)
    		
    		
    		// 3. Abono al banco (Por el importe de la comision)
    		def cuentaDeBanco=comision.cuenta
    		if(cuentaDeBanco.cuentaContable==null)
    			throw new RuntimeException("Cuenta de banco sin cuenta contable asignada: $cuentaDeBanco")
    		poliza.addToPartidas(
    			cuenta:cuentaDeBanco.cuentaContable,
    			debe:0.0,
    			haber:comision.comision.abs()*comision.tc,
    			asiento:asiento,
    			descripcion:"Comision $comision.cuenta ",
    			referencia:"$comision.referenciaBancaria",
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Comision'
    			,origen:comision.id)
    		
    		// 4. Abono al IVA (Por la comision)
    		poliza.addToPartidas(
    			cuenta:cuentaDeBanco.cuentaContable,
    			debe:0.0,
    			haber:comision.impuesto.abs()*comision.tc,
    			asiento:asiento,
    			descripcion:"IVA comision $comision.cuenta ",
    			referencia:"$comision.referenciaBancaria",
    			,fecha:poliza.fecha
    			,tipo:poliza.tipo
    			,entidad:'Comision'
    			,origen:comision.id)
    		
    		
    	}
    	
    	
    }
}
