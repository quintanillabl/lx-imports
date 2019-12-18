package com.luxsoft.impapx.contabilidad

// import org.apache.commons.lang.builder.EqualsBuilder
// import org.apache.commons.lang.builder.HashCodeBuilder
import org.springframework.context.ApplicationEvent;

import org.grails.databinding.BindingFormat
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import com.luxsoft.lx.utils.MonedaUtils


@EqualsAndHashCode(includes='ejercicio,mes,tipo,folio')
@ToString(includeNames=true,includePackage=false)
class Poliza {

	static auditable = true

	Integer ejercicio

	Integer mes
	
	String tipo

	String subTipo

	Integer folio = 0

	@BindingFormat('dd/MM/yyyy')
	Date fecha

	String descripcion

	BigDecimal debe = 0.0

	BigDecimal haber = 0.0

	Boolean manual=false

	Date cierre

	List partidas = []
	
	Date dateCreated

	Date lastUpdated
	
	static hasMany = [partidas:PolizaDet]

    static constraints = {
		//referencia(maxSize:30)
		ejercicio inList:(2013..2030)
		mes inList:(1..13)
		tipo(inList:['INGRESO','EGRESO','DIARIO','COMPRAS','GENERICA','CIERRE_ANUAL'])
		//tipo(inList:['INGRESO','EGRESO','DIARIO'])
		subTipo(maxSize:30)
		//folio unique:['ejercicio','mes','tipo','subTipo']
		descripcion(blank:false,maxSize:250)
		debe(nullable:false,scale:6)
		haber(nullable:false,scale:6)
		cierre nullable:true
    }
	
	
	static mapping ={
		partidas cascade: "all-delete-orphan"
		fecha type:'date'
	}
	
	static transients = {'cuadre'}
	
	def getCuadre(){
		return MonedaUtils.round(debe-haber)
	}
	
	def actualizar(){
		debe=MonedaUtils.round( partidas.sum (0.0,{it.debe}))
		haber=MonedaUtils.round( partidas.sum(0.0,{it.haber}))
	}

	
	def beforeInsert(){
		actualizar()
	}
	
	def beforeUpdate(){
		actualizar()
	}
	def afterInsert() {
		actualizar()
	}
	def afterUpdate() {
		actualizar()
	}
	
	def cuadrar(){
		def dif=debe-haber
		
		if(dif.abs()<=0.0)
			return
		if(dif.abs()<5.0){
			
			//Otros productos/gastos
			if(dif>0.0){
				
				def cta=CuentaContable.findByClave("704-0006")
				log.info 'Generando PRODUCTO en poliza: '+dif+' Cta: '+cta
				//Producto
				addToPartidas(
					cuenta:cta,
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
				def cta=CuentaContable.findByClave("703-0003")
				log.info 'Cuenta localizada: '+cta
				addToPartidas(
					cuenta:cta,
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
			log.info 'Registrando diferencia: '+dif
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
	
	// def afterUpdate(){
	// 	//publishEvent(new PolizaUpdateEvent(this))
		
	// }
	
	// def afterInsert(){
		
	// 	//log.info('Insertando poliza...')
	// 	//publishEvent(new PolizaUpdateEvent(this))
	// }
}

class PolizaUpdateEvent extends ApplicationEvent{
	
	PolizaUpdateEvent(Poliza poliza){
		super(poliza)
	}
}
