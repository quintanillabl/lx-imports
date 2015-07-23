<%@ page import="com.luxsoft.impapx.Venta"%>
<%@ page import="com.luxsoft.cfdi.Cfdi"%>
<g:set var="mnEnabled" value="${ventaInstance.moneda.currencyCode=='MXN'}"/>

<fieldset>
	<g:form class="form-horizontal" action="edit" id="${ventaInstance.id}">
		<fieldset>
			<f:with bean="ventaInstance" >
				<g:hiddenField name="clienteId" value="${ventaInstance?.cliente?.id}"/>
				<f:field property="cliente"/>
				<f:field property="fecha"/>
				<f:field property="moneda"/>
				<f:field property="tc" label="Tipo de cambio" input-disabled="${mnEnabled}"/>
				<f:field property="formaDePago"/>
				<f:field property="cuentaDePago"/>
				<f:field property="comentario" input-class="input-xxlarge"/>
			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Actualizar" />
				</button>
				<g:if test="${cfdi}" >
					<g:link controller="cfdi" action="show" class="btn  btn-success" id="${cfdi?.id}">
						CFDI :${cfdi.id}   UUID: ${cfdi.uuid?:'Por Timbrar' }
					</g:link>
				</g:if>
				<g:else>
					<g:link  controller="cfdi" action="facturar" class="btn btn-info" 
						onclick="return myConfirm2(this,'Facturar venta: ${ventaInstance.id}','Facturación');"
						id="${ventaInstance.id}">
  		 				Facturar
  					</g:link>
  					
  					<button class="btn btn-danger" type="submit" name="_action_delete">
						<i class="icon-trash icon-white"></i>
						<g:message code="default.button.delete.label" default="Delete" />
					</button>
  					
				</g:else>
				
				
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
	
});
</r:script>