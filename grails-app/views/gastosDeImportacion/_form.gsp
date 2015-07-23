<fieldset>
	<g:form class="form-horizontal" action="${action}" >
		<f:with bean="cuentaPorPagarInstance">
			<g:hiddenField name="id" value="${cuentaPorPagarInstance?.id}"/>
			<g:hiddenField name="provId" value="${cuentaPorPagarInstance?.proveedor?.id}"/>
			<f:field property="proveedor" input-required="true"/>
			<f:field property="documento" value="${cuentaPorPagarInstance.documento}"
								input-autocomplete="false"/>
			<f:field property="fecha" input-id="fecha" input-required="true" value="${cuentaPorPagarInstance.fecha}"/>
			<f:field property="vencimiento" input-id="vencimiento" input-required="true"
								value="${cuentaPorPagarInstance?.vencimiento}"/>
			<f:field property="moneda"/>
			<f:field property="tc" input-class="numeric"/>								
			<f:field property="importe" input-class="numeric" input-required="true"/>
			<f:field property="incrementable"  />
			<f:field property="tasaDeImpuesto" input-class="numeric"/>
			<f:field property="impuestos" input-class="numeric" />
			<f:field property="retTasa" input-id="retTasa" input-class="numeric" />
			<f:field property="retImp" input-id="retencion" input-class="numeric" />
			<f:field property="total" input-class="numeric"/>
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
		
				$('#fecha').mask("99/99/9999");
				$('.numeric').forceNumeric();
			
				$('#tasaDeImpuesto').blur(function(){
					var iva=+$(this).val();
					
					var importe=+$('#importe').val()
					var impuesto=(importe*iva)/100
					var total=importe+impuesto;
					
					$('#impuestos').val(impuesto);
					$('#total').val(total);
					
				});
				
				$('#retTasa').blur(function(){
					var ret=+$(this).val();
					
					var importe=+$('#importe').val()
					var retencion=(importe*ret)/100
					
					var total=+$('#total').val()
					total=total-retencion
					
					$('#retencion').val(retencion);
					$('#total').val(total);
					
				});
		
				
			
		});	
	</r:script>