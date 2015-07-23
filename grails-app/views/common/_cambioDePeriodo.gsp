<%@page expressionCodec="none"%>
<div class="modal fade" id="periodoDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Meses</h4>
			</div>
			<g:form controller="poliza" action="actualizarPeriodo" class="form-horizontal">
				<div class="modal-body">
					<f:with bean="${it}">
						<f:field property="mes" widget-class="form-control"/>
						<f:field property="ejercicio" widget-class="form-control"/>
					</f:with>
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
