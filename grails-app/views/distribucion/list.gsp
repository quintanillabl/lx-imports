

<%@ page import="com.luxsoft.impapx.Distribucion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'distribucion.label', default: 'Distribucion')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li class="active"><g:link class="list" action="list">
								<i class="icon-list icon-white"></i>
								<g:message code="default.list.label" args="[entityName]" />
							</g:link></li>
						<li><g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link></li>
					</ul>
				</div>
			</div>

			<div class="span9">

				<div class="page-header">
					<h2>
						<g:message code="default.list.label" args="[entityName]" />
					</h2>
				</div>

				<g:if test="${flash.message}">
					<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<table class="table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							
							<g:sortableColumn property="id" title="${message(code: 'distribucion.id.label', default: 'Id')}" />
							
							
							<g:sortableColumn property="fecha" title="${message(code: 'distribucion.fecha.label', default: 'Fecha')}" />
							
						
							<th class="header"><g:message code="distribucion.embarque.label" default="Embarque" /></th>
						
							<g:sortableColumn property="lastUpdated" title="${message(code: 'distribucion.lastUpdated.label', default: 'Modificado')}" />
						</tr>
					</thead>
					<tbody>
						<g:each in="${distribucionInstanceList}" var="distribucionInstance">
						<tr>	
							<td><g:link action="edit" id="${distribucionInstance.id}">${fieldValue(bean: distribucionInstance, field: "id")}</g:link></td>
							
							
							
							<td><lx:shortDate date="${distribucionInstance.fecha}"/></td>	
						
							
						
							<td>${fieldValue(bean: distribucionInstance, field: "embarque")}</td>
					
							<td><g:formatDate date="${distribucionInstance.lastUpdated}" /></td>
						</tr>
						</g:each>
						
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${distribucionInstanceTotal}" />
				</div>
			</div>

		</div>
	</div>
</body>
</html>
