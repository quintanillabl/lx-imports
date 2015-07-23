

<%@ page import="com.luxsoft.impapx.CompraDet" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'compraDet.label', default: 'CompraDet')}" />
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
							
							<th class="header"><g:message code="compraDet.producto.label" default="Producto" /></th>
						
							<g:sortableColumn property="solicitado" title="${message(code: 'compraDet.solicitado.label', default: 'Solicitado')}" />
						
							<th class="header"><g:message code="compraDet.compra.label" default="Compra" /></th>
						
							<g:sortableColumn property="descuento" title="${message(code: 'compraDet.descuento.label', default: 'Descuento')}" />
						
							<g:sortableColumn property="entregado" title="${message(code: 'compraDet.entregado.label', default: 'Entregado')}" />
						
							<g:sortableColumn property="importe" title="${message(code: 'compraDet.importe.label', default: 'Importe')}" />
						
							
						</tr>
					</thead>
					<tbody>
						<g:each in="${compraDetInstanceList}" var="compraDetInstance">
							<tr>
								
						<td><g:link action="show" id="${compraDetInstance.id}">${fieldValue(bean: compraDetInstance, field: "producto")}</g:link></td>
					
						<td>${fieldValue(bean: compraDetInstance, field: "solicitado")}</td>
					
						<td>${fieldValue(bean: compraDetInstance, field: "compra")}</td>
					
						<td>${fieldValue(bean: compraDetInstance, field: "descuento")}</td>
					
						<td>${fieldValue(bean: compraDetInstance, field: "entregado")}</td>
					
						<td>${fieldValue(bean: compraDetInstance, field: "importe")}</td>
					
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${compraDetInstanceTotal}" />
				</div>
			</div>

		</div>
	</div>
</body>
</html>
