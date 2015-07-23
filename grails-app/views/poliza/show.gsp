
<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'poliza.label', default: 'Poliza')}" />
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
				
					<g:if test="${polizaInstance?.descripcion}">
						<dt><g:message code="poliza.descripcion.label" default="Descripcion" /></dt>
						
							<dd><g:fieldValue bean="${polizaInstance}" field="descripcion"/></dd>
						
					</g:if>
				
					<g:if test="${polizaInstance?.debe}">
						<dt><g:message code="poliza.debe.label" default="Debe" /></dt>
						
							<dd><g:fieldValue bean="${polizaInstance}" field="debe"/></dd>
						
					</g:if>
				
					<g:if test="${polizaInstance?.haber}">
						<dt><g:message code="poliza.haber.label" default="Haber" /></dt>
						
							<dd><g:fieldValue bean="${polizaInstance}" field="haber"/></dd>
						
					</g:if>
				
					<g:if test="${polizaInstance?.tipo}">
						<dt><g:message code="poliza.tipo.label" default="Tipo" /></dt>
						
							<dd><g:fieldValue bean="${polizaInstance}" field="tipo"/></dd>
						
					</g:if>
				
					<g:if test="${polizaInstance?.dateCreated}">
						<dt><g:message code="poliza.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${polizaInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${polizaInstance?.fecha}">
						<dt><g:message code="poliza.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${polizaInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${polizaInstance?.folio}">
						<dt><g:message code="poliza.folio.label" default="Folio" /></dt>
						
							<dd><g:fieldValue bean="${polizaInstance}" field="folio"/></dd>
						
					</g:if>
				
					<g:if test="${polizaInstance?.lastUpdated}">
						<dt><g:message code="poliza.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${polizaInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${polizaInstance?.partidas}">
						<dt><g:message code="poliza.partidas.label" default="Partidas" /></dt>
						
							<g:each in="${polizaInstance.partidas}" var="p">
							<dd><g:link controller="polizaDet" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${polizaInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${polizaInstance?.id}">
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
