package com.luxsoft.econta.polizas

import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.lx.contabilidad.*

class PolizaUtils {
	
	
	


	public static IvaTrasladadoPendiente(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'209-0001')
	} 
	public static IvaTrasladadoCobrado(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'208-0001')	
	}

	public static IvaAcreditable(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'118-0001')	
	}

	public static IvaPendienteDeAcreditar(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'115-0001')
	}
	

	public static ContablesNoFiscales(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'704-0002')	
	}

	public static OtrosGastosNoFiscales(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'703-0002')		
	}

	
	public static RetencionIsrHonorariosAsimilados(def empresa){
		return CuentaContable.buscarPorClave(empresa,'216-0004')
	}

	public static RetencionIsrHonorarios(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'216-0001')
	}

	public static RetencionIsrServiciosProfesionales(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'216-0002')
	}

	public static RetencionIsrDividendos(def empresa){
	return CuentaContable.findByEmpresaAndClave(empresa,'216-0005')
	}

	

	// Cargo
	public static IvaRetenidoPendient(def empresa){ 
		return CuentaContable.findByEmpresaAndClave(empresa,'115-0002')	
	}

	// Abono
	public static ImpuestoRetenidoDeIva(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'216-0003')	
	}

	public static AcredoresDiversos(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'205-0001')
	}

	public static ComisionesBancarias(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'703-002')	
	}

	public static InteresesBancarios(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'703-0001')		
	}

	public static IsrBancario(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'750-0002')
	}

	public static Servicios(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'403-0001')	
	}

	public static void buildProcesadores(){
		
		if(!ProcesadorDePoliza.find{subTipo== 'COMPRAS'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'COMPRAS',
		        descripcion:'Poliza de compras',
		        label:'Compras',
		        service:'polizaDeComprasService'
			).save failOnError:true,flush:true
		}
		if(!ProcesadorDePoliza.find{subTipo== 'TRASPASOS'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'TRASPASOS',
		        descripcion:'Poliza de traspasos e inversiones',
		        label:'Traspasos/Inversiones',
		        service:'polizaDeTraspasosService'
			).save failOnError:true,flush:true
		}
		if(!ProcesadorDePoliza.find{subTipo== 'COMISIONES'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'COMISIONES',
		        descripcion:'Poliza de comisiones bancarias',
		        label:'Comisiones',
		        service:'polizaDeComisionesBancariasService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'COSTO_DE_VENTAS'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'COSTO_DE_VENTAS',
		        descripcion:'Poliza de costo de ventas',
		        label:'Costo de Ventas',
		        service:'polizaDeCostoDeVentasService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'FACTURACION'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'FACTURACION',
		        descripcion:'Poliza de costo de facturación',
		        label:'Facturación',
		        service:'polizaDeFacturacionService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'EGRESOS'}){
		    new ProcesadorDePoliza(
		        tipo:'EGRESO',
		        subTipo:'PAGO',
		        descripcion:'Póliza de egresos',
		        label:'Egresos',
		        service:'polizaDeEgresosService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'COBRANZA'}){
		    new ProcesadorDePoliza(
		        tipo:'INGRESO',
		        subTipo:'COBRANZA',
		        descripcion:'Póliza de cobranza',
		        label:'Cobranza',
		        service:'polizaDeCobranzaService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'FLETE'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'FLETE',
		        descripcion:'Póliza de fletes',
		        label:'Fletes',
		        service:'polizaDeFleteService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'VARIACION_CAMBIARIA'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'VARIACION_CAMBIARIA',
		        descripcion:'Póliza de vaciación cambiaria',
		        label:'Variación cambiaria',
		        service:'polizaDeVariacionCambiariaService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'DESCUENTOS_COMPRAS'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'DESCUENTOS_COMPRAS',
		        descripcion:'Póliza de descuentos sobre compras',
		        label:'Descuentos',
		        service:'polizaDeDescuentosSobreComprasService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'NOTAS_CREDITO'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'NOTAS_CREDITO',
		        descripcion:'Póliza de notas de credito',
		        label:'Notas de crédito',
		        service:'polizaDeNotaDeCreditoService'
			).save failOnError:true,flush:true
		}

		if(!ProcesadorDePoliza.find{subTipo== 'COMPRA_DOLARES'}){
		    new ProcesadorDePoliza(
		        tipo:'DIARIO',
		        subTipo:'COMPRA_DOLARES',
		        descripcion:'Póliza de compra de dolares',
		        label:'Compra dolares',
		        service:'polizaDeCompraDolaresService'
			).save failOnError:true,flush:true
		}

		
	}


	
}