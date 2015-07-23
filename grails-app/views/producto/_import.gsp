<div class="modal fade" id="importDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Importar PRODUCTO por clave</h4>
			</div>
			<g:form controller="importador" action="importarProducto" class="form-horizontal" >
				<div class="modal-body">
					<div class="form-group">
						<label for="clave" class="control-label col-sm-2">Clave</label>
						<div class="col-sm-10">
							<input name="clave" class="form-control" value="">
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-primary" name="aceptar"
							value="Importar" />
				</div>
			</g:form>

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>

%{-- <div class="modal hide fade" id="importarDialog" tabindex=-1
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h4 class="myModalLabel">Importar Producto desde Siipap</h4>
		</div>
		<div class="">
			<g:form controller="importador" action="importarProducto"
				name="importarForm">
				<input id="clave" class="span5" type="text" name="clave" value=""
					placeholder="Digite la clave a importar" autofocus="autofocus" required="true">
				<div class="form-actions">
					<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
					<button type="submit" class="btn btn-primary">
						<i class="icon-download icon-white"></i> Importar
					</button>
				</div>
			</g:form>
		</div> --}%