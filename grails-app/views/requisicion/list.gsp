<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName" value="${message(code: 'requisicion.label', default: 'Requisición')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<r:require module="dataTables"/>
</head>
<body>
 	<content tag="consultas">
 		<g:render template="/cuentaPorPagar/actions"/>
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="" action="create">
				<i class="icon-plus "></i>
				<g:message code="default.create.label" args="[entityName]" />
			</g:link>
		</li>
 	</content>
 	
 	<content tag="document">
 	
 		<h3><g:message code="requisicion.list.label" default="Requisiciones de pago"/></h3>
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		<table id="grid"
			class="table table-striped table-hover table-bordered table-condensed">
			<thead>
			<tr>
				<g:sortableColumn property="id"
					title="${message(code: 'requisicion.id.label', default: 'Folio')}" />
				<th class="header"><g:message
						code="requisicion.proveedor.label" default="Proveedor" /></th>
				<g:sortableColumn property="concepto"
					title="${message(code: 'requisicion.concepto.label', default: 'Concepto')}" />
				<g:sortableColumn property="fecha"
					title="${message(code: 'requisicion.fecha.label', default: 'Fecha')}" />
				<g:sortableColumn property="fechaDelPago"
					title="${message(code: 'requisicion.fechaDelPago.label', default: 'Fecha Del Pago')}" />
				<g:sortableColumn property="moneda"
					title="${message(code: 'requisicion.moneda.label', default: 'Moneda')}" />
				<g:sortableColumn property="tc"
					title="${message(code: 'requisicion.tc.label', default: 'Tc')}" />
				<g:sortableColumn property="total"
					title="${message(code: 'requisicion.total.label', default: 'Total')}" />
				<th>Pago</th>
				
			</tr>
			</thead>
			<tbody>
			<g:each in="${requisicionInstanceList}" var="requisicionInstance">
				<tr>
					<%--<td><g:link action="show" id="${requisicionInstance.id}">--%>
							<td><g:link action="edit" id="${requisicionInstance.id}">
							${fieldValue(bean: requisicionInstance, field: "id")}
						</g:link>
					</td>
					<td><g:link action="edit" id="${requisicionInstance.id}">
							${fieldValue(bean: requisicionInstance, field: "proveedor")}
						</g:link>
					</td>
					<td>${fieldValue(bean: requisicionInstance, field: "concepto")}</td>
					<td><lx:shortDate date="${requisicionInstance.fecha}"/></td>
					<td><lx:shortDate date="${requisicionInstance.fechaDelPago}"/></td>
					<td>${fieldValue(bean: requisicionInstance, field: "moneda")}</td>
					<td>${fieldValue(bean: requisicionInstance, field: "tc")}</td>
					<td><lx:moneyFormat number="${requisicionInstance.total}"/></td>
					<td>${fieldValue(bean: requisicionInstance, field: "pagoProveedor.id")}</td>
					
				</tr>
			</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${requisicionInstanceTotal}" />
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
