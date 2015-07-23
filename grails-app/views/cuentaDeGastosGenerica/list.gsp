<%@ page import="com.luxsoft.impapx.cxp.CuentaDeGastosGenerica" %>
<%@ page import="util.Periodo" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">

<g:set var="entityName"	value="${message(code: 'cuentaDeGastosGenerica.label', default: 'Cuentas de gastos genéricas')}" />


<title><g:message code="cuentaDeGastosGenerica.list.label" default="Cuentas de gastos genéricas"/></title>

<r:require module="dataTables"/>
</head>
<body>

	<content tag="header">
		<h4>Cuentas de gastos genéricas periodo: ${periodo}</h4>
	</content>
	
	<content tag="consultasPanelTitle"></content>
 	
 	<content tag="consultas">
 	</content>
 	
	
 	<content tag="operaciones">
		<li>
			<g:link action="create">Alta</g:link>
 		</li>
 		
 	</content>
 	
 	<content tag="document">	
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		
		<g:render template="/shared/messagePanel" model="[beanInstance:beanInstance]"/>
		
		<table id="grid"
			class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<th>Folio</th>
					<th>Fecha</th>
					<th>Proveedor</th>
					<th>Importe</th>
					<th>Impuestos</th>
					<th>Total</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${rows}" var="row">
					<tr id="${row.id}">
						<td><g:link action="edit" id="${row.id}">
							${row.id}
							</g:link>
						</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						<td>${fieldValue(bean: row, field: "proveedor")}</td>
						<td><lx:moneyFormat number="${row.importe}"/></td>
						<td><lx:moneyFormat number="${row.impuestos}"/></td>
						<td><lx:moneyFormat number="${row.total}"/></td>
					</tr>
				</g:each>
			</tbody>
		</table>
		
		
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
        	{ "sType": "numeric","bSortable": true,"aTargets":[0] }
         ],
         "bPaginate": false  
	});
	
});
</r:script>			
</body>
</html>



