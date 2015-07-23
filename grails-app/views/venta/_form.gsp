<%@ page import="com.luxsoft.impapx.Venta"%>
<fieldset>
	<g:form class="form-horizontal" action="create">
		<fieldset>
			<f:with bean="ventaInstance" >
				<g:hiddenField name="clase" value="${ventaInstance.clase}"/>
				<f:field property="cliente"/>
				<f:field property="fecha" input-id="fecha"/>
				<f:field property="moneda"/>
				<f:field property="tc" label="Tipo de cambio" input-disabled="true"/>
				<f:field property="formaDePago"/>
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
});
</r:script>