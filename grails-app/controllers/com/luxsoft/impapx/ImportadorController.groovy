package com.luxsoft.impapx

import com.luxsoft.impapx.contabilidad.CuentaContable;
import groovy.sql.Sql
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.exception.ExceptionUtils
import grails.plugin.springsecurity.annotation.Secured

/**
 * Controlador para la importacion de datos desde otras versiones del sistema
 * 
 * @author Ruben Cancino
 *
 */
 @Secured(["hasRole('COMPRAS')"])
class ImportadorController {
	
	def dataSource_importacion

    def index() {
		//redirect (action:'importadores')
	}
	def importadores(){
		def titulo="Importador de lineas"
		def fecha=new Date();
		[titulo:titulo,fecha:fecha]
	}
	
	def importarLineas(){
		def db=new Sql(dataSource_importacion)
		def res=db.eachRow("select * from sx_lineas") { row->
			Linea.findOrSaveByNombre(row.nombre)
		}
		redirect (controller:'linea', action:'list')
	}
	
	def importarMarcas(){
		def db=new Sql(dataSource_importacion)
		def res=db.eachRow("select * from sx_marcas") { row->
			Marca.findOrSaveByNombre(row.nombre)
		}
		redirect (controller:'marca', action:'list')
	}
	def importarClases(){
		def db=new Sql(dataSource_importacion)
		def res=db.eachRow("select * from sx_clases") { row->
			Clase.findOrSaveByNombre(row.nombre)
		}
		redirect (controller:'clase', action:'list')
	}

	def importarUnidades(){
		def db=new Sql(dataSource_importacion)
		def res=db.eachRow("select * from sx_unidades") { row->
			Unidad.findOrSaveWhere([clave:row.unidad,nombre:row.descripcion,factor:new BigDecimal(row.factor)])
		}
		redirect (controller:'unidad', action:'list')
	}
	
	def importarProductos(){
		def db = new Sql(dataSource_importacion)
		def res=db.eachRow("""
			select a.clave,a.descripcion,precioContado,precioCredito,b.nombre as linea,c.nombre as marca,d.nombre as clase,e.unidad as unidad
				   ,a.kilos,a.gramos,a.largo,a.ancho,a.calibre,a.caras,a.acabado,a.color,a.M2MILLAR as m2
						 from sx_productos a 
						 left join sx_lineas b on(a.linea_id=b.linea_id) 
						 left join sx_marcas c on (a.marca_id=c.marca_id) 
						 left join sx_clases d on (a.clase_id=d.clase_id)
						 left join sx_unidades e on(a.unidad=e.unidad)
						 where a.producto_id in(select producto_id from sx_proveedor_productos  where proveedor_id=60)
			"""
			)
		 { row->
			Producto producto=Producto.findOrCreateByClave(row.clave)
			producto.descripcion=row.descripcion
			producto.precioCredito=row.precioCredito
			producto.precioContado=row.preciocontado
			producto.linea=Linea.findByNombre(row.linea)
			producto.marca=row.marca!=null?Marca.findByNombre(row.marca):null
			producto.clase=row.clase!=null?Clase.findByNombre(row.clase):null
			producto.unidad=Unidad.findByClave(row.unidad)
			producto.kilos=row.kilos
			producto.gramos=row.gramos
			producto.largo=row.largo
			producto.ancho=row.ancho
			producto.calibre=row.calibre
			producto.caras=row.caras
			producto.acabado=row.acabado
			producto.color=row.color
			producto.m2=row.m2
			Producto pp=producto.save(failOnError:true)
			log.info 'Producto importado'+pp.properties
		}
		redirect (controller:'producto', action:'list',params:[max:20])
	}
	
	def importarProveedores(){
		def db=new Sql(dataSource_importacion)
		def res=db.eachRow("select * from impap.SX_PROVEEDORES") { row ->
			Proveedor.findOrSaveByNombre(row.nombre)
		}
		redirect (controller:'proveedor', action:'list',params:[max:20])
	}

	def importarProveedorProductos(){
		def db=new Sql(dataSource_importacion)
		def proveedores=Proveedor.list()
		proveedores.each { proveedor->
			
			def res=db.eachRow("""
			select b.nombre as proveedor,c.clave as producto,a.* from impap.sx_proveedor_productos	a 
				join impap.sx_proveedores b on (a.proveedor_id=b.proveedor_id)	
				join impap.sx_productos c on(a.producto_id=c.producto_id)
				where b.nombre=?
				""",[proveedor.nombre]){ row ->
						println 'Procesando: '+row
						Producto producto=Producto.findByClave(row.producto)
						if(producto){
							ProveedorProducto pp=new ProveedorProducto(proveedor:proveedor,producto:producto)
							pp.codigo=row.claveprov
							pp.descripcion=row.descriprov
							pp.costoUnitario=0
							pp.proveedor=proveedor
							pp.save(failOnError:true)
						}
						
						
				}
			
		}
		redirect(controller:'proveedor',action:'list')
		
	}


