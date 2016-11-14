<g:form class="form-horizontal" action="update" method="PUT" >
	
	<g:hiddenField name="id" value="${proveedorInstance.id}"/>
	<g:hiddenField name="version" value="${proveedorInstance.version}"/>

	<f:with bean="${proveedorInstance}">
		<div class="row">
			<div class="col-md-6">
				<f:field property="nombre" wrapper="bootstrap3" widget-class="form-control" />
				<f:field property="factorDeUtilidad" wrapper="bootstrap3" widget="numeric"/>
				<f:field property="tipoDeCosteo" wrapper="bootstrap3" widget-class="form-control"/>
				<f:field property="rfc" wrapper="bootstrap3" widget-class="form-control"/>
				<f:field property="nacional" wrapper="bootstrap3" widget-class="form-control"/>
				<f:field property="paisDeOrigen" wrapper="bootstrap3" widget-class="form-control"/>
	  			<f:field property="nacionalidad" wrapper="bootstrap3" widget-class="form-control"/>
	  			<f:field property="subCuentaOperativa" wrapper="bootstrap3" widget-class="form-control"/>
	  			<f:field property="cuentaBancaria" wrapper="bootstrap3" widget-class="form-control"/>
	  			<f:field property="bancoDestino" wrapper="bootstrap3" widget-class="form-control"/>
	  			
			</div>
			<div class="col-md-6">
				<f:field property="correoElectronico" wrapper="bootstrap3" widget-class="form-control"/>
				<f:field property="www" wrapper="bootstrap3" widget-class="form-control"/>
				<f:field property="lineaDeCredito" wrapper="bootstrap3" widget="numeric"/>
				<f:field property="plazo" wrapper="bootstrap3" widget="numeric"/>
				<f:field property="vencimentoBl" wrapper="bootstrap3" widget-class="form-control"/>
				
				<f:field property="agenciaAduanal" wrapper="bootstrap3" widget-class="form-control"/>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-8">
				<g:render template="/common/direccion" bean="${proveedorInstance}"/>
			</div>
			
		</div>

		<div class="form-group">
		    <div class="col-lg-offset-3 col-lg-9">
		        <button id="saveBtn" class="btn btn-primary ">
		            <i class="fa fa-floppy-o"></i> Actualizar
		        </button>
		        %{-- <a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a>  --}%
		    </div>
		</div>
		
	</f:with>
</g:form>