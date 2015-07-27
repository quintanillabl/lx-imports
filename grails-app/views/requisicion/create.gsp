<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Alta de requisición</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

 	<content tag="header">
 		<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
 		<div class="panel panel-primary">
 			<div class="panel-heading">
 				Alta de requisición de pago
 			</div>
 			<div class="panel-body ">
 				<g:hasErrors bean="${requisicionInstance}">
 					<div class="alert alert-danger">
 						<ul class="errors" >
 							<g:renderErrors bean="${requisicionInstance}" as="list" />
 						</ul>
 					</div>
 				</g:hasErrors>
				<legend>  <span id="conceptoLabel">Propiedades</span></legend> 
				<f:with bean="requisicionInstance">
				<div class="col-md-6">
					<f:field property="proveedor" widget-class="form-control" 
						wrapper="bootstrap3" widget-required="true"/>
					<f:field property="concepto" widget-class="form-control" wrapper="bootstrap3"/>
					<f:field property="fecha" wrapper="bootstrap3" widget-required="true"/>
					<f:field property="fechaDelPago" widget-class="form-control" wrapper="bootstrap3"/>
					<f:field property="formaDePago" 
						wrapper="bootstrap3"  widget-class="form-control"/>
					<f:field property="moneda" wrapper="bootstrap3"/>
					<f:field property="tc" widget-class="form-control tc" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="descuentoFinanciero" 
						widget-class="form-control porcentaje" wrapper="bootstrap3" widget-type="text"/>
					<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
				</div>
				
				</f:with>		
 			
 			</div>					
 		 
 			<div class="panel-footer">
 				<div class="btn-group">
 					<g:link class="btn btn-default " action="index"  >
 						<i class="fa fa-step-backward"></i> Facturas
 					</g:link>
 					<button id="saveBtn" class="btn btn-success ">
 							<i class="fa fa-floppy-o"></i> Salvar
 					</button>
 				</div>
 			</div><!-- end .panel-footer -->

 		</div>
 		</g:form>
 		<script type="text/javascript">
 			$(function(){
 				//$(".moneda").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
 				$(".tc").autoNumeric('init',{vMin:'0.0000'});
 				$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});
			 	$('#concepto').bind('change',function(e){
					var selected=$(this).val();
					if(selected=="ANTICIPO"){
						$("#embarqueAuto").removeAttr("disabled").attr("required","true");
					}else{
						$("#embarqueAuto")
						.attr("disabled",'disabled')
						.removeAttr("required");
					}
				});

				//$("#tc").attr("disabled",true); // Inicia deshabilitado

			 // 	$('#moneda').bind('change',function(e){
				// 	var selected=$(this).val();
				// 	if(selected=="MXN"){
				// 		$("#tc").attr("disabled",true);//.val(1.0);
				// 		$("#tc").autoNumericSet(1.0);
				// 	}else
				// 		$("#tc").removeAttr("disabled");
					
				// });

				$("#moneda").change(function(e){
					var date=$("#fecha").val();
					var selected=$(this).val();
					if(selected=="MXN"){
						$("#tc").autoNumeric('set',1.000);
					}else{
						if(!date.isBlank()){
							$.ajax({
								url:"${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
								success:function(response){
									console.log('OK: '+response);
									if(response!=null){
										if(response.factor!=null){
											$("#tc").autoNumeric('set',response.factor);
											console.log('Tipo de cambio: '+response.factor);
										}else if(response.error!=null){
											alert(response.error);
										}
									}
								},
								data:{
									fecha:date
								},
								error:function(request,status,error){
									alert("Error: "+status);
								}
							});
						}
					};
					
				});

				$('form[name=createForm]').submit(function(e){
		    		var button=$("#saveBtn");
		    		button.attr('disabled','disabled')
		    		.html('Procesando...');
		    		$(".money,.porcentaje,.tc",this).each(function(index,element){
		    		  var val=$(element).val();
		    		  var name=$(this).attr('name');
		    		  var newVal=$(this).autoNumeric('get');
		    		  $(this).val(newVal);
		    		  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
		    		});

		    		//e.preventDefault(); 
		    		return true;
				});

 			});
 		</script>
 		<%--
 		 	<script type="text/javascript">
 		 		$(function(){

 		 			$(".moneda").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
 		 			$(".tc").autoNumeric('init',{vMin:'0.0000'});
 		 			$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});
 		 			
		 			$('#totalesPanel').on('blur','input',function(){
						var importe=$(this).autoNumeric('get');
						actualizarTotal();
						
		 			});
		 			

 		 			var actualizarTotal=function(){
 		 				
 		 				var importe=$('#importe').autoNumeric('get');
 		 				var descuentos=$('#descuentos').autoNumeric('get');
 		 				
 		 				var subTotal=importe-descuentos;
 		 				$('#subTotal').autoNumeric('set',subTotal.round(2));

 		 				var iva=$("#tasaDeImpuesto").autoNumeric('get');

 		 				var impuestos=(iva/100.00)*subTotal;
 		 				$("#impuestos").autoNumeric('set',impuestos);

						var retImp=$('#retImp').autoNumeric('get');
 		 				
 		 				var total=subTotal+impuestos-retImp
 		 				$('#total').autoNumeric('set',total);

 		 			};

 		 			

 					$('form[name=updateForm]').submit(function(e){
 						console.log("Desablidatndo submit button....");

 			    		var button=$("#saveBtn");
 			    		button.attr('disabled','disabled')
 			    		.html('Procesando...');

 			    		$(".moneda,porcentaje",this).each(function(index,element){
 			    		  var val=$(element).val();
 			    		  var name=$(this).attr('name');
 			    		  var newVal=$(this).autoNumeric('get');
 			    		  $(this).val(newVal);
 			    		});
 			    		//e.preventDefault(); 
 			    		return true;
 					});
 		 		})
 		 	</script>
 		 	--%>
 	</content>

 	
	
	
</body>
</html>

