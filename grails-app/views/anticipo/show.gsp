
<%@ page import="com.luxsoft.impapx.cxp.Anticipo" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'anticipo.label', default: 'Anticipo')}" />
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
				
					
					<g:if test="${anticipoInstance?.complemento}">
						<dt><g:message code="anticipo.complemento.label" default="Complemento" /></dt>
						
							<dd><g:link controller="requisicion" action="show" id="${anticipoInstance?.complemento?.id}">${anticipoInstance?.complemento?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${anticipoInstance?.dateCreated}">
						<dt><g:message code="anticipo.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${anticipoInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${anticipoInstance?.embarque}">
						<dt><g:message code="anticipo.embarque.label" default="Embarque" /></dt>
						
							<dd><g:link controller="embarque" action="show" id="${anticipoInstance?.embarque?.id}">${anticipoInstance?.embarque?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${anticipoInstance?.gastosDeImportacion}">
						<dt><g:message code="anticipo.gastosDeImportacion.label" default="Gastos De Importacion" /></dt>
						
							<dd><g:fieldValue bean="${anticipoInstance}" field="gastosDeImportacion"/></dd>
						
					</g:if>
				
					<g:if test="${anticipoInstance?.impuestosAduanales}">
						<dt><g:message code="anticipo.impuestosAduanales.label" default="Impuestos Aduanales" /></dt>
						
							<dd><g:fieldValue bean="${anticipoInstance}" field="impuestosAduanales"/></dd>
						
					</g:if>
				
					<g:if test="${anticipoInstance?.lastUpdated}">
						<dt><g:message code="anticipo.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${anticipoInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${anticipoInstance?.requisicion}">
						<dt><g:message code="anticipo.requisicion.label" default="Requisicion" /></dt>
						
							<dd><g:link controller="requisicion" action="show" id="${anticipoInstance?.requisicion?.id}">${anticipoInstance?.requisicion?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${anticipoInstance?.sobrante}">
						<dt><g:message code="anticipo.sobrante.label" default="Sobrante" /></dt>
						
							<dd><g:link controller="movimientoDeCuenta" action="show" id="${anticipoInstance?.sobrante?.id}">${anticipoInstance?.sobrante?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${anticipoInstance?.total}">
						<dt><g:message code="anticipo.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${anticipoInstance}" field="total"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${anticipoInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${anticipoInstance?.id}">
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
