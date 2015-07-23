<%@ page import="com.luxsoft.impapx.cxc.CXCPago" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="CXPPago.list.label" default="Cobros registrados" /></title>
<r:require modules="dataTables,filterpane"/>
</head>
<body>
	<content tag="header">
		<h3>Cobros registrados</h3>
 	</content>
	<content tag="consultas">
 		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Cobros registrados
			</g:link>
		</li>
		
 	</content>
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de cobro</g:link></li>
 		<li>
			<filterpane:filterButton text="Filtrar" appliedText="Cambiar filtro" class="btn"/>
		</li>	
 	</content>
 	
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
		
		<table id="pagosGrid" class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<td>Id</td>
					<td>Banco</td>
					<td>Cliente</td>
					<td>Fecha</td>
					<td>F.P</td>
					<td>Mon</td>
					<td>T.C</td>
					<td>Total</td>
					<td>Disponible MN</td>		
				</tr>
			</thead>
			<tbody>
				<g:each in="${CXCPagoInstanceList}" var="row">
					<tr>
						<td><g:link action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "id")}
							</g:link>
						</td>
						<td>${fieldValue(bean: row, field: "cuenta.banco.nombre")}</td>
						<td>${fieldValue(bean: row, field: "cliente.nombre")}</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						<td>${fieldValue(bean: row, field: "formaDePago")}</td>
						<td>${fieldValue(bean: row, field: "moneda")}</td>
						<td>${fieldValue(bean: row, field: "tc")}</td>
						<td><lx:moneyFormat number="${row.total }"/></td>
						<td><lx:moneyFormat number="${row.disponibleMN }"/></td>	
					
					</tr>
				</g:each>
			</tbody>
			</table>
			<div class="pagination">
				<bootstrap:paginate total="${ CXCPagoInstanceTotal}" max='30' />
			</div>
			<filterpane:filterPane	domain="com.luxsoft.impapx.cxc.CXCPago"
				dialog="true"
				title="Filtrar"
				filterProperties="fecha,formaDePago,referenciaBancaria"
				associatedProperties="cuenta.banco"
				additionalProperties="identifier"
			/>	
	</content>
 
 <r:script>
 $(function(){
	
	$("#pagosGrid").dataTable({
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
