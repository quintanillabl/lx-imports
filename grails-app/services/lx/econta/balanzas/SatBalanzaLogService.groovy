package lx.econta.balanzas

import grails.transaction.Transactional

import org.bouncycastle.util.encoders.Base64

import org.apache.commons.lang.exception.ExceptionUtils
import com.luxsoft.impapx.contabilidad.CuentaContable
import com.luxsoft.impapx.contabilidad.SaldoPorCuentaContable
import com.luxsoft.impapx.contabilidad.PolizaDet


import lx.econta.utils.*
import com.luxsoft.impapx.Empresa

@Transactional
class SatBalanzaLogService {

    def generar(Empresa empresa, int ejercicio, int mes, String comentario = null) {

    	def tipo = definirTipo(empresa,ejercicio,mes)
    	Balanza balanza = buildBalanzaSat(empresa,ejercicio,mes,tipo[0])
    	byte[] xml = BalanzaUtils.toXmlByteArray(balanza)

    	def balanzaSat

    	def found = SatBalanzaLog.where { rfc == empresa.rfc && ejercicio == ejercicio && mes == mes && acuse == null}.find()
    	
    	if(found) 
    		balanzaSat = found
    	else {
    		balanzaSat = new SatBalanzaLog(
				rfc: balanza.rfc,
				nombre: empresa.nombre,
	 			ejercicio: balanza.anio,
				mes: mes,
				comentario: comentario,
				tipo:tipo)
    	}
    	balanzaSat.comentario = comentario
    	balanzaSat.xml = xml
        balanzaSat.fechaModBal = balanza?.fechaModBal?.toGregorianCalendar()?.getTime()
		balanzaSat.save failOnError:true, flush:true
    }

    def buildBalanzaSat(Empresa empresa, int ejercicio, int month,String tipo){

    	ObjectFactory factory = new ObjectFactory()
        Balanza balanza = factory.createBalanza()
        
        balanza.with {
            version = '1.1'
            anio = ejercicio
            mes = month.toString().padLeft(2,'0')
            rfc = empresa.rfc
            tipoEnvio = tipo
            noCertificado = empresa.numeroDeCertificado
			certificado = new String(Base64.encode(empresa.getCertificado().getEncoded()))
        }
    	def cuentas=CuentaContable.where{padre==null}
    	
    	cuentas.each { cta ->
    		Balanza.Ctas ctas = factory.createBalanzaCtas()
    		def saldo=SaldoPorCuentaContable.findWhere(cuenta:cta, year:ejercicio,mes:month)
    		if(saldo){
				ctas.with {
		    	    debe = saldo.debe
		    	    haber = saldo.haber
		    	    numCta = saldo.cuenta.clave
		    	    saldoIni = saldo.saldoInicial
		    	    saldoFin = saldo.saldoFinal
		    	}
		    	balanza.getCtas().add(ctas)
    		}
        	
        	cta.subCuentas.each { subCta ->
        		Balanza.Ctas child = factory.createBalanzaCtas()
        		def s2=SaldoPorCuentaContable.findWhere(cuenta:subCta, year:ejercicio,mes:month)
        		if(s2){
					child.with {
			    	    debe = s2.debe
			    	    haber = s2.haber
			    	    numCta = s2.cuenta.clave
			    	    saldoIni = s2.saldoInicial
			    	    saldoFin = s2.saldoFinal
			    	}
			    	balanza.getCtas().add(child)
        		}
				
        	}
    		
    	}
    	Date lastUpdated = findUltimaModificacionContable(empresa,ejercicio,month)
        if(lastUpdated)
            balanza.setFechaModBal(DateUtils.getXmlGregorianCalendar(lastUpdated))
        return balanza
    }

    def String definirTipo(Empresa empresa,int e, int m){
    	def found = SatBalanzaLog.where { rfc == empresa.rfc && ejercicio == 2016 && mes == 7 && acuse != null}.find()
    	return found ? 'COMPLEMENTARIA' : 'NORMAL' 
    }

    def findUltimaModificacionContable(Empresa empresa, int ejercicio, int mes){
    	//def found  = PolizaDet.where {poliza.ejercicio == ejercicio && poliza.mes == mes}.last()
        def found = SatBalanzaLog.where {rfc == empresa.rfc && ejercicio==ejercicio && mes==mes && acuse!= null}.find([sort:'id', order:'desc']).find()
    	return found ? found.get(0).poliza.lastUpdated : new Date()
    }

    def importar(byte[] data, fileName){
        println 'Importando :'+fileName
        Balanza balanza 
        try {
            balanza =  BalanzaUtils.read(data)
        }
        catch(javax.xml.bind.JAXBException jxe) {
            def mm = ExceptionUtils.getRootCauseMessage(jxe)
            log.error(jxe)
            throw new SatBalanzaLogException(
                "El archivo $fileName no es un archivo XML valido para crear una balanza (${mm})")
        }

        def tipoEnvio = balanza.getTipoEnvio()=='N'?'NORMAL':'COMPLEMENTARIA'
        
        SatBalanzaLog bal = SatBalanzaLog.where {
            rfc == balanza.rfc && ejercicio == balanza.anio && mes == balanza.mes.toInteger() && tipo == tipoEnvio
        }.find()

        if(bal){
            throw new SatBalanzaLogException("Ya exste la balanza  ${bal.mes} - ${bal.ejercicio}  para $bal.nombre Folio(Id):$bal.id")
        }

        def empresa = Empresa.where {rfc == balanza.rfc}.find()
        assert empresa, 'No existe empresa con rfc: '+balanza.rfc

        bal = new SatBalanzaLog(
                rfc:balanza.rfc,
                nombre:empresa.nombre,
                ejercicio: balanza.anio,
                mes: balanza.mes.toInteger(),
                comentario: "Importadoc con: ${fileName}",
                tipo: tipoEnvio,
                fechaModBal: balanza?.fechaModBal?.toGregorianCalendar()?.getTime(),
                xml: BalanzaUtils.toXmlByteArray(balanza)
            )
        bal.save failOnError:true, flush:true
    }


}

class SatBalanzaLogException extends RuntimeException {

    SatBalanzaLogException(String message){
        super(message)
    }

}
