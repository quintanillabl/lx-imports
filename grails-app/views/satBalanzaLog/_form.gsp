<form  class="form-horizontal">
	

	<fieldset>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Ejercicio</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${balanzaInstance.ejercicio}" format="###"/> 
				</p>
			</div>
		</div>
		
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Mes</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${balanzaInstance}" field="mes"/> </p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">RFC</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${balanzaInstance}" field="rfc"/> </p>
			</div>
		</div>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Comentario</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${balanzaInstance}" field="comentario"/> </p>
			</div>
		</div>
		
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Creado</label>
				<p class="form-control-static"><g:formatDate date="${balanzaInstance?.dateCreated}" format="dd/MM/yyyy hh:mm"/> </p>
			<div class="col-sm-8">
			</div>
		</div>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Modificado</label>
				<p class="form-control-static"><g:formatDate date="${balanzaInstance?.lastUpdated}" format="dd/MM/yyyy hh:mm"/> </p>
			<div class="col-sm-8">
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Enviado</label>
				<p class="form-control-static"><g:formatDate date="${balanzaInstance?.enviado}" format="dd/MM/yyyy hh:mm"/> </p>
			<div class="col-sm-8">
			</div>
		</div>
		
		
				
	</fieldset>
</form>

