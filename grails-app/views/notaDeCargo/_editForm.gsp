<%@ page import="com.luxsoft.impapx.Venta"%>
<g:set var="mnEnabled" value="${ventaInstance.moneda.currencyCode=='MXN'}"/>
<fieldset>
	<g:form class="form-horizontal" action="edit" id="${ventaInstance.id}">
		<fieldset>
			<f:with bean="ventaInstance" >
				<g:hiddenField name="clienteId" value="${ventaInstance?.cliente?.id}"/>
				<g:hiddenField name="cliente.id" value="${ventaInstance?.cliente?.id}"/>
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
				
				<f:field property="comentario" input-class="input-xxlarge"/>
			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Actualizar" />
				</button>
				<g:link  controller="cfdi" action="facturar" class="btn btn-info" 
					onclick="return myConfirm2(this,'Facturar cargo: ${ventaInstance.id}','Facturación');"
					id="${ventaInstance.id}">
  		 			Generar CFDI
  				</g:link>
				<button class="btn btn-danger" type="submit" name="_action_delete">
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.delete.label" default="Delete" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>
<r:script>
$(function(){
	//var mon=$("#moneda").val();
	//$(#moneda).attr('disabled',mon==""MXN);
	
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