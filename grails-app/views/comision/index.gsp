<!doctype html>
<html>
<head>
	<title>Comisiones</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	 Comisiones bancarias (Periodo: ${session.periodoTesoreria.asPeriodoText()})
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
	        		<lx:printButton/>
	        		<lx:createButton/>
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
	            	<table id="grid" class="grid table table-responsive table-striped table-bordered table-hover">
	            		<thead>
	            			<tr>
	            				<th>Folio</th>
	            				<th>Fecha</th>
	            				<th>Cuenta</th>
	            				<th>Comisión</th>
	            				<th>Impuesto</th>
	            				<th>Comentario</th>
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${comisionInstanceList}" var="row">
	            				<tr>
	            					<td>
	            						<g:link action="show" id="${row.id}">
	            							${row.id}
	            						</g:link>
	            					</td>
	            					<td><lx:shortDate date="${row.fecha}"/></td>
	            					<td>${fieldValue(bean: row, field: "cuenta")}</td>
	            					<td><lx:moneyFormat number="${row.comision}"/></td>
	            					<td><lx:moneyFormat number="${row.impuesto}"/></td>
	            					<td>${fieldValue(bean: row, field: "comentario")}</td>
	            				</tr>
	            			</g:each>
	            		</tbody>
	            	</table>
	            	
	            </div>
	        </div>
	    </div>
	    
	</div>
	
	<div class="modal fade" id="periodoDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Cambiar periodo</h4>
				</div>
				<g:form action="cambiarPeriodo" class="form-horizontal" >
					<div class="modal-body">
						<div class="form-group" id="data_4">
                            <label class="font-noraml">Month select</label>
                            <div class="input-group date">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input type="text" class="form-control" name="fecha"
                                	value="${formatDate(date:session.periodoTesoreria,format:'dd/MM/yyyy')}">
                            </div>
						</div>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Aceptar" />
					</div>
				</g:form>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>
	
	
	<script type="text/javascript">
		$(function(){
 			$('#grid').dataTable({
                responsive: true,
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

		});
	</script>
	
</content>
	
</body>
</html>

