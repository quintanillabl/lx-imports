

<div class="toolbar-panel">
	<div class="btn-group">
		%{-- <g:if test="${embarqueInstance.facturado<=0}"> --}%
		<g:if test="${embarqueInstance}">
			
			<a href="" data-target="#actualizarPreciosDialog" data-toggle="modal" class="btn btn-primary btn-outilie">
				<i class="fa fa-cog"></i> Actualizar precios
			</a>
			%{-- <g:remoteLink class="btn btn-info" id="${embarqueInstance.id}"
				action="actualizarPrecios" controller="embarque"
				update="costosGrid">
				
				<g:message code="default.button.load.label" default="Actualizar precios" />
			</g:remoteLink> --}%
		</g:if>
		<g:if test="${facturaGastos}">
			<g:link controller='gastosDeImportacion' action="edit" id="${facturaGastos?.id}" target="_blank"
				class="btn btn-default btn-outline">
				Cuenta de gastos: ${facturaGastos?.documento}
			</g:link>
		</g:if>
		
		<g:link action="imprimirAnalisisDeCosteo" class="btn btn-default btn-outline" id="${embarqueInstance.id}">
		    <i class="fa fa-print"></i> Imprimir análisis
		</g:link> 
	</div>
</div>
<div class="row">
	<div class="col-md-12 " >
		<g:render template="costosGrid" bean="${embarqueInstance}"/>	
	</div>
</div>
<div class="modal fade" id="actualizarPreciosDialog" tabindex="-1">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Actualizar precios</h4>
			</div>
			<g:form action="actualizarPrecios" class="form-horizontal" >
				<g:hiddenField name="id" value="${embarqueInstance.id}" />
				<div class="modal-body">
					<p>Este proceso actualizará los precios de venta de los productos</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<g:submitButton class="btn btn-info" name="aceptar"
							value="Actualizar" />
				</div>
			</g:form>

		</div>
		<!-- moda-content -->
	</div>
	<!-- modal-di -->
</div>
