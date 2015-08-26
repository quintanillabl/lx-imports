<g:form class="form-horizontal">
<f:with bean="${proveedorInstance}">
	
	<div class="row">
		<f:display property="nombre"/>
	</div>
	
	<div class="row">
		<div class="col-md-6">
			<f:display property="factorDeUtilidad" wrapper="bootstrap3" />
			<f:display property="tipoDeCosteo" wrapper="bootstrap3"/>
			<f:display property="rfc" wrapper="bootstrap3"/>
			<f:display property="nacional" wrapper="bootstrap3"/>
			<f:display property="paisDeOrigen" wrapper="bootstrap3"/>
  			<f:display property="nacionalidad" wrapper="bootstrap3"/>
  			<f:display property="subCuentaOperativa" wrapper="bootstrap3"/>
		</div>
		<div class="col-md-6">
			<f:display property="correoElectronico" wrapper="bootstrap3"/>
			<f:display property="www" wrapper="bootstrap3"/>
			<f:display property="lineaDeCredito" wrapper="bootstrap3"/>
			<f:display property="plazo" wrapper="bootstrap3"/>
			<f:display property="vencimentoBl" wrapper="bootstrap3" />
		</div>
	</div>
	<f:display property="direccion" />
</f:with>
</g:form>