<%@ page import="com.luxsoft.impapx.cxp.NotaDeCredito" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'notaDeCredito.label', default: 'Nota de Crédito')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<r:require module="dataTables"/>
</head>
<body>
	
	<content tag="header">
		<h3>Notas de crédito</h3>
 	</content>
 	
 	<content tag="consultas">
 		<li>
 		<g:link controller="cuentaPorPagar" action="list">
			Cuentas por pagar
		</g:link>
		</li>
		<li>
		<g:link class="list" action="list">
			<i class="icon-list"></i>
			Notas de crédito
		</g:link>
		</li>	
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="" action="create">
				<i class="icon-plus "></i>
				Alta de Nota
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
					<th>id</th>
					<th>Proveedor</th>
					<th>Docto</th>
					<th>Fecha</th>
					<th>Mon</th>
					<th>TC</th>
					<th>Tipo</th>
					<th>Total</th>
					<th>Aplicado</th>
					<th>Disponible</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${notaDeCreditoInstanceList}" var="row">
					<tr>
						<td><g:link action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "id")}
							</g:link>
						</td>
						<td>${fieldValue(bean: row, field: "proveedor.nombre")}</td>
						<td>${fieldValue(bean: row, field: "documento")}</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						<td>${fieldValue(bean: row, field: "moneda")}</td>
						<td>${fieldValue(bean: row, field: "tc")}</td>
						<td>${fieldValue(bean: row, field: "concepto")}</td>
						<td><lx:moneyFormat number="${row.total}"/></td>
						<td><lx:moneyFormat number="${row.aplicado?:0.0}"/></td>
						<td><lx:moneyFormat number="${row.disponible}"/></td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${notaDeCreditoInstanceTotal}" />
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
        	{ "sType": "numeric","bSortable": true,"aTargets":[0] }
         ],
         "bPaginate": false  
	});
});
</r:script>			
</body>
</html>



