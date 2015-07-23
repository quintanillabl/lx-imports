<%@ page import="com.luxsoft.impapx.contabilidad.Poliza" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">

<title><g:message code="diot.list.label" default="DIOT"/></title>
<r:require module="dataTables"/>
</head>
<body>
	
<div class="container-fluid">
	
	<div class="row-fluid">
		<div class="span12">
			<div class="alert ">
				<h2>DIOT Periodo: ${fecha?.text()}</h2>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			
			<div class="btn-toolbar">
				<div class="btn-group">
				<a href="#cambioDePeriodoDialog" class="btn btn-primary" data-toggle="modal">Procesar</a>
				<g:if test="${downloadFile}">
					<g:link class="btn btn-success" action="generarArchivo" params="[downloadFile:downloadFile]">Generar Archivo</g:link>
				</g:if>
				
			</div>
			
			</div>
				<ul class="nav nav-tabs" id="myTab">
					<li class="active"><a href="#analisis" data-toggle="tab">Análisis</a></li>
					<li class=""><a data-target="#diot" data-toggle="tab">DIOT</a></li>
				</ul>
				
				<div class="tab-content">
					<div class="tab-pane active" id="analisis">
						<g:render template="analisisGrid" model="['rows':rows]"/>
					</div> 
			
					<div class="tab-pane " id="diot">
						<g:render template="diotGrid" model="['diots':diots]"/>
					</div>
			
				</div>
			</div>
			
			<div  id="cambioDePeriodoDialog" class="modal hide fade" role="dialog" aria-hidden="true">
				<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4>Periodo</h4>
				</div>
				<div class="modal-body">
				<fieldset>
					<g:form action="generar" class="form-search">
						<label>Fecha: </label>
						<g:field id="fecha" type="string" name="fecha" value="${fecha?.text() }"/>
						<div class="form-actions">
							<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
						<button type="submit" class="btn btn-primary">
							<i class="icon-ok icon-white"></i>
							<g:message code="default.button.update.label" default="Ejecutar" />
						</button>
						</div>
					</g:form>
				</fieldset>
				</div>
			</div>
		
		
	</div>
	
</div> 	
 	
		
	
<r:script>
$(function(){
	$("#grid2").dataTable({
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



