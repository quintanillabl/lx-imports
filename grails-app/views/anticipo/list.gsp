<%@ page import="com.luxsoft.impapx.cxp.Anticipo" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title>Anticipos</title>
<r:require modules="dataTables,luxorTableUtils"/>
</head>
<body>
	<content tag="header">
		<h3>Anticipos periodo:  ${periodo}</h3>
 	</content>
 	
	<content tag="consultas">
 		<li><g:link class="list" action="list">
			<i class="icon-list"></i>
			Lista de anticipos
			</g:link>
		</li>
 	</content>
 	<content tag="operaciones">
 		<li>
			<g:render template="/poliza/cambiarPeriodo" bean="${session.periodoContable}"/>
 		</li>
 	</content>
 	
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
		
		<table id="anticiposGrid" class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<td>Folio</td>
					<td>Proveedor</td>
					<td>Fecha</td>
					<td>Requisición</td>
					<td>Concepto</td>
					<td>Complemento</td>
					<td>Total</td>
					<td>Gastos</td>
					<td>Impuestos</td>
					<td>Diferencia</td>
					<td>Sobrante</td>
					<td>Modificado</td>		
				</tr>
			</thead>
			<tbody>
				<g:each in="${anticipos}" var="row">
					<tr>
						<td><g:link action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "id")}
							</g:link>
						</td>
						<td>${fieldValue(bean: row, field: "requisicion.proveedor.nombre")}</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						
						<td>
							<g:link controller="requisicion" action="show" target="_blank"
								id="${row.requisicion.id}">
							${fieldValue(bean: row, field: "requisicion.id")}
							</g:link>
						</td>
						<td>${fieldValue(bean: row, field: "requisicion.concepto")}</td>
						<td>${fieldValue(bean: row, field: "complemento.id")}</td>
						<td><lx:moneyFormat number="${row.total }"/></td>
						<td><lx:moneyFormat number="${row.gastosDeImportacion }"/></td>
						<td><lx:moneyFormat number="${row.impuestosAduanales }"/></td>
						<td><lx:moneyFormat number="${row.diferencia }"/></td>
						<td><lx:moneyFormat number="${row?.sobrante?.importe }"/></td>
						
						<td>${fieldValue(bean: row, field: "lastUpdated")}</td>
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
        	{ "sWidth": "40px","sType": "numeric","bSortable": true,"aTargets":[0] }
         ],
         "bPaginate": false,
         bJQueryUI: true,  
         sPaginationType: "full_numbers",
         "bAutoWidth":false
	}).columnFilter();
	
	$("#dia").datepicker({
    	 dateFormat: 'dd/mm/yy',
         showOtherMonths: true,
         selectOtherMonths: true,
         showOn:'focus',
         showAnim:'fold',
         minDate:'01/10/2012',
         maxDate:'31/12/2015',
         navigationAsDateFormat:false,
         showButtonPanel: true,
         changeMonth:true,
         changeYear:true,
         closeText:'Cerrar'
      });
	
 });
 
 
 </r:script>
	
</body>
</html>