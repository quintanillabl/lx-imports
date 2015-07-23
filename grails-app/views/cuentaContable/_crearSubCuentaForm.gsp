<fieldset>
	<g:form class="form-horizontal" action="agregarSubCuenta" id="${cuentaContableInstance?.id}" >
		<fieldset>
			<f:with bean="${cuentaContableInstance}">
				<f:field property="clave"/>
				<f:field property="descripcion" input-class="input-xxlarge"/>
				<f:field property="detalle" value="${true }"/>
			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Update" />
				</button>
				<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
			</div>
		</fieldset>
	</g:form>
</fieldset>