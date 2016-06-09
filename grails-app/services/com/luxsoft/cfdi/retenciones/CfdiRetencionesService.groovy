package com.luxsoft.cfdi.retenciones


import grails.transaction.Transactional

import org.xml.sax.helpers.DefaultHandler
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory
import javax.xml.transform.stream.StreamSource
import java.text.SimpleDateFormat

import com.luxsoft.cfdi.retenciones.ObjectFactory
import com.luxsoft.cfdi.retenciones.Retenciones
import com.luxsoft.lx.core.Folio


@Transactional
class CfdiRetencionesService {

	def grailsApplication

    def retencionesBuilder

    def retencionesTibrador

    def retencionSellador

    def retencionesCadenaBuilder

    def save(RetencionesCommand command){
        def retenciones=new CfdiRetenciones(
            //folio:nextFolio(),
            emisor:command.empresa.nombre,
            emisorRfc:command.empresa.rfc,
            receptor:command.receptor,
            receptorRfc:command.receptorRfc,
            receptorCurp:command.receptorCurp,
            receptorNacionalidad:command.nacional?'Nacional':'Extranjero',
            receptorRegistroTributario:command.registroTributario,
            fecha:command.fecha,
            tipoDeRetencion:command.tipoDeRetencion,
            retencionDescripcion:command.retencionDescripcion,
            mesInicial:command.mesInicial,
            mesFinal:command.mesFinal,
            ejercicio:command.ejercicio,
            total:command.total,
            totalGravado:command.totalGravado,
            totalExcento:command.totalExcento,
            totalRetenido:command.totalRetenido
        )
        retenciones.save failOnError:true,flush:true
    }


    def generarXml(CfdiRetenciones retenciones){
        retenciones=retencionesBuilder.buildXml(retenciones)
        return retenciones

    }

    def cargarXml(CfdiRetenciones retenciones,File xmlFile){

        def xml = new XmlSlurper().parse(xmlFile)
        def data=xml.attributes()
        def timbre=xml.breadthFirst().find { it.name() == 'TimbreFiscalDigital'}
        def emisor=xml.breadthFirst().find { it.name() == 'Emisor'}

        //def empresa=Empresa.findByRfc(emisor.attributes()['RFCEmisor'])

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        
        
        retenciones.uuid=timbre.attributes()['UUID']
        retenciones.fechaDeTimbrado=df.parse(timbre.attributes()['FechaTimbrado'])
        retenciones.xml=xmlFile.getBytes()
        retenciones.xmlName=xmlFile.name
        
        
        retenciones.save flush:true
    }
    

    def timbrar(CfdiRetenciones bean){
        assert bean.xml,' No se ha generado el xml para este comprobante :'+bean
        bean=retencionesTibrador.timbrar(bean)
        return bean
    }


    def buildCatalogoDeRetenciones(){
        def map=[:]
        map['01']='Servicios profesionales'
        map['02']='Regalías por derechos de autor'
        map['03']='Autotransporte terrestre de carga'
        map.each{k,v->
            TipoDeRetencion.findOrSaveWhere(clave:k,descripcion:v)
        }
    }

    def buildCatalogoDeImpuestos(){
        def map=[:]
        map['01']='ISR'
        map['02']='IVA'
        map['03']='IEPS'
        map.each{k,v->
            TipoDeImpuesto.findOrSaveWhere(clave:k,descripcion:v)
        }
    }

    


}

