<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
	<title>Alta de movimiento bancario</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de movimiento bancario</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Movimientos</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-6">
				<lx:iboxTitle title="Alta de movimiento (Deposito o Retiro)"/>
				<div class="ibox-content">
					<lx:errorsHeader bean="${movimientoDeCuentaInstance}"/>
					<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
						<g:hiddenField name="ingreso" value="${movimientoDeCuentaInstance.ingreso}"/>
						<f:with bean="movimientoDeCuentaInstance">
							<f:field property="cuenta" 
								widget-class="form-control " wrapper="bootstrap3"
								widget-tabindex="2"/>
							<f:field property="concepto" widget-class="form-control " wrapper="bootstrap3">
								<g:select name="concepto" from="${conceptos }"  class="form-control"
									value="${movimientoDeCuentaInstance?.concepto}"/>
							</f:field> 
							<f:field property="fecha"  wrapper="bootstrap3"/>
							<f:field property="referenciaBancaria" widget-class="form-control" wrapper="bootstrap3"/>
							<f:field property="tc" widget="tc" wrapper="bootstrap3"/>
							<f:field property="importe" widget="money" wrapper="bootstrap3"/>
							<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
							%{-- <f:field property="ingreso" widget-class="form-control" wrapper="bootstrap3" label="Deposito"/> --}%
							<div class="form-group ">
								<label class="control-label col-md-3" for="anticipo">Anticipo</label>
								<div class="col-md-9 ui-front">
									<g:hiddenField id="anticipoId" name="anticipo.id"/>
									%{-- <g:field id="anticipoField" name="anticipoDesc" type="text"  class="form-control"/> --}%
									<input id="anticipoField" type="text" class="form-control" />
									%{-- <g:select name="anticipo" from="${anticipos}"  
										class="form-control chosen-select"
										noSelection="${['null':'Seleccione un anticipo']}"
										value="${movimientoDeCuentaInstance?.concepto}"
										optionKey="id"
										optionValue="${id}"/> --}%
								</div>
							</div>

							<div class="form-group">
								<div class="col-lg-offset-3 col-lg-10">
									%{-- <button class="btn btn-sm btn-white" type="submit">Sign in</button> --}%
									<button id="saveBtn" class="btn btn-primary ">
										<i class="fa fa-floppy-o"></i> Salvar
									</button>
									<lx:backButton/>
								</div>
								
								
							</div>
						</f:with>
						
					</g:form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			
			$('.input-group.date').bootstrapDP({
	            todayBtn: "linked",
	            keyboardNavigation: false,
	            forceParse: false,
	            calendarWeeks: true,
	            autoclose: true
			});
			$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
			
			$(".tc").autoNumeric('init',{vMin:'0.0000'});

			$('.chosen-select').chosen();
			
			$('form[name=createForm]').submit(function(e){
				//e.preventDefault(); 
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
	    		return true;
			});

			$("#anticipoField").autocomplete({
				source:'<g:createLink controller="anticipo" action="disponiblesJSONList"/>',
				minLength:3,
				select:function(e,ui){
					console.log('Valor seleccionado: '+ui.item.id);
					$("#anticipoId").val(ui.item.id);
				}
			});
		});
	</script>	
</content>
	


	
		 		
	 		// <script type="text/javascript">
	 		// 	$(function(){
 			// 		$("#fecha").datepicker({
    //             todayBtn: "linked",
    //             keyboardNavigation: false,
    //             forceParse: false,
    //             calendarWeeks: true,
    //             autoclose: true,
    //             format: 'dd/mm/yyyy',
    //        		});

 			// 	$('.input-group.date').datepicker({
	   //              todayBtn: "linked",
	   //              keyboardNavigation: false,
	   //              forceParse: false,
	   //              calendarWeeks: true,
	   //              autoclose: true
				// });
				// $('.chosen-select').chosen();
	 		// 		//$(".moneda").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
	 		// 		$(".tc").autoNumeric('init',{vMin:'0.0000'});
	 		// 		$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});
				//  	$('#concepto').bind('change',function(e){
				// 		var selected=$(this).val();
				// 		if(selected=="ANTICIPO"){
				// 			$("#embarqueAuto").removeAttr("disabled").attr("required","true");
				// 		}else{
				// 			$("#embarqueAuto")
				// 			.attr("disabled",'disabled')
				// 			.removeAttr("required");
				// 		}
				// 	});

				// 	$("#moneda").change(function(e){
				// 		var date=$("#fecha").val();
				// 		var selected=$(this).val();
				// 		if(selected=="MXN"){
				// 			$("#tc").autoNumeric('set',1.000);
				// 		}else{
				// 			if(!date.isBlank()){
				// 				$.ajax({
				// 					url:"${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
				// 					success:function(response){
				// 						console.log('OK: '+response);
				// 						if(response!=null){
				// 							if(response.factor!=null){
				// 								$("#tc").autoNumeric('set',response.factor);
				// 								console.log('Tipo de cambio: '+response.factor);
				// 							}else if(response.error!=null){
				// 								alert(response.error);
				// 							}
				// 						}
				// 					},
				// 					data:{
				// 						fecha:date
				// 					},
				// 					error:function(request,status,error){
				// 						alert("Error: "+status);
				// 					}
				// 				});
				// 			}
				// 		};
						
				// 	});

					

				// 	$("#anticipo2").autocomplete({
				// 		source:'<g:createLink controller="anticipo" action="disponiblesJSONList"/>',
				// 		minLength:3,
				// 		select:function(e,ui){
				// 			console.log('Valor seleccionado: '+ui.item.id);
				// 			$("#anticipoId").val(ui.item.id);
				// 		}
				// 	});

	 		// 	});
	 		// </script>
	
</body>
</html>


