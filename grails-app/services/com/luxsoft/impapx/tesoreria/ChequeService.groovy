package com.luxsoft.impapx.tesoreria

class ChequeService {

	def generarCheque(MovimientoDeCuenta egreso) {
		
		def cuenta=egreso.cuenta
		def folio=cuenta.folio++
		def cheque=new Cheque(cuenta:cuenta,egreso:egreso,folio:folio)
		cheque.fechaImpresion = new Date()
		egreso.referenciaBancaria = folio.toString()
		cuenta.save(flush:true)
		cheque.save failOnError:true
		return cheque
		
	}

    def cancelarCheque(Cheque cheque,String comentario) {
    	log.info "Cancelando cheque ${cheque.folio}: $comentario"
		def egreso=cheque.egreso
		def pago=PagoProveedor.findByEgreso(egreso)
		def requisicion=pago?.requisicion
		
		cheque.cancelacion=new Date()//c.fecha
		cheque.comentarioCancelacion=comentario
		cheque.egreso=null
		cheque.save(flush:true)
		return cheque
		
    }
}
