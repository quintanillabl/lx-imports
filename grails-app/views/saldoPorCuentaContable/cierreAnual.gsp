<%@ page import="com.luxsoft.impapx.contabilidad.SaldoPorCuentaContable" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">

<g:set var="entityName"
	value="${message(code: 'saldoPorCuentaContable.label', default: 'SaldoPorCuentaContable')}" />
<g:set var="periodo" value="${session.periodoContable}"/>
<title>Saldo de Cuentas</title>

<r:require module="dataTables"/>
</head>
<body>

	<content tag="header">
		<h4>Saldos al cierre  : ${session?.periodoCierre.toYear()}</h4>
	</content>
	
	
 	
 	<content tag="consultas">
 		
 		<li >
 			<g:link class="list" action="list">
				<i class="icon-list icon-white"></i>
				Saldos
			</g:link>
		</li>
		
		<li class="active">
 			<g:link action="cierreAnual">
				<i class="icon-list icon-white"></i>
				Cierre anual
			</g:link>
		</li>
		
 	</content>
 	
	
 	<content tag="operaciones">
 		<li>
 			<g:link action="generarCierreAnual" params="[year:session?.periodoCierre?.toYear()]">Generar cierre</g:link>
		</li>
		<li>
 			<g:link action="actualizarCierreAnual" params="[year:session?.periodoCierre?.toYear()]">Actualizar cierre anual</g:link>
		</li>
		<div class="accordion-group">
 	<div class="accordion-heading">
 		<a class="accordion-toggle alert" data-toggle="collapse" data-parent="#saldoDeCuentaAccordion" href="#collapseOne">
 			Cambiar año 
 		</a>
 	</div>
 	<div id="collapseOne" class="accordion-body collapse ">
 		<div class="accordion-inner ">
 			<g:form action="cierreAnual">
 				<fieldset>
 					
 					<g:datePicker name="year" precision="year" years="[2013,2014,2015,2016]" value="${session?.periodoCierre }"/>
 					
 				</fieldset>
 				<div class="btn-group btn-group-vertical">
 					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						Actualizar
					</button>
					
 				</div>
 			</g:form>
 		</div>
 	</div>
 </div>
		
 		
 		
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
					<th>Descripción</th>
					<th>Saldo inicial</th>
					<th>Debe</th>
					<th>Haber</th>
					<th>Saldo final</th>
					<th>Año</th>
					<th>Mes</th>
					<th>Modificado</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${saldoPorCuentaContableInstanceList}" var="row">
					<tr id="${row.id}">
						<td><g:link action="subcuentas" id="${row.id}" >
								${fieldValue(bean: row, field: "cuenta.clave")}
							</g:link>
						</td>
						<td>${fieldValue(bean: row, field: "cuenta.descripcion")}</td>
						<td><lx:moneyFormat number="${row.saldoInicial}"/></td>
						<td><lx:moneyFormat number="${row.debe}"/></td>
						<td><lx:moneyFormat number="${row.haber}"/></td>
						<td><lx:moneyFormat number="${row.saldoFinal}"/></td>
						<td><g:formatNumber number="${row.year}" format="####"/></td>
						<td><g:formatNumber number="${row.mes}"  format="##"/></td>
						<td><g:formatDate date="${row.lastUpdated}"/></td>
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




