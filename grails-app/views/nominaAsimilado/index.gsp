<%@ page import="com.luxsoft.nomina.NominaAsimilado" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Nómina de asimilados</title>
</head>
<body>
 <content tag="header">
 	Comprobantes de pago a personas asimilados a sueldo
 </content>
 <content tag="periodo">
 	Periodo:${session.periodo.mothLabel()} 
 </content>
 <content tag="operaciones">
 </content>

 <content tag="grid">
 	<table id="grid" class="table table-striped table-hover table-bordered table-condensed">
 		<thead>
 			<tr>
 				<td>Id</td>
 				<td>Nombre</td>
 				<td>Fecha</td>
 				
 				
 				<td>Concepto</td>
 				<td>Percepciones</td>
 				<td>Deducciones</td>
 				
 			</tr>
 		</thead>
 		<tbody>
 			<g:each in="${nominaAsimiladoInstanceList}" var="row">
 				<tr>
 					<td><g:link action="edit" id="${row.id}">
 						${fieldValue(bean: row, field: "id")}
 						</g:link>
 					</td>
 					<td>${fieldValue(bean: row, field: "asimilado.nombre")}</td>
 					<td><lx:shortDate date="${row.fecha }"/></td>
 					
 					
 					<td>${fieldValue(bean: row, field: "concepto")}</td>
 					<td><lx:moneyFormat number="${row.percepciones }"/></td>
 					<td><lx:moneyFormat number="${row.deducciones }"/></td>
 					
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


<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<g:set var="entityName" value="${message(code: 'nominaAsimilado.label', default: 'NominaAsimilado')}" />
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
						<g:message code="nominaAsimilado.list.label" 
							default='Catálogo de NominaAsimilado' />
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
						
							<g:sortableColumn property="formaDePago" title="${message(code: 'nominaAsimilado.formaDePago.label', default: 'Forma De Pago')}" />
						
							<th><g:message code="nominaAsimilado.cfdi.label" default="Cfdi" /></th>
						
							<th><g:message code="nominaAsimilado.asimilado.label" default="Asimilado" /></th>
						
							<g:sortableColumn property="dateCreated" title="${message(code: 'nominaAsimilado.dateCreated.label', default: 'Date Created')}" />
						
							<g:sortableColumn property="fecha" title="${message(code: 'nominaAsimilado.fecha.label', default: 'Fecha')}" />
						
							<g:sortableColumn property="lastUpdated" title="${message(code: 'nominaAsimilado.lastUpdated.label', default: 'Last Updated')}" />
						
						</tr>
					</thead>
					<tbody>
					<g:each in="${nominaAsimiladoInstanceList}" status="i" var="nominaAsimiladoInstance">
						<tr>
						
							<td><g:link action="show" id="${nominaAsimiladoInstance.id}">${fieldValue(bean: nominaAsimiladoInstance, field: "formaDePago")}</g:link></td>
						
							<td>${fieldValue(bean: nominaAsimiladoInstance, field: "cfdi")}</td>
						
							<td>${fieldValue(bean: nominaAsimiladoInstance, field: "asimilado")}</td>
						
							<td><g:formatDate date="${nominaAsimiladoInstance.dateCreated}" /></td>
						
							<td><g:formatDate date="${nominaAsimiladoInstance.fecha}" /></td>
						
							<td><g:formatDate date="${nominaAsimiladoInstance.lastUpdated}" /></td>
						
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${nominaAsimiladoInstanceCount ?: 0}" />
				</div>
			</div>
		</div> <!-- end .row 2 -->

	</div>
	
</body>
</html>
