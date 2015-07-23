<a href="" class="btn btn-default " data-toggle="modal" data-target="#pendienteDialog">
	<i class="fa fa-search"></i> ${label}
</a> 
<div class="modal fade" id="pendienteDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">${title}</h4>
			</div>

			<g:form action="delete" class="form-horizontal" >
				<g:hiddenField name="id" value="${id}"/>
				<div class="modal-body">
					<p><strong>Pendiente de implementar</strong></p>
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

