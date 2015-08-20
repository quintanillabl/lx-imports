package com.luxsoft.impapx

class Embarque {

	static auditable = true
	
	String bl
	Proveedor proveedor
	Date fechaEmbarque
	String nombre
	Aduana aduana
	Date ingresoAduana
	Integer contenedores
	String comentario
	Currency moneda;
	BigDecimal tc
	Date liberado
	Long cuentaDeGastos
	BigDecimal facturado
	BigDecimal valor
	
	List partidas
	
	static hasMany = [partidas:EmbarqueDet]
	
	Date dateCreated
	Date lastUpdated

    static constraints = {
		bl(blank:false,size:1..100,unique:true)		
		nombre(blank:false)
		fechaEmbarque(nullable:false)
		proveedor(nullable:false)
		ingresoAduana(nullable:true)
		contenedores(nullable:false)
		comentario(nullable:true,size:1..250)
		moneda(nullable:false,size:3)
		tc(nullable:false,scale:4)
		liberado(nullable:true)
    }
	
	String toString(){
		return "Id: ${id} BL: ${bl} $nombre (${proveedor.nombre})"
	}
	
	
	BigDecimal getTotal(String property){
		return partidas.sum(){
			it."${property}"
		}
	}
	
	static mapping = {
		partidas cascade: "all-delete-orphan"
		proveedor fetch:'join'
		aduana fetch:'join'
		cuentaDeGastos formula:"(SELECT max(g.id) FROM  cuenta_de_gastos g  where g.embarque_id=id)"
		facturado formula:"(select ifnull(sum(d.importe),0) from Venta_det d join embarque_det x on(x.id=d.embarque_id) where x.embarque_id=id)"
		valor formula:"(SELECT round(ifnull(sum(d.cantidad/u.factor*d.precio_de_venta),0),2) FROM  embarque_det d join producto p on(p.id=d.producto_id) join unidad u on(u.id=p.unidad_id) where d.embarque_id=id)"
	}
	
	
	BigDecimal porFacturar(){
		return valor-facturado
	}
	
	
}
