<%@ page import="com.luxsoft.impapx.cxc.CXCNota" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="CXPNota.list.label" default="CxC Notas de Crédito" /></title>
<r:require module="dataTables"/>
</head>
<body>
	<content tag="header">
		<h3>Notas de crédito</h3>
 	</content>
	<content tag="consultas">
 		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Notas registradas
			</g:link>
		</li>
 	</content>
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de nota</g:link></li>
 			
 	</content>
 	
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
		
		<table id="notasGrid" class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<td>Id</td>
					<td>Cliente</td>
					<td>Fecha</td>
					<td>Tipo</td>
					<td>Mon</td>
					<td>T.C</td>
					<td>Total</td>
					<td>Disponible</td>		
				</tr>
			</thead>
			<tbody>
				<g:each in="${CXCNotaInstanceList}" var="row">
					<tr>
						<td><g:link action="show" id="${row.id}">
							${fieldValue(bean: row, field: "id")}
							</g:link>
						</td>
						<td><g:link action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "cliente.nombre")}
							</g:link>
						</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						<td>${fieldValue(bean: row, field: "tipo")}</td>
						<td>${fieldValue(bean: row, field: "moneda")}</td>
						<td>${fieldValue(bean: row, field: "tc")}</td>
						<td><lx:moneyFormat number="${row.total }"/></td>
						<td><lx:moneyFormat number="${row.disponible }"/></td>	
					
					</tr>
				</g:each>
			</tbody>
			</table>
				
	</content>
 
 <r:script>
 $(function(){
	$("#notasGrid").dataTable({
		aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
        iDisplayLength: 50,
        "oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    },
    	"aoColumnDefs": [
        	{ "sType": "numeric","bSortable": true,"aTargets":[0] }
         ],
         "bPaginate": false  
	});
}); 
 </r:script>
	
</body>
</html>

