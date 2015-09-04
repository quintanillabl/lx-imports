package com.luxsoft.impapx



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import com.luxsoft.utils.Periodo


@Secured(["hasRole('COMPRAS')"])
@Transactional(readOnly = true)
class EmbarqueController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    def jasperService

    def embarqueService
    
    def reportService

    def beforeInterceptor = {
        if(!session.periodoEmbarques){
            def d1=new Date()-90
            session.periodoEmbarques=new Periodo(d1.inicioDeMes(),new Date())
        }
        session.periodo=session.periodoEmbarques
    }

    def cambiarPeriodo(Periodo periodo){
        //def fecha=params.date('fecha', 'dd/MM/yyyy')
        session.periodoEmbarques=periodo
        redirect(uri: request.getHeader('referer') )
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        params.sort=params.sort?:'lastUpdated'
        params.order='desc'
        def periodo=session.periodoEmbarques
        def list=Embarque.findAll(
            "from Embarque e  where date(e.dateCreated) between ? and ? order by e.lastUpdated desc",
            [periodo.fechaInicial,periodo.fechaFinal])
        [embarqueInstanceList:list]
    }

    def show(Embarque embarqueInstance) {
        respond embarqueInstance
    }

    def create() {
        respond new Embarque(params)
    }

    @Transactional
    def save(Embarque embarqueInstance) {
        if (embarqueInstance == null) {
            notFound()
            return
        }
        embarqueInstance.with{
            nombre=embarqueInstance?.nombre?.toUpperCase()
            bl=embarqueInstance?.bl?.toUpperCase()
            comentario=embarqueInstance?.comentario?.toUpperCase()
            moneda=Currency.getInstance('USD')
            tc=1
            embarqueInstance.validate()
        }
        if (embarqueInstance.hasErrors()) {
            respond embarqueInstance.errors, view:'create'
            return
        }

        embarqueInstance.save flush:true
        flash.message = message(code: 'default.created.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarqueInstance.id])
        redirect action:'edit',id:embarqueInstance.id
    }

    def edit(Embarque embarqueInstance) {
        respond embarqueInstance
    }

    @Transactional
    def update(Embarque embarqueInstance) {
        if (embarqueInstance == null) {
            notFound()
            return
        }

        if (embarqueInstance.hasErrors()) {
            respond embarqueInstance.errors, view:'edit'
            return
        }

        embarqueInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Embarque.label', default: 'Embarque'), embarqueInstance.id])
                redirect embarqueInstance
            }
            '*'{ respond embarqueInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Embarque embarqueInstance) {

        if (embarqueInstance == null) {
            notFound()
            return
        }

        embarqueInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Embarque.label', default: 'Embarque'), embarqueInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarque.label', default: 'Embarque'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def search(){
        def term='%'+params.term.trim()+'%'
        def query=Embarque.where{
            (id.toString()=~term || bl=~term || proveedor.nombre=~term || nombre=~term ) 
        }
        def embarques=query.list(max:30, sort:"bl",order:'desc')

        def embarquesList=embarques.collect { embarque ->
            def label="Id: ${embarque.id} BL: ${embarque.bl} ${embarque.nombre} (${embarque.proveedor.nombre}) ${embarque.fechaEmbarque}"
            //def label=embarque.toString()
            [id:embarque.id,label:label,value:label]
        }
        render embarquesList as JSON
    }

    def facturasPorAsignarJSON(){
        def facturas=CuentaPorPagar.findAll("from CuentaPorPagar as d where d.proveedor.id=? and d.total-d.analisisCosto>10 and d.documento like ? "
            ,[params.long('proveedorId'),params.term.toUpperCase()+"%"],[max:20])
        
        def res=facturas.collect{row ->
            def tot=g.formatNumber(number:row.total,format:'\$###,###,###.##')
            def analizado=g.formatNumber(number:row.analisisCosto,format:'\$###,###,###.##')
            def label="Fac: ${row.documento}  F: ${row.fecha.format('dd/MM/yyyy')} T: ${tot} Analizado: ${analizado}"
            [id:row.id,label:label,value:label,documento:row.documento]
        }
        render res as JSON
        
    }

    @Transactional
    def asignarFactura(){
        def factura=CuentaPorPagar.get(params.facturaId)
        JSONArray jsonArray=JSON.parse(params.partidas);
        def embarque
        jsonArray.each{it->
            def det=EmbarqueDet.get(it.toLong())
            if(det.factura==null){
                det.factura=factura
                factura.analisisCosto+=det.costoBruto
            }
            embarque=det.embarque
            embarque.save flush:true
        }
        if(factura.proveedor.vencimentoBl){
            factura.vencimiento=embarque.fechaEmbarque+factura.proveedor.plazo
            
        }
        factura.save flush:true
        def res=[documento:factura.documento]
        render res as JSON
    }

    @Transactional
    def cancelarAsignacionDeFacturas(){
        JSONArray jsonArray=JSON.parse(params.partidas);
        jsonArray.each{
            def det=EmbarqueDet.get(it.toLong())
            def factura=det.factura
            if(factura!=null){
                log.debug 'Actualizando analisis costo para factura: '+factura+ "  Analisis: "+factura.analisisCosto
                factura.analisisCosto-=det.costoBruto
                if(factura.analisisCosto<0)
                    factura.analisisCosto=0
                factura.save flush:true
            }
            det.factura=null
            det.save flush:true
            
        }
        def res=[documento:'']
        render res as JSON
    }

    /**
     * Elimina las partidas del embaruqe que falten de asignar kilos netos
     * 
     * @return
     */
     @Transactional
    def eliminarFaltantes(long id){
        def res=EmbarqueDet.findAll("from EmbarqueDet d where d.embarque.id=? and d.kilosNetos<=0",[id])
        log.debug 'Partidas a eliminar: '+res.size()
        flash.message="Partidas sin kilos netos asignados y ELIMINADAS "+res.size()
        res.each {embarqueDet ->
            def embarque=embarqueDet.embarque
            embarque.removeFromPartidas(embarqueDet)
            embarqueDet.compraDet.entregado-=embarqueDet.cantidad;
            embarque.save flush:true
            
        }
        redirect action:'edit',id:id
    }

    def print(Embarque embarqueInstance){
        
        def command=new com.luxsoft.lx.bi.ReportCommand()
        command.reportName="RelacionDeContenedoresPorEmbarque"
        command.empresa=session.empresa
        def stream=reportService.build(command,[
            ID:embarqueInstance.id as String,
            COMPANY:session.empresa.nombre])
        def file="RelacionDeContenedores_${embarqueInstance.id}_"+new Date().format('mmss')+'.'+command.formato.toLowerCase()
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
    }

    @Transactional
    def comprasPendientes(long id){
        def embarque=Embarque.get(id)
        def res=CompraDet.findAll("from CompraDet d left join fetch d.compra c left join fetch c.proveedor p where c.fecha>='2013/07/01' and c.proveedor=?  order by d.id desc"
            ,[embarque.proveedor]
            ,[max:1000]
            )
        res=res.grep({it.getPendiente()>0})
        [embarque:embarque,compraDetInstanceList:res,compraDetInstanceTotal:res.size()]
        
    }

    @Transactional
    def asignarComprasUnitarias(long embarqueId){
        def embarque=Embarque.findById(embarqueId,[fetch:[partidas:'eager']])
        JSONArray jsonArray=JSON.parse(params.partidas);
        def proveedor
        jsonArray.each{
            def det=CompraDet.get(it.toLong())
            
            def embarqueDet=new EmbarqueDet()
            embarqueDet.compraDet=det
            embarqueDet.producto=det.producto
            embarqueDet.cantidad=det.pendiente
            embarqueDet.actualizarKilosEstimados()
            //calculando precio
            def provProducto=ProveedorProducto.findByProveedorAndProducto(embarque.proveedor,det.producto)
            embarqueDet.precio=provProducto?.costoUnitario?:0
            
            embarque.addToPartidas(embarqueDet)
            det.entregado+=embarqueDet.cantidad;
            
        }
        
        embarque.save(failOnError:true)
        def res=[res:jsonArray.size()]
        render res as JSON
    }

    @Transactional
    def actualizarPrecios(long id){
        
        def embarque=Embarque.findById(id,[fetch:[partidas:'eager']])
        def list=embarque.partidas //EmbarqueDet.findAll("from EmbarqueDet e where e.embarque.id=?",[id])
        list.each {
            it.actualizarPrecioDeVenta()
            it.save flush:true 
        }
        render (template:'costosGrid' ,model: [partidas: list,embarqueInstance:embarque])
    }

    def contenedoresDeEmbarque(Long id){
        def contenedores=EmbarqueDet.executeQuery("\
            select d.embarque.id,d.embarque.bl ,d.contenedor,sum(d.kilosNetos) as kilosNetos \
            from EmbarqueDet d \
            where d.embarque.id=?\
            group by d.embarque.id,d.embarque.bl,d.contenedor",[id])
        def rows=contenedores.collect{
            def map=[embarque:it[0],bl:it[1],contenedor:it[2],kilosNetos:it[3]]
        }
        render( template:"contenedoresGrid",model:[contenedores:rows])
    }

    @Transactional
    def eliminarPartidas(){
        log.info 'Eliminando partidas de embarque :'+params
        def res=embarqueService.eliminarPartidas(params)
        def data=[status:'OK']
        render data as JSON
    }

    @Transactional
    def asignandoContenedor(String contenedor){
        log.info "Asignando contenedor ${contenedor} "+params.partidas
        JSONArray jsonArray=JSON.parse(params.partidas);
        jsonArray.each{
            def det=EmbarqueDet.get(it.toLong())
            det.contenedor=contenedor
            det.save flush:true
        }
        def res=[contenedor:params.contenedor]
        render res as JSON
    }

    def imprimirAnalisisDeCosteo(Embarque embarqueInstance){
        
        def command=reportService.buildCommand(session.empresa,'AnalisisDeCosteoDeImportacion')
        params.COMPANY=session.empresa.nombre
        params.ID=embarqueInstance.id
        println 'Imprimiendo analisis: '+params
        def stream=reportService.build(command,params)
        def file="AnalisisDeCosteoDeImportacion_${embarqueInstance.id}.pdf"
        render(
            file: stream.toByteArray(), 
            contentType: 'application/pdf',
            fileName:file)
    }

   // def disponibles=EmbarqueDet.executeQuery("select distinct(det.embarque) from EmbarqueDet det where det not in(select v.embarque from VentaDet v)")

}
