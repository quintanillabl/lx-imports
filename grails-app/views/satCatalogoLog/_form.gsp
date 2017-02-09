<form  class="form-horizontal">
	

	<fieldset>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Ejercicio</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${catalogoInstance.ejercicio}" format="###"/> 
				</p>
			</div>
		</div>
		
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Mes</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${catalogoInstance}" field="mes"/> </p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">RFC</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${catalogoInstance}" field="rfc"/> </p>
			</div>
		</div>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Comentario</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${catalogoInstance}" field="comentario"/> </p>
			</div>
		</div>
		
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Creado</label>
				<p class="form-control-static"><g:formatDate date="${catalogoInstance?.dateCreated}" format="dd/MM/yyyy hh:mm"/> </p>
			<div class="col-sm-8">
			</div>
		</div>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Modificado</label>
				<p class="form-control-static"><g:formatDate date="${catalogoInstance?.lastUpdated}" format="dd/MM/yyyy hh:mm"/> </p>
			<div class="col-sm-8">
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Enviado</label>
				<p class="form-control-static"><g:formatDate date="${catalogoInstance?.enviado}" format="dd/MM/yyyy hh:mm"/> </p>
			<div class="col-sm-8">
			</div>
		</div>
		
		
				
	</fieldset>
</form>

