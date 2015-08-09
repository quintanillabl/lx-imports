<%@ page import="com.luxsoft.impapx.tesoreria.MovimientoDeCuenta" %>
<!doctype html>
<html>
<head>
	<title>Bancos</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	Movimientos de cuenta Periodo: ${session.periodoTesoreria.asPeriodoText()}
</content>
	
<content tag="subHeader">
	<ol class="breadcrumb">
		<li>
			<g:link  params="[tipo:'TODOS']" >
				<g:if test="${tipo=='TODOS'}">
					<strong>Todos</strong>
				</g:if>
				<g:else>
					Todos
				</g:else>
				
			</g:link>
		</li>
		<li>
			<g:link  params="[tipo:'DEPOSITOS']">
				<g:if test="${tipo=='DEPOSITOS'}">
					<strong>Depositos</strong>
				</g:if>
				<g:else>
					Depositos
				</g:else>
			</g:link>
		</li>
		<li>
			<g:link  params="[tipo:'RETIROS']">
				<g:if test="${tipo=='RETIROS'}">
					<strong>Retiros</strong>
				</g:if>
				<g:else>
					Retiros
				</g:else>
			</g:link>
		</li>
	</ol>
</content>

<content tag="document">
	<div class="row">
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	        	
	        	<div class="ibox-title">
	        	    <button data-target="#periodoDialog" data-toggle="modal" class="btn btn-outline btn-success  dim">
	        	    	<i class="fa fa-calendar"></i> 
	        	   	</button>
	        	   	%{-- <button data-target="#createDialog" data-toggle="modal" class="btn  btn-default  dim"> 
	        	   		<i class="fa fa-plus"></i>
	        	   </button> --}%
	        	   <g:link action="depositar" class="btn  btn-outline btn-success  dim">
	        	   		<i class="fa fa-plus"> Depositar</i>
	        	   </g:link>
	        	   <g:link action="retirar" class="btn  btn-outline btn-danger  dim">
	        	   		<i class="fa fa-minus"> Retirar</i>
	        	   </g:link>
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
	            				<th>Id</th>
	            				<th>Banco</th>
	            				<th >Num</th>
	            				<th >Mon</th>
	            				<th >T.C.</th>
	            				<th>Fecha</th>
	            				<th>Concepto</th>
	            				<th >Importe</th>
	            				<th>Origen</th>
	            				<th >Comentario</th>
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${movimientoDeCuentaInstanceList}" var="row">
	            			<tr id="${row.id}">	
	            				<lx:idTableRow id="${row.id}"/>
	            				<td>${fieldValue(bean: row, field: "cuenta.nombre")}</td>
	            				<td>${fieldValue(bean: row, field: "cuenta.numero")}</td>
	            				<td>${fieldValue(bean: row, field: "moneda")}</td>
	            				<td>${fieldValue(bean: row, field: "tc")}</td>
	            				<td><lx:shortDate date="${row.fecha }"/></td>
	            				<td>${fieldValue(bean: row, field: "concepto")}</td>
	            				<lx:moneyTableRow number="${row.importe}"/>
	            				<td>${fieldValue(bean: row, field: "origen")}</td>
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
	
	<div class="modal fade" id="createDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Alta de movimiento</h4>
				</div>
				<g:form action="create" class="form-horizontal" >
					<div class="modal-body">
						<f:with bean="${new com.luxsoft.impapx.tesoreria.MovimientoDeCuenta(fecha:new Date())}">
							
							<g:hiddenField name="ingreso" value="${movimientoDeCuentaInstance?.ingreso }"/>
							<f:field property="cuenta" 
								widget-class="form-control " wrapper="bootstrap3"
								widget-tabindex="2"/>
							<f:field property="concepto" widget-class="form-control" wrapper="bootstrap3"/>
							
							%{-- <f:field property="concepto" >
								<g:select name="concepto" from="${conceptos }" 
									value="${movimientoDeCuentaInstance?.concepto}"/>
							</f:field>  --}%
							<f:field property="fecha"  wrapper="bootstrap3"/>
							<%-- <f:field property="tipo"/>--%>
							<f:field property="referenciaBancaria" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="tc" widget="tc" wrapper="bootstrap3"/>
							<f:field property="importe" widget="money" wrapper="bootstrap3"/>
							<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
						</f:with>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Buscar" />
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
			$('.input-group.date').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
			});
            $('.chosen-select').chosen();
            					

		});
	</script>
	
</content>
	
</body>
</html>
