<!doctype html>
<html>
<head>
	<title>Alta de traspaso</title>
	<meta name="layout" content="luxor">
	<asset:javascript src="forms/forms.js"/>
</head>
<body>

<content tag="header">Alta de traspaso</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Traspasos</g:link></li>
    	<li class="active"><strong>Alta</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-8">
				<div class="ibox float-e-margins">
					<lx:iboxTitle title="Traspaso entre cuentas"/>
				    <div class="ibox-content">
				    	<lx:errorsHeader bean="${traspasoInstance}"/>
				    	<g:form name="createForm" action="save" class="form-horizontal" method="POST">	
				    		<f:with bean="traspasoInstance">
				    			<f:field property="cuentaOrigen" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
				    			<f:field property="cuentaDestino" widget-class="form-control chosen-select" wrapper="bootstrap3"/>
				    			<f:field property="fecha" wrapper="bootstrap3"/>
				    			<f:field property="importe" widget="money" wrapper="bootstrap3"/>
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
			$(".money").autoNumeric('init',{mRound:'B',aSign: '$'});
			$('.chosen-select').chosen({placeholder_text_single:'Seleccione una cuenta'});
			
			$('form[name=createForm]').submit(function(e){
				// var cuenta=$('#cuenta').val();
				// if(cuenta==="null"){
				// 	console.log("Cuenta nula");
				// 	e.preventDefault(); 
				// 	alert('Seleccione la cuenta destino');
				// 	return false;
				// }
				// //e.preventDefault(); 
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
		});
	</script>	
</content>
	

</body>
</html>



