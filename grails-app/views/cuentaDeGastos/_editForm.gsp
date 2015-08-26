<div class="row">
	<div class="col-md-6">
	<g:form class="form-horizontal" action="update" method="PUT">
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
</div>


