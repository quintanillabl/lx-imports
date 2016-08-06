package lx.econta.balanzas

import groovy.util.slurpersupport.GPathResult
import groovy.xml.XmlUtil

import org.springframework.security.access.annotation.Secured
import com.luxsoft.lx.contabilidad.PeriodoContable

@Secured(["hasRole('CONTABILIDAD')"])
class SatBalanzaLogController {

    def satBalanzaLogService

    def index(Integer max) { 
    	params.max = Math.min(max ?: 100, 500)
		def q = SatBalanzaLog.where {}
			.order('ejercicio', 'desc')
			.order('mes','asc')
		[balanzaList:q.list(params), balanzaListCount: q.count(params)]
    }

    def generar(PeriodoContable periodo){
    	if (periodo == null) {
            flash.message = "Periodo nulo o invalido no se puede generar la balanza "
            redirect action: 'index'
            return
        }
        log.info("Generando balanza  SAT para $session.empresa ${periodo}  $params.comentario")
    	def balanza = satBalanzaLogService.generar(session.empresa, periodo.ejercicio, periodo.mes,params.comentario)
    	flash.message = "Generada exitosamente la balanza ${balanza.id} SAT para ${periodo} "
    	redirect action:'show', id:balanza.id
    }

    def show(SatBalanzaLog log){
    	[balanzaInstance:log, balanza:log.asBalanza()]
    }

    def mostrarXml(SatBalanzaLog log){
		if(log == null){
			flash.message = 'Registro de balanza nulo no se puede mostrar el xml'
			redirect action: 'index'
			return
		}
		render(text: log.asXmlString(), contentType: "text/xml", encoding: "UTF-8")
	}

	def descargarXml(SatBalanzaLog log){
		if(log == null){
			flash.message = 'No se encuentra el registro de balanza '
			redirect action: 'index'
			return
		}
		def balanza = log.asBalanza()
		String fileName = BalanzaUtils.getFileName(balanza)
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment; filename=\"$fileName\"")
		response.outputStream << new ByteArrayInputStream(log.xml)
		
	}

	def uploadAcuse(SatBalanzaLog log){
		
		if(log == null){
			flash.message = 'No se encuentra el registro de balanza '
			redirect action: 'index'
			return
		}

        def xml=request.getFile('file')
        
        if (xml.empty) {
            flash.message = 'Archivo incorrecto (archivo vacío)'
            redirect action:'show',id:log.id
            return
        }

		//log.info 'Cargando Acuse de envio '
		log.acuse = xml.getBytes()
		log.enviado = new Date()
		log.save failOnError:true, flush:true
		flash.message="Acuse registrado "
		redirect action:'show',params:[id:log.id]
    }

    def mostrarAcuseXml(SatBalanzaLog log){
		if(log == null){
			flash.message = 'Registro de balanza nulo no se puede mostrar el acuse'
			redirect action: 'index'
			return
		}
		GPathResult res = new XmlSlurper().parse(new ByteArrayInputStream(log.acuse))
		String s = XmlUtil.serialize(res)
		render(text: s, contentType: "text/xml", encoding: "UTF-8")
	}

    def descargarAcuseXml(SatBalanzaLog balanza){
		if(log == null){
			flash.message = 'No se encuentra el registro de balanza'
			redirect action: 'index'
			return
		}
		Balanza balanzaSat = balanza.asBalanza()
		
		String fileName = "${balanzaSat.rfc}${balanzaSat.anio}${balanzaSat.mes}B${balanzaSat.tipoEnvio}_Acuse.xml"
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment; filename=\"$fileName\"")
		response.outputStream << new ByteArrayInputStream(balanza.acuse)
		
	}

    def delete(SatBalanzaLog balanza){
    	if(balanza == null){
    		flash.message = 'Registro de balanza nulo no se puede eliminar'
			redirect action: 'index'
			return
    	}
    	balanza.delete flush:true
    	flash.message = "Balanza  ${balanza.id} eliminada"
    	redirect action:'index'
    }

    def uploadFile(){

        def xml=request.getFile('file')
        if (xml.empty) {
            flash.message = 'Archivo incorrecto (archivo vacío)'
            redirect action:'index'
            return
        }
        try {
        	log.info 'Importando balanza: '+params
        	def balanzaLog = satBalanzaLogService.importar(xml.getBytes(),xml.getOriginalFilename())
        	flash.message="Balanza importada ${balanzaLog.id}"
        	redirect action:'show',params:[id:balanzaLog.id]
        	return
        }
        catch(SatBalanzaLogException e) {
        	log.error(e)
        	flash.message = e.message
        	redirect action: 'index'
        	return
        }
    }
}
