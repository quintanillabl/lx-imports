<!doctype html>
<html>
<head>
	<title>Alta de pagoo</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de pago a proveedor</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Pagos</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<lx:iboxTitle title="Alta de pago a proveedo "/>
				<div class="ibox-content">
					<lx:errorsHeader bean="${pagoProveedorInstance}"/>
					<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
						<f:with bean="pagoProveedorInstance">

							<f:field property="requisicion" widget-class="form-control " wrapper="bootstrap3"/>
							<f:field property="fecha"  wrapper="bootstrap3"/>
							<f:field property="cuenta" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
							<f:field property="tipoDeCambio" widget="tc" wrapper="bootstrap3" widget-required="required"/>
							<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
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
	<script type="text/javascript">
		$(function(){
			
			$('.input-group.date').bootstrapDP({
	            todayBtn: "linked",
	            keyboardNavigation: false,
	            forceParse: false,
	            calendarWeeks: true,
	            autoclose: true
			});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			
			$('.chosen-select').chosen();
			
			$('form[name=createForm]').submit(function(e){
				//e.preventDefault(); 
	    		var button=$("#saveBtn");
	    		button.attr('disabled','disabled')
	    		 .html('Procesando...');
	    		$(".tc",this).each(function(index,element){
	    		   var val=$(element).val();
	    		  var name=$(this).attr('name');
	    		  var newVal=$(this).autoNumeric('get');
	    		  $(this).val(newVal);
	    		  console.log('Enviando elemento numerico con valor:'+name+" : "+val+ " new val:"+newVal);
	    		});
	    		return true;
			});
			

			$("#cuenta").change(function(){
				var date=$("#fecha").val();
				$.ajax({
					url:"${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
					success:function(response){
						console.log('OK: '+response);
						if(response!=null){
							if(response.factor!=null){
								$("#tipoDeCambio").val(response.factor);
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
				console.log('Fecha seleccionada: '+date);
			});
		});
	</script>	
</content>
</body>
</html>
