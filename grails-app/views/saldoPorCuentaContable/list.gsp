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
		<h4>Catálogo de cuentas  : ${periodo}</h4>
	</content>
	
	
 	
 	<content tag="consultas">
 		
 		<li class="active">
 			<g:link class="list" action="list">
				<i class="icon-list icon-white"></i>
				<g:message code="default.list.label" args="[entityName]" />
			</g:link>
		</li>
		
		<li>
 			<g:link action="cierreAnual">
				<i class="icon-list icon-white"></i>
				Cierre anual
			</g:link>
		</li>
		
 	</content>
 	
	
 	<content tag="operaciones">
 		<li>
 			<g:link action="actualizarSaldos">Actualizar saldos</g:link>
		</li>
		<li>
			<g:render template="cambiarPeriodo" bean="${session.periodoContable}"/>
 		</li>
 		
 		<g:jasperReport
 			jasper="BalanzaDeComprobacion" 
 			format="PDF,HTML" 
 			name="Balanza"
 			buttonPosition="top">
						<g:hiddenField name="YEAR" value="${session.periodoContable.year}"/>
						<g:hiddenField name="MES" value="${session.periodoContable.month}"/>
		</g:jasperReport>
 		
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
		
		
		<div  id="cambioDePeriodoDialog" class="modal hide fade" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4>Fecha</h4>
		</div>
	
		<div class="modal-body">
		
			<fieldset>
			<g:form action="generarPoliza" class="form-search">
				<label>Fecha: </label>
				<g:field id="dia" type="string" name="fecha" value="${periodo.currentDate.text()}"/>
				<div class="form-actions">
					<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						<g:message code="default.button.update.label" default="Actualizar" />
					</button>
				</div>
				
			</g:form>
			</fieldset>
		
		</div>
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




