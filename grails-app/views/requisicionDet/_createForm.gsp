<g:form class="form-horizontal" action="save" controller="requisicionDet">
	<f:with bean="requisicionDetInstance">
		<f:field property="documento" widget-class="form-control"/>
		<f:field property="fechaDocumento" />
		<f:field property="embarque" />
		<f:field property="total" widget="money"/>
	</f:with>
	<div class="col-md-4 col-md-offset-2">
		<button type="submit" class="btn btn-primary">
			<i class="icon-ok icon-white"></i>
			<g:message code="default.button.create.label" default="Salvar" />
		</button>
	</div>
</g:form>