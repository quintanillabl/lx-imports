package com.luxsoft.impapx

import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException
import grails.plugin.springsecurity.annotation.Secured
import com.luxsoft.cfdi.CFDIUtils;

@Transactional(readOnly = true)
@Secured(["hasRole('ADMIN')"])
class EmpresaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Empresa.list(params), model:[empresaInstanceCount: Empresa.count()]
    }

    def show(Empresa empresaInstance) {
        redirect action:'edit',id:empresaInstance.id
    }

    def create() {
        respond new Empresa(params)
    }

    @Transactional
    def save(Empresa empresaInstance) {
        if (empresaInstance == null) {
            notFound()
            return
        }

        if (empresaInstance.hasErrors()) {
            respond empresaInstance.errors, view:'create'
            return
        }

        empresaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.id])
                redirect empresaInstance
            }
            '*' { respond empresaInstance, [status: CREATED] }
        }
    }

    //@Secured(["hasAnyRole('ADMIN')"])
    def edit(Empresa empresaInstance) {
        respond empresaInstance
    }

    @Transactional
    @Secured(["hasAnyRole('ADMIN')"])
    def update(Empresa empresaInstance) {
        if (empresaInstance == null) {
            notFound()
            return
        }

        if (empresaInstance.hasErrors()) {
            respond empresaInstance.errors, view:'edit'
            return
        }

        empresaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Empresa.label', default: 'Empresa'), empresaInstance.id])
                redirect empresaInstance
            }
            '*'{ respond empresaInstance, [status: OK] }
        }
    }

    @Transactional
    @Secured(["hasAnyRole('ADMIN')"])
    def delete(Empresa empresaInstance) {

        if (empresaInstance == null) {
            notFound()
            return
        }

        empresaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Empresa.label', default: 'Empresa'), empresaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Transactional
    def registrarLlavePrivada(Empresa empresaInstance) {
        if (empresaInstance == null) {
            notFound()
            return
        }
        def file=request.getFile('file')
        empresaInstance.llavePrivada=file.getBytes()
        empresaInstance.save flush:true
        redirect action: 'edit',id:empresaInstance.id
        
    }

    @Transactional
    def registrarCertificado(Empresa empresaInstance) {
        if (empresaInstance == null) {
            notFound()
            return
        }
        def file=request.getFile('file')
        
        empresaInstance.numeroDeCertificado=file.getOriginalFilename()-'.cer'
        empresaInstance.certificadoDigital=file.getBytes()
        empresaInstance.save flush:true
        //forward action: 'edit',id:empresaInstance.id
        redirect action: 'edit',id:empresaInstance.id
    }

    @Transactional
    def registrarCertificadoPfx(Empresa empresaInstance) {
        if (empresaInstance == null) {
            notFound()
            return
        }
        def file=request.getFile('file')
        empresaInstance.certificadoDigitalPfx=file.getBytes()
        empresaInstance.save flush:true
        //forward action: 'edit',id:empresaInstance.id
        redirect action: 'edit',id:empresaInstance.id
        
    }

    
}
