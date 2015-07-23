<%@ page import="com.luxsoft.impapx.Venta" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${'Nota de Cargo'}" />
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
								Cargos
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								Alta de cargo
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span10">

				<div class="alert">
					<h4><strong>
					Venta: ${ventaInstance.id} ( ${ventaInstance.cliente})
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
				
				<g:render template="editForm" bean="${ventaInstance}"/>
			</div>

		</div>
		</div>
	</body>
</html>
