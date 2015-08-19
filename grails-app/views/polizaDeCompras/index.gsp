<!doctype html>
<html>
<head>
	<title>Pólizas de compras</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	Polizas de compras (${session.periodoContable.asPeriodoText()})
</content>
	
<content tag="subHeader">
	
</content>

<content tag="document">
	<div class="row">
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	        	
	        	<div class="ibox-title">
	        		 <button data-target="#periodoDialog" data-toggle="modal" class="btn btn-outline btn-success  dim">
	        		 	<i class="fa fa-calendar"></i> 
	        		</button>
	        		<lx:refreshButton/>
	        		<div class="btn-group">
	        		    <button type="button" name="operaciones"
	        		            class="btn btn-info dropdown-toggle" data-toggle="dropdown"
	        		            role="menu">
	        		            Reportes <span class="caret"></span>
	        		    </button>
	        		    <ul class="dropdown-menu">
	        		    	<li>
    		    	     		<g:jasperReport
    		    	     			jasper="BalanzaDeComprobacion" 
    		    	     			format="PDF,HTML" 
    		    	     			name="Balanza"
    		    	     			buttonPosition="top">
    		    	    						<g:hiddenField name="YEAR" value="${session.periodoContable.toYear()}"/>
    		    	    						<g:hiddenField name="MES" value="${session.periodoContable.toMonth()}"/>
    		    	    		</g:jasperReport>
	        		    	</li>
	        		    </ul>
	        		</div>
	        		<div class="btn-group">
	        		    <button type="button" name="operaciones"
	        		            class="btn btn-default dropdown-toggle" data-toggle="dropdown"
	        		            role="menu">
	        		            Operaciones <span class="caret"></span>
	        		    </button>
	        		    <ul class="dropdown-menu">
	    	 		 		<li>
	    	 		 			<a data-toggle="modal" data-target="#generarDialog"><i class="icon-plus "></i>Generar</a>
	    	 				</li>
    		    			
	        		    </ul>
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
	            </div>
	        </div>
	    </div>
	</div>

	<g:render template="/poliza/generarPoliza"/>
	
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
            $('#data_5 .input-group.date').bootstrapDP({
                format: 'dd/mm/yyyy',
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true,
                todayHighlight: true,

            });

		});
	</script>
	
</content>
	
</body>
</html>
