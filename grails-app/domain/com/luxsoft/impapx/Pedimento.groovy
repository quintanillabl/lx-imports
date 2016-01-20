package com.luxsoft.impapx

import java.math.MathContext;
import java.math.RoundingMode;

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import com.luxsoft.lx.utils.*
import util.Rounding;

/**
 * Entidad que representa las propiedades y caracteristicas de los pedimentos de importacion
 * mismos que estan asicioados a n facturas de importacion
 * 
 * @author Ruben Cancino
 *
 */
class Pedimento {

	static auditable = true
	
	Date fecha
	String pedimento
	BigDecimal dta=0
	BigDecimal prevalidacion=0
	BigDecimal tipoDeCambio=0
	BigDecimal arancel=0
	BigDecimal impuestoTasa=16
	BigDecimal impuesto=0
	BigDecimal incrementables=0.0
	String comentario
	Proveedor proveedor
	com.luxsoft.impapx.CuentaPorPagar incrementable1
	String referenciacg

	String agenteAduanal

	PaisDeOrigen paisDeOrigen
	BigDecimal contraPrestacion = 0.0
	
	
	Date dateCreated
	Date lastUpdated
	
	
	static hasMany =[embarques:EmbarqueDet]
	

    static constraints = {
		fecha(nullable:false)
		pedimento(nullable:false,blank:false,unique:true,maxSize:50)
		dta(nullable:false,scale:2)
		prevalidacion(nullable:false,scale:2)
		arancel(nullable:true,scale:2)
		tipoDeCambio(scale:4)
		impuesto(scale:2)
		impuestoTasa(sacle:4)
		comentario(nullable:true,maxSize:250)
		proveedor(nullable:true)
		incrementable1(nullable:true)
		referenciacg(nullable:true,maxSize:50)
		agenteAduanal(nullable:true)
		paisDeOrigen(nullable:true)
    }
	
	static mapping ={
		//embarques fetch:'join'
		//incrementables formula:'(select ifnull(sum(x.incrementables),0) from embarque_det x where x.pedimento_id=id)'
	}
	
	/**
	 * Total de impuestos solo para costeo
	 * 
	 * @return
	 */
	BigDecimal getTotal(){
		
		return dta+prevalidacion+arancel
	}
	
	BigDecimal getImpuestoMateriaPrima(){
		def impuestos=0
		impuestos=embarques.sum(0, {
			it.importe*tipoDeCambio*(this.impuestoTasa/100)
		})
		return Rounding.round(impuestos,2)
	}
	
	BigDecimal getIvaAcreditable(){
		def iva=dta+arancel+incrementables
		iva=iva*(this.impuestoTasa/100)
		return Rounding.round(iva+getImpuestoMateriaPrima(),0) 
	}
	
	BigDecimal getImpuestoPrevalidacion(){
		return Rounding.round(prevalidacion*(this.impuestoTasa/100),0)
	}
	
	static transients = ['total','impuestoMateriaPrima','ivaAcreditable']
	
	def beforeUpdate() {
		actualizarImpuestos()
		actualizarCostos()
	}
	
	def actualizarImpuestos(){
		//Actualizar los impuestos
		//impuesto=calcularImpuestoDinamico()
		def pedimento =this
		def tc= pedimento.tipoDeCambio
		def factorIva = pedimento.impuestoTasa/100


		def ivaMateriaPrima = pedimento.embarques.sum 0.0,{
    		it.importe*tc*factorIva
		}
		def ivaIncrementables = pedimento.incrementables * factorIva

		def ivaPrevalidacion = pedimento.prevalidacion * factorIva

		def ivaDta = pedimento.dta*factorIva

		def ivaArancel = pedimento.arancel*factorIva

		def ivaContraPrestacion = pedimento.contraPrestacion*factorIva
/*
println " Iva M.P. : "+ivaMateriaPrima
println " Iva Incrementables: "+ivaIncrementables

println " Iva DTA: "+ivaDta
println " Iva Arancel: "+ivaArancel
println " Iva Prevalidacion: "+ivaPrevalidacion
println " Iva Contra prestacion: "+ivaContraPrestacion
*/
		def iva1 = ivaMateriaPrima + ivaIncrementables + ivaDta + ivaArancel
		def totalCP = MonedaUtils.round(ivaPrevalidacion + ivaContraPrestacion + pedimento.contraPrestacion,0)

//println "Iva 1 "+ iva1
//println "Total cp: "+totalCP

		this.impuesto = iva1 + pedimento.dta + pedimento.arancel + pedimento.prevalidacion + totalCP
		this.impuesto = MonedaUtils.round(this.impuesto,0)
//println " Valor del pedimento "+impuesto

	}
	
	/*
	def BigDecimal calcularImpuestoDinamico(){
		def impuesto=0
		impuesto=embarques.sum (0.0,{
			it.importe*tipoDeCambio*(this.impuestoTasa/100)
			}
		)
		impuesto=impuesto.setScale(2, BigDecimal.ROUND_HALF_UP);
			def iva=0
		def ivaPrev=Rounding.round(this.prevalidacion*(1+this.impuestoTasa/100),0)
		iva=(this.dta+arancel)*(1+this.impuestoTasa/100)
		impuesto=Rounding.round(impuesto+iva,0)+ivaPrev
		return impuesto
	}
	*/
	
	def actualizarCostos(){
	
		def importe=getTotal()
		def kilosTotales=embarques.sum {it.kilosNetos}

		embarques.each {

			def gasto=it.kilosNetos*importe/kilosTotales
			gasto=gasto.setScale(2, BigDecimal.ROUND_HALF_UP);
			it.gastosPorPedimento=gasto
			
		}
	}
	
	/*
	@Override
	public boolean equals(Object obj) {
		if(! (obj.instanceOf(Pedimento)) )
			return false
		if(this.is(obj))
			return true
		def eb=new EqualsBuilder()
		eb.append(pedimento, obj.pedimento)
		return eb.isEquals()
	}
	
	@Override
	public int hashCode() {
		def hcb=new HashCodeBuilder(17,35)
		hcb.append(pedimento)
		return hcb.toHashCode()
	}*/

	def getPaisDeVenta(){
		if(embarques)
			return embarques.embarque.first().proveedor.direccion.pais
		return 'NO DISPONILBE'
	}
}
