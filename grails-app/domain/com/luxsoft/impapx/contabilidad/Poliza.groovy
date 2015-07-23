package com.luxsoft.impapx.contabilidad

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import org.springframework.context.ApplicationEvent;


class Poliza {
	
	String tipo
	Integer folio
	Date fecha;
	String descripcion
	BigDecimal debe
	BigDecimal haber
	List partidas
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [partidas:PolizaDet]

    static constraints = {
		//referencia(maxSize:30)
		descripcion(blank:false,maxSize:250)
		debe(nullable:false,scale:6)
		haber(nullable:false,scale:6)
		tipo(inList:['INGRESO','EGRESO','DIARIO','COMPRAS','GENERICA','CIERRE_ANUAL'])
    }
	
	
	static mapping ={
		partidas cascade: "all-delete-orphan"
	}
	
	static transients = {'cuadre'}
	
	def getCuadre(){return debe-haber}
	
	boolean equals(Object obj){
		if(!obj.instanceOf(Poliza))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(id, obj.tipo)
		eb.append(id, obj.folio)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(tipo)
		hb.append(folio)
		return hb.toHashCode()
	}
	
	def actualizarImportes(){
		if(!partidas) partidas=[]
		debe=partidas.sum (0.0,{it.debe})
		haber=partidas.sum(0.0,{it.haber})
	}
	
	def beforeInsert(){
		actualizarImportes()
		//cuadrar()
	}
	
	def beforeUpdate(){
		actualizarImportes()
		//cuadrar()
	}
	
	def cuadrar(){
		def dif=debe-haber
		
		if(dif.abs()<=0.0)
			return
		if(dif.abs()<5.0){
			
			//Otros productos/gastos
			if(dif>0.0){
				
				def cta=CuentaContable.findByClave("702-0003")
				println 'Generando PRODUCTO en poliza: '+dif+' Cta: '+cta
				//Producto
				addToPartidas(
					cuenta:CuentaContable.findByClave("702-0003"),
					debe:0.0, 
					haber:dif.abs(),
					asiento:"OTROS INGRESOS "+tipo,
					descripcion:"OTROS INGRESOS",
					referencia:"",
					fecha:fecha
					,tipo:tipo
				)
			}else{
				//Gasto
				def cta=CuentaContable.findByClave("704-0002")
				println 'Cuenta localizada: '+cta
				addToPartidas(
					cuenta:CuentaContable.findByClave("704-0002"),
					debe:dif.abs(),
					haber:0.0,
					asiento:"OTROS GASTOS "+tipo,
					descripcion:"OTROS GASTOS",
					referencia:"",
					fecha:fecha
					,tipo:tipo
				)
			
			}
		}else{
			//Cuadre por verificar
			println 'Registrando diferencia: '+dif
			addToPartidas(
			cuenta:CuentaContable.findByClave("800-0001"),
			debe:dif<0.0?dif.abs():0.0,
			haber:dif>0.0?dif.abs():0.0,
			asiento:"CUDRE POR ACLARAR "+tipo,
			descripcion:"VERIFICAR",
			referencia:"",
			fecha:fecha
			,tipo:tipo
			)
		}
		
	}
	
	def afterUpdate(){
		publishEvent(new PolizaUpdateEvent(this))
	}
	
	def afterInsert(){
		log.info('Insertando poliza...')
		publishEvent(new PolizaUpdateEvent(this))
	}
}

class PolizaUpdateEvent extends ApplicationEvent{
	
	PolizaUpdateEvent(Poliza poliza){
		super(poliza)
	}
}
