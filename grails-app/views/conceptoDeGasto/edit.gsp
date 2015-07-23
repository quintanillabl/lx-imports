<%@ page import="com.luxsoft.impapx.cxp.ConceptoDeGasto" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'conceptoDeGasto.label', default: 'ConceptoDeGasto')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<r:require module="luxorForms"/>
	</head>
	<body>
		<div class="row-fluid">

			
			
			<div class="span9">

				<div class="page-header">
					<h3><g:message code="default.edit.label" args="[entityName]" /></h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${conceptoDeGastoInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${conceptoDeGastoInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>

				<fieldset>
					<g:form class="form-horizontal" action="edit" id="${conceptoDeGastoInstance?.id}" >
						<g:hiddenField name="version" value="${conceptoDeGastoInstance?.version}" />
						<fieldset>
							<f:with bean="conceptoDeGastoInstance">
								<g:hiddenField name="factura.id" value="${conceptoDeGastoInstance?.factura?.id }"/>
								
								<f:field property="concepto" >
									<g:hiddenField id="conceptoId" name="concepto.id" 
										value="${conceptoDeGastoInstance?.concepto?.id}"/>
									<g:field type="text" id="concepto" name="conceptoDesc" required="true" class="input-xxlarge" 
										value="${conceptoDeGastoInstance?.concepto}"/>
								</f:field>
								<f:field property="tipo" value="${conceptoDeGastoInstance?.tipo}"/>
								<f:field property="descripcion" input-required="true"  input-class="input-xxlarge"/>
						 
								<f:field property="importe" input-required="true" input-id="c_Importe" input-class="moneyField"/>
								<f:field property="ietu" input-required="true" input-id="c_ietu" input-class="moneyField"/>
								<f:field property="impuestoTasa" input-required="true" input-class="tasa"/>
								<f:field property="impuesto" input-required="true" input-class="moneyField"/>
								<f:field property="retensionTasa" input-required="true" input-class="tasa"/>
								<f:field property="retension" input-required="true" input-class="moneyField"/>
								<f:field property="retensionIsrTasa" input-required="true" input-class="tasa"/>
								<f:field property="retensionIsr" input-required="true" input-class="moneyField"/>
								<f:field property="descuento"  input-class="moneyField"/>
								<f:field property="rembolso"  input-class="moneyField" label="Vales"/>
								<f:field property="fechaRembolso"  input-id="fechaRembolso" label="Fecha Vales"/>
								<f:field property="otros"  input-class="moneyField"/>
								<f:field property="comentarioOtros"  />
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									<g:message code="default.button.update.label" default="Update" />
								</button>
								<button type="submit" class="btn btn-danger" name="_action_delete" formnovalidate>
									<i class="icon-trash icon-white"></i>
									<g:message code="default.button.delete.label" default="Delete" />
								</button>
							</div>
						</fieldset>
					</g:form>
				</fieldset>

			</div>
			
<r:script>

$(function(){
	
	$("#concepto").autocomplete({
			source:'<g:createLink controller="cuentaContable" action="cuentasDeDetalleJSONList"/>',
			minLength:3,
			select:function(e,ui){
				console.log('Valor seleccionado: '+ui.item.id);
				$("#conceptoId").val(ui.item.id);
			}
	});
	
	
	$(".tasa").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
	$('#c_Importe').blur(function(){
		console.log('Importe registrado');
		var importe=$("#c_Importe").autoNumericGet();
		$('#c_ietu').autoNumericSet(importe);
	});
	
	
});

</r:script>

		</div>
	
	</body>
</html>
