package com.luxsoft.lx.core

class CoreTagLib {

    //static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]x
  
    static namespace="lx"

    def deleteButton={ attrs->
    	def action=attrs.action?:'delete'
    	def controller=attrs.controller?:controllerName
    	def label=attrs.label?:'Eliminar'
    	def bean=attrs.bean
    	if(!bean)
    		throw new IllegalArgumentException("Tag lx:deleteButton Debe pasar el  bean a eliminar")
    	def model=[action:actionName,controller:controller]
    	//out<<render(template:"/common/deleteDialog")
    	//out<<"<a href='#deleteDialog' class="btn btn-danger"><i class='fa fa-trash'></i> Eliminar</a>"
    	//out << "<a href="#deleteDialog" class="btn btn-danger"><i class="fa fa-trash"></i> Eliminar</a>"
    	out << render(template:"/common/buttons/deleteButton" ,model:[it:bean])
    	//out << 'HOLA'
    }

    def printButton={ attrs->
        def action=attrs.action?:'print'
        def controller=attrs.controller?:controllerName
        def label=attrs.label?:'Imprimir'
        def id=attrs.id
        def model=[action:action,controller:controller,id:id,label:label]
        out << render(template:"/common/buttons/printButton" ,model:model)
        
    }

    def refreshButton={ attrs->
        def action=attrs.action?:'index'
        def controller=attrs.controller?:controllerName
        def label=attrs.label?:'Refrescar'
        def id=attrs.id
        def model=[action:action,controller:controller,id:id,label:label]
        out << render(template:"/common/buttons/refreshButton" ,model:model)
    }

    def createButton={ attrs->
        def action=attrs.action?:'create'
        def controller=attrs.controller?:controllerName
        def label=attrs.label?:'Nuevo'
        def id=attrs.id
        def model=[action:action,controller:controller,id:id,label:label]
        out << render(template:"/common/buttons/createButton" ,model:model)
    }

    def searchButton={ attrs->
        def action=attrs.action?:'search'
        def controller=attrs.controller?:controllerName
        def label=attrs.label?:'Busqueda'
        def title=attrs.title?:'Busqueda avanzada'
        def id=attrs.id
        def model=[action:action,controller:controller,id:id,label:label,title:title]
        out << render(template:"/common/buttons/searchButton" ,model:model)
    }

    def editButton={ attrs ->
        def action=attrs.action?:'edit'
        def controller=attrs.controller?:controllerName
        def label=attrs.label?:'Editar'
        def id=attrs.id
        def model=[action:action,controller:controller,id:id,label:label]
        out << render(template:"/common/buttons/editButton" ,model:model)

    }

    def backButton={ attrs ->
        def action=attrs.action?:'index'
        def controller=attrs.controller?:controllerName
        def label=attrs.label?:'Regresar'
        def id=attrs.id
        def clazz=attrs.class?:'btn btn-outline btn-default'
        def model=[action:action,controller:controller,id:id,label:label,clazz:clazz]
        out << render(template:"/common/buttons/backButton" ,model:model)
    }

    def errorsHeader={ attrs ->
        def bean=attrs.bean
        if(!bean)
            throw new IllegalArgumentException("Tag errorsHeader requiere del parametro   bean ")
        out << render(template:"/common/components/errorsHeader" ,model:[it:bean])
        
    }

    def dateCell={attrs ->
        def date=attrs.date
        StringBuilder sb = new StringBuilder()
        assert date,"Debe definir la fecha "
        sb << """
                <td>
                    <g:formatDate date="${date}" format:'dd/MM/yyyy'>
                </td>
            """
        out<<sb.toString()
    }

    /**
     * Renders la fecha en el fomrato estandarizado dd/MM/yyyy
     * @attrs date REQUIRED La fecha a formatear
     */
    def shortDate={ attrs ->
        out << g.formatDate(date:attrs.date,format:"dd/MM/yyyy")
    }
    
    /**
     * Formatea un numero a una cantidad moentaria  
     * 
     * @attrs number REQUIRED El numero a formatear en moneda
     */
    def moneyFormat={ attrs ->
        out <<g.formatNumber(number:attrs.number, type:"currency", currencyCode:"MXN",locale:"es_MX")
    }
    
