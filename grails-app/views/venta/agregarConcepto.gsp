<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<g:set var="entityName" value="${message(code: 'venta.label', default: 'Venta')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
	<r:require module="luxorForms"/>
</head>
<body>
	<div class="container">
		
		<div class="row">
			<div class="span12">
				<div class="alert">
					<h4><strong>
					<g:link action='edit' id="${ventaInstance.id}">Venta: ${ventaInstance.id} ( ${ventaInstance.cliente})</g:link>
					</strong></h4>
				</div>
				
			</div>
		</div>
		
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
			<g:form class="form-horizontal" action="agregarConcepto" id="${ventaInstance.id}">
				<fieldset>
					<f:with bean="ventaDetInstance" >
						<f:field property="producto"/>
						<f:field property="cantidad"/>
						<f:field property="precio" input-id="precio" input-class="auto"/>
						<f:field property="descuentos" input-id="descuentos"/>
						<f:field property="comentario" input-class="input-xxlarge"/>
					</f:with>
					<div class="form-actions">
						<button type="submit" class="btn btn-primary">
							<i class="icon-shopping-cart icon-white"></i>
							Agregar
						</button>
					</div>
				</fieldset>
		</g:form>
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
