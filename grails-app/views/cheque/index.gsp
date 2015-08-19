<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<!doctype html>
<html>
<head>
	<title>Cheques</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	 Bitácora de cheques generados (Periodo: ${session.periodoTesoreria.asPeriodoText()})
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
	            				<th>Id</th>
	            				<th>Cuenta</th>
	            				<th>Desc</th>
	            				<th>Pago</th>
	            				<th>Fecha</th>
	            				<th>Folio</th>
	            				<th>Importe</th>
	            				<th>Impreso</th>
	            				<th>Cancelación</th>
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
                            <label class="font-noraml">Seleccionar mes</label>
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
                aLengthMenu: [[50, 100, 150, 200, -1], [50, 100, 150, 200, "Todos"]],
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


