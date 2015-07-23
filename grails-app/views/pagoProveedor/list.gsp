<%@ page import="com.luxsoft.impapx.tesoreria.PagoProveedor" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="pagoProveedor.list.label" default="Pagos a proveedores"/></title>
<r:require module="dataTables"/>
</head>
<body>
	
	<content tag="header">
		<h3>Tesorería - Pagos a proveedores</h3>
 	</content>
	<content tag="consultas">
 		<g:render template="/movimientoDeCuenta/consultas"/>
 	</content>
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de Pago</g:link></li>
 	</content>
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
 		<table id="grid" class="table table-striped table-hover table-bordered table-condensed">
			<thead>
			<tr>
				<g:sortableColumn property="id" title="${"Id"}" />
				<g:sortableColumn property="cuenta.nombre" title="${"Cuenta"}" />
				<g:sortableColumn property="requisicion.id" title="${"Req"}" />
				
				<th class="header"><g:message code="movimientoDeCuenta.tc.label" default="TC" /></th>
				<g:sortableColumn property="fecha" title="${"Fecha"}" />
				
			
				<th class="header"><g:message code="movimientoDeCuenta.importe.label" default="Importe" /></th>
				<th class="header"><g:message code="movimientoDeCuenta.pago.label" default="CxP" /></th>
			</tr>
			</thead>
			<tbody>
			<g:each in="${pagoProveedorInstanceList}"
				var="pago">
				<tr>
					<td><g:link action="show"
							id="${pago.id}">
							${fieldValue(bean: pago, field: "id")}
						</g:link>
					</td>
					<td>${fieldValue(bean: pago, field: "egreso.cuenta.nombre")} - ${fieldValue(bean: pago, field: "egreso.cuenta.numero")}
					</td>
					
					<td><g:link controller="requisicion" action="show"
							id="${pago.requisicion.id}">
							${fieldValue(bean: pago, field: "requisicion.id")} (${fieldValue(bean: pago, field: "requisicion.proveedor.nombre")})
						</g:link>
					</td>
					<td>${fieldValue(bean: pago, field: "egreso.moneda")}</td>
					<td><lx:shortDate date="${pago.fecha }"/></td>
					
					
					<td><lx:moneyFormat number="${pago.egreso.importe }"/></td>
					<td>${fieldValue(bean: pago, field: "comentario")}</td>
				</tr>
			</g:each>
		</tbody>
			
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${pagoProveedorInstanceTotal}" />
		</div>
		
 	</content>

<r:script>
$(function(){
	$("#grid").dataTable({
		//aLengthMenu: [[50, 100, 150, 200, -1], [50, 100, 150, 200, "Todos"]],
        //iDisplayLength: 50,
        "oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    },
    	"aoColumnDefs": [
        	{ "sType": "numeric","bSortable": true,"aTargets":[0] }
        	//,{ "sType": "numeric","bSortable": true,"aTargets":[1] }
         ],
         "bPaginate": true  
	});
});
</r:script>
	
</body>

</html>


