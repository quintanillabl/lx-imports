<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="periodo" value="${session.periodoContable}"/>
<title><g:message code="diot.list.label" default="DIOT"/></title>
<r:require module="dataTables"/>
</head>
<body>

	<content tag="header">
	</content>
	
	<content tag="consultasPanelTitle">
	</content>
 	
 	<content tag="consultas">
 	</content>
 	
	
 	<content tag="operaciones">
 		<li>
 			<g:link action="generar" ><i class="icon-tasks"></i>Generar</g:link>
		</li>
		<li>
			<g:render template="cambiarPeriodo" bean="${session.periodoContable}"/>
 		</li>
 		
 	</content>
 	
 	<content tag="document">	
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		<g:render template="/shared/messagePanel" model="[beanInstance:polizaInstance]"/>
		<table id="grid"
			class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<th>ID</th>
					<th>Tipo</th>
					<th>Folio</th>
					<th>Fecha</th>
					<th>Clave</th>
					<th>Concepto</th>
					<th>Descripción</th>
					<th>Debe</th>
					<th>Haber</th>
					<th>Proveedor</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${rows}" var="row">
					<tr>
						<td>${row }</td>
						<td>${row }</td>
						<td>${row }</td>
						<td>${row }</td>
						<td>${row }</td>
						<td>${row }</td>
						<td>${row }</td>
						<td>${row }</td>
						<td>${row }</td>
						<td>${row }</td>
					</tr>
				</g:each>
			</tbody>
		</table>
		
		
		<div  id="createDialog" class="modal hide fade" role="dialog" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4>Alta de póliza genérica</h4>
		</div>
	
		<div class="modal-body">
		
			<fieldset>
				<g:form class="form-horizontal" action="create" >
						<fieldset>
							<f:with bean="${new Poliza() }">
								<f:field property="fecha">
									<g:field type="text" name="fecha" id="fecha" value="${new Date().text() }"/>
								</f:field>
								<f:field property="descripcion" input-class="input-xxmedium"/>
								
								
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									<g:message code="default.button.create.label" default="Create" />
								</button>
							</div>
						</fieldset>
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
	$("#fecha").datepicker({
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



