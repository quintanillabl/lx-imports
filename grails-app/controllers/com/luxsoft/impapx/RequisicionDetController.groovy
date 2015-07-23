package com.luxsoft.impapx

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

import com.luxsoft.impapx.cxp.ConceptoDeGasto;

import util.MonedaUtils;

class RequisicionDetController {

    static allowedMethods = [create: ['GET', 'POST'],  delete: 'POST']
	
	def requisicionService

    def index() {
        redirect action: 'list', params: params
    }
	
	
	def edit() {
		switch (request.method) {
		case 'GET':
			def det=RequisicionDet.get(params.id)
			[requisicionDetInstance: det,requisicionInstance:det.requisicion]
			break
		case 'POST':
			println 'Persistiendo requisicionDet: '+params
			def det=RequisicionDet.get(params.id)
			
			bindData(det,params,[include:['total']])
			
			if(det?.factura?.conceptos){
				ConceptoDeGasto cc=det.factura.conceptos.iterator().next()
				if(cc.impuestoTasa){
					det.importe=MonedaUtils.calcularImporteDelTotal(det.total,cc.impuestoTasa/100)
					det.ietu=det.importe
					det.impuestos=det.importe*(cc.impuestoTasa/100)
				}
			}
			det.requisicion.actualizarImportes()
			if (det.requisicion.save()) {
				redirect controller:'requisicion', action: 'edit', id: det.requisicion.id
				return
			}
			flash.message = message(code: 'default.created.message', args: [message(code: 'requisicionDet.label', default: 'RequisicionDet'), requisicionDetInstance.id])
			redirect controller:'requisicion', action: 'edit', id: requisicion.id
			break
		}
	}
	
   

    def create() {
		switch (request.method) {
		case 'GET':
			//def det=RequisicionDet.get(params.id)
			def requisicion=Requisicion.get(params.requisicionId)
        	//[requisicionDetInstance: det,requisicionInstance:det.requisicion]
			[requisicionDetInstance: new RequisicionDet(params),requisicionInstance:requisicion]
			break
		case 'POST':
			/*
			def requisicion=Requisicion.get(params.requisicionId)
	        
			requisicionDetInstance.importe=requisicionDetInstance.total
			requisicion.addToPartidas(requisicionDetInstance)
			requisicion.actualizarImportes()
			*/
			def requisicionDetInstance = new RequisicionDet(params)
			def requisicion=requisicionService.agregarPartida(params.long('requisicionId'), requisicionDetInstance)
	        if (requisicion) {
	            //render view: 'create', model: [requisicionDetInstance: requisicionDetInstance]
				redirect controller:'requisicion', action: 'edit', id: requisicion.id
	            return
	        }
			flash.message = message(code: 'default.created.message', args: [message(code: 'requisicionDet.label', default: 'RequisicionDet'), requisicionDetInstance.id])
	        redirect controller:'requisicion', action: 'edit', id: requisicion.id
			break
		}
    }


	def embarquesDisponiblesJSONList(){
		println 'Embarques disponibles para anticipo: '+params
		def embarques=Embarque.findAll(
			"from Embarque e where e.id=? "
			,[params.long('term')],[max:10])
		def embarquesList=embarques.collect { row ->
			def label=row.toString()
			[id:row.id,label:label,value:label]
		}
		println 'Embarques registrados: '+embarquesList.size()
		render embarquesList as JSON
	}

    
}
