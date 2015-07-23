
<%@ page import="com.luxsoft.impapx.Linea" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'linea.label', default: 'Linea')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<asset:stylesheet src="datatables/dataTables.css"/>
	<asset:javascript src="datatables/dataTables.js"/> 
</head>
<body>

	<div class="container">
		
		<div class="row">

			<div class="col-md-12">
				<div class="well well-sm">
					<h3><g:message code="default.list.label" args="[entityName]" /></h3>
					<g:if test="${flash.message}">
						<span class="label label-warning">${flash.message}</span>
					</g:if>
				</div>
			</div>
		</div><!-- end .row -->

		<div class="row toolbar-panel">
		    
		    <div class="col-md-4">
		    	<input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
		      </div>

		    <div class="btn-group">
		    	<lx:refreshButton/>
		        <lx:printButton/>
		        <lx:createButton/>
		        <lx:searchButton/>
		    </div>
		</div>

		<div class="row">
			<div class="col-md-12">
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
				<div class="pagination">
					<g:paginate total="${lineaInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->

	</div>
	
</body>
</html>
