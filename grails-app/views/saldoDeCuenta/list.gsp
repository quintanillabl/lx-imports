<%@ page import="com.luxsoft.impapx.tesoreria.SaldoDeCuenta" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'saldoDeCuenta.label', default: 'Saldos de cuentas')}" />
<title>Saldos de cuentas bancarias</title>
<r:require module="luxorForms"/>
</head>
<body>
	<content tag="header">
		<h3>Saldo de cuentas al : ${periodo.text()} (Periodo contable: ${rango})</h3>
 	</content>
	 
	<content tag="consultas">
 		<li>
 			<g:link action="list">Saldos</g:link>
 			<g:link controller="movimientoDeCuenta" action="list">Operaciones</g:link>
			
		</li>
 	</content>
 	
 	<content tag="operaciones">
 		<li>
 			<a href="#cambioDePeriodoDialog" data-toggle="modal">Cambiar periodo</a>
			<g:link action="actualizarSaldos" >Actualizar saldo</g:link>
		</li>
 		
 	</content> 
 	
 	<content tag="document">
 	<g:if test="${flash.message}">
		<bootstrap:alert class="alert-info">
			${flash.message}
		</bootstrap:alert>
	</g:if>
	<table
		class="table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th class="header">Folio</th>
				<th class="header">Cuenta</th>
				<th class="header">Mon</th>
				<th class="header">Inicial</th>
				<th class="header">Ingresos</th>
				<th class="header">Egresos</th>
				<th class="header">Saldo</th>
				<th class="header">Saldo MN</th>
				<th class="header">Año</th>
				<th class="header">Mes</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${saldoDeCuentaInstanceList}"
				var="row">
				<tr>
					<td>
						<g:link action="detalleDeMovimientos"
							id="${row.cuenta.id}" params="[periodo:periodo.text()]">
							${fieldValue(bean: row, field: "id")}
						</g:link></td>
					<td>
						<g:link action="detalleDeMovimientos"
							id="${row.cuenta.id}" params="[periodo:periodo.text()]">
							${fieldValue(bean: row, field: "cuenta")}
						</g:link>
					</td>
					<td>${fieldValue(bean: row, field: "cuenta.moneda")}</td>
					<td><lx:moneyFormat number="${row.saldoInicial}"/></td>
					<td><lx:moneyFormat number="${row.ingresos.abs()}"/></td>
					<td><lx:moneyFormat number="${row.egresos.abs()}"/></td>
					<td><lx:moneyFormat number="${row.saldoFinal}"/></td>
					<td><lx:moneyFormat number="${row.saldoFinalMN}"/></td>
					<td><g:formatNumber number="${row.year}" format="####"/></td>
					<td>${fieldValue(bean: row, field: "mes")}</td>
				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bootstrap:paginate total="${saldoDeCuentaInstanceTotal}" />
	</div>
	
	<div  id="cambioDePeriodoDialog" class="modal hide fade" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4>Periodo</h4>
		</div>
	
		<div class="modal-body">
		
			<fieldset>
			<g:form action="cambiarPeriodo" class="form-search">
				<label>Fecha final: </label>
				<g:field id="periodo" type="string" name="fecha" value="${(new Date()-1).format('dd/MM/yyyy') }"/>
				<div class="form-actions">
					<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
					<button id="actualizarBtn" type="submit" class="btn btn-primary">
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
 	$.datepicker.setDefaults( $.datepicker.regional[ "es" ] );
 	$("#periodo").datepicker({
    	 dateFormat: 'dd/mm/yy',
         showOtherMonths: true,
         selectOtherMonths: true,
         showOn:'both',
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

