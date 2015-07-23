<fieldset>
	<g:form class="form-horizontal" action="edit" id="${cuentaContableInstance?.id}" >
		<g:hiddenField name="version" value="${cuentaContableInstance?.version}" />
		<fieldset>
			<f:with bean="${cuentaContableInstance}">
				<f:field property="clave"/>
				<f:field property="descripcion" input-class="input-xxlarge"/>
				<f:field property="tipo"/>
				<f:field property="subTipo"/>
				<f:field property="naturaleza"/>
				<f:field property="deResultado"/>
				<f:field property="presentacionContable"/>
				<f:field property="presentacionFinanciera"/>
				<f:field property="presentacionFiscal"/>
				<f:field property="presentacionPresupuestal"/>
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