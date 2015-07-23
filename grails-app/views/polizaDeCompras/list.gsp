<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">

<g:set var="entityName"
	value="${message(code: 'poliza.label', default: 'Poliza')}" />
<g:set var="periodo" value="${session.periodoContable}"/>
<title><g:message code="polizasDeCompras.list.label" default="Pólizas de compras"/></title>

<r:require module="dataTables"/>
</head>
<body>

	<content tag="header">
		<h4>Polizas Compras : ${periodo}</h4>
	</content>
	
	<content tag="consultasPanelTitle">Pólizas</content>
 	
 	<content tag="consultas">
 		<g:render template="/poliza/polizas"/>
 	</content>
 	
	
 	<content tag="operaciones">
 		<li>
 			<a href="#cambioDePeriodoDialog" data-toggle="modal"><i class="icon-plus "></i>Generar</a>
		</li>
		<li>
			<g:render template="/poliza/cambiarPeriodo" bean="${session.periodoContable}"/>
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
					<th>Folio</th>
					<th>Fecha</th>
					<th>Descripción</th>
					<th>Debe</th>
					<th>Haber</th>
					<th>Cuadre</th>
					<th>Modificada</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${polizaInstanceList}" var="row">
					<tr>
						<td><g:link controller="poliza" action="edit" id="${row.id}">
							${fieldValue(bean: row, field: "folio")}
							</g:link>
						</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						<td><g:link action="mostrarPoliza" id="${row.id}">
							${fieldValue(bean: row, field: "descripcion")}</g:link>
						</td>
						<td><lx:moneyFormat number="${row.debe}"/></td>
						<td><lx:moneyFormat number="${row.haber}"/></td>
						<td><lx:moneyFormat number="${row.cuadre}"/></td>
						<td><g:formatDate date="${row.lastUpdated}"/></td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${polizaInstanceTotal}" />
		</div>
		
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



