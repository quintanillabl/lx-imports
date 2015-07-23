
<%@ page import="com.luxsoft.impapx.cxp.NotaDeCredito" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'notaDeCredito.label', default: 'NotaDeCredito')}" />
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
				
					<g:if test="${notaDeCreditoInstance?.importe}">
						<dt><g:message code="notaDeCredito.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.impuestos}">
						<dt><g:message code="notaDeCredito.impuestos.label" default="Impuestos" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="impuestos"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.total}">
						<dt><g:message code="notaDeCredito.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="total"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.tc}">
						<dt><g:message code="notaDeCredito.tc.label" default="Tc" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="tc"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.impuestoTasa}">
						<dt><g:message code="notaDeCredito.impuestoTasa.label" default="Impuesto Tasa" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="impuestoTasa"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.comentario}">
						<dt><g:message code="notaDeCredito.comentario.label" default="Comentario" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="comentario"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.concepto}">
						<dt><g:message code="notaDeCredito.concepto.label" default="Concepto" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="concepto"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.aplicaciones}">
						<dt><g:message code="notaDeCredito.aplicaciones.label" default="Aplicaciones" /></dt>
						
							<g:each in="${notaDeCreditoInstance.aplicaciones}" var="a">
							<dd><g:link controller="aplicacion" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.dateCreated}">
						<dt><g:message code="notaDeCredito.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${notaDeCreditoInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.disponible}">
						<dt><g:message code="notaDeCredito.disponible.label" default="Disponible" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="disponible"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.fecha}">
						<dt><g:message code="notaDeCredito.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${notaDeCreditoInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.lastUpdated}">
						<dt><g:message code="notaDeCredito.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${notaDeCreditoInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.moneda}">
						<dt><g:message code="notaDeCredito.moneda.label" default="Moneda" /></dt>
						
							<dd><g:fieldValue bean="${notaDeCreditoInstance}" field="moneda"/></dd>
						
					</g:if>
				
					<g:if test="${notaDeCreditoInstance?.proveedor}">
						<dt><g:message code="notaDeCredito.proveedor.label" default="Proveedor" /></dt>
						
							<dd><g:link controller="proveedor" action="show" id="${notaDeCreditoInstance?.proveedor?.id}">${notaDeCreditoInstance?.proveedor?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${notaDeCreditoInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${notaDeCreditoInstance?.id}">
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
