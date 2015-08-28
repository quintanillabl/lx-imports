<%@ page import="com.luxsoft.impapx.cxc.CXCNota" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	
	<g:set var="entityName" value="${message(code: 'CXCNota.label', default: 'CXCNota')}" scope="request"/>
	<g:set var="entity" value="${CXCNotaInstance}" scope="request" />
	<title>CXC Nota ${CXCNotaInstance.id}</title>
</head>
<body>
	
	<content tag="header">
		Nota ${CXCNotaInstance.id}
 	</content>
 	<content tag="subHeader">
 		<ol class="breadcrumb">
            <li><g:link action="index">${entityName}(s)</g:link></li>
            <li><g:link action="show" id="${entity.id}">Consulta</g:link></li>
            <li><g:link action="edit" id="${entity.id}"><strong>Edición</strong></g:link></li>
        </ol>
 	</content>

 	<content tag="document">
 		<div class="ibox float-e-margins">
 			<div class="ibox-title">
				<g:if test="${!CXCNotaInstance.cfdi}">
					
					<g:link  action="generarCFDI" class="btn btn-info btn-outline" 
						onclick="return confirm('Generar comprobante fiscal: ${CXCNotaInstance.id}');"
						id="${CXCNotaInstance.id}">
			 				Generar CFDI
						</g:link>
					<lx:deleteButton bean="${CXCNotaInstance}"/>
				</g:if>
				<g:else>
					<g:link  action="cancelarCFDI" class="btn btn-info" 
						onclick="return confirm('Generar comprobante fiscal: ${CXCNotaInstance.id}');"
						id="${CXCNotaInstance.id}">
			 				Cancelar CFD
						</g:link>
				</g:else>
 			</div>
 		    <div class="ibox-content">
 		    	
 		    	
		 		<ul class="nav nav-tabs" id="editTab">
					<li class="active"><a href="#conceptosPanel" 	  data-toggle="tab">Conceptos</a></li>
					<li><a href="#aplicacionesPanel" data-toggle="tab">Aplicaciones</a></li>
					<li><a href="#abonoPanel"  data-toggle="tab">Propiedades</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="conceptosPanel">
						<g:render template="conceptosPanel" 
							model="[CXCAbonoInstance:CXCNotaInstance,conceptos:CXCNotaInstance.partidas]"/> 
					</div>
					<div class="tab-pane " id="aplicacionesPanel">
						<g:render template="aplicacionesPanel" 
							model="[CXCAbonoInstance:CXCNotaInstance,aplicaciones:CXCNotaInstance.aplicaciones]"/> 
					</div>
					<div class="tab-pane " id="abonoPanel">
						<g:render template="editForm" bean="${CXPAbonoInstance}"/>
					</div>
					
				</div>
 		    </div>
 		</div>
 	</content>
 	
 	
</body>
</html>


