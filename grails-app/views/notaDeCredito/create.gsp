<%@ page import="com.luxsoft.impapx.cxp.NotaDeCredito" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<g:set var="entityName" value="${message(code: 'notaDeCredito.label', default: 'NotaDeCredito')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<r:require module="luxorForms"/>
	</head>
	<body>
		<content tag="header">
		<h3>Alta de notas de crédito</h3>
 	</content>
 	
 	<content tag="consultas">
 		<%-- <g:render template="/cuentaPorPagar/actions"/>--%>
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="list" action="list">
				<i class="icon-list"></i>
				Notas
			</g:link>
 			<g:link class="" action="create">
				<i class="icon-plus "></i>
				Alta de Nota
			</g:link>
		</li>
 	</content>
 	
 	<content tag="document">
		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
		<g:hasErrors bean="${notaDeCreditoInstance}">
			<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${notaDeCreditoInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
			</bootstrap:alert>
		</g:hasErrors>
		<fieldset>
			<g:form class="form-horizontal" action="create" >
				<fieldset>
					<f:with bean="notaDeCreditoInstance">
						<f:field property="proveedor"/>
						<f:field property="concepto"/>
						<f:field property="moneda"/>
						<f:field property="tc"/>
						<f:field property="documento"/>
						<f:field property="fecha"/>
						<f:field property="importe" input-class="autoCalculate moneyField"/>
						<f:field property="impuestoTasa" input-class="autoCalculate"/>
						<f:field property="impuestos" input-class="autoCalculate moneyField"/>
						<f:field property="total" input-class="autoCalculate moneyField"/>
						<f:field property="comentario" input-class="input-xxlarge"/>
					</f:with>
					
					<div class="form-actions">
						<button type="submit" class="btn btn-primary">
							<i class="icon-ok icon-white"></i>
							<g:message code="default.button.create.label" default="Create" />
						</button>
					</div>
				</fieldset>
			</g:form>
		</fieldset>
 	</content>
		
	</body>
<r:script>
$(function(){
	//var mon=$("#moneda").val();
	//$(#moneda).attr('disabled',mon==""MXN);
	
	$('#moneda').bind('change',function(e){
		var selected=$(this).val();
		if(selected=="MXN"){
			$("#tc").attr("disabled",true);//.val(1.0);
			$("#tc").autoNumericSet(1.0);
		}else
			$("#tc").attr("disabled",false);
		
	});
	
	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
 	$("#tc").autoNumeric({vMin:'0.0000'});
	$("#fecha").mask("99/99/9999");
	$("#impuestoTasa").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
	
	//$('#importe').autoNumeric();
	//$('#impuestos').autoNumeric();
	//$('#total').autoNumeric();
		
	$('.autoCalculate').blur(function(){
		var importe=$("#importe").autoNumericGet();
		var tasa=$("#impuestoTasa").autoNumericGet();
		var impuestos=importe*(tasa/100);
		var total=(+importe)+(+impuestos);
		console.log('Importe : '+importe+ 'Impuestos:'+impuestos+' Total:'+total);
		$('#impuestos').autoNumericSet(impuestos);
		$('#total').autoNumericSet(total);
	});
	
});
</r:script>
</html>
