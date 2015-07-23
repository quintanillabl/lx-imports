
<%@ page import="com.luxsoft.impapx.TipoDeCambio" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'tipoDeCambio.label', default: 'TipoDeCambio')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
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
								<g:message code="default.list.label" args="[entityName]" />
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="page-header">
					<h3><g:message code="default.show.label" args="[entityName]" /></h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${tipoDeCambioInstance?.fecha}">
						<dt><g:message code="tipoDeCambio.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${tipoDeCambioInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${tipoDeCambioInstance?.monedaOrigen}">
						<dt><g:message code="tipoDeCambio.monedaOrigen.label" default="Moneda Origen" /></dt>
						
							<dd><g:fieldValue bean="${tipoDeCambioInstance}" field="monedaOrigen"/></dd>
						
					</g:if>
				
					<g:if test="${tipoDeCambioInstance?.monedaFuente}">
						<dt><g:message code="tipoDeCambio.monedaFuente.label" default="Moneda Fuente" /></dt>
						
							<dd><g:fieldValue bean="${tipoDeCambioInstance}" field="monedaFuente"/></dd>
						
					</g:if>
				
					<g:if test="${tipoDeCambioInstance?.factor}">
						<dt><g:message code="tipoDeCambio.factor.label" default="Factor" /></dt>
						
							<dd><g:formatNumber number="${tipoDeCambioInstance.factor }" format="##.####" /></dd>
						
					</g:if>
				
					<g:if test="${tipoDeCambioInstance?.fuente}">
						<dt><g:message code="tipoDeCambio.fuente.label" default="Fuente" /></dt>
						
							<dd><g:fieldValue bean="${tipoDeCambioInstance}" field="fuente"/></dd>
						
					</g:if>
				
					<g:if test="${tipoDeCambioInstance?.dateCreated}">
						<dt><g:message code="tipoDeCambio.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${tipoDeCambioInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${tipoDeCambioInstance?.lastUpdated}">
						<dt><g:message code="tipoDeCambio.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${tipoDeCambioInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${tipoDeCambioInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${tipoDeCambioInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
					</div>
				</g:form>

			</div>

		</div>
	</body>
</html>
