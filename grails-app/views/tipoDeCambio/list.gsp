

<%@ page import="com.luxsoft.impapx.TipoDeCambio" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'tipoDeCambio.label', default: 'TipoDeCambio')}" />
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
							
							<g:sortableColumn property="fecha" title="${message(code: 'tipoDeCambio.fecha.label', default: 'Fecha')}" />
						
							<g:sortableColumn property="monedaOrigen" title="${message(code: 'tipoDeCambio.monedaOrigen.label', default: 'Moneda Origen')}" />
						
							<g:sortableColumn property="monedaFuente" title="${message(code: 'tipoDeCambio.monedaFuente.label', default: 'Moneda Fuente')}" />
						
							<g:sortableColumn property="factor" title="${message(code: 'tipoDeCambio.factor.label', default: 'Factor')}" />
						
							<g:sortableColumn property="fuente" title="${message(code: 'tipoDeCambio.fuente.label', default: 'Fuente')}" />
						
							<g:sortableColumn property="dateCreated" title="${message(code: 'tipoDeCambio.dateCreated.label', default: 'Date Created')}" />
						
							
						</tr>
					</thead>
					<tbody>
						<g:each in="${tipoDeCambioInstanceList}" var="tipoDeCambioInstance">
							<tr>
								
						<td><g:link action="show" id="${tipoDeCambioInstance.id}">${fieldValue(bean: tipoDeCambioInstance, field: "fecha")}</g:link></td>
					
						<td>${fieldValue(bean: tipoDeCambioInstance, field: "monedaOrigen")}</td>
					
						<td>${fieldValue(bean: tipoDeCambioInstance, field: "monedaFuente")}</td>
					
						<td><g:formatNumber number="${tipoDeCambioInstance.factor }" format="##.####" /></td>
					
						<td>${fieldValue(bean: tipoDeCambioInstance, field: "fuente")}</td>
					
						<td><g:formatDate date="${tipoDeCambioInstance.dateCreated}" /></td>
					
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${tipoDeCambioInstanceTotal}" />
				</div>
			</div>

		</div>
	</div>
</body>
</html>
