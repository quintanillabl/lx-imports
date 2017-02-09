<form  class="form-horizontal">
	

	<fieldset>
		<legend> Datos de control </legend>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Total de cuentas</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${catalogo.getCtas().size()}" format="###"/> 
				</p>
			</div>
		</div>
		
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Cuents de mayor</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${catalogo.getCtas().findAll{it.nivel == 1}.size()}" format="###"/> 
				</p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Sub Cuentas</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${catalogo.getCtas().findAll{it.nivel == 2}.size()}" format="###"/> 
				</p>
			</div>
		</div>
				
	</fieldset>
</form>

