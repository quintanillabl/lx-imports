package com.luxsoft.impapx.contabilidad

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import com.luxsoft.lx.sat.CuentaSat


class CuentaContable {
	
	String clave
	String descripcion
	String tipo
	String subTipo
	CuentaContable padre
	boolean detalle=false
	boolean deResultado=false
	String naturaleza
	boolean presentacionContable=false
	boolean presentacionFiscal=false
	boolean presentacionFinanciera=false
	boolean presentacionPresupuestal=false
	CuentaSat cuentaSat

	Boolean suspendida = false
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [subCuentas:CuentaContable]

    static constraints = {
		clave(nullable:true,maxSize:100)
		descripcion(blank:false,maxSize:300)
		detalle(nullable:false)
		tipo(nullable:false,inList:['ACTIVO','PASIVO','CAPITAL','ORDEN'])
		subTipo(nullable:true)
		naturaleza(inList:['DEUDORA','ACREEDORA','MIXTA'])
		cuentaSat(nullable:true)
		
    }
	
	static mapping ={
		subCuentas cascade: "all-delete-orphan"
	}
	
	String toString(){
		return clave+" "+descripcion
	}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(CuentaContable))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(id, obj.clave)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(clave)
		return hb.toHashCode()
	}
	
	static CuentaContable buscarPorClave(String clave){
		def found=CuentaContable.findByClave(clave)
		if(!found)

			throw new RuntimeException("No existe la cuenta contable: $clave")
		return found
	}
}
