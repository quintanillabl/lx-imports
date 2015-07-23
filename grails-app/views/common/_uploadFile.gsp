<div class="modal fade" id="uploadFileDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Selección de archivo para importar</h4>
			</div>
			
			<g:uploadForm class="form" action="uploadFile" >
				<div class="modal-body">
					
					<div class="form-group">
					    <label for="inputFile">Archivo de importación</label>
					    <input type="file" id="inputFile" name="file" accept="application/txt" autocomplete="off">
					    <p class="help-block">Seleccion el archivo para cargar</p>
					 </div> 
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<g:submitButton class="btn btn-primary" name="aceptar"value="Aceptar" />
				</div>
			</g:uploadForm>


		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>
