<form  class="form-horizontal">
	

	<fieldset>
		<legend> Datos de control </legend>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Total de polizas</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${satPolizasLogInstance.asPolizas().getPoliza().size()}" format="###"/> 
				</p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Debe</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber 
						number="${ satPolizasLogInstance.asPolizas().getPoliza().sum{it.getTransaccion().sum{t-> t.debe}} }" 
						type="currency"/> 
				</p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Haber</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber 
						number="${satPolizasLogInstance.asPolizas().getPoliza().sum{it.getTransaccion().sum{t-> t.haber}}}" 
						type="currency"/> 
				</p>
			</div>
		</div>
		

				
	</fieldset>
</form>

