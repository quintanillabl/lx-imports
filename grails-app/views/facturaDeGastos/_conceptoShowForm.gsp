<form class="form-horizontal">
	
	<f:with bean="${it}">
		<f:display property="concepto" wrapper="bootstrap3"/>
		<f:display property="tipo" 
			widget-class="form-control" wrapper="bootstrap3"/>
		<f:display property="descripcion" 
			widget-required="true" widget-class="form-control" wrapper="bootstrap3"/>
		<f:display property="importe" 
			widget-required="true"  widget="money" wrapper="bootstrap3"/>
		<f:display property="impuestoTasa" 
			widget-required="true" widget="porcentaje" wrapper="bootstrap3"/>
		<f:display property="retensionTasa" label="Ret (%)"
			widget-required="true" widget="porcentaje" wrapper="bootstrap3"/>
		<f:display property="retensionIsrTasa"  label="Ret ISR(%)"
			widget-required="true" widget="porcentaje" wrapper="bootstrap3"/>
		<f:display property="descuento"  
			widget="money" wrapper="bootstrap3"/>
		<f:display property="rembolso"  
			widget="money" label="Vales" wrapper="bootstrap3"/>
		<f:display property="fechaRembolso"  
			label="Fecha Vales" wrapper="bootstrap3"/>
		<f:display property="otros"  
			widget="money" wrapper="bootstrap3"/>
		<f:display property="comentarioOtros"
			widget-class="form-control" wrapper="bootstrap3"/>
	</f:with>		
			
</form>