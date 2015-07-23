<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">

<g:set var="periodo" value="${session.periodoContable}"/>
<title><g:message code="polizasDeDiario.label" default="Póliza de diario"/></title>

<r:require module="dataTables"/>
</head>
<body>

	<content tag="header">
		<div class="accordion" id="saldoDeCuentaAccordion">
 			<div class="accordion-group">
 				<div class="accordion-heading">
 					<a class="accordion-toggle alert" data-toggle="collapse" data-parent="#saldoDeCuentaAccordion" href="#collapseOne">
 						Polizas Diario : ${poliza.folio}  ${poliza.fecha.text()} ${poliza.descripcion} 
 						Debe:  <lx:moneyFormat number="${poliza.debe}"/> 
 						Haber :  <lx:moneyFormat number="${poliza.haber}"/>
 						Cuadre :  <lx:moneyFormat number="${poliza.cuadre}"/>
 					</a>
 				</div>
 				<div id="collapseOne" class="accordion-body collapse ">
 					<div class="accordion-inner ">
					
					
					
 				</div>
 				
 				<g:jasperReport
 						jasper="PolizaContable" 
 						format="PDF,HTML,XLS" 
 						name="Imprimir">
						<g:hiddenField name="ID" value="${poliza.id}"/>
				</g:jasperReport>
 				
 				</div>
 			</div>
 		</div>
		
	</content>
	
 	<content tag="consultas">
 		<li>
 			<g:link class="" action="list">
				Polizas 
			</g:link>
		</li>
 	</content>
 	
 	<content tag="operaciones">
 		
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
					<th>Cuenta</th>
					<th>Concepto</th>
					<th>Debe</th>
					<th>Haber</th>
					<th>Descripcion</th>
					<th>Referencia</th>
					<th>Asiento</th>
					<th>Entidad</th>
					<th>Origen</th>
					
					
				</tr>
			</thead>
			<tbody>
				<g:each in="${partidas}" var="row">
					<tr>
						<td>${fieldValue(bean: row, field: "cuenta.clave")}</td>
						<td>${fieldValue(bean: row, field: "cuenta.descripcion")}</td>
						<td><lx:moneyFormat number="${row.debe}"/></td>
						<td><lx:moneyFormat number="${row.haber}"/></td>
						<td>${fieldValue(bean: row, field: "descripcion")}</td>
						<td>${fieldValue(bean: row, field: "referencia")}</td>
						<td>${fieldValue(bean: row, field: "asiento")}</td>
						<td>${fieldValue(bean: row, field: "entidad")}</td>
						<td><g:formatNumber number="${row.origen}" format="########"/></td>
					</tr>
				</g:each>
			</tbody>
			<tfoot>
				<tr>
					<th>Cuenta</th>
					<th>Concepto</th>
					<th>Debe</th>
					<th>Haber</th>
					<th>Descripcion</th>
					<th>Referencia</th>
					<th>Asiento</th>
					<th>Entidad</th>
					<th>Origen</th>
				</tr>
			</tfoot>
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
         "bPaginate": false,
         bJQueryUI: true,  
         sPaginationType: "full_numbers"
	}).columnFilter();
	/*
	$("#grid").dataTable({
			bJQueryUI: true,
            sPaginationType: "full_numbers"
		}).columnFilter();*/
});
</r:script>			
</body>
</html>



