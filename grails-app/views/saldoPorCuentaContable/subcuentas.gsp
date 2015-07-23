<%@ page import="com.luxsoft.impapx.contabilidad.SaldoPorCuentaContable" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">


<g:set var="periodo" value="${session.periodoContable}"/>
<title>Saldo por sub cuenta</title>

<r:require module="dataTables"/>
</head>
<body>

	<content tag="header">
		<h4>Detalle por sub cuenta  Cuenta: <g:link action="list">${saldo.cuenta}</g:link>
			<p>Periodo: ${saldo.mes} - ${saldo.year }  </p>
		</h4>
		<ul>
			<li>Saldo inicial:<lx:moneyFormat number="${saldo.saldoInicial }"/></li>
			<li>Debe:<lx:moneyFormat number="${saldo.debe }"/></li>
			<li>Haber:<lx:moneyFormat number="${saldo.haber }"/></li>
			<li>Saldo final:<lx:moneyFormat number="${saldo.saldoFinal }"/></li>
		</ul>
	</content>
	
	
 	
 	<content tag="consultas">
 		
 		<li class="active">
 			<g:link class="list" action="list">
				<i class="icon-list icon-white"></i>
				Saldos
			</g:link>
		</li>
		
 	</content>
 	
	
 	<content tag="operaciones">
 		<li>
 			<g:link action="actualizarSaldos">Actualizar saldo</g:link>
		</li>
		<li>
			<g:render template="cambiarPeriodo" bean="${session.periodoContable}"/>
 		</li>
 		
 		<li>
 			<g:jasperReport
 			jasper="BalanzaDeComprobacion" 
 			format="PDF" 
 			name="Balanza de comprobación"
 			buttonPosition="top">
						<g:hiddenField name="YEAR" value="${session.periodoContable.year}"/>
						<g:hiddenField name="MES" value="${session.periodoContable.month}"/>
			</g:jasperReport>
 		</li>
 		
 		<li>
 			<g:jasperReport
 			jasper="BalanceGeneral" 
 			format="PDF" 
 			name="Balance general"
 			buttonPosition="top">
						<g:hiddenField name="YEAR" value="${session.periodoContable.year}"/>
						<g:hiddenField name="MES" value="${session.periodoContable.month}"/>
			</g:jasperReport>
 		</li>
 		
 		<li>
 			<g:jasperReport
 			jasper="EstadoDeResultados" 
 			format="PDF" 
 			name="Estado de resultados"
 			buttonPosition="top">
						<g:hiddenField name="YEAR" value="${session.periodoContable.year}"/>
						<g:hiddenField name="MES" value="${session.periodoContable.month}"/>
			</g:jasperReport>
 		</li>
 		
 		<li>
 			<g:jasperReport
 			jasper="AuxiliarContablePorPeriodo" 
 			format="PDF" 
 			name="Auxiliar "
 			buttonPosition="top">
						<g:hiddenField name="YEAR" value="${session.periodoContable.year}"/>
						<g:hiddenField name="MES" value="${session.periodoContable.month}"/>
			</g:jasperReport>
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
						<td><g:link action="auxiliar" id="${row.id}" params="[year:row.year,month:row.mes,saldoPadre:saldo.id]">
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



