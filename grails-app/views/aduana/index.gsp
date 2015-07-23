
<%@ page import="com.luxsoft.impapx.Aduana" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'aduana.label', default: 'Aduana')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<asset:stylesheet src="datatables/dataTables.css"/>
	<asset:javascript src="datatables/dataTables.js"/> 
</head>
<body>

	<div class="container">
		
		<div class="row">

			<div class="col-md-12">
				<div class="alert alert-info">
					<h3>
						<g:message code="aduana.list.label" 
							default='Catálogo de Aduana' />
					</h3>
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
						
							<g:sortableColumn property="nombre" title="${message(code: 'aduana.nombre.label', default: 'Nombre')}" />
						
							<th><g:message code="aduana.direccion.label" default="Direccion" /></th>
						
						</tr>
					</thead>
					<tbody>
					<g:each in="${aduanaInstanceList}" status="i" var="aduanaInstance">
						<tr>
						
							<td><g:link action="show" id="${aduanaInstance.id}">${fieldValue(bean: aduanaInstance, field: "nombre")}</g:link></td>
						
							<td>${fieldValue(bean: aduanaInstance, field: "direccion")}</td>
						
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${aduanaInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->

	</div>
	
</body>
</html>
