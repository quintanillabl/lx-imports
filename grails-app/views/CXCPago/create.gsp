<!doctype html>
<html>
<head>
	<title>Cobro</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de cobro</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Cobros</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Alta de cobro"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${CXCPagoInstance}"/>
				    	<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
				    		<f:with bean="CXCPagoInstance">
				    			<f:field property="cliente" wrapper="bootstrap3"/>
				    			<f:field property="fecha" wrapper="bootstrap3"/>
				    			<f:field property="formaDePago" 
				    				widget-class="form-control chosen-select" wrapper="bootstrap3"/>
				    			<f:field property="cuenta" 
				    				widget-class="form-control chosen-select" label="Cuenta destino" wrapper="bootstrap3"/>
				    			%{-- <f:field property="tc" widget="tc" wrapper="bootstrap3"/> --}%
				    			<f:field property="total" widget="money" wrapper="bootstrap3" />
				    			<f:field property="fechaBancaria" wrapper="bootstrap3"/>
				    			<f:field property="referenciaBancaria" 
				    				widget-class="form-control" wrapper="bootstrap3"/>
				    			<f:field property="comentario" 
				    				widget-class="form-control" wrapper="bootstrap3"/>
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
			
			$('#total,#cuenta').each(function(){
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

			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			//$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
			$(".money").autoNumeric('init',{mRound:'B',aSign: '$'});
			$('.chosen-select').chosen({placeholder_text_single:'Seleccione una cuenta'});
			
			$('form[name=createForm]').submit(function(e){
				var cuenta=$('#cuenta').val();
				if(cuenta==="null"){
					console.log("Cuenta nula");
					e.preventDefault(); 
					alert('Seleccione la cuenta destino');
					return false;
				}
				//e.preventDefault(); 
	    		var button=$("#saveBtn");
	    		button.attr('disabled','disabled')
	    		 .html('Procesando...');
	    		$(".money",this).each(function(index,element){
	    		  var val=$(element).val();
	    		  var name=$(this).attr('name');
	    		  var newVal=$(this).autoNumeric('get');
	    		  $(this).val(newVal);
	    		  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
	    		});
	    		return true;
			});	

			
			// $("#cuenta").change(function(e){
			// 	var date=$("#fecha").val();
			// 	var selected=$(this).val();
			// 	if(selected=="MXN"){
			// 		$("#tc").autoNumeric('set',1.000);
			// 	}else{
			// 		if(!date.isBlank()){
			// 			$.ajax({
			// 				url:"${createLink(action:'ajaxTipoDeCambioDiaAnterior')}",
			// 				success:function(response){
			// 					console.log('OK: '+response);
			// 					if(response!=null){
			// 						if(response.factor!=null){
			// 							$("#tc").autoNumeric('set',response.factor);
			// 							console.log('Tipo de cambio: '+response.factor);
			// 						}else if(response.error!=null){
			// 							alert(response.error);
			// 						}
			// 					}
			// 				},
			// 				data:{
			// 					fecha:date,
			// 					cuentaId:
			// 				},
			// 				error:function(request,status,error){
			// 					alert("Error: "+status);
			// 				}
			// 			});
			// 		}
			// 	};
				
			// });
			
		});
	</script>	
</content>
	

</body>
</html>


