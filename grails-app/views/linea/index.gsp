
<%@ page import="com.luxsoft.impapx.Linea" %>
<!DOCTYPE html>
<html>
<head>
	
	<title>Líneas</title>
	<meta name="layout" content="catalogos">
</head>
<body>

<content tag="header">
	Catálogo de líneas
</content>

<content tag="grid">
	<table id="grid" class="table table-striped table-bordered table-condensed luxor-grid">
		<thead>
			<tr>
				<g:sortableColumn property="nombre" title="${message(code: 'linea.nombre.label', default: 'Nombre')}" />
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'linea.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'linea.lastUpdated.label', default: 'Last Updated')}" />
			
			</tr>
		</thead>
		<tbody>
			<g:each in="${lineaInstanceList}" status="i" var="lineaInstance">
				<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
				
					<td><g:link action="show" id="${lineaInstance.id}">${fieldValue(bean: lineaInstance, field: "nombre")}</g:link></td>
				
					<td><g:formatDate date="${lineaInstance.dateCreated}" /></td>
				
					<td><g:formatDate date="${lineaInstance.lastUpdated}" /></td>
				
				</tr>
			</g:each>
		</tbody>
	</table>
</content>

	
	
</body>
</html>
