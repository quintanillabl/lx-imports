package lx.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.Empresa
import com.luxsoft.impapx.contabilidad.Poliza
import lx.econta.utils.DateUtils

@Transactional
class SatPolizasLogService {

    def save(Empresa empresa, SatPolizasLog polizas) {
    	log.info ("Generando registro de polizas para $polizas")
    	def q = Poliza.where {ejercicio == polizas.ejercicio && mes == polizas.mes}
    	
    	Polizas pza = buildPolizas(polizas)
    	ObjectFactory factory = new ObjectFactory()

    	q.list(sort:'subTipo').each { p ->
    		log.debug("Procesando $p.subTipo ${p.folio}")
    		println "Procesando $p.subTipo ${p.folio} ${p.fecha.text()}"
    		
    		Polizas.Poliza poliza = factory.createPolizasPoliza()
    		poliza.setConcepto(p.concepto)
        	poliza.setFecha(DateUtils.getXmlGregorianCalendar(p.fecha))
        	poliza.setNumUnIdenPol("${p.subTipo} - ${p.folio}")

        	p.partidas.each { det ->	
        		Polizas.Poliza.Transaccion trx = factory.createPolizasPolizaTransaccion()
        		trx.setConcepto(det.concepto)
        		trx.setDebe(det.debe)
        		trx.setHaber(det.haber)
        		trx.setNumCta(det.cuenta.clave)
        		trx.setDesCta(det.cuenta.descripcion)
        		
                
                if(det.cheque){ 
                    def ch = det.cheque
                    log.debug("Registrando Transaccion CHEQUE para PolizaCheque ${det.cheque.id}")
                    Polizas.Poliza.Transaccion.Cheque cheque = factory.createPolizasPolizaTransaccionCheque()
                    cheque.with { 
                        banEmisNal = ch.bancoEmisorNacional.clave
                        ctaOri = ch.cuentaOrigen
                        num = ch.numero
                        fecha = DateUtils.getXmlGregorianCalendar(ch.fecha)
                        rfc = ch.rfc
                        benef = ch.beneficiario
                        moneda = CMoneda.valueOf(ch.moneda.currencyCode)
                        tipCamb = ch.tipoDeCambio
                        monto = ch.monto
                    }
                    trx.getCheque().add(cheque)
                }
                if(det.transferencia){
                    def tr = det.transferencia
                    log.debug("Registrando Transaccion TRANSFERENCIA para PolizaCheque ${det.transferencia.id}")
                    Polizas.Poliza.Transaccion.Transferencia transferencia = factory.createPolizasPolizaTransaccionTransferencia()
                    transferencia.with{
                        bancoOriNal = tr.bancoOrigenNacional.clave
                        bancoDestNal = tr.bancoDestinoNacional.clave
                        ctaOri = tr.cuentaOrigen
                        ctaDest = tr.cuentaDestino
                        fecha = DateUtils.getXmlGregorianCalendar(tr.fecha)
                        benef = tr.beneficiario
                        moneda = CMoneda.valueOf(tr.moneda.currencyCode)
                        rfc = tr.rfc
                        monto = tr.monto
                    }
                    trx.getTransferencia().add(transferencia)
                }

                poliza.getTransaccion().add(trx)
        	}
        	pza.getPoliza().add(poliza)
    	}
    	polizas.xml = PolizasUtils.toXmlByteArray(pza)
    	polizas.save failOnError:true ,flush:true
    }

    private Polizas buildPolizas(SatPolizasLog polizasLog){
    	ObjectFactory factory = new ObjectFactory()
    	Polizas polizas = factory.createPolizas()
    	polizas.setVersion("1.1")
        polizas.setAnio(polizasLog.ejercicio)
        polizas.setMes(polizasLog.mes.toString().padLeft(2,'0'))
        polizas.setTipoSolicitud(polizasLog.tipo.value())
        polizas.setNumTramite(polizasLog.numeroDeTramite)
        polizas.setNumOrden(polizasLog.numeroDeOrden)
        polizas.setRFC(polizasLog.rfc)
        return polizas
    }
}
