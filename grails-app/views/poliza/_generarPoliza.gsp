<div class="modal fade" id="generarDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Generar póliza</h4>
			</div>
			<g:form action="generarPoliza" class="form-horizontal" >
				<div class="modal-body">

					<div class="form-group" id="data_5">
						<label for="fecha" class="control-label col-sm-2">Fecha</label>
						<div class="col-sm-10 ">
							%{-- <input name="fecha" class="form-control " value=""> --}%
							<div class="input-group date">
							    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							    <input id="fecha" type="text" name="fecha" class="form-control" 
							    	value="${session.periodoContable.format('dd/MM/yyyy')}"/>
							</div>
						</div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-info" name="aceptar"
							value="Generar" />
				</div>
			</g:form>

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>	