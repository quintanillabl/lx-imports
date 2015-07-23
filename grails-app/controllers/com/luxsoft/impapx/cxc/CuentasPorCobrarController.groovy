package com.luxsoft.impapx.cxc

import com.luxsoft.impapx.Venta;

class CuentasPorCobrarController {

    def index() { 
		
	}
	
	def pendientes(){
		def list=Venta.findAllBySaldoGreaterThan(0)
        [ventaInstanceList: list, ventaInstanceTotal: list.size()]
	}
	
	
}
