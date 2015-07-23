
<%@ page import="com.luxsoft.impapx.RequisicionDet" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'requisicionDet.label', default: 'RequisicionDet')}" />
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
				
					<g:if test="${requisicionDetInstance?.documento}">
						<dt><g:message code="requisicionDet.documento.label" default="Documento" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="documento"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.factura}">
						<dt><g:message code="requisicionDet.factura.label" default="Factura" /></dt>
						
							<dd><g:link controller="cuentaPorPagar" action="show" id="${requisicionDetInstance?.factura?.id}">${requisicionDetInstance?.factura?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.dateCreated}">
						<dt><g:message code="requisicionDet.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${requisicionDetInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.fechaDocumento}">
						<dt><g:message code="requisicionDet.fechaDocumento.label" default="Fecha Documento" /></dt>
						
							<dd><g:formatDate date="${requisicionDetInstance?.fechaDocumento}" /></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.ietu}">
						<dt><g:message code="requisicionDet.ietu.label" default="Ietu" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="ietu"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.importe}">
						<dt><g:message code="requisicionDet.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.impuestos}">
						<dt><g:message code="requisicionDet.impuestos.label" default="Impuestos" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="impuestos"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.lastUpdated}">
						<dt><g:message code="requisicionDet.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${requisicionDetInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.requisicion}">
						<dt><g:message code="requisicionDet.requisicion.label" default="Requisicion" /></dt>
						
							<dd><g:link controller="requisicion" action="show" id="${requisicionDetInstance?.requisicion?.id}">${requisicionDetInstance?.requisicion?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.retencionFlete}">
						<dt><g:message code="requisicionDet.retencionFlete.label" default="Retencion Flete" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="retencionFlete"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.retencionHonorarios}">
						<dt><g:message code="requisicionDet.retencionHonorarios.label" default="Retencion Honorarios" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="retencionHonorarios"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.retencionISR}">
						<dt><g:message code="requisicionDet.retencionISR.label" default="Retencion ISR" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="retencionISR"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.total}">
						<dt><g:message code="requisicionDet.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="total"/></dd>
						
					</g:if>
				
					<g:if test="${requisicionDetInstance?.totalDocumento}">
						<dt><g:message code="requisicionDet.totalDocumento.label" default="Total Documento" /></dt>
						
							<dd><g:fieldValue bean="${requisicionDetInstance}" field="totalDocumento"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${requisicionDetInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${requisicionDetInstance?.id}">
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
