
<%@ page import="com.luxsoft.lx.contabilidad.ProcesadorDePoliza" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'procesadorDePoliza.label', default: 'ProcesadorDePoliza')}" />
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
						<g:message code="procesadorDePoliza.list.label" 
							default='Catálogo de ProcesadorDePoliza' />
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
						
							<g:sortableColumn property="subTipo" title="${message(code: 'procesadorDePoliza.subTipo.label', default: 'Sub Tipo')}" />
						
							<g:sortableColumn property="descripcion" title="${message(code: 'procesadorDePoliza.descripcion.label', default: 'Descripcion')}" />
						
							<g:sortableColumn property="service" title="${message(code: 'procesadorDePoliza.service.label', default: 'Service')}" />
						
							<g:sortableColumn property="tipo" title="${message(code: 'procesadorDePoliza.tipo.label', default: 'Tipo')}" />
						
							<g:sortableColumn property="label" title="${message(code: 'procesadorDePoliza.label.label', default: 'Label')}" />
						
						</tr>
					</thead>
					<tbody>
					<g:each in="${procesadorDePolizaInstanceList}" status="i" var="procesadorDePolizaInstance">
						<tr>
						
							<td><g:link action="show" id="${procesadorDePolizaInstance.id}">${fieldValue(bean: procesadorDePolizaInstance, field: "subTipo")}</g:link></td>
						
							<td>${fieldValue(bean: procesadorDePolizaInstance, field: "descripcion")}</td>
						
							<td>${fieldValue(bean: procesadorDePolizaInstance, field: "service")}</td>
						
							<td>${fieldValue(bean: procesadorDePolizaInstance, field: "tipo")}</td>
						
							<td>${fieldValue(bean: procesadorDePolizaInstance, field: "label")}</td>
						
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${procesadorDePolizaInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->

	</div>
	
</body>
</html>
