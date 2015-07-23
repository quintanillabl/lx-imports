package com.luxsoft.impapx

import com.luxsoft.impapx.contabilidad.CuentaContable

class Proveedor {
	
	String nombre
	Direccion direccion
	String correoElectronico
	String www
	BigDecimal factorDeUtilidad
	String tipoDeCosteo='NORMAL'
	String rfc
	boolean nacional=false
	BigDecimal lineaDeCredito=0
	int plazo
	boolean vencimentoBl=false
	//CuentaContable cuentaContable
	String subCuentaOperativa
	String paisDeOrigen
	String nacionalidad
	
	
	Date dateCreated
	Date lastUpdated
	
	static embedded = ['direccion']
	
	static hasMany = [productos:ProveedorProducto]
	
    static constraints = {
		nombre(blank:false,size:3..255)
		factorDeUtilidad(nullable:true,scale:4)
		tipoDeCosteo(nullable:true,inList:['NORMAL','ESPECIAL'])
		correoElectronico(nullable:true,email:true)
		www(nullable:true,url:true)
		direccion(nullable:true)
		rfc(nullable:true,maxSize:13)
		nacional(nullable:true)
		//cuentaContable(nullable:true)
		subCuentaOperativa(nullable:true,maxSize:4)
		paisDeOrigen(nullable:true)
		nacionalidad(nullable:true)
    }
	
	String toString(){
		return nombre;
	}
	
	static mapping = {
		//productos sort: 'codigo', order: 'desc'
		productos cascade: "all-delete-orphan"
		
	}
}
