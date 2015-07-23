
<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'requisicion.label', default: 'Requisicion')}" />
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
				
					<g:if test="${requisicionInstance?.proveedor}">
						<dt><g:message code="requisicion.proveedor.label" default="Proveedor" /></dt>
						
							<dd><g:link controller="proveedor" action="show" id="${requisicionInstance?.proveedor?.id}">${requisicionInstance?.proveedor?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${requisicionInstance?.concepto}">
						<dt><g:message code="requisicion.concepto.label" default="Concepto" /></dt>
						
							<dd><g:fieldValue bean="${requisicionInstance}" field="concepto"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionInstance?.fecha}">
						<dt><g:message code="requisicion.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${requisicionInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${requisicionInstance?.fechaDelPago}">
						<dt><g:message code="requisicion.fechaDelPago.label" default="Fecha Del Pago" /></dt>
						
							<dd><g:formatDate date="${requisicionInstance?.fechaDelPago}" /></dd>
						
					</g:if>
				
					<g:if test="${requisicionInstance?.moneda}">
						<dt><g:message code="requisicion.moneda.label" default="Moneda" /></dt>
						
							<dd><g:fieldValue bean="${requisicionInstance}" field="moneda"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionInstance?.tc}">
						<dt><g:message code="requisicion.tc.label" default="Tc" /></dt>
							<dd><g:formatNumber number="${requisicionInstance.tc}" format="####.######"/>
					</g:if>
				
					<g:if test="${requisicionInstance?.descuentoFinanciero}">
						<dt><g:message code="requisicion.descuentoFinanciero.label" default="Descuento Financiero" /></dt>
							<dd><g:formatNumber number="${requisicionInstance.descuentoFinanciero}" format="####.######"/>
						
					</g:if>
				
					<g:if test="${requisicionInstance?.importe}">
						<dt><g:message code="requisicion.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${requisicionInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionInstance?.impuestos}">
						<dt><g:message code="requisicion.impuestos.label" default="Impuestos" /></dt>
						
							<dd><g:fieldValue bean="${requisicionInstance}" field="impuestos"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionInstance?.total}">
						<dt><g:message code="requisicion.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${requisicionInstance}" field="total"/></dd>
						
					</g:if>
				
				</dl>
				
				<g:jasperReport jasper="Requisicion" format="PDF,HTML" name="Imprimir">
					<g:hiddenField name="ID" value="${requisicionInstance.id}"/>
					<g:hiddenField name="MONEDA" value="${requisicionInstance.moneda}"/>
				</g:jasperReport>

				<g:form>
					<g:hiddenField name="id" value="${requisicionInstance?.id}" />
					<div class="form-actions">
						<g:if test="${!requisicionInstance.pagoProveedor}">
							<g:link class="btn" action="edit" id="${requisicionInstance?.id}">
								<i class="icon-pencil"></i>
								<g:message code="default.button.edit.label" default="Edit" />
							</g:link>
							<button class="btn btn-danger" type="submit" name="_action_delete">
								<i class="icon-trash icon-white"></i>
								<g:message code="default.button.delete.label" default="Delete" />
							</button>
						</g:if>
						
						
					</div>
				</g:form>

			</div>

		</div>
	</body>
</html>
