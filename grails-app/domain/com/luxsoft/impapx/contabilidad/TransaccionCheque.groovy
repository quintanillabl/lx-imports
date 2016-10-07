package com.luxsoft.impapx.contabilidad



import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode
import com.luxsoft.lx.utils.MonedaUtils
import com.luxsoft.lx.sat.BancoSat


@ToString(excludes='dateCreated,lastUpdated,polizaDet',includeNames=true,includePackage=false)
@EqualsAndHashCode(includes='cuentaOrigen,numero,fecha')
class TransaccionCheque {

    
	String numero
	
	BancoSat bancoEmisorNacional

	String bancoEmisorExtranjero

	String cuentaOrigen

	Date fecha

	String beneficiario

	String rfc

	BigDecimal monto

	Currency moneda = MonedaUtils.PESOS

	BigDecimal tipoDeCambio = 1.0

    Date dateCreated
    
    Date lastUpdated


    static constraints = {
    	numero minSize:1,maxSize:20
        bancoEmisorNacional nullable:true
    	bancoEmisorExtranjero nullable:true,maxSize:150
    	cuentaOrigen maxSize:50
    	beneficiario maxSize:300
    	rfc maxSize:13
    	monto scale:6
    	tipoDeCambio scale:5
    }

    static belongsTo = [polizaDet:PolizaDet]

    static mapping = {
    	//fecha type:'date'
    }

    String info(){
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)
        builder.div{
            p('Num: '+ this.numero)
            p('BanEmisNal:' + this.bancoEmisorNacional)
            p('BanEmisExt: ' + this.bancoEmisorExtranjero)
            p('CtaOri: '+ this.cuentaOrigen)
            p('Fecha: ' + this.fecha)
            p('Benef: ' + this.beneficiario)
            p('RFC: ' + this.rfc)
            p('Monto: ' + this.monto)
            p('Moneda: ' + this.moneda.getCurrencyCode() )
            p('TipCamb: ' + this.tipoDeCambio)
            
        }
        return writer.toString()
    }
}
