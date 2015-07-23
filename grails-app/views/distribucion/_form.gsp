<fieldset>
	<g:form class="form-horizontal" action="create">
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
					<g:message code="default.button.create.label" default="Create" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>