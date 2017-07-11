package com.luxsoft.cfdix.v33

import javax.xml.bind.JAXBContext
import java.text.SimpleDateFormat
import org.apache.commons.lang.builder.ToStringStyle
import org.apache.commons.lang.builder.ToStringBuilder

import com.luxsoft.impapx.cxp.ComprobanteFiscal
import com.luxsoft.impapx.cxp.ComprobanteFiscalException
import com.luxsoft.impapx.cxp.ComprobanteExistenteException
import com.luxsoft.cfdi.Cfdi
import lx.cfdi.v33.Comprobante
import lx.cfdi.v33.CfdiUtils
import com.luxsoft.impapx.Empresa
import com.luxsoft.impapx.Proveedor
import com.luxsoft.impapx.FacturaDeGastos
import com.luxsoft.impapx.contabilidad.CuentaContable
import com.luxsoft.impapx.cxp.ConceptoDeGasto
import com.luxsoft.lx.utils.MonedaUtils


class ImportadorDeCfdiV33 {

	def build(def comprobante, def cfdiFile, def cxp){
    	
    	assert comprobante.name() == 'Comprobante'
        assert comprobante.attributes()['Version'] == '3.3', 'No es la version de cfdi 3.3'        

        def receptor = comprobante.breadthFirst().find { it.name() == 'Receptor'}
        def receptorRfc = receptor.attributes().Rfc

        def emisor= comprobante.breadthFirst().find { it.name() == 'Emisor'}
        def timbre=comprobante.breadthFirst().find { it.name() == 'TimbreFiscalDigital'}

        
        def empresa=Empresa.first()
        
        def nombre = emisor.attributes()['Nombre']
        def rfc = emisor.attributes()['Rfc']

        if(empresa.rfc!=receptorRfc && empresa.rfc!= rfc){
            throw new ComprobanteFiscalException(
                message:"El el receptor del CFDI: ${cfdiFile.getOriginalFilename()} no corresponde  a ${empresa.nombre} Emprea RFC: ${empresa.rfc}  Receptor del CFDI: ${receptorRfc}")
        }

        def proveedor=Proveedor.findByRfc(rfc)
        assert proveedor, 'No se ha registrado el proveedor ' + rfc
        
        /**/
        def serie=comprobante.attributes()['Serie']
        def folio=comprobante.attributes()['Folio']
        def fecha=Date.parse("yyyy-MM-dd'T'HH:mm:ss",comprobante.attributes()['Fecha'])
        def uuid=timbre.attributes()['UUID']
        def subTotal = comprobante.attributes()['SubTotal'] as BigDecimal
        def total = comprobante.attributes()['Total'] as BigDecimal
        def descuento = comprobante.attributes()['Descuento'] as BigDecimal
         /**/
        
        def comprobanteFiscal=ComprobanteFiscal.findByUuid(uuid)
        if(comprobanteFiscal){
        	throw new ComprobanteExistenteException(comprobanteFiscal);
        }

    	comprobanteFiscal=new ComprobanteFiscal(
    		cfdi:cfdiFile.getBytes(),
    		cfdiFileName:cfdiFile.getOriginalFilename(),
    		uuid:uuid,
    		serie:serie,
    		folio:folio,
            emisorRfc:rfc,
            receptorRfc:receptorRfc,
            total:total,
            fecha:fecha
        )
        
        cxp.proveedor = proveedor
        cxp.documento = folio?:'ND'
        cxp.fecha = fecha
        cxp.vencimiento = new Date()+1
        cxp.moneda = Currency.getInstance('MXN')
        cxp.tc = 1
        cxp.importe = subTotal
        cxp.descuentos = 0
        cxp.subTotal = 0
        cxp.impuestos = 0
        cxp.total = total
        cxp.retTasa = 0
        cxp.retImp = 0
        cxp.comentario = "CFDI Importado"
        cxp.gastoPorComprobar = false

        // Impuestos trasladados
        def traslados = comprobante.breadthFirst().find { it.name() == 'Traslados'}
        if(traslados){
            traslados.children().each{ t->
               if(t.attributes()['Impuesto']=='002'){ // IVA
                   
               	def tasa=t.attributes()['TasaOCuota'] as BigDecimal

				cxp.tasaDeImpuesto = tasa
				cxp.impuestos = t.attributes()['Importe'] as BigDecimal
                
               }
            }
        }

        // Impuestos retendidos
        def retenciones=comprobante.breadthFirst().find { it.name() == 'Retenciones'}
        if(retenciones){
            retenciones.children().each{
                def map=it.attributes()
                if(map.Impuesto=='002'){
                    def imp = map.Importe as BigDecimal
                    def tasa = map['TasaOCuota'] as BigDecimal
        			cxp.retTasa = tasa
        			cxp.retImp = imp
                    
                }
                if(map.Impuesto=='ISR'){
                    def imp = map.Importe as BigDecimal
                    def tasa = map['TasaOCuota'] as BigDecimal
                   	cxp.retensionIsr=imp
                }
            }
        }

        /*
        def traslados=xml.breadthFirst().find { it.name() == 'Traslados'}
        if(traslados){
            traslados.children().each{ t->
                if(t.attributes()['impuesto']=='IVA'){
                    def tasa=t.attributes()['tasa'] as BigDecimal
                    cxp.impuestos=t.attributes()['importe'] as BigDecimal
                    cxp.tasaDeImpuesto=tasa
                }
            }
        }
        def retenciones=xml.breadthFirst().find { it.name() == 'Retenciones'}
        if(retenciones){
            retenciones.breadthFirst().each{
                def map=it.attributes()
                if(map.impuesto=='IVA'){
                   	def imp=map.importe as BigDecimal
                   	def tasa=imp*100/subTotal
                   	cxp.retTasa=tasa
        			cxp.retImp=imp
                }
                if(map.impuesto=='ISR'){
                   def imp=map.importe as BigDecimal
                   def tasa=imp*100/subTotal
                   cxp.retensionIsr=imp
                }
            }
        }
        */
        registrarConceptos(cxp,comprobante)
        
        cxp.comprobante = comprobanteFiscal
        cxp.save flush:true,failOnError:true
        log.info 'Cuenta por pogar importada: '+cxp
        return cxp
    }

