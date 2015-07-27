<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Requisición ${requisicionInstance.id}</title>
	<asset:javascript src="forms/forms.js"/>
	<asset:javascript src="legacy/luxorTableUtils.js"/>
</head>
<body>

 	<content tag="header">

 		<div class="col-md-12 ">
 			<div class="panel panel-primary">
 				<div class="panel-heading">
 					Requisición: ${requisicionInstance.id} ${requisicionInstance.proveedor}
 				</div>
 				<g:hasErrors bean="${requisicionInstance}">
 					<div class="alert alert-danger">
 						<ul class="errors" >
 							<g:renderErrors bean="${requisicionInstance}" as="list" />
 						</ul>
 					</div>
 				</g:hasErrors>
 				<div class="btn-group">
 				    <g:link action="index" class="btn btn-default ">
 				        <i class="fa fa-step-backward"></i> Requisiciones
 				    </g:link>
 				    
 				    <g:link action="print" class="btn btn-default " id="${requisicionInstance.id}">
 				        <i class="fa fa-print"></i> Imprimir
 				    </g:link>
 				    <g:if test="${!requisicionInstance.pagoProveedor}">
 				    	<g:link controller="requisicionDet" action="create" class="btn btn-default " id="${requisicionInstance.id}">
 				    	    <i class="fa fa-plus"></i> Agregar concepto
 				    	</g:link> 
 				    	<g:link action="selectorDeFacturas" 
 				    		class="btn btn-default " id="${requisicionInstance.id}">
 				    	    <i class="fa fa-cart-plus"></i> Agregar factura
 				    	</g:link> 
 				    	<a id="eliminarPartidas" class="btn btn-default"><i class="fa fa-trash"></i> Eliminar partidas</a> 
 				    	<buttn id="saveBtn" class="btn btn-success">
 				    		<i class="fa fa-floppy-o"></i> Salvar
 				    	</buttn>
 				    	<lx:deleteButton bean="${requisicionInstance}" />
 				    </g:if> 
 				</div>
 				<div class="panel-body">
	 				<g:form name="updateForm" action="update" method="PUT" class="form-horizontal" >
	 				<f:with bean="requisicionInstance">
					<div class="col-md-6">
						<f:display property="concepto" 
							widget-class="form-control" wrapper="bootstrap3"/>
						<f:field property="fecha" 
							wrapper="bootstrap3"/>
						<f:field property="fechaDelPago" label="Pago" wrapper="bootstrap3"/>
						<f:field property="formaDePago" widget-class="form-control" wrapper="bootstrap3" label="F. de Pago"/>
						<f:field property="moneda" wrapper="bootstrap3"/>
						<f:field property="tc" widget="tc" label="T.C." wrapper="bootstrap3"/>
						<f:field property="descuentoFinanciero" widget="porcentaje" label="D.F." wrapper="bootstrap3"/>
						<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
					</div>
					<div class="col-md-6">
						<f:display property="importe" wrapper="bootstrap3" widget="money"/>
						<f:display property="impuestos" wrapper="bootstrap3" widget="money"/>
						<f:display property="impuestos" wrapper="bootstrap3" widget="money"/>
						<f:display property="retencionHonorarios" wrapper="bootstrap3" widget="money"/>
						<f:display property="retencionFlete" wrapper="bootstrap3" widget="money"/>
						<f:display property="retencionISR" wrapper="bootstrap3" widget="money"/>
						<f:display property="total" wrapper="bootstrap3" widget="money"/>
					</div>
					</f:with>
	 				</g:form>
 				</div>
 				
 				<table class=" grid table table-striped table-hover table-bordered table-condensed">
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
 							    <td>${fieldValue(bean: row, field: "documento")}</td>
 								<td><lx:shortDate date="${row.fechaDocumento}" /></td>
 								<td><lx:moneyFormat number="${row.totalDocumento }" /></td>
 								<td><lx:moneyFormat number="${row.total }" /></td>
 								<td>${fieldValue(bean: row, field: "embarque.id")}</td>
 								<td><lx:shortDate date="${row?.factura?.vencimiento}" /></td>
 							</tr>
 						</g:each>
 					</tbody>
 					%{-- <tfoot>
 						<tr>
 							<td></td>
 							<td></td>
 							<td><label class="pull-right" >Total: </label></td>
 							<td><lx:moneyFormat number="${requisicionInstance.total }" /></td>
 							<td></td>
 						</tr>
 					</tfoot> --}%
 				</table>
		 			
 			</div>
 		</div>

 		<script type="text/javascript">
 			$(function(){
 				$(".porcentaje").autoNumeric('init',{aSign: '%', pSign: 's', vMax: '99.99'});
 				$(".tc").autoNumeric('init',{vMin:'0.0000'});
 				$(".grid tbody tr").hover(function(){
 					$(this).toggleClass("info");
 				});
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

 				$("#saveBtn").on('click',function(){
 					$('form[name=updateForm]').submit();
 				});

				$('form[name=updateForm]').submit(function(e){

					$("#saveBtn")
					.attr('disabled','disabled')
					.html('Procesando...');
					
					$(".money,.porcentaje,.tc",this).each(function(index,element){
					  var val=$(element).val();
					  var name=$(this).attr('name');
					  var newVal=$(this).autoNumeric('get');
					  $(this).val(newVal);
					  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
					});
		    		e.preventDefault(); 
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
					
					$.ajax({
						url:"${createLink(controller:'requisicion',action:'eliminarPartidas')}",
						data:{
							requisicionId:${requisicionInstance.id},partidas:JSON.stringify(res)
						},
						success:function(response){
							//$("#facturasGrid").html(response);
							location.reload();
						},
						error:function(request,status,error){
							alert("Error: "+status);
						}
					});
				});
				
				
				
 			});
 		</script>
 	</content>

 	
	
</body>
</html>

