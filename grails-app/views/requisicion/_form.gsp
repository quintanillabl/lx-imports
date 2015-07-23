<fieldset>
	<g:form class="form-horizontal" action="edit"
		id="${requisicionInstance?.id}">
		<g:hiddenField name="version" value="${requisicionInstance?.version}" />
		<fieldset>
			<f:with bean="requisicionInstance">
				<f:field property="proveedor"/>
				<f:field property="concepto" >
					<g:textField name="concepto" readOnly="true" value="${requisicionInstance?.concepto }"/>
				</f:field>
				<f:field property="fecha" />
				<f:field property="fechaDelPago" />
				<f:field property="formaDePago" />
				<f:field property="moneda" />
				<f:field property="tc" input-class="tcField" />
				<f:field property="descuentoFinanciero" input-class="porcentField" input-readOnly="true"/>
				<f:field property="comentario" input-class="comentario" />
				<%-- 
				<g:if test="${requisicionInstance.anticipo}">
					<f:field property="anticipo">
						<g:link controller="anticipo" action="show" id="${requisicionInstance.anticipo.id}">
							${requisicionInstance.anticipo.id} - ${requisicionInstance.anticipo.embarque} 
						</g:link>
					</f:field>
				</g:if>
				--%>
			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Update" />
				</button>
				<button type="submit" class="btn btn-danger" name="_action_delete"
					formnovalidate>
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.delete.label" default="Delete" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>

<r:script>
 $(function(){
 	$(".tcField").autoNumeric({vMin:'0.0000'});
 	$(".porcentField").autoNumeric({vMax:'100.00',vMin:'0.00'});
 });
 </r:script>
		
		