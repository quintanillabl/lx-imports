<legend>  <span id="conceptoLabel">Propiedades</span></legend> 
<g:form name="updateForm" action="update" class="form-horizontal" id="${facturaDeGastosInstance.id}" method="PUT">
	<f:with bean="facturaDeGastosInstance">
		<div class="row">
			<div class="col-md-6">
				<f:display property="proveedor" widget-class="form-control" 
					wrapper="bootstrap3" widget-required="true"/>
				<f:field property="documento" widget-class="form-control" wrapper="bootstrap3"/>
				<f:field property="fecha" wrapper="bootstrap3" widget-required="true"/>
				<f:field property="vencimiento" wrapper="bootstrap3"  />
				<f:display property="moneda" wrapper="bootstrap3"/>
				<f:display property="tc" widget-class="form-control" wrapper="bootstrap3"/>
				<f:field property="comentario" widget-class="form-control" wrapper="bootstrap3"/>
			</div>
			<div class="col-md-6">
				<f:display property="importe" widget="money" wrapper="bootstrap3" />
				<f:display property="subTotal" widget="money" wrapper="bootstrap3"/>
				<f:display property="descuentos" widget="money" wrapper="bootstrap3"/>
				<f:display property="impuestos" widget="money" wrapper="bootstrap3"/>
				<f:display property="tasaDeImpuesto" widget="porcentaje" widget-class="form-control" wrapper="bootstrap3"/>
				<f:display property="retTasa" widget="porcentaje" widget-class="form-control" wrapper="bootstrap3"/>
				<f:display property="retImp" widget="money" wrapper="bootstrap3"/>
				<f:display property="total" widget="money" wrapper="bootstrap3"/>
			</div>
		</div>
	</f:with>
</g:form>