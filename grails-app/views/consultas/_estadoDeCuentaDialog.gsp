<%@page expressionCodec="none"%>
<div class="modal fade" id="estadoDeCuentaDialog" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel"> ${proveedorInstance.nombre} </h4>
				<p>Estado de cuenta</p>
			</div>

			<g:form class="form-horizontal" action="estadoDeCuentaProveedor" >
				<g:hiddenField name="proveedor" value="${proveedorInstance.id}"/>
				<div class="modal-body">

					<div class="form-group" id="data_1">
                        <label class="font-noraml">Corte</label>
                        <div class="input-group date">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" name="fechaCorte"
                            	value="${formatDate(date:new Date(),format:'dd/MM/yyyy')}">
                        </div>
					</div>


					
				</div>	
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<g:submitButton class="btn btn-primary" name="aceptar" value="Aceptar" />
				</div>
			</g:form>

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->

</div>

