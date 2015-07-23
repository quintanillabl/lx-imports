<%@ page import="com.luxsoft.impapx.tesoreria.MovimientoDeCuenta" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta')}" />
<title>Tesorería</title>
<r:require module="dataTables"/>
</head>
<body>
	<content tag="header">
		<h3>Movimientos de cuentas Periodo: ${periodo.asPeriodoText()}</h3>
 	</content>
	<content tag="consultas">
 		<g:render template="/movimientoDeCuenta/consultas"/>
 	</content>
 	<content tag="operaciones">
 		<a href="#cambioDePeriodoDialog" data-toggle="modal">Cambiar periodo</a>
 		<li><g:link  action="depositar">Depositar</g:link></li>
 		<li><g:link  action="retirar">Retirar</g:link></li>
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
				<th>Id</th>
				<th>Banco</th>
				<th class="header">Num</th>
				<th class="header">Mon</th>
				<th class="header"><g:message code="movimientoDeCuenta.tc.label" default="TC" /></th>
				<th>Fecha</th>
				<th>Concepto</th>
				<th class="header"><g:message code="movimientoDeCuenta.importe.label" default="Importe" /></th>
				<th>Origen</th>
				<th class="header"><g:message code="movimientoDeCuenta.comentario.label" default="Comentario" /></th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${movimientoDeCuentaInstanceList}"
				var="movimientoDeCuentaInstance">
				<tr>
					<td><g:link action="show"
							id="${movimientoDeCuentaInstance.id}">
							${movimientoDeCuentaInstance.id}
						</g:link></td>
					<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "cuenta.nombre")}</td>
					<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "cuenta.numero")}</td>
					<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "moneda")}</td>

					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "tc")}
					</td>

					<td>
						<lx:shortDate date="${movimientoDeCuentaInstance.fecha }"/>
					</td>

					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "concepto")}
					</td>

					
					<td><lx:moneyFormat number="${movimientoDeCuentaInstance.importe }"/></td>
					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "origen")}
					</td>
					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "comentario")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<%-- <div class="pagination">
		<bootstrap:paginate total="${movimientoDeCuentaInstanceTotal}" />
	</div>
	--%>
	
	<div  id="cambioDePeriodoDialog" class="modal hide fade" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4>Periodo</h4>
		</div>
		<div class="modal-body">
			<fieldset>
			<g:form action="list" class="form-search">
				<label>Fecha: </label>
				<g:field id="periodo" type="string" name="periodo" value="${session.periodo.text() }"/>
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
		aLengthMenu: [[50, 100, 150, 200, -1], [50, 100, 150, 200, "Todos"]],
        iDisplayLength: 50,
        "oLanguage": {
      		"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    },
    	"aoColumnDefs": [
        	{ "sType": "numeric","bSortable": true,"aTargets":[0] }
        	//,{ "sType": "numeric","bSortable": true,"aTargets":[1] }
         ],
         "bPaginate": true  
	});
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
