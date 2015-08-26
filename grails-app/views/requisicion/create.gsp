<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	<title>Alta de requisición</title>
	<asset:javascript src="forms/forms.js"/>
</head>
<body>
	
	<div class="row wrapper border-bottom white-bg page-heading">
       <div class="col-lg-10">
           <h2>Alta de requisición de pago</h2>
           <ol class="breadcrumb">
           	<li><g:link action="index">Requisiciones</g:link></li>
           	<li class="active"><strong>Alta</strong></li>
           </ol>
       </div>
       <div class="col-lg-2">
			
       </div>
	</div>
	


	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>Alta de requisición</h5>
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
						<lx:errorsHeader bean="${requisicionInstance}"/>
						<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
							
							<f:with bean="requisicionInstance">
								<f:field property="proveedor" 
									wrapper="bootstrap3" widget-tabindex="2"/>
								<f:field property="concepto" 
									widget-class="form-control chosen-select" wrapper="bootstrap3"
									widget-tabindex="2"/>
								<f:field property="fecha" wrapper="bootstrap3" />
								<f:field property="fechaDelPago" widget-class="form-control" wrapper="bootstrap3"/>
								<f:field property="formaDePago" 
									wrapper="bootstrap3"  widget-class="form-control chosen-select"/>
								<f:field property="moneda" wrapper="bootstrap3"/>
								<f:field property="tc" widget-class="form-control tc" wrapper="bootstrap3" widget-type="text"/>
								<f:field property="descuentoFinanciero" 
									widget-class="form-control porcentaje" wrapper="bootstrap3" widget-type="text"/>
								<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
							</f:with>
							<div class="form-group">
								<div class="col-lg-offset-3 col-lg-10">
									%{-- <button class="btn btn-sm btn-white" type="submit">Sign in</button> --}%
									<button id="saveBtn" class="btn btn-primary ">
										<i class="fa fa-floppy-o"></i> Salvar
									</button>
								</div>
							</div>	
							
						</g:form>
					</div>

				</div>
			</div>
		</div>
		 		
	</div>
	

 	

 	
	 		<script type="text/javascript">
	 			$(function(){
 					$("#fecha").bootstrapDP({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true,
                format: 'dd/mm/yyyy',
           		});

 				$('.input-group.date').bootstrapDP({
	                todayBtn: "linked",
	                keyboardNavigation: false,
	                forceParse: false,
	                calendarWeeks: true,
	                autoclose: true,
	                format: 'dd/mm/yyyy',
				});
				$('.chosen-select').chosen();
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
	
</body>
</html>

