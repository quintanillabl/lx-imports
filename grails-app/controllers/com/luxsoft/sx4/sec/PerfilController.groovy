package com.luxsoft.sx4.sec



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('ADMIN')"])
@Transactional(readOnly = true)
class PerfilController {

    static allowedMethods = [update: "PUT"]
    
    @Transactional
    def edit() {
        def user=getAuthenticatedUser()
        if(user.perfil==null){
            user.perfil=new Perfil()
            user.save flush:true
        }
        [perfilInstance:user.perfil]
    }

    @Transactional
    def update(Perfil perfilInstance) {
        if (perfilInstance == null) {
            notFound()
            return
        }
        if (perfilInstance.hasErrors()) {
            respond perfilInstance.errors, view:'edit'
            return
        }
        perfilInstance.save flush:true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Perfil.label', default: 'Perfil'), perfilInstance.id])
                redirect perfilInstance
            }
            '*'{ respond perfilInstance, [status: OK] }
        }
    }

    @Transactional
    def uploadFoto(UploadFotoCommand  command){

        println 'Foto: '+command.foto
        def usuario=command.usuario
        usuario.perfil.foto=command.foto
        usuario.save failOnError:true
        flash.message="Foto actualizada"
        redirect action:'edit', id:usuario.perfil.id
    }
    
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'perfil.label', default: 'Perfil'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def renderFoto(){
        println 'Render foto: '+params
        def perfil=Perfil.get(params.id)
        if(perfil.foto){
            response.setContentLength(perfil.foto.size())
            response.outputStream.write(perfil.foto)
        }else{
            response.sendError(404)
        }
    }
}

class UploadFotoCommand{
    Usuario usuario
    byte[] foto

    static constraints = {
        foto maxSize:(1024 * 512)  // 50kb para almacenar el xml
    }
}
