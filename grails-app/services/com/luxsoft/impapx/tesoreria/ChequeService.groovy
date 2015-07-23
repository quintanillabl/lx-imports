package com.luxsoft.impapx.tesoreria

class ChequeService {

    def cancelarCheque(CancelacionCommand c) {
		def cheque= Cheque.get(c.id)
		def egreso=cheque.egreso
		def pago=PagoProveedor.findByEgreso(egreso)
		def requisicion=pago?.requisicion
		
		cheque.cancelacion=new Date()//c.fecha
		cheque.comentarioCancelacion=c.comentario
		//cheque.egreso=null
		//cheque.save()
		
		egreso.importe=0
		egreso.comentario="CANCELADO"
		egreso.concepto="CARGO CHEQUE CANCELADO"
		pago.egreso=null
		pago.delete()
		
		//cheque.save(failOnError:true)

    }
}
