<%@ page import="com.luxsoft.impapx.tesoreria.MovimientoDeCuenta" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta')}" />
<title>Detalle de movimientos por cuenta</title>
<r:require module="luxorForms"/>
</head>
<body>
	<content tag="header">
		
		
 	</content>
	 
	<content tag="consultas">
 		<li>
 			<g:link action="list">Saldos</g:link>
		</li>
 	</content>
 	
 	<content tag="operaciones">
 		<li>
 			<a href="#cambioDePeriodoDialog" data-toggle="modal">Cambiar periodo</a>
			<%-- <g:link action="actualizarSaldoDeCuenta" params="[periodo:periodo.text()]">Actualizar saldo</g:link>--%>
			
		</li>
 		
 	</content> 
 	
 	<content tag="document">
 		<div class="accordion" id="saldoDeCuentaAccordion">
 			<div class="accordion-group">
 				<div class="accordion-heading">
 					<a class="accordion-toggle alert" data-toggle="collapse" data-parent="#saldoDeCuentaAccordion" href="#collapseOne">
 						Cuenta  :${saldoDeCuenta.cuenta} ( Periodo:  ${periodo.inicioDeMes().text()} - ${periodo.finDeMes().text()} )
 					</a>
 				</div>
 				<div id="collapseOne" class="accordion-body collapse ">
 					<div class="accordion-inner ">
 						
				Saldo Inicial :  <lx:moneyFormat number="${saldoDeCuenta.saldoInicial}"/><br>
				Ingresos :  <lx:moneyFormat number="${saldoDeCuenta.ingresos.abs()}"/><br>
				Egresos :  <lx:moneyFormat number="${saldoDeCuenta.egresos.abs()}"/><br>
				Saldo Final   :  <lx:moneyFormat number="${saldoDeCuenta.saldoFinal}"/><br>
 					</div>
 					<g:jasperReport
 						controller="saldoDeCuenta"
 						action="imprimirEstadoDeCuenta" 
 						jasper="EstadoDeCuentaBanco" 
 						format="PDF,HTML,XLS" 
 						name="Imprimir">
						<g:hiddenField name="cuentaId" value="${saldoDeCuenta.cuenta.id}"/>
						<g:hiddenField name="periodo" value="${periodo.text()}"/>
					</g:jasperReport>
 				</div>
 			</div>
 		</div>
 		
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
				<th class="header">Fecha</th>
				<th class="header">Concepto</th>
				<th class="header">Tipo</th>
				<th class="header">Ingreso</th>
				<th class="header">Egreso</th>
				<th class="header">Acumulado</th>
				<th class="header">Comentario</th>
			</tr>
		</thead>
		<tbody>
			<g:set var="acumulado" value="${saldoDeCuenta.saldoInicial}"/>
			<g:each in="${movimientos}"
				var="movimientoDeCuentaInstance">
				<g:set var="acumulado" value="${acumulado+=movimientoDeCuentaInstance.importe}"/>
				<tr>
					<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "id")}</td>
					<td><lx:shortDate date="${movimientoDeCuentaInstance.fecha }"/></td>
					<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "concepto")}</td>
					<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "tipo")}</td>
					<g:if test="${movimientoDeCuentaInstance.importe>0}">
						<td><lx:moneyFormat number="${movimientoDeCuentaInstance.importe }"/></td>
					</g:if>
					<g:else>
						<td></td>
					</g:else>
					<g:if test="${movimientoDeCuentaInstance.importe<0}">
						<td><lx:moneyFormat number="${movimientoDeCuentaInstance.importe.abs() }"/></td>
					</g:if>
					<g:else>
						<td></td>
					</g:else>
					<td><lx:moneyFormat number="${acumulado }"/></td>
					<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "comentario")}</td>

				</tr>
			</g:each>
		</tbody>
	</table>
	
	<div  id="cambioDePeriodoDialog" class="modal hide fade" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4>Periodo</h4>
		</div>
	
		<div class="modal-body">
		
			<fieldset>
			<g:form action="detalleDeMovimientos"  id="${saldoDeCuenta.cuenta.id}" class="form-search">
				<label>Fecha final: </label>
				<g:field id="periodo" type="string" name="fecha" value="${periodo.text()}"/>
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
 	$.datepicker.setDefaults( $.datepicker.regional[ "es" ] );
 	$("#periodo").datepicker({
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

