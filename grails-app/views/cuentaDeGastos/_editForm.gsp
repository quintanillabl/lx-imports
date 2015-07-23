
<div class="col-md-6  form-panel">
	<g:form class="form-horizontal" action="edit" method="PUT">
		<g:hiddenField name="id" value="${cuentaDeGastosInstance.id}" />
		<g:hiddenField name="version" value="${cuentaDeGastosInstance.version}" />
		<f:with bean="cuentaDeGastosInstance">
			<f:field property="proveedor" label="Agente" input-required="true"/>
			<f:field property="fecha"/>
			<f:field property="embarque" widget-class="form-control"/>
			<f:field property="referencia" widget-class="form-control"/>
			<f:field property="comentario" widget-class="form-control"/>
		</f:with>
		<div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		    	<button type="submit" class="btn btn-primary">
		    		<i class="icon-ok icon-white"></i>
		    		<g:message code="default.button.update.label" default="Update" />
		    	</button>
		    </div>
		</div>
	</g:form>
</div>

%{-- <fieldset>
	<g:form class="form-horizontal" action="edit"
		id="${cuentaDeGastosInstance?.id}">
		<g:hiddenField name="version"
			value="${cuentaDeGastosInstance?.version}" />
		<fieldset>
			<f:with bean="cuentaDeGastosInstance">
				<f:field property="proveedor" label="Agente" input-required="true"/>
				<f:field property="fecha"/>
				<f:field property="embarque" input-class="span7"/>
				<f:field property="referencia"/>
				<f:field property="comentario">
					<g:textArea name="comentario" value="${cuentaDeGastos?.comentario }" cols="40" rows="4" class="span7"/>
				</f:field>
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
</fieldset> --}%