    /**
     * Da formato adecuado a los cantidades utlizadas en medidas de kilos
     * @attrs number REQUIRED El numero de kilos a dar formato
     */
    def kilosFormat={attrs ->
        out <<g.formatNumber(number:attrs.number, format:"###,###,###")
    }
    
    /**
     * Da formato adecuado a cantidades utilizadas para expresar millares de hojas
     * 
     * @attrs number REQUIRED El numero de millares a dar formato
     */
    def millaresFormat={attrs ->
        out <<g.formatNumber(number:attrs.number, format:"###,###,###.###")
    }

    def idFormat={ attrs ->
        out <<g.formatNumber(number:attrs.id, format:"###")

    }

    /**
    *
    * Genera un TD apropiado para la columna de identificacion 
    * 
    * @attrs id REQUIRED La propiedad id del bean a mostrar
    * @attrs action La accion para el Link generado default show
    * @attrs controller El controlador para la accion
    **/
    def idTableRow={ attrs ->
        def action=attrs.action?:'show'
        def controller=attrs.controller?:controllerName
        def id=attrs.id
        StringBuilder sb = new StringBuilder()
        if(id){
            sb << """
                <td>
                    <a href="${g.createLink(action:action,controller:controller,id:id)}">${id}</a>
                </td>
            """
        }else{
            sb << """
                <td></td>
            """
        }
        out<<sb.toString()
    }

    /**
     * Presneta un td formateao en moneda para el importe especificado
     * 
     * @attrs number REQUIRED El numero a formatear en moneda
     */
    def moneyTableRow={ attrs ->
        //out << "<td class="${attrs.number>0?'text-success':'text-danger'}">"
        def number=attrs.number
        out <<"""
           <td class="${number>=0?'text-success':'text-danger'}"> 
        """
        //out <<"<td class=${number>=0?text-success:text-danger}>" 
        out << lx.moneyFormat(number:attrs.number)
        out << "</td>"
    }

    def iboxBackButton={ attrs ->
        def action=attrs.action?:'index'
        StringBuilder sb = new StringBuilder()
        sb << """
            <a href="${g.createLink(action:action)}"><i class='fa fa-step-backward'></i></a>
        """
        out<<sb.toString()
    }

    def iboxTitle={ attrs ->
        def title=attrs.title?:''
        StringBuilder sb = new StringBuilder()
        sb << """
            <div class="ibox-title">
                <h5>${title}</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
                    </a>
                </div>
            </div>
        """
        out<<sb.toString()
    }

    def warningLabel = {
        if(flash.message) {
            out << " <small><span class='label label-warning' >${flash.message}</span></small> "
        }
        //out << "<g:if test=${flash.message}><small><span class='label label-warning' >${flash.message}</span></small></g:if> "
    }

    def ibox = { attrs, body ->
        out << "<div class='ibox float-e-margins'>"
        out << body()
        out << "</div>"
    }

    def iboxContent = { attrs, body ->
        out << "<div class='ibox-content'>"
        out << body()
        out << "</div>"
    }

    /**
     * Titulo para el Ibox
     * 
     * @attrs title  Título para el ibox
     */
    def iboxTitle2 = { attrs, body ->
        out << "<div class='ibox-title'>"
        out << "<h5>${attrs.title}</h5>"
        out << body()
        out << "</div>"
    }

    def iboxTools = {attrs, body ->
        out << "<div class='ibox-tools'>"
        out << body()
        out << "<a class='collapse-link'><i class='fa fa-chevron-up'></i></a>"
        
        out << "</div>" 
    }

    def iboxFooter = { attrs, body ->
        out << "<div class='ibox-footer'>"
        out << body()
        out << "</div>"
    }

    def header = { attrs, body ->
        out << "<div class='row wrapper border-bottom white-bg page-heading' >"
        out << body()
        out << "</div>"

    }

    /**
     * Html Div decorado para contenido
     *
     * @attrs animated  Animar el div al presentarlo
     * @attrs animatedType  Tipo de anima
     */
    def wrapper = { attrs, body ->
        def animated = attrs.animated ? 'animated' : ''
        def animatedType = attrs.animatedType ?: 'fadeInRight'
        out << "<div class=\"row wrapper wrapper-content white-bg ${animated} ${animatedType}\" >"
        out << body()
        out << "</div>"
    }
}
