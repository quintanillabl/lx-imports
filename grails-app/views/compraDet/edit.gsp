<%@ page import="com.luxsoft.impapx.CompraDet" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'compraDet.label', default: 'CompraDet')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">

			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Compra</li>
						
						
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="alert">
					<g:link controller="compra" action="edit" id="${compraDetInstance.compra.id}" >
					<h3>
						Compra: ${compraDetInstance?.id}
					</h3>
					</g:link>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${compraDetInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${compraDetInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>
				<g:render template="form" bean="${compraDetInstance}"/>
				

			</div>

		</div>
	</body>
</html>
