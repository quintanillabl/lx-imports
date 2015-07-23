<%@ page import="com.luxsoft.impapx.Venta" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'venta.label', default: 'Venta')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<r:require modules="luxorTableUtils,dataTables,luxorForms" />
		
	</head>
	<body>
		<div class="container-fluid">
		<div class="row-fluid">

			<div class="span2">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Operaciones</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								Ventas
							</g:link>
						</li>
						
					</ul>
				</div>
			</div>
			
			<div class="span10">

				<div class="alert">
					<h4><strong>
					Venta ${ventaInstance.clase=='generica'?'Genérica':'Importaciones'}: ${ventaInstance.id} ( ${ventaInstance.cliente})
					</strong></h4>
				</div>
				
				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${ventaInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${ventaInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>
				<ul class="nav nav-tabs" id="myTab">
					<li class=""><a href="#venta" data-toggle="tab">Venta</a></li>
					<li class="active"><a href="#partidas" data-toggle="tab">Partidas</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane " id="venta">
						
						<g:render template="editForm" bean="${ventaInstance}"/>
					</div>
					<div class="tab-pane active" id="partidas">
						<g:render template="partidasPanel" bean="${ventaInstance}"/>
					</div>
					
				</div>
			</div>

		</div>
		</div>
	</body>
</html>
