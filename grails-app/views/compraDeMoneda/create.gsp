<!doctype html>
<html>
<head>
	<title>Compra de moneda</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de compra de moneda</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Compra de moneda</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>
	
<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<lx:iboxTitle title="Alta de compra de moneda "/>
				<div class="ibox-content">
					<lx:errorsHeader bean="${compraDeMonedaInstance}"/>
					<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
						<f:with bean="compraDeMonedaInstance">
							<f:field property="requisicion" widget-class="form-control " wrapper="bootstrap3">
								<g:select class="form-control chosen-select"  
									name="${property}" 
									value="${value?.id}"
									from="${com.luxsoft.impapx.Requisicion.findAll("from Requisicion r left join fetch r.pagoProveedor pp where r.concepto='COMPRA_MONEDA' and r.total>0 and pp is  null")}" 
									optionKey="id" 
									required='required'
									/>
							</f:field>
							<f:field property="fecha"  wrapper="bootstrap3"/>
							<f:field property="moneda" wrapper="bootstrap3"/>
							<f:field property="tipoDeCambioCompra"  
								widget="tc" wrapper="bootstrap3" label="TC Compra" />
							<f:field property="tipoDeCambio" 
								widget="tc" wrapper="bootstrap3" />

							<f:field property="cuentaOrigen" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
							<f:field property="cuentaDestino" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
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
			$('.tc').each(function(){
				$(this).attr("required",true)
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
			

			// $("#cuenta").change(function(){
			// 	var date=$("#fecha").val();
			// 	$.ajax({
			// 		url:"${createLink(controller:'tipoDeCambio', action:'ajaxTipoDeCambioDiaAnterior')}",
			// 		success:function(response){
			// 			console.log('OK: '+response);
			// 			if(response!=null){
			// 				if(response.factor!=null){
			// 					$("#tipoDeCambio").val(response.factor);
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
			// 	console.log('Fecha seleccionada: '+date);
			// });
		});
	</script>	
</content>
</body>
</html>

