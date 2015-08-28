package com.luxsoft.impapx.cxp


import java.text.SimpleDateFormat
import com.luxsoft.impapx.*


class ComprobanteFiscalService {

	static transactional = false

    def importar(def cfdiFile,def cxp){
    	
    	File xmlFile = File.createTempFile(cfdiFile.getName(),".temp");
        cfdiFile.transferTo(xmlFile)

        def xml = new XmlSlurper().parse(xmlFile)
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        if(xml.name()=='Comprobante'){
            def data=xml.attributes()
            log.debug 'Comprobante:  '+xml.attributes()  

            def receptor=xml.breadthFirst().find { it.name() == 'Receptor'}
            def empresa=Empresa.first()
            def empresaRfc=Empresa.findByRfc(receptor.attributes()['rfc'])
            assert empresa.rfc==empresaRfc,"El CFDI ${cfdiFile.getOriginalFilename()} no es corresponde a esta  empresa "

            def emisorNode= xml.breadthFirst().find { it.name() == 'Emisor'}

            def nombre=emisorNode.attributes()['nombre']
            def rfc=emisorNode.attributes()['rfc']
            def proveedor=Proveedor.findByRfc(empresa,rfc)
            
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
            def comprobanteFiscal=ComprobanteFiscal.findByUuid(uuid)
            if(comprobanteFiscal){
            	throw new RuntimeException("CFDI ${uuid} ya importado");
            }

        	comprobanteFiscal=new ComprobanteFiscal(
        		cfdi:xmlFile.getBytes(),
        		cfdiFileName:cfdiFile.getOriginalFilename(),
        		uuid:uuid,
        		serie:serie,
        		folio:folio
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
            cxp.total=0
            cxp.retTasa=0
            cxp.retImp=0
            cxp.comentario="CFDI Importado"

            def traslados=xml.breadthFirst().find { it.name() == 'Traslados'}
            if(traslados){
                traslados.children().each{ t->
                    if(t.attributes()['impuesto']=='IVA'){
                        def tasa=t.attributes()['tasa'] as BigDecimal
                        cxp.impuestos=t.attributes()['importe'] as BigDecimal
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

            /*
            def conceptos=xml.breadthFirst().find { it.name() == 'Conceptos'}
            conceptos.children().each{
                def model=it.attributes()
                def det=new GastoDet(
                    cuentaContable:cuenta,
                    descripcion:model['descripcion'],
                    unidad:model['unidad'],
                    cantidad:model['cantidad'],
                    valorUnitario:model['valorUnitario'],
                    importe:model['importe'],
                    comentario:"Concepto importado  ${xmlFile.name}"
                )
                if(!gasto.partidas){
                    det.retencionIsr=gasto.retensionIsr
                    det.retencionIsrTasa=gasto.retensionIsrTasa
                    det.retencionIva=gasto.retensionIva
                    det.retencionIvaTasa=gasto.retensionIvaTasa
                }
                gasto.addToPartidas(det)
            }
            */
                
            cxp.comprobante=comprobanteFiscal
            cxp.save failOnError:true,flush:true

        }
        return null
    }

}