	/*	
	def importarCompras(){
		def sql=sql()
		def res=sql.eachRow("select * from SX_COMPRAS2  where PROVEEDOR_ID=60 and DEPURACION is null and fecha>=? ",[new Date().parse('dd/MM/yyyy','01/02/2012')]) { row ->
			Compra c=Compra.findOrCreateByOrigen(row.COMPRA_ID)
			Proveedor p=Proveedor.findOrSaveByNombre(row.nombre)
			c.proveedor=p
			c.fecha=row.fecha
			c.comentario=row.comentario
			c.origen=row.compra_id
			c.depuracion=row.depuracion
			c.folio=row.folio
			c.entrega=row.entrega
			c.moneda=Currency.getInstance(row.moneda)
			c.tc=row.tc
			c.importe=row.importe_bruto
			c.descuentos=row.importe_desc
			c.subtotal=row.importe_neto
			c.impuestos=row.impuestos
			c.total=row.total
			c.partidas=[]
			
			def dets=db.eachRow("select * from sx_compras2_det where compra_id=?",[row.compra_id]) {
				  
				CompraDet cd=new CompraDet()
				Producto prod=Producto.findByClave(it.clave)
				cd.producto=prod
				cd.solicitado=it.solicitado
				cd.precio=0
				cd.descuento=0
				cd.importeDescuento=0
				cd.importe=0
				cd.compra=c
				c.partidas.add(cd)
				//c.addToPartidas(cd)
				
			}
			
			c.save(failOnError:true)
		}
		redirect (controller:'compra', action:'list',params:[max:20])
	}
	*/
	
	def importarCompra(long folio){
		def proveedorOrigenParaCompras=grailsApplication.config.proveedorOrigenParaCompras
		assert proveedorOrigenParaCompras,'No hay proveedor origen de compras para importacion'
		Compra found=Compra.findByFolio(folio)

		if(found){
			throw new RuntimeException("Compra $folio ya importada, borrar si se requiere re importar");
		}
		log.info 'Importando compra: '+folio +" Proveedor origen ID: "+proveedorOrigenParaCompras
		
		SingleConnectionDataSource ds=new SingleConnectionDataSource(

		        driverClassName:'com.mysql.jdbc.Driver',
		        url:'jdbc:mysql://10.10.1.229/siipapx',
		        username:'root',
		        password:'sys',
		        suppressClose:true)
		Sql sql=new Sql(ds)

		//def sql= new Sql(dataSource_importacion)

		def row=sql.firstRow("SELECT c.*,p.nombre FROM compra c join proveedor p on (p.id=c.proveedor_id)  where PROVEEDOR_ID=?  and folio=? and fecha>'2017-01-01' ",
			,[proveedorOrigenParaCompras,folio])
		log.info 'Importando :'+row
		
		Compra c=Compra.findOrCreateByOrigen(row.id)
		Proveedor p=Proveedor.findOrSaveByNombre(row.nombre)

		c.proveedor=p
		c.fecha=row.fecha
		c.comentario=row.comentario
		c.origen=row.id
		c.depuracion=row.ultima_depuracion
		c.folio=row.folio
		c.entrega=row.entrega
		c.moneda=Currency.getInstance(row.moneda)
		c.tc=row.tipo_de_cambio
		c.importe=row.importe_bruto
		c.descuentos=row.importe_descuento
		c.subtotal=row.importe_neto
		c.impuestos=row.impuestos
		c.total=row.total
		c.partidas=[]
		log.info 'Importando partidas '+row.id
		//def rows=sql().rows('select * from sx_compras2_det where compra_id=:id',[id:row.compra_id] )
		//def sql2=sql()
		
		sql.eachRow('select d.*,p.clave from compra_det d join producto p on (p.id=d.producto_id) where compra_id=?',[c.origen]) {
			CompraDet cd=new CompraDet()
			Producto prod=Producto.findByClave(it.clave)
			cd.producto=prod
			cd.solicitado=it.solicitado
			cd.precio=0
			cd.descuento=0
			cd.importeDescuento=0
			cd.importe=0
			cd.compra=c
			c.partidas.add(cd)
			//c.addToPartidas(cd)
		}
		
		def comp=c.save(failOnError:true,flush:true)
		log.info 'Compra generada: '+comp
		redirect (controller:'compra', action:'edit',id:comp.id)
		/*
		def res=sql.eachRow("select * from SX_COMPRAS2  where PROVEEDOR_ID=?  and folio=? and fecha>'2012-01-01' "
			,[proveedorOrigenParaCompras,folio]) { row ->
			log.info 'Importando compra: '+row.folio +" Prov: "+row.nombre
			Compra c=Compra.findOrCreateByOrigen(row.COMPRA_ID)
			Proveedor p=Proveedor.findOrSaveByNombre(row.nombre)
			c.proveedor=p
			c.fecha=row.fecha
			c.comentario=row.comentario
			c.origen=row.compra_id
			c.depuracion=row.depuracion
			c.folio=row.folio
			c.entrega=row.entrega
			c.moneda=Currency.getInstance(row.moneda)
			c.tc=row.tc
			c.importe=row.importe_bruto
			c.descuentos=row.importe_desc
			c.subtotal=row.importe_neto
			c.impuestos=row.impuestos
			c.total=row.total
			c.partidas=[]
			
			def dets=sql.eachRow("select * from sx_compras2_det where compra_id=?",[row.compra_id]) {
				  
				CompraDet cd=new CompraDet()
				Producto prod=Producto.findByClave(it.clave)
				cd.producto=prod
				cd.solicitado=it.solicitado
				cd.precio=0
				cd.descuento=0
				cd.importeDescuento=0
				cd.importe=0
				cd.compra=c
				c.partidas.add(cd)
				//c.addToPartidas(cd)
				
			}
			
			def comp=c.save(failOnError:true)
			log.info 'Compra generada: '+comp
		}
		redirect (controller:'compra', action:'index')
		*/
	}
	
