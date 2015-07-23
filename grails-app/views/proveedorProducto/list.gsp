

<%@ page import="com.luxsoft.impapx.ProveedorProducto" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'proveedorProducto.label', default: 'ProveedorProducto')}" />
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
							
							<th class="header"><g:message code="proveedorProducto.producto.label" default="Producto" /></th>
						
							<g:sortableColumn property="codigo" title="${message(code: 'proveedorProducto.codigo.label', default: 'Codigo')}" />
						
							<g:sortableColumn property="descripcion" title="${message(code: 'proveedorProducto.descripcion.label', default: 'Descripcion')}" />
						
							<g:sortableColumn property="costoUnitario" title="${message(code: 'proveedorProducto.costoUnitario.label', default: 'Costo Unitario')}" />
						
							<th class="header"><g:message code="proveedorProducto.proveedor.label" default="Proveedor" /></th>
						
							
						</tr>
					</thead>
					<tbody>
						<g:each in="${proveedorProductoInstanceList}" var="proveedorProductoInstance">
							<tr>
								
						<td><g:link action="show" id="${proveedorProductoInstance.id}">${fieldValue(bean: proveedorProductoInstance, field: "producto")}</g:link></td>
					
						<td>${fieldValue(bean: proveedorProductoInstance, field: "codigo")}</td>
					
						<td>${fieldValue(bean: proveedorProductoInstance, field: "descripcion")}</td>
					
						<td>${fieldValue(bean: proveedorProductoInstance, field: "costoUnitario")}</td>
					
						<td>${fieldValue(bean: proveedorProductoInstance, field: "proveedor")}</td>
					
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${proveedorProductoInstanceTotal}" />
				</div>
			</div>

		</div>
	</div>
</body>
</html>
