<%@ page import="com.luxsoft.impapx.tesoreria.PagoProveedor" %>
<!doctype html>
<html>
<head>
	<title><g:message code="diot.list.label" default="DIOT"/></title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	DIOT Periodo: ${session.periodoContable.asPeriodoText()}
</content>
	

<content tag="document">
	<div class="row">
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	        	
	        	<div class="ibox-title">
	        		<button data-target="#periodoDialog" data-toggle="modal" class="btn btn-outline btn-success  dim">
	        		 	<i class="fa fa-calendar"></i> 
	        		</button>
	        		<div class="btn-group">
	        		    <g:link action="generar" class="btn btn-outline btn-primary">
	        		    	<i class="fa fa-cog"></i> Generar
	        		    </g:link>
	        		    <g:if test="${downloadFile}">
	        		    	<g:link class="btn btn-outline btn-success" action="generarArchivo" params="[downloadFile:downloadFile]">Generar Archivo</g:link>
	        		    </g:if>
	        		</div>
	        	    <div class="ibox-tools">
	        	        <a class="collapse-link">
	        	            <i class="fa fa-chevron-up"></i>
	        	        </a>
	        	        <a class="close-link">
	        	            <i class="fa fa-times"></i>
	        	        </a>
	        	    </div>
	        	</div>
	            
	            <div class="ibox-content">
	            	<div>
	            		<ul class="nav nav-tabs" id="myTab">
	            			<li class="active"><a href="#analisis" data-toggle="tab">Análisis</a></li>
	            			<li class=""><a data-target="#diot" data-toggle="tab">DIOT</a></li>
	            		</ul>
	            		
	            		<div class="tab-content">
	            			<div class="tab-pane active" id="analisis">
	            				<div style=" overflow-x: scroll">
	            						<g:render template="analisisGrid" model="['rows':rows]"/>
	            				</div>
	            				
	            				
	            				
	            			</div> 
	            			<div class="tab-pane " id="diot">
	            				<g:render template="diotGrid" model="['diots':diots]"/>
	            			</div>
	            		</div>
	            	</div>
	            </div>

	        </div>
	    </div>
	</div>
	
	
	<g:render template="/poliza/cambiarPeriodo"/>
	
	<script type="text/javascript">
		$(function(){
 			$('#grid').dataTable({
                responsive: true,
                aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
                "language": {
					"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    		},
	    		"dom": 'T<"clear">lfrtip',
	    		"tableTools": {
	    		    "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
	    		},
	    		//"scrollX": true,
	    		"order": []
            });
            $('#data_4 .input-group.date').bootstrapDP({
                minViewMode: 1,
                format: 'dd/mm/yyyy',
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true,
                todayHighlight: true,

            });
      //       $('#analisis').slimScroll({
      //   		height: '250px'
    		// });

		});
	</script>
	
</content>
	
</body>
</html>

%{-- 

	
	
			
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
		
		
	 --}%
		
	




