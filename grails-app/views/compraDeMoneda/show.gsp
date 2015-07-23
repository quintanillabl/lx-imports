
<%@ page import="com.luxsoft.impapx.tesoreria.CompraDeMoneda" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'compraDeMoneda.label', default: 'CompraDeMoneda')}" />
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
				
					<g:if test="${compraDeMonedaInstance?.formaDePago}">
						<dt><g:message code="compraDeMoneda.formaDePago.label" default="Forma De Pago" /></dt>
						
							<dd><g:fieldValue bean="${compraDeMonedaInstance}" field="formaDePago"/></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.tipoDeCambioCompra}">
						<dt><g:message code="compraDeMoneda.tipoDeCambioCompra.label" default="Tipo De Cambio Compra" /></dt>
						
							<dd><g:fieldValue bean="${compraDeMonedaInstance}" field="tipoDeCambioCompra"/></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.tipoDeCambio}">
						<dt><g:message code="compraDeMoneda.tipoDeCambio.label" default="Tipo De Cambio" /></dt>
						
							<dd><g:fieldValue bean="${compraDeMonedaInstance}" field="tipoDeCambio"/></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.diferenciaCambiaria}">
						<dt><g:message code="compraDeMoneda.diferenciaCambiaria.label" default="Diferencia Cambiaria" /></dt>
						
							<dd><g:fieldValue bean="${compraDeMonedaInstance}" field="diferenciaCambiaria"/></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.cuentaDestino}">
						<dt><g:message code="compraDeMoneda.cuentaDestino.label" default="Cuenta Destino" /></dt>
						
							<dd><g:link controller="cuentaBancaria" action="show" id="${compraDeMonedaInstance?.cuentaDestino?.id}">${compraDeMonedaInstance?.cuentaDestino?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.cuentaOrigen}">
						<dt><g:message code="compraDeMoneda.cuentaOrigen.label" default="Cuenta Origen" /></dt>
						
							<dd><g:link controller="cuentaBancaria" action="show" id="${compraDeMonedaInstance?.cuentaOrigen?.id}">${compraDeMonedaInstance?.cuentaOrigen?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.dateCreated}">
						<dt><g:message code="compraDeMoneda.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${compraDeMonedaInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.fecha}">
						<dt><g:message code="compraDeMoneda.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${compraDeMonedaInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.lastUpdated}">
						<dt><g:message code="compraDeMoneda.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${compraDeMonedaInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.moneda}">
						<dt><g:message code="compraDeMoneda.moneda.label" default="Moneda" /></dt>
						
							<dd><g:fieldValue bean="${compraDeMonedaInstance}" field="moneda"/></dd>
						
					</g:if>
				
					<g:if test="${compraDeMonedaInstance?.requisicion}">
						<dt><g:message code="compraDeMoneda.requisicion.label" default="Requisicion" /></dt>
						
							<dd><g:link controller="requisicion" action="show" id="${compraDeMonedaInstance?.requisicion?.id}">${compraDeMonedaInstance?.requisicion?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${compraDeMonedaInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${compraDeMonedaInstance?.id}">
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
