<!doctype html>
<html>
<head>
	<title>Saldo de cuentas</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	Saldos de cuentas bancarias: ${session.periodoTesoreria.asPeriodoText()}
</content>
	

<content tag="document">
	<div class="row">
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	        	
	        	<div class="ibox-title">
	        	    <button data-target="#periodoDialog" data-toggle="modal" class="btn btn-outline btn-success  dim">
	        	    	<i class="fa fa-calendar"></i> 
	        	   	</button>
	        	   	
	        	   	<g:link action="actualizarSaldos" class="btn  btn-outline btn-primary" onclick="return confirm('Actualizar saldos para todas las cuentas?');">
	        	   		<i class="fa fa-cog"> Actualizar saldos</i>
	        	   	</g:link>
	        	   	<lx:printButton/>
	        	    <div class="ibox-tools">
	        	        <a class="collapse-link">
	        	            <i class="fa fa-chevron-up"></i>
	        	        </a>
	        	        <a class="close-link">
	        	            <i class="fa fa-times"></i>
	        	        </a>
	        	    </div>
	        	</div>
	            %{-- <lx:iboxTitle></lx:iboxTitle> --}%
	            <div class="ibox-content">
	            	<table id="grid" class="grid table table-responsive table-striped table-bordered table-hover">
	            		<thead>
	            			<tr>
	            				<th>Folio</th>
	            				<th>Cuenta</th>
	            				<th>Mon</th>
	            				<th>Inicial</th>
	            				<th>Ingresos</th>
	            				<th>Egresos</th>
	            				<th>Saldo</th>
	            				<th>Saldo MN</th>
	            				<th>Año</th>
	            				<th>Mes</th>
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${saldoDeCuentaInstanceList}" var="row">
	            				<tr>
	            					<td>
	            						<g:link action="detalleDeMovimientos"
	            							id="${row.cuenta.id}" params="[periodo:periodo.text()]">
	            							${fieldValue(bean: row, field: "id")}
	            						</g:link></td>
	            					<td>
	            						<g:link action="detalleDeMovimientos"
	            							id="${row.cuenta.id}" params="[periodo:periodo.text()]">
	            							${fieldValue(bean: row, field: "cuenta")}
	            						</g:link>
	            					</td>
	            					<td>${fieldValue(bean: row, field: "cuenta.moneda")}</td>
	            					<td><lx:moneyFormat number="${row.saldoInicial}"/></td>
	            					<td><lx:moneyFormat number="${row.ingresos.abs()}"/></td>
	            					<td><lx:moneyFormat number="${row.egresos.abs()}"/></td>
	            					<td><lx:moneyFormat number="${row.saldoFinal}"/></td>
	            					<td><lx:moneyFormat number="${row.saldoFinalMN}"/></td>
	            					<td><g:formatNumber number="${row.year}" format="####"/></td>
	            					<td>${fieldValue(bean: row, field: "mes")}</td>
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

            $('#data_4 .input-group.date').datepicker({
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
