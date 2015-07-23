
<%@ page import="com.luxsoft.impapx.CompraDet" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'compraDet.label', default: 'CompraDet')}" />
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
				
					<g:if test="${compraDetInstance?.producto}">
						<dt><g:message code="compraDet.producto.label" default="Producto" /></dt>
						
							<dd><g:link controller="producto" action="show" id="${compraDetInstance?.producto?.id}">${compraDetInstance?.producto?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${compraDetInstance?.solicitado}">
						<dt><g:message code="compraDet.solicitado.label" default="Solicitado" /></dt>
						
							<dd><g:fieldValue bean="${compraDetInstance}" field="solicitado"/></dd>
						
					</g:if>
				
					<g:if test="${compraDetInstance?.compra}">
						<dt><g:message code="compraDet.compra.label" default="Compra" /></dt>
						
							<dd><g:link controller="compra" action="show" id="${compraDetInstance?.compra?.id}">${compraDetInstance?.compra?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${compraDetInstance?.descuento}">
						<dt><g:message code="compraDet.descuento.label" default="Descuento" /></dt>
						
							<dd><g:fieldValue bean="${compraDetInstance}" field="descuento"/></dd>
						
					</g:if>
				
					<g:if test="${compraDetInstance?.entregado}">
						<dt><g:message code="compraDet.entregado.label" default="Entregado" /></dt>
						
							<dd><g:fieldValue bean="${compraDetInstance}" field="entregado"/></dd>
						
					</g:if>
				
					<g:if test="${compraDetInstance?.importe}">
						<dt><g:message code="compraDet.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${compraDetInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${compraDetInstance?.importeDescuento}">
						<dt><g:message code="compraDet.importeDescuento.label" default="Importe Descuento" /></dt>
						
							<dd><g:fieldValue bean="${compraDetInstance}" field="importeDescuento"/></dd>
						
					</g:if>
				
					<g:if test="${compraDetInstance?.precio}">
						<dt><g:message code="compraDet.precio.label" default="Precio" /></dt>
						
							<dd><g:fieldValue bean="${compraDetInstance}" field="precio"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${compraDetInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${compraDetInstance?.id}">
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
