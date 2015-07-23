
<%@ page import="com.luxsoft.cfd.FolioFiscal" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'folioFiscal.label', default: 'FolioFiscal')}" />
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
				
					<g:if test="${folioFiscalInstance?.anoAprobacion}">
						<dt><g:message code="folioFiscal.anoAprobacion.label" default="Ano Aprobacion" /></dt>
						
							<dd><g:fieldValue bean="${folioFiscalInstance}" field="anoAprobacion"/></dd>
						
					</g:if>
				
					<g:if test="${folioFiscalInstance?.asignacion}">
						<dt><g:message code="folioFiscal.asignacion.label" default="Asignacion" /></dt>
						
							<dd><g:formatDate date="${folioFiscalInstance?.asignacion}" /></dd>
						
					</g:if>
				
					<g:if test="${folioFiscalInstance?.folio}">
						<dt><g:message code="folioFiscal.folio.label" default="Folio" /></dt>
						
							<dd><g:fieldValue bean="${folioFiscalInstance}" field="folio"/></dd>
						
					</g:if>
				
					<g:if test="${folioFiscalInstance?.folioFinal}">
						<dt><g:message code="folioFiscal.folioFinal.label" default="Folio Final" /></dt>
						
							<dd><g:fieldValue bean="${folioFiscalInstance}" field="folioFinal"/></dd>
						
					</g:if>
				
					<g:if test="${folioFiscalInstance?.folioInicial}">
						<dt><g:message code="folioFiscal.folioInicial.label" default="Folio Inicial" /></dt>
						
							<dd><g:fieldValue bean="${folioFiscalInstance}" field="folioInicial"/></dd>
						
					</g:if>
				
					<g:if test="${folioFiscalInstance?.noAprobacion}">
						<dt><g:message code="folioFiscal.noAprobacion.label" default="No Aprobacion" /></dt>
						
							<dd><g:fieldValue bean="${folioFiscalInstance}" field="noAprobacion"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${folioFiscalInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${folioFiscalInstance?.id}">
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
