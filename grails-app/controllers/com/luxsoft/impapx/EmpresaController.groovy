package com.luxsoft.impapx

import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.cfdi.CFDIUtils;

class EmpresaController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [empresaInstanceList: Empresa.list(params), empresaInstanceTotal: Empresa.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[empresaInstance: new Empresa(params)]
			break
		case 'POST':
			//def direccion=new Direccion(params)
	        def empresaInstance = new Empresa(params)
			//empresaInstance.direccion=direccion
	        if (!empresaInstance.save(flush: true)) {
	            render view: 'create', model: [empresaInstance: empresaInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.id])
	        redirect action: 'show', id: empresaInstance.id
			break
		}
    }

    def show() {
        def empresaInstance = Empresa.get(params.id)
        if (!empresaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'list'
            return
        }
		//def certificadoDigital=CFDIUtils.leerCertificado(empresaInstance)
		//def pk=CFDIUtils.leerLlavePrivada(empresaInstance)
		def certificadoDigital 
		def llavePrivada
		/*
		if(!certificadoDigital){
			File archivo=new File("C:\\Basura\\CFDI PFX\\PAPER\\pim050124gy7_1211121031s.cer")
			empresaInstance.certificadoDigital=archivo.getBytes()
			
			
			empresaInstance.llavePrivada=new File("C:\\Basura\\CFDI PFX\\PAPER\\paper2012.key").getBytes()
			empresaInstance.certificadoDigitalPfx=new File("C:\\Basura\\CFDI PFX\\PAPER\\certificadopaper.pfx").getBytes()
			
			empresaInstance.save(failOnError:true)
			
		}*/
		try {
			certificadoDigital=empresaInstance.certificado
			llavePrivada=empresaInstance.privateKey
		} catch (Exception e) {
			e.printStackTrace()
		}
		
        [empresaInstance: empresaInstance,certificadoDigital:certificadoDigital,llavePrivada:llavePrivada]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def empresaInstance = Empresa.get(params.id)
	        if (!empresaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [empresaInstance: empresaInstance]
			break
		case 'POST':
			println 'Actualizando empresa: '+params
	        def empresaInstance = Empresa.get(params.id)
	        if (!empresaInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (empresaInstance.version > version) {
	                empresaInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'empresa.label', default: 'Empresa')] as Object[],
	                          "Another user has updated this Empresa while you were editing")
	                render view: 'edit', model: [empresaInstance: empresaInstance]
	                return
	            }
	        }

	        empresaInstance.properties = params

	        if (!empresaInstance.save(flush: true)) {
	            render view: 'edit', model: [empresaInstance: empresaInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.id])
	        redirect action: 'show', id: empresaInstance.id
			break
		}
    }

    def delete() {
        def empresaInstance = Empresa.get(params.id)
        if (!empresaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'list'
            return
        }

        try {
            empresaInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
