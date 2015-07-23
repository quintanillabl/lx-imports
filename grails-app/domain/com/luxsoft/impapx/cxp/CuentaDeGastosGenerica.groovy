package com.luxsoft.impapx.cxp


import com.luxsoft.impapx.Embarque;
import com.luxsoft.impapx.Proveedor;
import com.luxsoft.impapx.FacturaDeGastos


import java.util.Date;

class CuentaDeGastosGenerica {
	
	Proveedor proveedor
	
	Date fecha
	String comentario
	BigDecimal importe=0
	BigDecimal impuestos=0
	BigDecimal retension=0
	BigDecimal retensionIsr=0
	BigDecimal total=0 //Suma del total de las facturas
	
	BigDecimal descuento=0 //Sumado de las facturas
	BigDecimal rembolso=0  //Sumado de las facturas
	BigDecimal otros=0  //Sumado de las facturas
	
	Date dateCreated
	Date lastUpdated
	
	
	
	static hasMany = [facturas:FacturaDeGastos]

    static constraints = {
		comentario(nullable:true,maxSize:250)
		proveedor(nullable:true)
		otros nullable:true
    }
	
	def actualizarImportes(){
		importe=facturas.sum 0, {it.importe*it.tc}
		impuestos=facturas.sum 0,{it.impuestos*it.tc}
		total=facturas.sum 0,{it.total*it.tc}
		
		retension=facturas.sum 0,{it.retImp}
		retensionIsr=facturas.sum 0,{it.retensionIsr}
		descuento=facturas.sum 0,{it.descuento}
		rembolso=facturas.sum 0,{it.rembolso}
		otros=facturas.sum 0,{it.otros}
		
		
		
		
		
	}
	
	
}
