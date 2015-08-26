<g:form action="updateForm" class="form-horizontal" method="PUT">  
    <g:hiddenField name="id" value="${compraInstance?.id}" />
    <g:hiddenField name="version" value="${compraInstance?.version}" />
    <f:with bean="compraInstance">
    	<f:display property="proveedor" 
    		wrapper="bootstrap3" widget-tabindex="2"/>
    	<f:display property="folio" wrapper="bootstrap3"/>
    	<f:display property="fecha" wrapper="bootstrap3"/>
    	<f:display property="entrega" wrapper="bootstrap3"/>
    	<f:display property="moneda" wrapper="bootstrap3"/>
    	<f:display property="tc" widget="tc" wrapper="bootstrap3" widget-type="text"/>
    	<f:display property="comentario" 
    		widget-class="form-control" wrapper="bootstrap3"/>
    </f:with>
    <div class="form-group">
        <div class="col-lg-offset-3 col-lg-9">
            <button id="saveBtn" class="btn btn-primary ">
                <i class="fa fa-floppy-o"></i> Actualizar
            </button>
        </div>
    </div>
</g:form>