
<%@ page import="com.luxsoft.impapx.cxp.Pago" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'pago.label', default: 'Pago')}" />
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
				
					<g:if test="${pagoInstance?.importe}">
						<dt><g:message code="pago.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${pagoInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.impuestos}">
						<dt><g:message code="pago.impuestos.label" default="Impuestos" /></dt>
						
							<dd><g:fieldValue bean="${pagoInstance}" field="impuestos"/></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.total}">
						<dt><g:message code="pago.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${pagoInstance}" field="total"/></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.tc}">
						<dt><g:message code="pago.tc.label" default="Tc" /></dt>
						
							<dd><g:fieldValue bean="${pagoInstance}" field="tc"/></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.impuestoTasa}">
						<dt><g:message code="pago.impuestoTasa.label" default="Impuesto Tasa" /></dt>
						
							<dd><g:fieldValue bean="${pagoInstance}" field="impuestoTasa"/></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.comentario}">
						<dt><g:message code="pago.comentario.label" default="Comentario" /></dt>
						
							<dd><g:fieldValue bean="${pagoInstance}" field="comentario"/></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.aplicaciones}">
						<dt><g:message code="pago.aplicaciones.label" default="Aplicaciones" /></dt>
						
							<g:each in="${pagoInstance.aplicaciones}" var="a">
							<dd><g:link controller="aplicacion" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
					<g:if test="${pagoInstance?.dateCreated}">
						<dt><g:message code="pago.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${pagoInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.disponible}">
						<dt><g:message code="pago.disponible.label" default="Disponible" /></dt>
						
							<dd><g:fieldValue bean="${pagoInstance}" field="disponible"/></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.fecha}">
						<dt><g:message code="pago.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${pagoInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.lastUpdated}">
						<dt><g:message code="pago.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${pagoInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.moneda}">
						<dt><g:message code="pago.moneda.label" default="Moneda" /></dt>
						
							<dd><g:fieldValue bean="${pagoInstance}" field="moneda"/></dd>
						
					</g:if>
				
					<g:if test="${pagoInstance?.proveedor}">
						<dt><g:message code="pago.proveedor.label" default="Proveedor" /></dt>
						
							<dd><g:link controller="proveedor" action="show" id="${pagoInstance?.proveedor?.id}">${pagoInstance?.proveedor?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${pagoInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${pagoInstance?.id}">
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
