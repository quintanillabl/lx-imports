<fieldset>
	<g:form class="form-horizontal" action="#" id="${CXCNotaInstance?.id}">
		<fieldset>
			<f:with bean="CXCNotaInstance">
				<g:hiddenField name="version" value="${CXCNotaInstance?.version}" />
				<g:if test="${!CXCNotaInstance.aplicaciones}">
					<f:field property="cliente" />
				</g:if>
				<f:field property="fecha">
					<lx:shortDate date="${value}" />
				</f:field>
				
				<f:field property="moneda" input-readOnly="true">
					<g:textField name="moneda" value="${value }" readOnly="true" />
				</f:field>
				<f:field property="tc" input-class="tc" input-readOnly="true" />
				<f:field property="total" input-class="moneyField"
					input-readOnly="true" />
				<f:field property="comentario" input-class="input-xxlarge"
					input-readOnly="true" />
			</f:with>
			
			<div class="form-actions">
				<%-- 
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Update" />
				</button>--%>
				<g:if test="${!CXCNotaInstance.comprobanteFiscal}">
					<g:link  action="generarCFDI" class="btn btn-info" 
						onclick="return myConfirm2(this,'Generar comprobante fiscal: ${CXCNotaInstance.id}','CFD');"
						id="${CXCNotaInstance.id}">
  		 				Generar CFDI
  					</g:link>
  					<button type="submit" class="btn btn-danger" name="_action_delete"
						formnovalidate>
						<i class="icon-trash icon-white"></i>
						<g:message code="default.button.delete.label" default="Delete" />
					</button>
				</g:if>
				<g:else>
					<g:link  action="cancelarCFDI" class="btn btn-info" 
						onclick="return myConfirm2(this,'Generar comprobante fiscal: ${CXCNotaInstance.id}','CFDI');"
						id="${CXCNotaInstance.id}">
  		 				Cancelar CFD
  					</g:link>
				</g:else>
				
			</div>
		</fieldset>
	</g:form>
</fieldset>
<r:script>
 $(function(){
 	$(".moneyField").autoNumeric({vMin:'0.00',wEmpty:'zero'});
 	$(".tcField").autoNumeric({vMin:'0.0000'});
 	
 });
 </r:script>