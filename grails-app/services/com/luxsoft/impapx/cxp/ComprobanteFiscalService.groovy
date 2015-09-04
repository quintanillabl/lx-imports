package com.luxsoft.impapx.cxp

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import org.apache.commons.lang.exception.ExceptionUtils
import groovy.xml.*

import com.luxsoft.impapx.*
import com.luxsoft.cfdi.Acuse
import com.luxsoft.lx.utils.MonedaUtils
import com.luxsoft.impapx.contabilidad.CuentaContable






class ComprobanteFiscalService {

	static transactional = false

    def  consultaService

    def importar(def cfdiFile,def cxp){
    	
    	File xmlFile = File.createTempFile(cfdiFile.getName(),".temp");
        cfdiFile.transferTo(xmlFile)

        def xml = new XmlSlurper().parse(xmlFile)
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        if(xml.name()!='Comprobante')
            throw new ComprobanteFiscalException(message:"${cfdiFile.getOriginalFilename()} no es un CFDI valido")

        
        def data=xml.attributes()
        log.debug 'Comprobante:  '+xml.attributes()  

        
        def empresa=Empresa.first()
        
        def receptorNode=xml.breadthFirst().find { it.name() == 'Receptor'}
        def receptorRfc=receptorNode.attributes()['rfc']
        
       
        if(empresa.rfc!=receptorRfc){
            throw new ComprobanteFiscalException(
                message:"Em el CFDI ${cfdiFile.getOriginalFilename()} el receptor (${receptorRfc}) no es para esta empresa (${empresa.rfc})")
        }
        def emisorNode= xml.breadthFirst().find { it.name() == 'Emisor'}
        def nombre=emisorNode.attributes()['nombre']
        def rfc=emisorNode.attributes()['rfc']
        def proveedor=Proveedor.findByRfc(rfc)
            
        if(!proveedor){
            log.debug "Alta de proveedor: $nombre ($rfc)"
            def domicilioFiscal=emisorNode.breadthFirst().find { it.name() == 'DomicilioFiscal'}
            def dom=domicilioFiscal.attributes()
            def direccion=new Direccion(
                calle:dom.calle,
                numeroExterior:dom.noExterior,
                numeroInterior:dom.noInterior,
                colonia:dom.colonia,
                municipio:dom.municipio,
                estado:dom.estado,
                pais:dom.pais,
                codigoPostal:dom.codigoPostal)
            proveedor=new Proveedor(nombre:nombre,rfc:rfc,direccion:direccion,empresa:empresa)
            proveedor.save failOnError:true,flush:true
            
        }
        def serie=xml.attributes()['serie']
        def folio=xml.attributes()['folio']
        def fecha=df.parse(xml.attributes()['fecha'])
        def timbre=xml.breadthFirst().find { it.name() == 'TimbreFiscalDigital'}
        def uuid=timbre.attributes()['UUID']
            
        def subTotal=data['subTotal'] as BigDecimal
        def total=data['total'] as BigDecimal
        def comprobanteFiscal=ComprobanteFiscal.findByUuid(uuid)
        if(comprobanteFiscal){
        	throw new RuntimeException("CFDI ${uuid} ya importado");
        }

    	comprobanteFiscal=new ComprobanteFiscal(
    		cfdi:xmlFile.getBytes(),
    		cfdiFileName:cfdiFile.getOriginalFilename(),
    		uuid:uuid,
    		serie:serie,
    		folio:folio,
            emisorRfc:rfc,
            receptorRfc:receptorRfc,
            total:total
        )
        
        cxp.proveedor=proveedor
        cxp.documento=folio?:'ND'
        cxp.fecha=fecha
        cxp.vencimiento=new Date()+1
        cxp.moneda=Currency.getInstance('MXN')
        cxp.tc=1
        cxp.importe=subTotal
        cxp.descuentos=0
        cxp.subTotal=0
        cxp.impuestos=0
        cxp.total=total
        cxp.retTasa=0
        cxp.retImp=0
        cxp.comentario="CFDI Importado"

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
        registrarConceptos(cxp,xml)
        if(!comprobanteFiscal.validate()){
            log.error 'Errores de validacion: '+comprobanteFiscal.errors
        }
        cxp.comprobante=comprobanteFiscal

        cxp.save flush:true,failOnError:true
        log.info 'Cuenta por pogar importada: '+cxp
        return cxp
    }

