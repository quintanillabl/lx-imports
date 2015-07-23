<%@ page import="com.luxsoft.impapx.cxp.Pago" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="Pago.list.label" default="Pagos registrados" /></title>
<r:require module="dataTables"/>
</head>
<body>
	<content tag="header">
		<h3>Pagos registrados</h3>
 	</content>
	<content tag="consultas">
		<li><g:link class="list" controller="cuentaPorPagar">
			<i class="icon-list"></i>
			CxP
			</g:link>
		</li>
 		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Pagos
			</g:link>
		</li>
 	</content>
 	<content tag="operaciones">
 		<li><g:link  action="create" class="disabled">Alta de pago</g:link></li>
 	</content>
 	
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
		
		<table id="grid" class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<td>Id</td>
					<td>Proveedor</td>
					<td>Fecha</td>
					<td>Mon</td>
					<td>Total</td>
					<td>Disponible</td>		
				</tr>
			</thead>
			<tbody>
				<g:each in="${pagoInstanceList}" var="row">
					<tr>
						<td><g:link action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "id")}
							</g:link>
						</td>
						<td>${fieldValue(bean: row, field: "proveedor.nombre")}</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						<td>${fieldValue(bean: row, field: "moneda")}</td>
						<td><lx:moneyFormat number="${row.total }"/></td>
						<td><lx:moneyFormat number="${row.disponible }"/></td>
					</tr>
				</g:each>
			</tbody>
			</table>
			<div class="pagination">
				<bootstrap:paginate total="${pagoInstanceTotal}" />
			</div>
				
	</content>
 
 <r:script>
 $(function(){
	$("#grid2").dataTable({
		aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
        iDisplayLength: 50,
        "oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    },
    	"aoColumnDefs": [
        	{ "sType": "numeric","bSortable": true,"aTargets":[0] }
         ],
         "bPaginate": true  
	});
});
 
 
 
 </r:script>
	
</body>
</html>