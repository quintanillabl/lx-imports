
<%@ page import="com.luxsoft.impapx.cxp.CuentaDeGastosGenerica" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'cuentaDeGastosGenerica.label', default: 'CuentaDeGastosGenerica')}" />
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
				
					<g:if test="${cuentaDeGastosGenericaInstance?.comentario}">
						<dt><g:message code="cuentaDeGastosGenerica.comentario.label" default="Comentario" /></dt>
						
							<dd><g:fieldValue bean="${cuentaDeGastosGenericaInstance}" field="comentario"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.referencia}">
						<dt><g:message code="cuentaDeGastosGenerica.referencia.label" default="Referencia" /></dt>
						
							<dd><g:fieldValue bean="${cuentaDeGastosGenericaInstance}" field="referencia"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.proveedor}">
						<dt><g:message code="cuentaDeGastosGenerica.proveedor.label" default="Proveedor" /></dt>
						
							<dd><g:link controller="proveedor" action="show" id="${cuentaDeGastosGenericaInstance?.proveedor?.id}">${cuentaDeGastosGenericaInstance?.proveedor?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.dateCreated}">
						<dt><g:message code="cuentaDeGastosGenerica.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${cuentaDeGastosGenericaInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.facturas}">
						<dt><g:message code="cuentaDeGastosGenerica.facturas.label" default="Facturas" /></dt>
						
							<g:each in="${cuentaDeGastosGenericaInstance.facturas}" var="f">
							<dd><g:link controller="facturaDeGastos" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.fecha}">
						<dt><g:message code="cuentaDeGastosGenerica.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${cuentaDeGastosGenericaInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.importe}">
						<dt><g:message code="cuentaDeGastosGenerica.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${cuentaDeGastosGenericaInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.impuestos}">
						<dt><g:message code="cuentaDeGastosGenerica.impuestos.label" default="Impuestos" /></dt>
						
							<dd><g:fieldValue bean="${cuentaDeGastosGenericaInstance}" field="impuestos"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.lastUpdated}">
						<dt><g:message code="cuentaDeGastosGenerica.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${cuentaDeGastosGenericaInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaDeGastosGenericaInstance?.total}">
						<dt><g:message code="cuentaDeGastosGenerica.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${cuentaDeGastosGenericaInstance}" field="total"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${cuentaDeGastosGenericaInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${cuentaDeGastosGenericaInstance?.id}">
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
