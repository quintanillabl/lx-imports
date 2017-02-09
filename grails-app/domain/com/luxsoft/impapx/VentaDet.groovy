package com.luxsoft.impapx


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

class VentaDet {
		
	static auditable = true
	
	Producto producto
	BigDecimal cantidad
	BigDecimal precio
	BigDecimal importe
	BigDecimal descuentos=0
	BigDecimal subtotal
	BigDecimal impuesto
	BigDecimal impuestoTasa=16
	
	BigDecimal costo
	BigDecimal kilos
	String comentario
	String contenedor
	EmbarqueDet embarque
	String aduana
	
	Date dateCreated
	Date lastUpdated

	static belongsTo = [venta:Venta]
	

    static constraints = {
		producto(nullable:false)
		cantidad(nullable:false,scale:3)
		//precio(nullable:false,scale:2,validator:{return it>0})
		precio(nullable:false,scale:2)
		importe(nullable:false,scale:2)
		descuentos(nullable:false,scale:2)
		subtotal(nullable:false,scale:2)
		impuesto(nullable:false,scale:2)
		costo(nullable:false,scale:2)
		kilos(nullable:false,scale:3)
		contenedor(nullable:true)
		embarque(nullable:true)
		comentario(nullable:true,maxSize:300)
		aduana(nullable:true,maxSize:50)
    }
	
	static mapping = {
		
		producto fetch:'join'
		
	}
	
	def beforeDelete(){
		//Actaulizar la vinvulacion
	}
	
	def actualizarImportes(){
		importe=precio*cantidad/(producto.unidad.factor)
		subtotal=importe
		//impuesto=subtotal*(impuestoTasa/100)
		impuesto=0
	}
	
	def BigDecimal getCantidadEnUnidad(){
		return cantidad/producto.unidad.factor
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(VentaDet)) )
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(producto, obj.producto)
		eb.append(cantidad, obj.cantidad)
		eb.append(cantidad, obj.precio)
		eb.append(comentario, obj.comentario)
		return eb.isEquals()
	}
	
	@Override
	public int hashCode() {
		def hcb=new HashCodeBuilder(17,35)
		hcb.append(producto)
		hcb.append(cantidad)
		hcb.append(precio)
		hcb.append(comentario)
		return hcb.toHashCode()
	}
}
