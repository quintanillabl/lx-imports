
<%@ page import="com.luxsoft.impapx.ProveedorProducto" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'proveedorProducto.label', default: 'ProveedorProducto')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="span12">

			<div class="page-header">
				<h3>${proveedorProductoInstance?.proveedor?.nombre}</h3>
			</div>

			<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
			</g:if>

			<dl>
			
				<g:if test="${proveedorProductoInstance?.producto}">
					<dt><g:message code="proveedorProducto.producto.label" default="Producto" /></dt>
					
						<dd><g:link controller="producto" action="show" id="${proveedorProductoInstance?.producto?.id}">${proveedorProductoInstance?.producto?.encodeAsHTML()}</g:link></dd>
					
				</g:if>
			
				<g:if test="${proveedorProductoInstance?.codigo}">
					<dt><g:message code="proveedorProducto.codigo.label" default="Codigo" /></dt>
					
						<dd><g:fieldValue bean="${proveedorProductoInstance}" field="codigo"/></dd>
					
				</g:if>
			
				<g:if test="${proveedorProductoInstance?.descripcion}">
					<dt><g:message code="proveedorProducto.descripcion.label" default="Descripcion" /></dt>
					
						<dd><g:fieldValue bean="${proveedorProductoInstance}" field="descripcion"/></dd>
					
				</g:if>
			
				<g:if test="${proveedorProductoInstance?.costoUnitario}">
					<dt><g:message code="proveedorProducto.costoUnitario.label" default="Costo Unitario" /></dt>
					
						<dd><g:fieldValue bean="${proveedorProductoInstance}" field="costoUnitario"/></dd>
					
				</g:if>
			
				
			
			</dl>

			<g:form>
				<g:hiddenField name="id" value="${proveedorProductoInstance?.id}" />
				<div class="form-actions">
					<g:link class="btn" action="edit" id="${proveedorProductoInstance?.id}">
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
	</body>
</html>
