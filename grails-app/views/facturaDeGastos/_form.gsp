<fieldset>
	<g:form class="form-horizontal" action="${action}" >
		<f:with bean="cuentaPorPagarInstance">
			<g:hiddenField name="id" value="${cuentaPorPagarInstance?.id}"/>
			<g:hiddenField name="provId" value="${cuentaPorPagarInstance?.proveedor?.id}"/>
			<g:if test="${!cuentaPorPagarInstance.id }">
				<f:field property="proveedor" input-required="true"/>
			</g:if>
			
			<f:field property="documento" value="${cuentaPorPagarInstance.documento}"
								input-autocomplete="false"/>
			<f:field property="fecha" input-id="fecha" input-required="true" value="${cuentaPorPagarInstance.fecha}"/>
			<f:field property="vencimiento" input-id="vencimiento" input-required="true"
								value="${cuentaPorPagarInstance?.vencimiento}"/>
			<f:field property="importe" input-disabled="true"
				value="${lx.moneyFormat(number:cuentaPorPagarInstance.importe) }"/>
			<f:field property="impuestos" input-disabled="true"
				value="${lx.moneyFormat(number:cuentaPorPagarInstance.impuestos) }"/>
			<f:field property="retImp" input-disabled="true"
				value="${lx.moneyFormat(number:cuentaPorPagarInstance.retImp) }"/>
			<f:field property="retensionIsr" input-disabled="true"
				value="${lx.moneyFormat(number:cuentaPorPagarInstance.retensionIsr) }"/>
			<f:field property="total" input-disabled="true"
				value="${lx.moneyFormat(number:cuentaPorPagarInstance.total) }"/>
			<f:field property="descuento" input-disabled="true"
				value="${lx.moneyFormat(number:cuentaPorPagarInstance.descuento) }"/>
			<f:field property="rembolso" input-disabled="true"
				value="${lx.moneyFormat(number:cuentaPorPagarInstance.rembolso) }"/>
			
			<%-- 
			<f:field property="tasaDeImpuesto" />
			<f:field property="impuestos" input-class="moneyField" />
			<f:field property="total" input-class="moneyField"/>
			
			--%>
			<f:field property="comentario">
					<g:textArea name="comentario" value="${cuentaPorPagarInstance?.comentario}" rows="3" cols="70" class ="span7"/>
			</f:field >  
		</f:with>
		<div class="form-actions">
			<g:if test="${action=='edit'}">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Actualizar" />
				</button>
				<button class="btn btn-danger" type="submit" name="_action_delete"
					onclick="return confirm('Eliminar la factura?')">
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.delete.label" default="Delete" />
				</button>
			</g:if>
			<g:else>
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.create.label" default="Crear" />
				</button>
			</g:else>
			
			
		</div>
		</g:form>
</fieldset>

<r:script>
$(function(){
		
	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero',mRound:'B'});
	$("#tasaDeImpuesto").autoNumeric({aSign: ' %', pSign: 's', vMin:'0.00',vMax: '100.00',wEmpty:'zero'} );
	$('#fecha').mask("99/99/9999");
	
	$('#tasaDeImpuesto').blur(function(){
		var importe=$("#importe").autoNumericGet();
		var tasa=$("#tasaDeImpuesto").autoNumericGet();
		var impuestos=importe*(tasa/100);
		var total=(+importe)+(+impuestos);
		console.log('Importe : '+importe+ 'Impuestos:'+impuestos+' Total:'+total);
		$('#impuestos').autoNumericSet(impuestos);
		$('#total').autoNumericSet(total);
	});
	
				
			
});	
	</r:script>