<%@ page import="com.luxsoft.impapx.cxp.Pago" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<g:set var="entityName" value="${message(code: 'pago.label', default: 'Pago')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">
	Pago: ${pagoInstance} (${pagoInstance.proveedor})
</content>
 	
<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="index">Pagos</g:link></li>
		<li><g:link action="create">Alta</g:link></li>
		<li><g:link action="edit" id="${pagoInstance.id}"><strong>Edición</strong></g:link></li>
	</ol>
</content> 	

<content tag="document">
	<div class="row">
		<div class="col-md-8">
			<div class="ibox float-e-margins">
				<div class="ibox-title"></div>
			    <div class="ibox-content">
			 		<ul class="nav nav-tabs" id="myTab">
						<li class="active"><a href="#aplicacionesPanel" data-toggle="tab">Aplicaciones</a></li>
						<li class=""><a href="#editForm" data-toggle="tab">Abono</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="aplicacionesPanel">
							<g:render template="aplicacionesPanel" 
								model="[abonoInstance:pagoInstance,aplicaciones:pagoInstance.aplicaciones]"/>
						</div>
						<div class="tab-pane " id="editForm">
							<g:render template="editForm" bean="${pagoInstance}"/>
						</div>
					</div>
			    </div>
			</div>
		</div>
	</div>
	
</content>
 	
 	
 	%{-- <content tag="document2">
 		
 		<g:render template="/shared/messagePanel" model="[beanInstance:pagoInstance]"/>
 		<div class="alert">
 			<h5>Pago:${pagoInstance.id} ${pagoInstance.proveedor.nombre} Disponible: ${pagoInstance.disponible}</h5>
 		</div>
 		
		
		
 	</content> --}%
		
	</body>

</html>
