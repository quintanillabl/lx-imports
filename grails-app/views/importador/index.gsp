

<%@ page import="com.luxsoft.impapx.Compra" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'compra.label', default: 'Compra')}" />
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
						<li><g:link action="importarCompras" controller="importador" 
								onclick="return myConfirm2(this,'Importar compras de SiipapEx','Importación de información');">
								<i class="icon-download"></i> Importar
							</g:link>
						</li>
						<li><g:link action="importarCompra" controller="importador"  data-target="#importarCompraDialog" data-toggle="modal">
								<i class="icon-download"></i> Importar
							</g:link>
						</li>
						
					</ul>
				</div>
			</div>

			<div class="span9">

				<div class="page-header">
					<h4>
						<g:message code="default.list.label" args="[entityName]" />
					</h4>
				</div>

				<g:if test="${flash.message}">
					<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<table class="table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<g:sortableColumn property="proveedor" title="${message(code: 'compra.fecha.label', default: 'Proveedor')}" />
							<g:sortableColumn property="depuracion" title="${message(code: 'compra.depuracion.label', default: 'Folio')}" />
							<g:sortableColumn property="fecha" title="${message(code: 'compra.fecha.label', default: 'Fecha')}" />
							<g:sortableColumn property="entrega" title="${message(code: 'compra.entrega.label', default: 'Entrega')}" />
							<g:sortableColumn property="depuracion" title="${message(code: 'compra.depuracion.label', default: 'Depuracion')}" />
							<g:sortableColumn property="comentario" title="${message(code: 'compra.comentario.label', default: 'Comentario')}" />
							
						</tr>
					</thead>
					<tbody>
						<g:each in="${compraInstanceList}" var="compraInstance">
							<tr>
								
						<td><g:link action="edit" id="${compraInstance.id}">${fieldValue(bean: compraInstance, field: "proveedor")}</g:link></td>
						<td>${fieldValue(bean: compraInstance, field: "folio")}</td>
						<td><g:formatDate date="${compraInstance.fecha}" format="dd/MM/yyyy"/></td>
						<td><g:formatDate date="${compraInstance.entrega}" format="dd/MM/yyyy"/></td>
						<td><g:formatDate date="${compraInstance.depuracion}" /></td>
						
						<td>${fieldValue(bean: compraInstance, field: "comentario")}</td>
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${compraInstanceTotal}" />
				</div>
			</div>

		</div>
	</div>
	
	<div class="modal hide fade" id="importarCompraDialog" tabindex=-1 role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="myModalLabel">Importar Compra desde Siipap</h4>
		</div>
		<div class="">
			<g:form controller="importador" action="importarCompra" name="importarForm">
				<input id="folio" class="span5" type="text" name="folio" value="" placeholder="Digite el folio a importar">
				<div class="form-actions">
					<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
					<button type="submit" class="btn btn-primary">
					<i class="icon-download"></i> Importar
					</button>
				</div>
			</g:form>
		</div>
	</div>

</body>
</html>
