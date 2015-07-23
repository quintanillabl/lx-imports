
<%@ page import="com.luxsoft.impapx.Cliente" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
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
						<g:message code="cliente.list.label" 
							default='Catálogo de Cliente' />
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
							<g:sortableColumn 
								property="nombre" 
								title="${message(code: 'cliente.nombre.label', default: 'Nombre')}" />
							<g:sortableColumn 
								property="rfc" 
								title="${message(code: 'cliente.rfc.label', default: 'Rfc')}" />
							<g:sortableColumn 
								property="email1" 
								title="${message(code: 'cliente.email1.label', default: 'Email')}" />
							<th>F.P</th>
							<th>Estado</th>
						
						</tr>
					</thead>
					<tbody>
					<g:each in="${clienteInstanceList}" status="i" var="clienteInstance">
						<tr>
						
							<td><g:link action="show" id="${clienteInstance.id}">${fieldValue(bean: clienteInstance, field: "nombre")}</g:link></td>
							<td>${fieldValue(bean: clienteInstance, field: "rfc")}</td>
							<td>${fieldValue(bean: clienteInstance, field: "email1")}</td>
							<td>${fieldValue(bean: clienteInstance, field: "formaDePago")}</td>
							<td>${fieldValue(bean: clienteInstance, field: "direccion.estado")}</td>
						
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${clienteInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->

	</div>
	
</body>
</html>