    def actualizar(def cxp,def cfdiFile){
        log.info 'Actualizando CFDI para la cuenta por pagar:'+cxp.id
        
        File xmlFile = File.createTempFile(cfdiFile.getName(),".temp");
        cfdiFile.transferTo(xmlFile)
        
        def xml = new XmlSlurper().parse(xmlFile)
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        def data=xml.attributes()
        log.debug 'Comprobante:  '+xml.attributes()  
        def empresa=Empresa.first()
        def receptorNode=xml.breadthFirst().find { it.name() == 'Receptor'}
        def receptorRfc=receptorNode.attributes()['rfc']
        if(empresa.rfc!=receptorRfc){
            throw new ComprobanteFiscalException(
                message:"Em el CFDI ${cfdiFile.getOriginalFilename()} el receptor (${receptorRfc}) no es para esta empresa (${empresa.rfc})")
        }
        def emisorNode= xml.breadthFirst().find { it.name() == 'Emisor'}
        def nombre=emisorNode.attributes()['nombre']
        def rfc=emisorNode.attributes()['rfc']
        def proveedor=Proveedor.findByRfc(rfc)
            
        if(!proveedor){
            log.debug "Alta de proveedor: $nombre ($rfc)"
            def domicilioFiscal=emisorNode.breadthFirst().find { it.name() == 'DomicilioFiscal'}
            def dom=domicilioFiscal.attributes()
            def direccion=new Direccion(
                calle:dom.calle,
                numeroExterior:dom.noExterior,
                numeroInterior:dom.noInterior,
                colonia:dom.colonia,
                municipio:dom.municipio,
                estado:dom.estado,
                pais:dom.pais,
                codigoPostal:dom.codigoPostal)
            proveedor=new Proveedor(nombre:nombre,rfc:rfc,direccion:direccion,empresa:empresa)
            proveedor.save failOnError:true,flush:true
            
        }
        def serie=xml.attributes()['serie']
        def folio=xml.attributes()['folio']
        def fecha=df.parse(xml.attributes()['fecha'])
        def timbre=xml.breadthFirst().find { it.name() == 'TimbreFiscalDigital'}
        def uuid=timbre.attributes()['UUID']
            
        def subTotal=data['subTotal'] as BigDecimal
        def total=data['total'] as BigDecimal
        
        
        if(cxp.comprobante==null){
            cxp.comprobante=new ComprobanteFiscal()
        }
        def comprobanteFiscal=cxp.comprobante
        
        comprobanteFiscal.with{
            cfdi=xmlFile.getBytes()
            cfdiFileName=cfdiFile.getOriginalFilename()
            uuid=uuid
            serie=serie
            folio=folio
            emisorRfc=rfc
            receptorRfc=receptorRfc
            total=total
        }
        
        // if(cxp.comprobante){
        //     def found=cxp.comprobante
        //     cxp.comprobante=null
        //     found.delete flush:true
        // }

        // if(comprobanteFiscal){
        //     throw new RuntimeException("CFDI ${uuid} ya importado");
        // }

        
        cxp.comprobante=comprobanteFiscal
        cxp.save(flush:true)
        log.info 'CFDI de cuenta por pagar actualizado: '+cxp
        return cxp
    }

