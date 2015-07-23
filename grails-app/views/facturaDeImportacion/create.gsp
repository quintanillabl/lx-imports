<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'facturaDeImportacion.label', default: 'FacturaDeImportacion')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<r:require modules="datepicker,luxorForms,autoNumeric"/>
		
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								<g:message code="factura.list.label" default="Facturas"  />
							</g:link>
						</li>
						<li class="active">
							<g:link class="create" action="create">
								<i class="icon-plus icon-white"></i>
								<g:message code="factura.create.label" default="Nueva Factura" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="page-header">
					<h3><g:message code="default.create.label" args="[entityName]" /></h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${facturaDeImportacionInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${facturaDeImportacionInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>				
				<g:render template="form" bean="${facturaDeImportacionInstance}" model="[action:'create']"/>
			</div>

		</div>
	</body>
</html>
