package com.luxsoft.econta.polizas

import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.lx.contabilidad.*

class PolizaUtils {
	
	
	public static void buildProcesadores(){
		ProcesadorDePoliza.findOrSaveWhere(
			tipo:'DIARIO',
			subTipo:'COMPRAS',
			descripcion:'Poliza de compras',
			label:'Compras',
			service:'polizaDeComprasService')

		
		
	}


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
		return CuentaContable.findByEmpresaAndClave(empresa,'119-0001')
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
		return CuentaContable.findByEmpresaAndClave(empresa,'119-0002')	
	}

	// Abono
	public static ImpuestoRetenidoDeIva(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'216-0003')	
	}

	public static AcredoresDiversos(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'205-0001')
	}

	public static ComisionesBancarias(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'701-0002')	
	}

	public static InteresesBancarios(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'702-0001')		
	}

	public static IsrBancario(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'750-0002')
	}

	public static Servicios(def empresa){
		return CuentaContable.findByEmpresaAndClave(empresa,'403-0001')	
	}


	
}