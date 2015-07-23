<%@ page import="com.luxsoft.impapx.cxp.NotaDeCredito" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<g:set var="entityName" value="${message(code: 'notaDeCredito.label', default: 'NotaDeCredito')}" />
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
				Notas
			</g:link>
 			<g:link class="" action="create">
				<i class="icon-plus "></i>
				Alta de Nota
			</g:link>
		</li>
 	</content>
 	
 	<content tag="document">
 		
 		<g:render template="/shared/messagePanel" model="[beanInstance:notaDeCreditoInstance]"/>
 		<div class="alert">
 			<h5>Nota:${notaDeCreditoInstance.id} ${notaDeCreditoInstance.proveedor.nombre} Disponible: ${notaDeCreditoInstance.disponible}</h5>
 		</div>
 		<ul class="nav nav-tabs" id="myTab">
			<li class=""><a href="#editForm" data-toggle="tab">Abono</a></li>
			<li class="active"><a href="#aplicacionesPanel" data-toggle="tab">Aplicaciones</a></li>
		</ul>
		<%-- 
		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
		<g:hasErrors bean="${notaDeCreditoInstance}">
			<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${notaDeCreditoInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
			</bootstrap:alert>
		</g:hasErrors>
		--%>
		<div class="tab-content">
		
			<div class="tab-pane " id="editForm">
				<g:render template="editForm" bean="${notaDeCreditoInstance}"/>
			</div>
		
			<div class="tab-pane active" id="aplicacionesPanel">
				<g:render template="aplicacionesPanel" 
					model="[abonoInstance:notaDeCreditoInstance,aplicaciones:notaDeCreditoInstance.aplicaciones]"/>
			</div>
		</div>
 	</content>
		
	</body>

</html>
