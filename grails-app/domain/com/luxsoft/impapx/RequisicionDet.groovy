package com.luxsoft.impapx


import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class RequisicionDet {

	static auditable = true
	
	String documento
	Date fechaDocumento
	BigDecimal totalDocumento=0
	
	BigDecimal importe=0
	BigDecimal impuestos=0
	BigDecimal total=0
	
	//Moneda nacional
	BigDecimal ietu=0
	BigDecimal retencionHonorarios=0
	BigDecimal retencionFlete=0
	BigDecimal retencionISR=0
	
	CuentaPorPagar factura
	
	Embarque embarque
	BigDecimal impuestosAduanales=0.0
	BigDecimal gastosDeImportacion=0.0
	
	Date dateCreated
	Date lastUpdated
	
	static belongsTo = [requisicion:Requisicion]

    static constraints = {
		documento(blank:false,maxSize:20)
		factura(nullable:true)
		impuestosAduanales (nullable:true)
		gastosDeImportacion( nullable:true) 
		embarque(nullable:true)
    }
	
	static mapping ={
		requisicion fetch:'join'
		partidas cascade: "all-delete-orphan"
	}
	
	def actualizarImportes(){
		//total=importe+(impuestos-retencionFlete-retencionHonorarios-retencionISR)
	}
	
	def beforeUpdate(){
		actualizarImportes()
	}
	
	def beforeInsert(){
		actualizarImportes()
	}
	
	def afterDelete(){
		//println 'RequisicionDet eliminado actualizando requisitado de factura....'
		//actualizarRequisitadoEnFactura()
	}
	def afterInsert(){
		//println 'RequisicionDet insertada actualizando requisitado de factura....'
		//actualizarRequisitadoEnFactura()
	}
	def afterUpdate(){
		//actualizarRequisitadoEnFactura()
	}
	
	def actualizarRequisitadoEnFactura(){
		if(factura){
			CuentaPorPagar.withNewSession {
				def fac=CuentaPorPagar.get(factura.id)
				def sum=CuentaPorPagar.executeQuery("select sum(a.total) from RequisicionDet a where a.factura.id=?",[fac.id])
				fac.requisitado=sum[0]?:0.0
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(RequisicionDet)) )
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(documento, obj.documento)
		return eb.isEquals()
	}
	@Override
	public int hashCode() {
		def hcb=new HashCodeBuilder(17,35)
		hcb.append(id)
		hcb.append(documento)
		return hcb.toHashCode()
	}
	
}
