package com.luxsoft.impapx

import com.luxsoft.impapx.contabilidad.CuentaContable
import com.luxsoft.lx.sat.BancoSat

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

	String cuentaBancaria
	BancoSat bancoDestino

	String paisDeOrigen

	String nacionalidad

	Boolean agenciaAduanal = false
	
	
	Date dateCreated
	Date lastUpdated
	
	static embedded = ['direccion']
	
	static hasMany = [productos:ProveedorProducto,agentes:String]

	
	
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
		cuentaBancaria nullable:true, maxSize:30
		bancoDestino nullable:true
		//agenciaAduanal nullable:true
    }
	
	String toString(){
		return nombre;
	}
	
	static mapping = {
		//productos sort: 'codigo', order: 'desc'
		productos cascade: "all-delete-orphan"
		// hasMany joinTable: [name: 'agentes_aduanales',
		//                            key: 'agente_id',
		//                            column: 'nickname',
		//                            type: "text"]
		
	}
}
