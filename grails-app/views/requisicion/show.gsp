<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	
	<title>Requisición ${requisicionInstance.id}</title>
</head>
<body>

	<div class="row wrapper border-bottom white-bg page-heading">
       <div class="col-lg-10">
           <h2>Requisición ${requisicionInstance}</h2>
           <ol class="breadcrumb">
           		<li><g:link action="index">Requisiciones</g:link></li>
           		<li><g:link action="create">Alta</g:link></li>
           		<li class="active"><strong>Consulta</strong></li>
           		<g:if test="${!requisicionInstance.pagoProveedor}">
           			<li><g:link action="edit" id="${requisicionInstance.id}">Edición</g:link></li>
           		</g:if>
           </ol>
       </div>
       <div class="col-lg-2">
			
       </div>
	</div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Folio: ${requisicionInstance.id}"/>
					<g:form name="editForm" action="save" class="form-horizontal" method="POST">	
					<f:with bean="requisicionInstance">
					<div class="ibox-content">
						<lx:errorsHeader bean="${requisicionInstance}"/>
						<div class="btn-group">
							<lx:backButton label="Requisiciones"/>
							<lx:printButton id="${requisicionInstance.id}"/>
						</div>
						<div class="row">
							<div class="col-lg-6">
								<f:display property="id" wrapper="bootstrap3" label="Folio"/>
								<f:display property="concepto" 
									widget-class="form-control chosen-select" wrapper="bootstrap3"
									widget-tabindex="2"/>
								<f:display property="fecha" wrapper="bootstrap3" />
								<f:display property="fechaDelPago" widget-class="form-control" wrapper="bootstrap3"/>
								<f:display property="formaDePago" 
									wrapper="bootstrap3"  widget-class="form-control chosen-select"/>
								<f:display property="moneda" wrapper="bootstrap3"/>
								<f:display property="tc" widget-class="form-control tc" wrapper="bootstrap3" widget-type="text"/>
								<f:display property="descuentoFinanciero" 
									widget-class="form-control porcentaje" wrapper="bootstrap3" widget-type="text"/>
								<f:display property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
								<f:display property="pagoProveedor" wrapper="bootstrap3" />
							</div>
							<div class="col-lg-6">
								<f:display property="importe" wrapper="bootstrap3" widget="money"/>
								<f:display property="impuestos" wrapper="bootstrap3" widget="money"/>
								<f:display property="impuestos" wrapper="bootstrap3" widget="money"/>
								<f:display property="retencionHonorarios" wrapper="bootstrap3" widget="money"/>
								<f:display property="retencionFlete" wrapper="bootstrap3" widget="money"/>
								<f:display property="retencionISR" wrapper="bootstrap3" widget="money"/>
								<f:display property="total" wrapper="bootstrap3" widget="money"/>
							</div>
						</div>
						
					</div>
					</f:with>
					</g:form>
				</div> <!-- End ibox 1 -->
			</div>
		</div>

		<div class="ibox float-e-margins">
			<lx:iboxTitle title="Partidas"/>
			<div class="ibox-content">
				<table class=" grid table  table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<th class="header">Documento</th>
							<th class="header">Fecha</th>
							<th>Total Dcto</th>
							<th class="header">A Pagar</th>
							<th class="header">Embarque</th>
							<th>Vto</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${requisicionInstance.partidas}" var="row">
							<tr id="${row.id}">
								<td>
									<g:link controller="requisicionDet" action="edit" id="${row.id}">
										${fieldValue(bean: row, field: "documento")}</td>
									</g:link>
								</td>
							    <td>

							    
								<td><lx:shortDate date="${row.fechaDocumento}" /></td>
								<td><lx:moneyFormat number="${row.totalDocumento }" /></td>
								<td><lx:moneyFormat number="${row.total }" /></td>
								<td>${fieldValue(bean: row, field: "embarque.id")}</td>
								<td><lx:shortDate date="${row?.factura?.vencimiento}" /></td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div> <!-- End ibox 2 Partidas -->
	</div>
	<script type="text/javascript">
		$(function(){
			$('.chosen-select').chosen();
		});
	</script>
	
</body>
</html>