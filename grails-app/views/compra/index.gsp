
<%@ page import="com.luxsoft.impapx.Compra" %>
<!DOCTYPE html>
<html>
<head>
	<title>Compras</title>
	<meta name="layout" content="operaciones">
</head>
<body>

<content tag="header">
	Compras
</content>

<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>

<content tag="grid">
	<table id="grid" class="table table-striped table-bordered table-condensed luxor-grid" width="100%">
		<thead>
			<tr>
				<th><g:message code="compra.proveedor.label" default="Proveedor" /></th>
				<g:sortableColumn property="fecha" title="${message(code: 'compra.fecha.label', default: 'Fecha')}" />
			
				<g:sortableColumn property="entrega" title="${message(code: 'compra.entrega.label', default: 'Entrega')}" />
			
				<g:sortableColumn property="depuracion" title="${message(code: 'compra.depuracion.label', default: 'Depuracion')}" />
			
				<g:sortableColumn property="comentario" title="${message(code: 'compra.comentario.label', default: 'Comentario')}" />
			
				<g:sortableColumn property="moneda" title="${message(code: 'compra.moneda.label', default: 'Moneda')}" />
			
			</tr>
		</thead>
		<tbody>
			<g:each in="${compraInstanceList}" status="i" var="compraInstance">
				<tr>
				
					<td><g:link action="show" id="${compraInstance.id}">${fieldValue(bean: compraInstance, field: "proveedor")}</g:link></td>
				
					<td><g:formatDate date="${compraInstance.fecha}" /></td>
					<td><g:formatDate date="${compraInstance.entrega}" /></td>
					<td><g:formatDate date="${compraInstance.depuracion}" /></td>
					<td>${fieldValue(bean: compraInstance, field: "comentario")}</td>
					<td>${fieldValue(bean: compraInstance, field: "moneda")}</td>
				
				</tr>
			</g:each>
		</tbody>
	</table>
</content>

<content tag="searchService">
	<g:createLink action="search"/>
</content>
	
</body>
</html>
