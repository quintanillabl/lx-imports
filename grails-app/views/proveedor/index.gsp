
<%@ page import="com.luxsoft.impapx.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'proveedor.label', default: 'Proveedor')}" />
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
						<g:message code="proveedor.list.label" 
							default='Catálogo de Proveedor' />
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
							<th>Nombre</th>
							<th>RFC</th>
							<th>Email</th>
							<th>Pais</th>
							<th>Modificado</th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${proveedorInstanceList}" status="i" var="proveedorInstance">
						<tr>
							<td>
								<g:link action="show" id="${proveedorInstance.id}">
									${fieldValue(bean: proveedorInstance, field: "nombre")}
								</g:link>
							</td>
							<td>${fieldValue(bean: proveedorInstance, field: "rfc")}</td>
							<td>${fieldValue(bean: proveedorInstance, field: "correoElectronico")}</td>
							<td>${fieldValue(bean: proveedorInstance, field: "direccion.pais")}</td>
							<td>${formatDate(date: proveedorInstance.lastUpdated)}</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${proveedorInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->

	</div>
	
</body>
</html>