    def registrarConceptos(def cxp,def xml){
        if(cxp.instanceOf(FacturaDeGastos)){
        	if(cxp.conceptos) cxp.conceptos.clear()
            def concepto=CuentaContable.buscarPorClave('600-0000')
            def conceptos=xml.breadthFirst().find { it.name() == 'Conceptos'}
            conceptos.children().each{
                def model=it.attributes()
                def det=new ConceptoDeGasto(
                    concepto:concepto,
                    tipo:'GASTOS',
                    descripcion:model['Descripcion'],
                    unidad:model['Unidad'],
                    cantidad:model['Cantidad'],
                    valorUnitario:model['ValorUnitario'],
                    importe:model['Importe']
                )
                det.impuestoTasa=cxp.tasaDeImpuesto
                det.impuesto=MonedaUtils.calcularImpuesto(det.importe,det.impuestoTasa/100)
                det.total=det.importe+det.impuesto
                if(!cxp.conceptos){
                	if(it.Impuestos?.Retenciones?.Retencion[0]){
                	    def retencion = it.Impuestos.Retenciones.Retencion[0].attributes()
                	    if(retencion.Impuesto == '001'){
                	        det.retencionIsr = retencion.Importe as BigDecimal
                	        det.retensionTasa = retencion.TasaOCuota as BigDecimal
                	    }
                	    if(retencion.Impuesto == '002'){
                	        det.retension = retencion.Importe as BigDecimal
                	        det.retensionTasa = retencion.TasaOCuota as BigDecimal
                	    }
                	}
                	
                }
                cxp.addToConceptos(det)
            }
            
        }
    }

}