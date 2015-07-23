package com.luxsoft.impapx

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import util.Rounding;

class EmbarqueDet {
	
	Producto producto
	CompraDet compraDet
	String contenedor
	Integer tarimas=0
	
	BigDecimal kilosNetos=0 // El usuario captura
	BigDecimal kilosEstimados=0 //Se calculan conforme al producto con la formula:  (cantidad/producto.unidad.factor)*producto.kilosPorMillar
	
	BigDecimal cantidad
	BigDecimal precio=0	//Precio por tonelada en el catalogo de ProveedorProducto
	BigDecimal importe=0  		//kilosNetos*costoUnitario
	BigDecimal tc=0
	
	// Costos en MN
	BigDecimal costoBruto=0
	BigDecimal gastosHonorarios=0 //(kilosNetos*totalHonorarios)/embarque.kilosNetos
	BigDecimal gastosPorPedimento=0 //(kilosNetos*valorPedimentoAsignado)/
	BigDecimal incrementables=0
	BigDecimal costoNeto=0  //costoBruto+gosto
	BigDecimal costoUnitarioNeto=0
	BigDecimal precioDeVenta=0
	BigDecimal gramos
	
	CuentaPorPagar factura
	Pedimento pedimento
	
	static belongsTo = [embarque:Embarque]
	static hasMany = [distribuciones:DistribucionDet]
	

    static constraints = {
		producto(nullable:true)
		compraDet(nullable:false)
		contenedor(nullable:true,maxSize:30)
		
		precio(scale:2)
		importe(scale:2)
		costoBruto(scale:2)
		gastosHonorarios(scale:4)
		gastosPorPedimento(scale:2)
		costoUnitarioNeto(scale:4)
		precioDeVenta(scale:2)
		
		factura(nullable:true)
		pedimento(nullable:true)
		cantidad(scale:3)
		incrementables(nullable:true,scale:2)
		gramos(nullable:true)
    }
	
	static mapping={
		producto fetch:'join'
	}
	
	static transients = ['importeDeVenta','CostoDeImportacion','CostoBrutoMateriaPrima','kilosPorMillar']
	
	BigDecimal getImporteDeVenta(){
		return (precioDeVenta*cantidad)/producto.unidad.factor
	}
	
	BigDecimal getKilosPorMillar(){
		return ((producto.ancho*producto.largo)/10000)*producto.gramos
	}
	
	/*
	def importeMN(){
		return importe/tc
	}*/
	
	void actualizarCostos(){
		
		// Importes en moneda origen
		//Modifica el costoBruto en para los casos de costeo especial
		if(embarque.proveedor.tipoDeCosteo=='ESPECIAL'){
			def res=Math.min(kilosEstimados, kilosNetos)
			importe=(res*precio)/1000
			importe=costoBruto.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		//Costos en moneda nacional
		tc=0
		if(factura)
			tc=factura.tc
		if(pedimento)
			tc=pedimento.tipoDeCambio
		costoBruto=importe*tc
		costoBruto=costoBruto.setScale(2, BigDecimal.ROUND_HALF_UP);
		if(tc>0){
			costoNeto=costoBruto+gastosHonorarios+gastosPorPedimento+incrementables
			costoUnitarioNeto=costoNeto/(cantidad/producto.unidad.factor)
		}else{
			costoNeto=0.0
			costoUnitarioNeto=0.0
		}
		
	}
	
	
	
	
	void actualizarPrecioDeVenta(){
		def factor=embarque.proveedor.factorDeUtilidad?:0
		factor=1+(factor/100)
		def res=costoUnitarioNeto*factor
		precioDeVenta=res
	}
	
	//Se calculan conforme al producto con la formula:  (cantidad/producto.unidad.factor)*producto.kilosPorMillar
	void actualizarKilosEstimados(){
		def res=(cantidad/producto.unidad.factor)*producto.kilos
		kilosEstimados=res
	}
	
	def cantidadPorTarima(){
		if(tarimas>0)
			return cantidad/this.tarimas
		return 0;
	}
	
	def beforeUpdate(){
		actualizarCostos()
		actualizarPrecioDeVenta()
	}
	def beforeInsert(){
		actualizarCostos()
		actualizarPrecioDeVenta()
	}
	
	BigDecimal getCostoDeImportacion(){
		
		if(costoBruto>0){
			def costos=gastosHonorarios+gastosPorPedimento+incrementables
			def res=(costos/costoBruto)
			return res;
		}else
			return 0.0;
		
	}
	
	
	boolean equals(Object obj){
		if(!obj.instanceOf(EmbarqueDet))
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(id, obj.id)
		eb.append(producto, obj.producto)
		eb.append(cantidad, obj.cantidad)
		eb.append(kilosNetos, obj.kilosNetos)
		return eb.isEquals()
	}
	
	int hashCode(){
		def hb=new HashCodeBuilder(17,35)
		hb.append(id)
		hb.append(producto)
		hb.append(cantidad)
		hb.append(kilosNetos)
		return hb.toHashCode()
	}
	
	
	def getCostoBrutoMateriaPrima(){
		//var costo=$("#precio").val();
		//var kilos=$(this).val();
		//kilos=kilos/1000 // Precio por tonelada
		//var importe=kilos*costo;
		//importe=Math.round(importe*100)/100;
		//$("#importe").val(importe);
		
		
		def kilos=kilosNetos
		if(embarque.proveedor.tipoDeCosteo=='ESPECIAL'){
			kilos=Math.min(kilosEstimados, kilosNetos)
		}
		def importe=(kilos*precio)/1000
		return Rounding.round(importe, 6)
	}
	
	
	
}
