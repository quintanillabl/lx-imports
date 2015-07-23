<div class="modal fade" id="searchDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Busqueda de productos</h4>
			</div>
			<g:form action="search" class="form-horizontal" >
				<div class="modal-body">
					<f:with bean="${new com.luxsoft.impapx.Producto()}">
						<f:field property="clave" widget-class="form-control" />
						<f:field property="descripcion" widget-class="form-control" />
						<f:field property="linea" widget-class="form-control" />
					</f:with>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-info" name="aceptar"
							value="Buscar" />
				</div>
			</g:form>

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>