

<%@ page import="com.luxsoft.cfd.FolioFiscal" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'folioFiscal.label', default: 'FolioFiscal')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li class="active"><g:link class="list" action="list">
								<i class="icon-list icon-white"></i>
								<g:message code="default.list.label" args="[entityName]" />
							</g:link></li>
						<li><g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link></li>
					</ul>
				</div>
			</div>

			<div class="span9">

				<div class="page-header">
					<h2>
						<g:message code="default.list.label" args="[entityName]" />
					</h2>
				</div>

				<g:if test="${flash.message}">
					<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<table class="table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							
							<g:sortableColumn property="anoAprobacion" title="${message(code: 'folioFiscal.anoAprobacion.label', default: 'Ano Aprobacion')}" />
							<g:sortableColumn property="serie" title="${message(code: 'folioFiscal.serie.label', default: 'Serie')}" />
						
							<g:sortableColumn property="asignacion" title="${message(code: 'folioFiscal.asignacion.label', default: 'Asignacion')}" />
						
							<g:sortableColumn property="folio" title="${message(code: 'folioFiscal.folio.label', default: 'Folio')}" />
						
							<g:sortableColumn property="folioFinal" title="${message(code: 'folioFiscal.folioFinal.label', default: 'Folio Final')}" />
						
							<g:sortableColumn property="folioInicial" title="${message(code: 'folioFiscal.folioInicial.label', default: 'Folio Inicial')}" />
						
							<g:sortableColumn property="noAprobacion" title="${message(code: 'folioFiscal.noAprobacion.label', default: 'No Aprobacion')}" />
						
							
						</tr>
					</thead>
					<tbody>
						<g:each in="${folioFiscalInstanceList}" var="folioFiscalInstance">
							<tr>
								
						<td><g:link action="show" id="${folioFiscalInstance.id}">${fieldValue(bean: folioFiscalInstance, field: "anoAprobacion")}</g:link></td>
					
						<td>${fieldValue(bean: folioFiscalInstance, field: "serie")}</td>
						<td><g:formatDate date="${folioFiscalInstance.asignacion}" /></td>
					
						<td>${fieldValue(bean: folioFiscalInstance, field: "folio")}</td>
					
						<td>${fieldValue(bean: folioFiscalInstance, field: "folioFinal")}</td>
					
						<td>${fieldValue(bean: folioFiscalInstance, field: "folioInicial")}</td>
					
						<td>${fieldValue(bean: folioFiscalInstance, field: "noAprobacion")}</td>
					
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${folioFiscalInstanceTotal}" />
				</div>
			</div>

		</div>
	</div>
</body>
</html>
