<form  class="form-horizontal">
	

	<fieldset>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Ejercicio</label>
			<div class="col-sm-8">
				<p class="form-control-static">
					<g:formatNumber number="${satPolizasLogInstance.ejercicio}" format="###"/> 
				</p>
			</div>
		</div>
		
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Mes</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${satPolizasLogInstance}" field="mes"/> </p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">RFC</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${satPolizasLogInstance}" field="rfc"/> </p>
			</div>
		</div>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Tipo</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${satPolizasLogInstance}" field="tipo"/> </p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Número de tramite</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${satPolizasLogInstance}" field="numeroDeTramite"/> </p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Número de orden</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${satPolizasLogInstance}" field="numeroDeOrden"/> </p>
			</div>
		</div>

		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Comentario</label>
			<div class="col-sm-8">
				<p class="form-control-static"><g:fieldValue bean="${satPolizasLogInstance}" field="comentario"/> </p>
			</div>
		</div>
		
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Creado</label>
				<p class="form-control-static"><g:formatDate date="${satPolizasLogInstance?.dateCreated}" format="dd/MM/yyyy hh:mm"/> </p>
			<div class="col-sm-8">
			</div>
		</div>
		<div class="form-group">
			<label for="socio" class="col-sm-4 control-label">Modificado</label>
				<p class="form-control-static"><g:formatDate date="${satPolizasLogInstance?.lastUpdated}" format="dd/MM/yyyy hh:mm"/> </p>
			<div class="col-sm-8">
			</div>
		</div>

		
		
		
				
	</fieldset>
</form>