	/**
	 * Temporal para la fase de desarrollo e implementacion
	 * 
	 * @return
	 */
	def importarCatalogos(){
		importarLineas()
		importarClases()
		importarMarcas()
		importarProductos()
		importarProveedores()
	}
	
	def importarProducto(){
		//println 'Importando productos '+params
		def db = new Sql(dataSource_importacion)
		
		
	/*	def rows=db.rows("""
			select a.clave,a.descripcion,precioContado,precioCredito,b.nombre as linea,c.nombre as marca,d.nombre as clase,e.unidad as unidad
				   ,a.kilos,a.gramos,a.largo,a.ancho,a.calibre,a.caras,a.acabado,a.color,a.M2MILLAR as m2
						 from sx_productos a 
						 left join sx_lineas b on(a.linea_id=b.linea_id) 
						 left join sx_marcas c on (a.marca_id=c.marca_id) 
						 left join sx_clases d on (a.clase_id=d.clase_id)
						 left join sx_unidades e on(a.unidad=e.unidad)
						 where a.clave=?
			""",[params.clave]
			)*/
		 
		//println 'Productos a importar: '+rows
		def res=db.eachRow("""
			select a.clave,a.descripcion,precioContado,precioCredito,b.nombre as linea,c.nombre as marca,d.nombre as clase,e.unidad as unidad
				   ,a.kilos,a.gramos,a.largo,a.ancho,a.calibre,a.caras,a.acabado,a.color,a.M2MILLAR as m2
						 from sx_productos a 
						 left join sx_lineas b on(a.linea_id=b.linea_id) 
						 left join sx_marcas c on (a.marca_id=c.marca_id) 
						 left join sx_clases d on (a.clase_id=d.clase_id)
						 left join sx_unidades e on(a.unidad=e.unidad)
						 where a.clave=?
			""",[params.clave]
			)
		 { row->
			
			Producto producto=Producto.findOrCreateByClave(row.clave)
			producto.descripcion=row.descripcion
			producto.precioCredito=row.precioCredito
			producto.precioContado=row.preciocontado
			producto.linea=Linea.findByNombre(row.linea)
			producto.marca=row.marca!=null?Marca.findByNombre(row.marca):null
			producto.clase=row.clase!=null?Clase.findByNombre(row.clase):null
			producto.unidad=Unidad.findByClave(row.unidad)
			producto.kilos=row.kilos
			producto.gramos=row.gramos
			producto.largo=row.largo
			producto.ancho=row.ancho
			producto.calibre=row.calibre
			producto.caras=row.caras
			producto.acabado=row.acabado
			producto.color=row.color
			producto.m2=row.m2
			Producto pp=producto.save(failOnError:true)
			log.info 'Producto importado'+pp.properties
		}
		db.close()
		redirect (controller:'producto', action:'index',params:[max:20])
	}
	
	
	def importarCuentasContables(){
		def db=new Sql(dataSource_importacion)
		def res=db.eachRow("SELECT * FROM SX_CUENTAS_CONTABLES") { row ->
			
			CuentaContable c=new CuentaContable()
			c.clave=row.clave
			c.descripcion=row.descripcion
			c.tipo=row.tipo
			c.subTipo=row.sub_tipo
			c.deResultado=row.de_resultado
			c.naturaleza=row.naturaleza
			c.presentacionContable=row.pres_contable
			c.presentacionFiscal=row.pres_fiscal
			c.presentacionFinanciera=row.pres_financiera
			c.presentacionPresupuestal=row.pres_presupuestal
			
			c.save(failOnError:true)
			}
		redirect (action:'list',params:[max:100])
	}
	/*
	def agregarCuentaContable(String cuentaMayor){
		def cuenta=CuentaContable.findByClave(cuentaMayor)
		def detalle=new CuentaContable(params)
		detalle.detalle=true
		cuenta.addToSubCuentas(detalle)
		cuenta.save(flush:true)
		redirect (action:'list',params:[max:100])
	}
	*/

	private sql(){
    	//def db=grailsApplication.config.luxor.empleadosDb
    	SingleConnectionDataSource ds=new SingleConnectionDataSource(
            driverClassName:'com.mysql.jdbc.Driver',
            url:"jdbc:mysql://10.10.1.228/produccion",
            username:"root",
            password:"sys")
        Sql sql=new Sql(ds)
        return sql
    }
}
