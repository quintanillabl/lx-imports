<a href="" class="btn btn-danger " data-toggle="modal" data-target="#deleteDialog"><i class="fa fa-trash"></i> Eliminar</a> 
<div class="modal fade" id="deleteDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Eliminar el registro ${it.id}</h4>
			</div>

			<g:form action="delete" class="form-horizontal" method="DELETE">
				<g:hiddenField name="id" value="${it.id}"/>
				<div class="modal-body">
					<p><small>${it}</small></p>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-danger" name="aceptar"
							value="Eliminar" />
				</div>
			</g:form>


		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>