    def registrarConceptos(def cxp,def xml){
        if(cxp.instanceOf(FacturaDeGastos)){
            def concepto=CuentaContable.buscarPorClave('600')
            def conceptos=xml.breadthFirst().find { it.name() == 'Conceptos'}
            conceptos.children().each{
                def model=it.attributes()
                def det=new ConceptoDeGasto(
                    concepto:concepto,
                    tipo:'GASTOS',
                    descripcion:model['descripcion'],
                    unidad:model['unidad'],
                    cantidad:model['cantidad'],
                    valorUnitario:model['valorUnitario'],
                    importe:model['importe']
                )
                det.impuestoTasa=cxp.tasaDeImpuesto
                det.impuesto=MonedaUtils.calcularImpuesto(det.importe,det.impuestoTasa/100)
                det.total=det.importe+det.impuesto
                if(!cxp.conceptos){
                    det.retensionIsr=cxp.retensionIsr
                    //det.retensionIsrTasa=cxp.retensionIva
                    det.retension=cxp.retImp
                    det.retensionTasa=cxp.retTasa
                }
                cxp.addToConceptos(det)
            }
            
        }
    }

    def getXml(ComprobanteFiscal cf){
        ByteArrayInputStream is=new ByteArrayInputStream(cf.cfdi)
        def xml = new XmlSlurper().parse(is)
        return xml
    }

    

    def validar(ComprobanteFiscal cf){
        //Acuse acuse=buscarAcuse(cf.cxp.proveedor.rfc,cf.cxp.proveedor.)
        try {

            def xml=getXml(cf)
            def data=xml.attributes()
            def total=data['total']
            Acuse acuse=buscarAcuse(cf.emisorRfc,cf.receptorRfc,total,cf.uuid)
            
            JAXBContext context = JAXBContext.newInstance(Acuse.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ByteArrayOutputStream out=new ByteArrayOutputStream()
            m.marshal(acuse,out);
            
            cf.acuse=out.toByteArray()
            cf.acuseEstado=acuse.getEstado().getValue().toString()
            cf.acuseCodigoEstatus=acuse.getCodigoEstatus().getValue().toString()
            cf.save flush:true,failOnError:true
            log.info 'CFDI validado '+cf.id
            return cf
        }
        catch(Exception e) {
            log.error e
            String msg=ExceptionUtils.getRootCauseMessage(e)
            throw new ComprobanteFiscalException(message:msg,comprobante:cf)
        }
    }

    def Acuse buscarAcuse(String emisorRfc,String receptorRfc,def stotal,String uuid){
        
        String qq="?re=$emisorRfc&rr=$receptorRfc&tt=$stotal&id=$uuid"
        log.info 'Validando en SAT Expresion impresa: '+qq
        Acuse acuse=consultaService.consulta(qq)
        log.info 'Acuse obtenido: '+acuse
        return acuse
    }
    /*
    def Acuse buscarAcuse(String emisorRfc,String receptorRfc,BigDecimal total,String uuid){
        DecimalFormat format=new DecimalFormat("####.000000")
        String stotal=format.format(total)
        String qq="?re=$emisorRfc&rr=$receptorRfc&tt=$stotal&id=$uuid"
        log.info 'Validando en SAT Expresion impresa: '+qq
        Acuse acuse=consultaService.consulta(qq)
        log.info 'Acuse obtenido: '+acuse
        return acuse
    }
    */

    

    def  toXml(Acuse acuse){
        try {
            JAXBContext context = JAXBContext.newInstance(Acuse.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter w=new StringWriter();
            m.marshal(acuse,w);
            return w.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    def  toAcuse(byte[] data){
        try {
            JAXBContext context = JAXBContext.newInstance(Acuse.class)
            Unmarshaller u = context.createUnmarshaller()
            ByteArrayInputStream is=new ByteArrayInputStream(data)
            Object o = u.unmarshal( is )
            return (Acuse)o
            
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    def getCfdiXml(ComprobanteFiscal cf){
        def xml = getXml(cf)
        return XmlUtil.serialize(xml)
    }

    

}

class ComprobanteFiscalException extends RuntimeException{
    String message
    ComprobanteFiscal comprobante
}
