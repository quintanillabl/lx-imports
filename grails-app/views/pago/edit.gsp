<%@ page import="com.luxsoft.impapx.cxp.Pago" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<g:set var="entityName" value="${message(code: 'pago.label', default: 'Pago')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<r:require module="luxorForms"/>
	</head>
	<body>
	<content tag="header">
		
 	</content>
 	
 	<content tag="consultas">
 		<g:link controller="cuentaPorPagar" action="list">
			Cuentas por pagar
		</g:link>
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="list" action="list">
				<i class="icon-list"></i>
				Pagos
			</g:link>
 			
		</li>
 	</content>
 	
 	<content tag="document">
 		
 		<g:render template="/shared/messagePanel" model="[beanInstance:pagoInstance]"/>
 		<div class="alert">
 			<h5>Pago:${pagoInstance.id} ${pagoInstance.proveedor.nombre} Disponible: ${pagoInstance.disponible}</h5>
 		</div>
 		<ul class="nav nav-tabs" id="myTab">
			<li class=""><a href="#editForm" data-toggle="tab">Abono</a></li>
			<li class="active"><a href="#aplicacionesPanel" data-toggle="tab">Aplicaciones</a></li>
		</ul>
		
		<div class="tab-content">
		
			<div class="tab-pane " id="editForm">
				<g:render template="editForm" bean="${pagoInstance}"/>
			</div>
		
			<div class="tab-pane active" id="aplicacionesPanel">
				<g:render template="aplicacionesPanel" 
					model="[abonoInstance:pagoInstance,aplicaciones:pagoInstance.aplicaciones]"/>
			</div>
		</div>
 	</content>
		
	</body>

</html>
