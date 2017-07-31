package com.luxsoft.impapx

import com.luxsoft.impapx.Venta

class CargoDet {
  
  BigDecimal cantidad
  String unidad
  String numeroDeIdentificacion
  String descripcion
  BigDecimal valorUnitario
  BigDecimal importe
  String comentario
  
  static belongsTo = [venta:Venta]

  static constraints = {
    cantidad(nullable:false,scale:2)
    unidad(nullable:false,maxSize:100)
    numeroDeIdentificacion(nullable:false,maxSize:50)
    descripcion(nullable:false,maxSize:200)
    valorUnitario(nullable:false,scale:2)
    importe(nullable:false,scale:2)
    comentario(nullable:false,maxSize:300)
  }
  
  String toString(){
    return "$numeroDeIdentificacion $descripcion $cantidad $importe"
  }
  
}
