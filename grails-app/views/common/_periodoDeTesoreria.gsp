<div class="modal fade" id="periodoDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Cambiar periodo</h4>
			</div>
			<g:form action="cambiarPeriodo" class="form-horizontal" >
				<div class="modal-body">
					<div class="form-group" id="data_4">
                        <label class="font-noraml">Seleccione un mes</label>
                        <div class="input-group date">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" name="fecha"
                            	value="${formatDate(date:session.periodoTesoreria,format:'dd/MM/yyyy')}">
                        </div>
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-info" name="aceptar"
							value="Aceptar" />
				</div>
			</g:form>

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>