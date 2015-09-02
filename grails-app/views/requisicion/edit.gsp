<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	<title>Requisición ${requisicionInstance.id}</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

	<div class="row wrapper border-bottom white-bg page-heading">
       <div class="col-lg-10">
           <h2>Requisición ${requisicionInstance}</h2>
           <ol class="breadcrumb">
           		<li><g:link action="index">Requisiciones</g:link></li>
           		<li><g:link action="create">Alta</g:link></li>
           		<li class="active"><strong>Edición</strong></li>
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
					<g:form name="updateForm" action="update" class="form-horizontal" method="PUT">	
					<g:hiddenField name="id" value="${requisicionInstance.id}"/>
					<g:hiddenField name="version" value="${requisicionInstance.version}"/>
					<f:with bean="requisicionInstance">
					<div class="ibox-content">
						<lx:errorsHeader bean="${requisicionInstance}"/>
						<div class="btn-group">
							<lx:backButton label="Requisiciones"/>
							<lx:printButton id="${requisicionInstance.id}"/>
							<g:if test="${!requisicionInstance.pagoProveedor}">
								<button id="saveBtn" class="btn btn-primary ">
									<i class="fa fa-floppy-o"></i> Salvar
								</button>
								<a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
							</g:if>
							
						</div>
						<div class="row">
							<div class="col-lg-6">
								<f:display property="concepto" 
									widget-class="form-control" wrapper="bootstrap3"/>
								<f:display property="fecha" 
									wrapper="bootstrap3"/>
								<f:field property="fechaDelPago" label="Pago" wrapper="bootstrap3"/>
								<f:field property="formaDePago" widget-class="form-control chosen-select" wrapper="bootstrap3" label="F. de Pago"/>
								<f:display property="moneda" wrapper="bootstrap3"/>
								<f:field property="tc" widget="tc" label="T.C." wrapper="bootstrap3"/>
								<f:field property="descuentoFinanciero" widget="porcentaje" label="D.F." wrapper="bootstrap3"/>
								<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
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
				<div class="btn-group">
				    <g:if test="${!requisicionInstance.pagoProveedor}">
				    	<a data-toggle="modal" data-target="#createConceptoDialog" data-requisicion="${requisicionInstance.id}"
				    		class="btn btn-default ">
				    		<i class="fa fa-plus"></i> Agregar concepto
				    	</a>
				    	<g:link action="selectorDeFacturas" 
				    		class="btn btn-default " id="${requisicionInstance.id}">
				    	    <i class="fa fa-cart-plus"></i> Agregar factura
				    	</g:link> 
				    	<a id="eliminarPartidas" class="btn btn-danger"><i class="fa fa-trash"></i> Eliminar partidas</a> 
				    </g:if> 
				</div>
				<table class=" grid table  table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<th>Factura</th>
							<th>Documento</th>
							<th>Fecha</th>
							<th>Total Dcto</th>
							<th>A Pagar</th>
							<th>Embarque</th>
							<th>Vto</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${requisicionInstance.partidas}" var="row">
							<tr id="${row.id}">
								<td>${fieldValue(bean: row, field: "factura.id")}</td>
							    <td>${fieldValue(bean: row, field: "documento")}</td>
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
	
	<div class="modal fade in" id="createConceptoDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">${requisicionInstance}</h4>
				</div>
				<g:form name="updateForm" class="form-horizontal" action="savePartida" >
				<div class="modal-body">
					
						<f:with bean="${new  com.luxsoft.impapx.RequisicionDet()}">
							<g:hiddenField name="requisicion.id" value="${requisicionInstance.id}"/>
							<f:field property="documento" widget-class="form-control"/>
							<f:field property="fechaDocumento" />
							<f:field property="embarque" />
							<f:field property="total" widget="money"/>
						</f:with>
						
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">
							<i class="icon-ok icon-white"></i>
							<g:message code="default.button.create.label" default="Salvar" />
						</button>
				</div>
				</g:form>
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>

	<g:render template="/common/deleteDialog" bean="${requisicionInstance}"/>
	
	
	<script type="text/javascript">
		$(function(){
			//$('.chosen-select').chosen();
			$('.input-group.date').bootstrapDP({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
			});
			$(".porcentaje").autoNumeric('init',{aSign: '%', pSign: 's', vMax: '99.99'});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			$(".grid tbody tr").click(function(){
	 			$(this).toggleClass("success selected");
	 		});
	 		
			var selectRows=function(){
				var res=[];
				var data=$(".grid .selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			};

			$('form[name=updateForm]').submit(function(e){

				$("#saveBtn")
				.attr('disabled','disabled')
				.html('Procesando...');
				try{
					$(".money,.porcentaje,.tc",this).each(function(index,element){
					  var val=$(element).val();
					  var name=$(this).attr('name');
					  var newVal=$(this).autoNumeric('get');
					  $(this).val(newVal);
					  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
					});
				}catch(err){
					console.log(err);
				}
	    		//e.preventDefault(); 
	    		return true;
			});

			$("#eliminarPartidas").click(function(e){
				var res=selectRows();
				if(res.length==0){
					alert('Debe seleccionar al menos un registro');
					return;
				}
				var ok=confirm('Eliminar  ' + res.length+' partida(s)?');
				if(!ok)
					return;
				console.log('Cancelando facturas: '+res);
				$.post(
					"${createLink(controller:'requisicion',action:'eliminarPartidas')}",
					{requisicionId:${requisicionInstance.id},partidas:JSON.stringify(res)})
				.done(function(data){
					location.reload();
				})
				.fail(function(jqXHR, textStatus, errorThrown){
					alert("Error: "+textStatus);
				});	
				
			});

			
		});
		
	</script>

</body>
</html>

