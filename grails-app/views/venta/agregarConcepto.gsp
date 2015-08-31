<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<g:set var="entityName" value="${message(code: 'venta.label', default: 'Venta')}" />
	<title>Partida de venta</title>
	<asset:javascript src="forms/forms.js"/>
	
</head>
<body>
<content tag="header">
	Venta ${ventaInstance.id} (${ventaInstance.cliente})
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link controller="venta" action="edit" id="${ventaInstance.id}">Venta ${ventaInstance.id}</g:link></li>
    	<li class="active"><strong>Partida nueva</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="col-md-8">
		<div class="ibox float-e-margins">
			<lx:iboxTitle title="Partida de venta"/>
		    <div class="ibox-content">
					<g:form class="form-horizontal" name="createForm" action="agregarConcepto" id="${ventaInstance.id}">
						<fieldset>
							<f:with bean="ventaDetInstance" >

								<f:field property="producto" wrapper="bootstrap3" widget-class="form-control chosen-select"/>
								<f:field property="cantidad" widget="numeric" wrapper="bootstrap3"/>
								<f:field property="precio" widget="money" wrapper="bootstrap3"/>
								<f:field property="descuentos" widget="porcentaje" wrapper="bootstrap3"/>
								<f:field property="comentario" wrapper="bootstrap3" widget-class="form-control"/>
							</f:with>
							<div class="form-group">
							    <div class="col-lg-offset-3 col-lg-9">
							        <button id="saveBtn" class="btn btn-primary ">
							            <i class="fa fa-floppy-o"></i> Salvar
							        </button>
							        <lx:backButton/>
							    </div>
							</div>
						</fieldset>
					</g:form>
		    </div>
		</div>	
	</div>
	
	<script type="text/javascript">
		$('.chosen-select').chosen();
		$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
		$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
		$(".tc").autoNumeric('init',{vMin:'0.0000'});
		$(".porcentaje").autoNumeric('init',{altDec: '%', vMax: '99.99'});

		$('form[name=createForm]').submit(function(e){
		    console.log("Desablidatndo submit button....");

		    var button=$("#saveBtn");
		    button.attr('disabled','disabled')
		    .html('Procesando...');

		    $(".money,.porcentaje,.numeric,.tc",this).each(function(index,element){
		      var val=$(element).val();
		      var name=$(this).attr('name');
		      var newVal=$(this).autoNumeric('get');
		      $(this).val(newVal);
		    });
		    //e.preventDefault(); 
		    return true;
		});

		$('.auto').blur(function(){
			var precio=$("#precio").autoNumeric('get');
			var cantidad=$("#cantidad").autoNumeric('get');
			
			//var importe=(+importe)+(+impuestos);
			//console.log('Importe : '+importe+ 'Impuestos:'+impuestos+' Total:'+total);
			//$('#impuestos').autoNumericSet(impuestos);
			//$('#total').autoNumericSet(total);
		});
	</script>
</content>

	<div class="container">
		
		
		
		<div class="row">
			<div class="span12">
				<g:if test="${flash.message}">
					<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${ventaInstance}">
					<bootstrap:alert class="alert-error">
						<ul>
							<g:eachError bean="${ventaInstance}" var="error">
								<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
									<g:message error="${error}"/>
								</li>
							</g:eachError>
						</ul>
					</bootstrap:alert>
				</g:hasErrors>
			</div>
		</div>
		
		<div>
			<fieldset>
			
		</fieldset>
	</div>
		 
	</div>
	
<r:script>
$(function(){
	
	$("#cantidad").autoNumeric({vMin:'0.000',wEmpty:'zero'});
	$("#precio").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
	//$("#importe").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
	$("#descuentos").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
	
	//$('#importe').autoNumeric();
	//$('#impuestos').autoNumeric();
	//$('#total').autoNumeric();
		
	$('.auto').blur(function(){
		var precio=$("#precio").autoNumericGet();
		var cantidad=$("#cantidad").autoNumericGet();
		
		//var importe=(+importe)+(+impuestos);
		//console.log('Importe : '+importe+ 'Impuestos:'+impuestos+' Total:'+total);
		//$('#impuestos').autoNumericSet(impuestos);
		//$('#total').autoNumericSet(total);
	});
	
});
</r:script>
	
</body>
</html>
