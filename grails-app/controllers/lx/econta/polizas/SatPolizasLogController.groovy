package lx.econta.polizas

import org.springframework.security.access.annotation.Secured
import groovy.transform.ToString
import org.grails.databinding.BindingFormat
import com.luxsoft.utils.Periodo


@Secured(["hasRole('CONTABILIDAD')"])
class SatPolizasLogController {

    def satPolizasLogService

    def index(Integer max) { 
    	params.max = Math.min(max ?: 100, 500)
		def q = SatPolizasLog.where {rfc == session.empresa.rfc}
			.order('ejercicio', 'desc')
			.order('mes','asc')
		[polizasList:q.list(params), polizasListCount: q.count(params)]
    }

    def create() {
        respond new SatPolizasLog(ejercicio:new Date().toYear(),mes: new Date().toMonth())
    }
    
    def save( SatPolizasLog satPolizasLogInstance ) {
        if (satPolizasLogInstance == null) {
            notFound()
            return
        }
        /*
        if (satPolizasLogInstance.hasErrors()) {
            respond satPolizasLogInstance, view:'create'
            return
        }
        */
        satPolizasLogInstance = satPolizasLogService.save(session.empresa,satPolizasLogInstance)
        flash.message="Registro de polizas generada: ${satPolizasLogInstance.id}"
        redirect action:'show',id:satPolizasLogInstance.id

        
    }

    def show(SatPolizasLog satPolizasLogInstance) {
        respond satPolizasLogInstance
    }

    def mostrarXml(SatPolizasLog polizas){
        if(polizas == null){
            flash.message = 'Registro de polizas nulo no se puede mostrar el xml'
            redirect action: 'index'
            return
        }
        render(text: polizas.asXmlString(), contentType: "text/xml", encoding: "UTF-8")
    }

    def descargarXml(SatPolizasLog polizas){
        if(polizas == null){
            flash.message = 'No se encuentra el registro de polizas '
            redirect action: 'index'
            return
        }
        def polizasSat = polizas.asPolizas()
        String fileName = PolizasUtils.getFileName(polizasSat)
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment; filename=\"$fileName\"")
        response.outputStream << new ByteArrayInputStream(polizas.xml)
        
    }

    def uploadAcuse(RegistroDeAcusePolizasCommand command){
        
        if(command.hasErrors()){
            respond polizaInstance.errors, view:'show'
            return
        }
        
        def xml=request.getFile('file')
        
        if (xml.empty) {
            flash.message = 'Archivo incorrecto (archivo vacío)'
            redirect action:'show',id:log.id
            return
        }
        
        def polizas = command.polizas
        polizas.acuse = xml.getBytes()
        polizas.save failOnError:true, flush:true
        flash.message="Acuse registrado "
        redirect action:'show',params:[id:polizas.id]
    }

    def uploadAcuseDeAceptacion(SatPolizasLog polizas){
        
        if(polizas == null){
            flash.message = 'No se encuentra el registro de polizas '
            redirect action: 'index'
            return
        }
        def xml=request.getFile('file')
        
        if (xml.empty) {
            flash.message = 'Archivo incorrecto (archivo vacío)'
            redirect action:'show',id:log.id
            return
        }
        polizas.acuseDeAceptacion = xml.getBytes()
        polizas.save failOnError:true, flush:true
        flash.message="Acuse registrado "
        redirect action:'show',params:[id:polizas.id]
    }
    

    def descargarAcuseXml(SatPolizasLog polizas){
        if(log == null){
            flash.message = 'No se encuentra el registro de polizas'
            redirect action: 'index'
            return
        }
        Polizas polizasSat = polizas.asPolizas()
        
        String fileName = "${polizasSat.rfc}${polizasSat.anio}${polizasSat.mes}PL_Acuse.pdf"
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment; filename=\"$fileName\"")
        response.outputStream << new ByteArrayInputStream(polizas.acuse)
        
    }

    def descargarAcuseDeAceptacion(SatPolizasLog polizas){
        if(polizas == null){
            flash.message = 'Registro de polizas nulo no se puede descargar el acuse de aceptacion'
            redirect action: 'index'
            return
        }
        Polizas polizasSat = polizas.asPolizas()
        String fileName = "${polizasSat.rfc}${polizasSat.anio}${polizasSat.mes}B${polizasSat.tipoEnvio}_AcuseDeAceptacion.pdf"
        render(
            file: polizas.acuseDeAceptacion, 
            contentType: 'application/pdf',
            fileName:fileName)
    }

    def delete(SatPolizasLog polizas){
        if(polizas == null){
            flash.message = 'Registro de poliazas nulo no se puede eliminar'
            redirect action: 'index'
            return
        }
        polizas.delete flush:true
        flash.message = "Registro de polizas  ${polizas.id} eliminado"
        redirect action:'index'
    }

}

@ToString(includeNames=true,includePackage=false)
class RegistroDeAcusePolizasCommand {

    SatPolizasLog polizas

    @BindingFormat('dd/MM/yyyy')
    Date fecha=new Date()

}


