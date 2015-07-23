
<%@ page import="com.luxsoft.impapx.Distribucion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'distribucion.label', default: 'Distribucion')}" />
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
				
					
				
					<g:if test="${distribucionInstance?.contenedores}">
						<dt><g:message code="distribucion.contenedores.label" default="Contenedores" /></dt>
						
							<dd><g:fieldValue bean="${distribucionInstance}" field="contenedores"/></dd>
						
					</g:if>
				
					<g:if test="${distribucionInstance?.dateCreated}">
						<dt><g:message code="distribucion.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${distribucionInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${distribucionInstance?.embarque}">
						<dt><g:message code="distribucion.embarque.label" default="Embarque" /></dt>
						
							<dd><g:link controller="embarque" action="show" id="${distribucionInstance?.embarque?.id}">${distribucionInstance?.embarque?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${distribucionInstance?.fecha}">
						<dt><g:message code="distribucion.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${distribucionInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${distribucionInstance?.lastUpdated}">
						<dt><g:message code="distribucion.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${distribucionInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${distribucionInstance?.partidas}">
						<dt><g:message code="distribucion.partidas.label" default="Partidas" /></dt>
						
							<g:each in="${distribucionInstance.partidas}" var="p">
							<dd><g:link controller="distribucionDet" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
					
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${distribucionInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${distribucionInstance?.id}">
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
