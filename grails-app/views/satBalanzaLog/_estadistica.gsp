<form  class="form-horizontal">
	

	<fieldset>
		<legend> Datos de control </legend>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Total de cuentas</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${balanza.getCtas().size()}" format="###"/> 
				</p>
			</div>
		</div>
		
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Cuents de mayor</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${balanza.getCtas().findAll{it.numCta.endsWith('0')}.size()}" format="###"/> 
				</p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Sub Cuentas</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${balanza.getCtas().findAll{!it.numCta.endsWith('0')}.size()}" format="###"/> 
				</p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Debe</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${balanza.getCtas().findAll{it.numCta.endsWith('0')}.sum{it.debe}}" type="currency"/> 
				</p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Haber</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${balanza.getCtas().findAll{!it.numCta.endsWith('0')}.sum{it.haber}}" type="currency"/> 
				</p>
			</div>
		</div>
				
	</fieldset>
</form>

