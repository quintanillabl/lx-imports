
<%@ page import="com.luxsoft.impapx.cxc.CXCPago" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'CXCPago.label', default: 'CXCPago')}" />
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
				
					<g:if test="${CXCPagoInstance?.importe}">
						<dt><g:message code="CXCPago.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.impuesto}">
						<dt><g:message code="CXCPago.impuesto.label" default="Impuesto" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="impuesto"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.total}">
						<dt><g:message code="CXCPago.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="total"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.tc}">
						<dt><g:message code="CXCPago.tc.label" default="Tc" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="tc"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.impuestoTasa}">
						<dt><g:message code="CXCPago.impuestoTasa.label" default="Impuesto Tasa" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="impuestoTasa"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.comentario}">
						<dt><g:message code="CXCPago.comentario.label" default="Comentario" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="comentario"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.formaDePago}">
						<dt><g:message code="CXCPago.formaDePago.label" default="Forma De Pago" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="formaDePago"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.referenciaBancaria}">
						<dt><g:message code="CXCPago.referenciaBancaria.label" default="Referencia Bancaria" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="referenciaBancaria"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.aplicaciones}">
						<dt><g:message code="CXCPago.aplicaciones.label" default="Aplicaciones" /></dt>
						
							<g:each in="${CXCPagoInstance.aplicaciones}" var="a">
							<dd><g:link controller="CXCAplicacion" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.aplicado}">
						<dt><g:message code="CXCPago.aplicado.label" default="Aplicado" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="aplicado"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.cliente}">
						<dt><g:message code="CXCPago.cliente.label" default="Cliente" /></dt>
						
							<dd><g:link controller="cliente" action="show" id="${CXCPagoInstance?.cliente?.id}">${CXCPagoInstance?.cliente?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.disponible}">
						<dt><g:message code="CXCPago.disponible.label" default="Disponible" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="disponible"/></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.fecha}">
						<dt><g:message code="CXCPago.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${CXCPagoInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.fechaBancaria}">
						<dt><g:message code="CXCPago.fechaBancaria.label" default="Fecha Bancaria" /></dt>
						
							<dd><g:formatDate date="${CXCPagoInstance?.fechaBancaria}" /></dd>
						
					</g:if>
				
					<g:if test="${CXCPagoInstance?.moneda}">
						<dt><g:message code="CXCPago.moneda.label" default="Moneda" /></dt>
						
							<dd><g:fieldValue bean="${CXCPagoInstance}" field="moneda"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${CXCPagoInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${CXCPagoInstance?.id}">
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
