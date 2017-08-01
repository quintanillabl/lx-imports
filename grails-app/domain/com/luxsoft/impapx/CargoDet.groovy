package com.luxsoft.impapx

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.luxsoft.impapx.Venta
import com.luxsoft.cfdi.Cfdi

class CargoDet {
  
  BigDecimal cantidad = 1.0
  String unidad = 'P1'
  String numeroDeIdentificacion = 'CARGOS'
  String claveProdServ = '01010101'
  String descripcion = 'Cargo moratorio'
  BigDecimal valorUnitario = 0.0
  BigDecimal importe = 0.0
  String comentario = ''

  String documento 
  BigDecimal saldo = 0.0
  Date corte
  Date vto
  Integer atraso
  Boolean mismoMes = true
  Integer diasPena = 0  
  BigDecimal tasaCetes = 0.0699 
  BigDecimal penaPorDia = 0.0
  
  Cfdi cfdi
  
  static belongsTo = [venta:Venta]

  static constraints = {
    cantidad(nullable:false,scale:2)
    unidad(nullable:false,maxSize:100)
    numeroDeIdentificacion(nullable:false,maxSize:50)
    descripcion(nullable:false,maxSize:200)
    //valorUnitario(nullable:false,scale:2)
    //importe(nullable:false,scale:2)
    cfdi nullable: true
    comentario(nullable:false,maxSize:300)
  }
  
  String toString(){
    return "$numeroDeIdentificacion $descripcion $cantidad $importe"
  }

  @Override
  public boolean equals(Object obj) {
    if(! (obj.instanceOf(VentaDet)) )
      return false
    if(this.is(obj))
      return true
    def eb=new EqualsBuilder()
    eb.append(cantidad, obj.id)
    eb.append(numeroDeIdentificacion, obj.numeroDeIdentificacion)
    eb.append(cfdi, obj.cfdi)
    return eb.isEquals()
  }
  
  @Override
  public int hashCode() {
    def hcb=new HashCodeBuilder(17,35)
    hcb.append(id)
    hcb.append(numeroDeIdentificacion)
    hcb.append(cfdi)
    return hcb.toHashCode()
  }
  
}
