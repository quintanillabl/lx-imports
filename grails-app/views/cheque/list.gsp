<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'cheque.label', default: 'Cheque')}" />
<title>Cheques emitidos</title>
<r:require modules="luxorForms,dataTables"/>

</head>
<body>
	<content tag="header">
		<h3>Periodo : ${periodo.asPeriodoText()} </h3>
 	</content>
	 
	<content tag="consultas">
 		<li>
 			<g:link controller="movimientoDeCuenta" action="list">Operaciones</g:link>
 			<g:link action="list">Cheques</g:link>
		</li>
 	</content>
 	
 	<content tag="operaciones">
 		<li>
 			<a href="#cambioDePeriodoDialog" data-toggle="modal">Cambiar periodo</a>
		</li>
 		<li><g:link class="create" action="create">
				<i class="icon-plus"></i>
				Registrar cheque
			</g:link>
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
				<th class="header">Id</th>
				<th class="header">Cuenta</th>
				<th class="header">Desc</th>
				<th class="header">Pago</th>
				<th class="header">Fecha</th>
				<th class="header">Folio</th>
				<th class="header">Importe</th>
				<th class="header">Impreso</th>
				<th class="header">Cancelación</th>
				
			</tr>
		</thead>
		<tbody>
			<g:each in="${chequeInstanceList}"
				var="row">
				<tr>
					<td>
						<g:link action="show"
							id="${row.id}" >
							${row.id}
						</g:link>
					</td>
					<td>
						${fieldValue(bean: row, field: "cuenta.numero")}
					</td>
					<td>${fieldValue(bean: row, field: "cuenta.nombre")}</td>
					<td>${fieldValue(bean: row, field: "egreso.id")}</td>
					<td><lx:shortDate date="${row.egreso.fecha }"/></td>
					<td><g:formatNumber number="${row.folio}" format="####"/></td>
					<td><lx:moneyFormat number="${row.egreso.importe.abs()}"/></td>
					<td><lx:shortDate date="${row.fechaImpresion}"/></td>
					<td><g:if test="${!row.cancelacion }">
							<g:link action="cancelar" id="${row.id}" ><i class="icon-remove-sign"></i> Cancelar</g:link>
						</g:if>
						<g:else>
							<lx:shortDate date="${row.cancelacion}"/>	
						</g:else>
					</td>				
					
					
					
				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bootstrap:paginate total="${chequeInstanceTotal}" />
	</div>
	
	<div  id="cambioDePeriodoDialog" class="modal hide fade" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4>Periodo</h4>
		</div>
	
		<div class="modal-body">
		
			<fieldset>
			<g:form action="list" class="form-search">
				<label>Fecha final: </label>
				<g:field id="periodo" type="string" name="periodo" value="${periodo.format('dd/MM/yyyy') }"/>
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
         navigationAsDateFormat:false,
         showButtonPanel: true,
         changeMonth:true,
         changeYear:true,
         closeText:'Cerrar'
      });
 });
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
});
 </r:script>
	
</body>
</html>



