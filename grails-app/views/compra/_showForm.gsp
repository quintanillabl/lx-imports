<form class="form-horizontal" >  
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
            <div class="btn-group">
                <lx:backButton/>
                <lx:createButton/>
                <lx:editButton id="${entity?.id}"/>
                <lx:printButton/>
            </div>
        </div>
    </div>
</form>