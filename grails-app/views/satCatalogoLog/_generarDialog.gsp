<%@page expressionCodec="none"%>
<div class="modal fade" id="generarDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Alta de cat&aacute;logo SAT (XML)</h4>
			</div>
			<g:form  action="generar" class="form-horizontal">
				<div class="modal-body">
					<f:with bean="${session.periodoContable}">
						<f:field property="mes" widget-class="form-control"/>
						<f:field property="ejercicio" widget-class="form-control"/>
					</f:with>
					<div class="form-group">
						<label for="comentarioField" class="col-sm-2 control-label">Comentario</label>
						<div class="col-sm-10">
							<g:field id="comentarioField" 
								name="comentario" 
								class="form-control"  />
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-primary" name="aceptar"
							value="Aceptar" />
				</div>
			</g:form>


		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>
