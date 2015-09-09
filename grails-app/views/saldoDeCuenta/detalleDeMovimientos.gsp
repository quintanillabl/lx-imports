<%@ page import="com.luxsoft.impapx.tesoreria.MovimientoDeCuenta" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta')}" />
<title>Detalle de movimientos por cuenta</title>
</head>
<body>

<content tag="header">
	Cuenta  :${saldoDeCuenta.cuenta.numero} ( Periodo:  ${session.periodoTesoreria.asPeriodoText()})
	
</content>

<content tag="subHeader">
	<ol class="breadcrumb">
	    <li><g:link action="index">Saldos</g:link></li>
	    <li class="active"><strong>Movimientos</strong></li>
	</ol>
</content>
<content tag="document">
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">

					<div class="col-md-3">
					    <div class="input-group">
					            <span class="input-group-btn">
					                <button data-target="#periodoDialog" data-toggle="modal" class="btn  btn-outline btn-success ">
					            <i class="fa fa-calendar"></i> 
					        </button>
					            </span>
					        <input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
					    </div>
					</div>
					Saldo Inicial :  <lx:moneyFormat number="${saldoDeCuenta.saldoInicial}"/>
					Ingresos :  <lx:moneyFormat number="${saldoDeCuenta.ingresos.abs()}"/>
					Egresos :  <lx:moneyFormat number="${saldoDeCuenta.egresos.abs()}"/>
					Saldo Final   :  <lx:moneyFormat number="${saldoDeCuenta.saldoFinal}"/>
 					<g:jasperReport
 						controller="saldoDeCuenta"
 						action="imprimirEstadoDeCuenta" 
 						jasper="EstadoDeCuentaBanco" 
 						format="PDF,HTML,XLS" 
 						name="Imprimir">
						<g:hiddenField name="cuentaId" value="${saldoDeCuenta.cuenta.id}"/>
						<g:hiddenField name="periodo" value="${periodo.text()}"/>
						<g:hiddenField name="COMPANY" value="${session.empresa.nombre}"/>
					</g:jasperReport>


				</div>
			    <div class="ibox-content">
			 		
			 		<table
			 			class="table table-striped table-hover table-bordered table-condensed">
			 			<thead>
			 				<tr>
			 					<th>Folio</th>
			 					<th>Fecha</th>
			 					<th>Concepto</th>
			 					<th>Tipo</th>
			 					<th>Ingreso</th>
			 					<th>Egreso</th>
			 					<th>Acumulado</th>
			 					<th>Comentario</th>
			 				</tr>
			 			</thead>
			 			<tbody>
			 				<g:set var="acumulado" value="${saldoDeCuenta.saldoInicial}"/>
			 				<g:each in="${movimientos}"
			 					var="movimientoDeCuentaInstance">
			 					<g:set var="acumulado" value="${acumulado+=movimientoDeCuentaInstance.importe}"/>
			 					<tr>
			 						<td>${movimientoDeCuentaInstance.id}</td>
			 						<td><lx:shortDate date="${movimientoDeCuentaInstance.fecha }"/></td>
			 						<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "concepto")}</td>
			 						<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "tipo")}</td>
			 						<g:if test="${movimientoDeCuentaInstance.importe>0}">
			 							<td><lx:moneyFormat number="${movimientoDeCuentaInstance.importe }"/></td>
			 						</g:if>
			 						<g:else>
			 							<td></td>
			 						</g:else>
			 						<g:if test="${movimientoDeCuentaInstance.importe<0}">
			 							<td><lx:moneyFormat number="${movimientoDeCuentaInstance.importe.abs() }"/></td>
			 						</g:if>
			 						<g:else>
			 							<td></td>
			 						</g:else>
			 						<td><lx:moneyFormat number="${acumulado }"/></td>
			 						<td>${fieldValue(bean: movimientoDeCuentaInstance, field: "comentario")}</td>

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
			    aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
			    "language": {
			        "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
			    },
			    "dom": 'T<"clear">lfrtip',
			    "tableTools": {
			        "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
			    },
			    "order": []
			});
			$("#filtro").on('keyup',function(e){
			    var term=$(this).val();
			    $('#grid').DataTable().search(
			        $(this).val()
			            
			    ).draw();
			});
			$('#data_4 .input-group.date').bootstrapDP({
						minViewMode: 1,
			            format: 'dd/mm/yyyy',
			            keyboardNavigation: false,
			            forceParse: false,
			            autoclose: true,
			            todayHighlight: true
			        });

		});
	</script>
</content>
 	
 
	
</body>
</html>

