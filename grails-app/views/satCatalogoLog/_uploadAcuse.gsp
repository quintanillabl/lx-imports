<div class="modal fade" id="uploadAcuseDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Cargar Acuse de recepcion del SAT</h4>
			</div>
			
			<g:uploadForm name="uploadAcuse" class="form-horizontal" action="uploadAcuse" >
				<div class="modal-body">
					
					<g:hiddenField name="id" value="${catalogoInstance.id}"/>

					<div class="form-group">
					    <label for="inputFile" class="col-sm-4 control-label"> Catalogo</label>
					    <div class="col-sm-8">
					    	<input type="file" id="inputFile" name="file" accept="application/xml" required 
					    		class="form-control">
					    	<p class="help-block">Seleccion el archivo XML</p>
					    </div>
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
