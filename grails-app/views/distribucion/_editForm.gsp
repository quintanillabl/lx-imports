<fieldset>
	<g:form class="form-horizontal" action="edit" id="${distribucionInstance.id}">
		<fieldset>
			<f:with bean="${distribucionInstance}">
				<f:field property="fecha" />
				<f:field property="embarque" />
				<f:field property="comentario">
					<g:textArea name="comentario" value="${distribucionInstance?.comentario }" rows="5" cols="50"/>
				</f:field>

			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Actualizar" />
				</button>
				<button type="submit" class="btn btn-danger"  name="_action_delete" onclick="return confirm('Eliminar lista de distribucion?')">
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.delete.label" default="Delete" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>