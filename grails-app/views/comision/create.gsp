<!doctype html>
<html>
<head>
	<title>Alta de comisión bancaria</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de comisión</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Comisiones</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Comisión bancaria"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${comisionInstance}"/>
				    	<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
				    		<f:with bean="comisionInstance">
				    			<f:field property="fecha" wrapper="bootstrap3"/>
				    			<f:field property="cuenta" wrapper="bootstrap3"/>
				    			<f:field property="comision" widget="money" wrapper="bootstrap3"/>
				    			<f:field property="impuestoTasa" widget="porcentaje" wrapper="bootstrap3" label="Tasa de impuesto(%)"/>
				    			<f:field property="impuesto" widget="money"  wrapper="bootstrap3"/>
				    			<f:field property="referenciaBancaria" widget-class="form-control " wrapper="bootstrap3"/>
				    			<f:field property="comentario" widget-class="form-control "  wrapper="bootstrap3"/>
				    			<div class="form-group">
				    				<div class="col-lg-offset-3 col-lg-10">
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
	</div>
	<script type="text/javascript">
		$(function(){
			
			$('#comision,#cuenta').each(function(){
				$(this).attr("required",true);
			});

			$('.input-group.date').bootstrapDP({
				format: 'dd/mm/yyyy',
	            todayBtn: "linked",
	            keyboardNavigation: false,
	            forceParse: false,
	            calendarWeeks: true,
	            autoclose: true
			});

			$(".money").autoNumeric('init',{mRound:'B',aSign: '$'});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			$(".porcentaje").autoNumeric('init',{vMin:'0.01',vMax:'99.00'})
			$('.chosen-select').chosen({placeholder_text_single:'Seleccione una cuenta'});
			
			$('form[name=createForm]').submit(function(e){
	    		var button=$("#saveBtn");
	    		button.attr('disabled','disabled')
	    		 .html('Procesando...');
	    		$(".money,.porcentaje",this).each(function(index,element){
	    		  var val=$(element).val();
	    		  var name=$(this).attr('name');
	    		  var newVal=$(this).autoNumeric('get');
	    		  $(this).val(newVal);
	    		  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
	    		});
	    		return true;
			});	

		 	$("#impuestoTasa").blur(function(){
				
				var comision=$("#comision").autoNumeric('get');
				var tasa=$("#impuestoTasa").autoNumeric('get');
				tasa=tasa/100;
				var impuesto=comision*tasa;
				//importe=Math.round(importe*100)/100;
				$("#impuesto").autoNumeric('set',impuesto);
				//$("#hiddenImpuesto").val(impuesto);
				
			});
				
			// $("#cuenta").change(function(){
			// 	var date=$("#fecha").val();
			// 	$.ajax({
			// 		url:"${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
			// 		success:function(response){
			// 			console.log('OK: '+response);
			// 			if(response!=null){
			// 				if(response.factor!=null){
			// 					$("#tc").val(response.factor);
			// 					console.log('Tipo de cambio: '+response.factor);
			// 				}else if(response.error!=null){
			// 					alert(response.error);
			// 				}
			// 			}
			// 		},
			// 		data:{
			// 			fecha:date
			// 		},
			// 		error:function(request,status,error){
			// 			alert("Error: "+status);
			// 		}
			// 	});
				
			// });

		});
	</script>	
</content>
	

</body>
</html>




 