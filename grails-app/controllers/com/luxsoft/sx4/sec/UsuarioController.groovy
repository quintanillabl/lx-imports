package com.luxsoft.sx4.sec



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(["hasRole('ADMIN')"])
@Transactional(readOnly = true)
class UsuarioController {

    def usuarioService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        respond Usuario.list(params), model:[usuarioInstanceCount: Usuario.count()]
    }

    def show(Usuario usuarioInstance) {
        respond usuarioInstance
    }

    def create() {
        respond new Usuario(params)
    }

    @Transactional
    def save(Usuario usuarioInstance) {
        if (usuarioInstance == null) {
            notFound()
            return
        }

        if (usuarioInstance.hasErrors()) {
            respond usuarioInstance.errors, view:'create'
            return
        }

        usuarioInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect usuarioInstance
            }
            '*' { respond usuarioInstance, [status: CREATED] }
        }
    }

    def edit(Usuario usuarioInstance) {
        respond usuarioInstance
    }

    @Transactional
    def update(Usuario usuarioInstance) {
        if (usuarioInstance == null) {
            notFound()
            return
        }

        if (usuarioInstance.hasErrors()) {
            respond usuarioInstance.errors, view:'edit'
            return
        }
        UsuarioRole.removeAll(usuarioInstance,false)

        def roles=params.roles
        roles.each{
            //println 'Evaluando rol: '+rol
            def rol=Role.get(it)
            if(!UsuarioRole.exists(usuarioInstance.id,it.toLong())){
                UsuarioRole.create(usuarioInstance,rol,false)
            }
            
        }
        usuarioInstance.save failOnError:true
        flash.message = message(code: 'default.updated.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
        redirect usuarioInstance
    }

    @Transactional
    def delete(Usuario usuarioInstance) {

        if (usuarioInstance == null) {
            notFound()
            return
        }

        usuarioInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Transactional
    def importarEmpleado(Long id){
        def usuario=usuarioService.importar(id)
        if(usuario)
            flash.message="Usuario ${usuario.nombre} importado"
        else
            flash.message="Usuario ${id} no localizado"
        redirect action:'index'
    }

    def getEmpleadosAsJSON() {
        def res=usuarioService.getEmpleadosAsJSON(params.term)
        render res
    }

    @Transactional
    def cambioDePassword(Usuario usuarioInstance,CambioDePassword command){
        if(request.method=='GET'){
            return [usuarioInstance:usuarioInstance,passwordCommand:new CambioDePassword()]
        }
        
        println "************************************************************************************************************"


        command.validate()
        if(command.hasErrors()){
            
            flash.message="Errores de validación"
            return [usuarioInstance:usuarioInstance,passwordCommand:command]
        }
        usuarioInstance.password=command.password
        usuarioInstance.save flush:true
        flash.message="Password actualizado"
        redirect action:'edit',params:[id:usuarioInstance.id]

    }

    @Secured(["permitAll"])
    def passwordExpired() {
       [command:new CambioDePassword(username:session[grails.plugin.springsecurity.SpringSecurityUtils.SPRING_SECURITY_LAST_USERNAME_KEY])]
    }

    @Secured(["permitAll"])
    @Transactional
    def updatePassword(CambioDePassword command){
        if(command.hasErrors()){
            render view:'passwordExpired',model:[command:command]
            return
        }
        Usuario user = Usuario.findByUsername(command.username)
        user.password=command.password
        user.passwordExpired=false
        user.save flush:true,failOnError:true
        flash.message='Password actualizado'
        redirect uri:'/'
    }

}


class CambioDePassword{
    
    String username
    String currentPassword
    String password
    String confirmarPassword

    static constraints={
        password nullable:false
        confirmarPassword nullable:false,validator:{ val,obj ->
            if(obj.password!=val){
                return 'noMatch'
            }
            else{
                return true;
            }
        }
    }
}
