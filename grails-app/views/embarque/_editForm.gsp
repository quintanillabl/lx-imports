<div class="col-md-8 col-md-offset-2 form-panel">
	<g:form class="form-horizontal" action="edit" method="PUT">
		<g:hiddenField name="id" value="${embarqueInstance.id}" />
		<g:hiddenField name="version" value="${embarqueInstance?.version}" />
		<f:with bean="embarqueInstance">
			<f:display property="proveedor"/>
			<f:field property="bl" widget-class="uppercase-field form-control"/>
			<f:field property="nombre" widget-class="form-control uppercase-field"/>
			<f:field property="fechaEmbarque"/>
			<f:field property="aduana"/>
			<f:field property="ingresoAduana" label="ETA"/>
			<f:field property="contenedores" widget-class="form-control entero" widget-type="text"/>
			<f:field property="comentario" widget-class="form-control" />
		</f:with>
		<div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		    	<g:actionSubmit class="btn btn-primary" 
		    		action="update" 
		    		value="${message(code: 'default.button.update.label', default: 'Update')}" />
		    </div>
		</div>
	</g:form>
</div>