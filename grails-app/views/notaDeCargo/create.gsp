<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		
		<title>Nota de Cargo</title>
		<r:require module="luxorForms"/>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								Cargos
							</g:link>
						</li>
						<li class="active">
							<g:link class="create" action="create">
								<i class="icon-plus icon-white"></i>
								Alta de Cargo
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="page-header">
					<h3>Alta de Nota de Cargo</h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${ventaInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${ventaInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>

				<fieldset>
	<g:form class="form-horizontal" action="create" >
		<fieldset>
			<f:with bean="ventaInstance" >
				<g:hiddenField name="tipo" value="${'NOTA_DE_CARGO' }"/>
				<g:hiddenField name="cuentaDePago" value="${'0000' }"/>
				<f:field property="cliente"/>
				<f:field property="fecha" input-id="fecha"/>
				<f:field property="moneda"/>
				<f:field property="tc" label="Tipo de cambio" input-disabled="true"/>
				<f:field property="importe" />
				<f:field property="impuestos" />
				<f:field property="total" />
				<f:field property="formaDePago"/>
				
				<f:field property="comentario" input-class="input-xxlarge" label="Concepto" input-required="true"/>
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
				
			</div>

		</div>
		
<r:script>
$(function(){
	$('#moneda').bind('change',function(e){
		var selected=$(this).val();
		if(selected=="MXN"){
			$("#tc").attr("disabled",true).val(1.0);
		}else
			$("#tc").attr("disabled",false);
		
	});
	$("#cuentaDePago").mask("9999");
	$("#fecha").mask("99/99/9999");
	
	$('#importe').autoNumeric();
	$('#impuestos').autoNumeric();
	$('#total').autoNumeric();
		
	$('#importe').blur(function(){
		var importe=$(this).autoNumericGet();
		var impuestos=importe*.16;
		var total=importe*1.16;
		//$('#subTotal').autoNumericSet(importe);
		$('#impuestos').autoNumericSet(impuestos);
		$('#total').autoNumericSet(total);
	});
	
});
</r:script>
	</body>
</html>
