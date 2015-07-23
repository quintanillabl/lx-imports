<%@ page import="com.luxsoft.impapx.contabilidad.CuentaContable" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'cuentaContable.label', default: 'CuentaContable')}" />
<title><g:message code="cuentaContable.list.label" default="Cuentas contables" /></title>
<r:require module="dataTables"/>
</head>
<body>
	
	<content tag="header">
		<h3>Catálogo de cuentas contables</h3>
 	</content>
 	
 	<content tag="consultas">
 			
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="" action="create">
				<i class="icon-plus "></i>
				Alta de cuenta
			</g:link>
		</li>
 	</content>
 	
 	<content tag="document">	
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		
		<table id="grid"
			class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<th>Clave</th>
					<th>Descripción</th>
					<th>Tipo</th>
					<th>Sub Tipo</th>
					<th>Naturaleza</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${cuentaContableInstanceList}" var="row">
					<tr>
						<td><g:link action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "clave")}
							</g:link>
						</td>
						<td><g:link action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "descripcion")}
							</g:link>
						</td>
						
						<td>${fieldValue(bean: row, field: "tipo")}</td>
						<td>${fieldValue(bean: row, field: "subTipo")}</td>
						<td>${fieldValue(bean: row, field: "naturaleza")}</td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${cuentaContableInstanceTotal}" />
		</div>
	</content>
<r:script>
$(function(){
	$("#grid").dataTable({
		aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
        iDisplayLength: 50,
        "oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    },
    	"aoColumnDefs": [
        	//{ "sType": "numeric","bSortable": true,"aTargets":[0] }
         ],
         "bPaginate": false  
	});
});
</r:script>			
</body>
</html>



