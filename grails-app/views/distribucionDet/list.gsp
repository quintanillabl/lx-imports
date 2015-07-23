

<%@ page import="com.luxsoft.impapx.DistribucionDet" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'distribucionDet.label', default: 'DistribucionDet')}" />
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
							
							<g:sortableColumn property="cantidadPorTarima" title="${message(code: 'distribucionDet.cantidadPorTarima.label', default: 'Cantidad Por Tarima')}" />
						
							<g:sortableColumn property="cantidad" title="${message(code: 'distribucionDet.cantidad.label', default: 'Cantidad')}" />
						
							<g:sortableColumn property="comentarios" title="${message(code: 'distribucionDet.comentarios.label', default: 'Comentarios')}" />
						
							<g:sortableColumn property="contenedor" title="${message(code: 'distribucionDet.contenedor.label', default: 'Contenedor')}" />
						
							<th class="header"><g:message code="distribucionDet.distribucion.label" default="Distribucion" /></th>
						
							<th class="header"><g:message code="distribucionDet.embarqueDet.label" default="Embarque Det" /></th>
						
							
						</tr>
					</thead>
					<tbody>
						<g:each in="${distribucionDetInstanceList}" var="distribucionDetInstance">
							<tr>
								
						<td><g:link action="show" id="${distribucionDetInstance.id}">${fieldValue(bean: distribucionDetInstance, field: "cantidadPorTarima")}</g:link></td>
					
						<td>${fieldValue(bean: distribucionDetInstance, field: "cantidad")}</td>
					
						<td>${fieldValue(bean: distribucionDetInstance, field: "comentarios")}</td>
					
						<td>${fieldValue(bean: distribucionDetInstance, field: "contenedor")}</td>
					
						<td>${fieldValue(bean: distribucionDetInstance, field: "distribucion")}</td>
					
						<td>${fieldValue(bean: distribucionDetInstance, field: "embarqueDet")}</td>
					
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${distribucionDetInstanceTotal}" />
				</div>
			</div>

		</div>
	</div>
</body>
</html>
