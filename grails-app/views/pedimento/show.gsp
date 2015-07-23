
<%@ page import="com.luxsoft.impapx.Pedimento" %>
<!doctype html>
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'pedimento.label', default: 'Pedimento')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row">
			<div class="col-md-3">
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
			
			<div class="col-md-9">

				<div class="page-header">
					<h3><g:message code="default.show.label" args="[entityName]" /></h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${pedimentoInstance?.fecha}">
						<dt><g:message code="pedimento.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${pedimentoInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${pedimentoInstance?.pedimento}">
						<dt><g:message code="pedimento.pedimento.label" default="Pedimento" /></dt>
						
							<dd><g:fieldValue bean="${pedimentoInstance}" field="pedimento"/></dd>
						
					</g:if>
				
					<g:if test="${pedimentoInstance?.dta}">
						<dt><g:message code="pedimento.dta.label" default="Dta" /></dt>
						
							<dd><g:fieldValue bean="${pedimentoInstance}" field="dta"/></dd>
						
					</g:if>
				
					<g:if test="${pedimentoInstance?.prevalidacion}">
						<dt><g:message code="pedimento.prevalidacion.label" default="Prevalidacion" /></dt>
						
							<dd><g:fieldValue bean="${pedimentoInstance}" field="prevalidacion"/></dd>
						
					</g:if>
				
					<g:if test="${pedimentoInstance?.tipoDeCambio}">
						<dt><g:message code="pedimento.tipoDeCambio.label" default="Tipo De Cambio" /></dt>
						
							<dd><g:fieldValue bean="${pedimentoInstance}" field="tipoDeCambio"/></dd>
						
					</g:if>
				
					<g:if test="${pedimentoInstance?.impuesto}">
						<dt><g:message code="pedimento.impuesto.label" default="Impuesto" /></dt>
						
							<dd><g:fieldValue bean="${pedimentoInstance}" field="impuesto"/></dd>
						
					</g:if>
				
					<g:if test="${pedimentoInstance?.impuestoTasa}">
						<dt><g:message code="pedimento.impuestoTasa.label" default="Impuesto Tasa" /></dt>
						
							<dd><g:fieldValue bean="${pedimentoInstance}" field="impuestoTasa"/></dd>
						
					</g:if>
				
					<g:if test="${pedimentoInstance?.dateCreated}">
						<dt><g:message code="pedimento.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${pedimentoInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${pedimentoInstance?.lastUpdated}">
						<dt><g:message code="pedimento.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${pedimentoInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${pedimentoInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${pedimentoInstance?.id}">
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
