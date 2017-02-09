package com.luxsoft.lx.contabilidad


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured
import grails.converters.JSON
import com.luxsoft.lx.bi.ReportCommand
import com.luxsoft.impapx.contabilidad.SaldoPorCuentaContable


@Secured(["hasRole('CONTABILIDAD')"])
@Transactional(readOnly = true)
class BalanzaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def balanzaService
    def reportService

    def index() {
    	
        def periodo=session.periodoContable
        def saldos=SaldoPorCuentaContable.where {
        	year==periodo.ejercicio && mes == periodo.mes
        }.list()
        [saldoPorCuentaContableInstanceList: saldos]
    }
    
    def balanzaDeComprobacion(){
        def command=new ReportCommand()
        command.reportName="BalanzaDeComprobacion"
        command.empresa=session.empresa
        params.YEAR=session.periodoContable.ejercicio as String
        params.MES=session.periodoContable.mes as String
        params.EMPRESA=session.empresa.nombre
        params.INICIAL=0.0
        def stream=reportService.build(command,params)
        def file="Balanza_${params.YEAR}${params.MES}_"+new Date().format('ss')+'.'+command.formato.toLowerCase()
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
    }

    def balanceGeneral(){
        def command=new ReportCommand()
        command.reportName="BalanceGeneral"
        command.empresa=session.empresa
        params.YEAR=session.periodoContable.ejercicio as String
        params.MES=session.periodoContable.mes as String
        params.EMPRESA=session.empresa.nombre
        params.INICIAL=0.0
        def stream=reportService.build(command,params)
        def file="BalanceGeneral_${params.YEAR}${params.MES}_"+new Date().format('ss')+'.'+command.formato.toLowerCase()
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
    }

    def estadoDeResultados(){
        def command=new ReportCommand()
        command.reportName="EstadoDeResultados"
        command.empresa=session.empresa
        params.YEAR=session.periodoContable.ejercicio as String
        params.MES=session.periodoContable.mes as String
        params.EMPRESA=session.empresa.nombre
        params.INICIAL=0.0
        def stream=reportService.build(command,params)
        def file="EstadoDeResultados_${params.YEAR}${params.MES}_"+new Date().format('ss')+'.'+command.formato.toLowerCase()
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
    }
}
