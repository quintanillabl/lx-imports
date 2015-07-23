

<div class="toolbar-panel">
	<div class="btn-group">
		<g:if test="${embarqueInstance.facturado<=0}">
			%{-- <g:remoteLink class="btn btn-success" id="${embarqueInstance.id}"
				action="cargarPartidas" controller="embarque"
				update="costosGrid">
				<i class="icon-refresh icon-white"></i>
				<g:message code="default.button.load.label" default="Refrescar" />
			</g:remoteLink> --}%
			<g:remoteLink class="btn btn-info" id="${embarqueInstance.id}"
				action="actualizarPrecios" controller="embarque"
				update="costosGrid">
				<i class="icon-check icon-white"></i>
				<g:message code="default.button.load.label" default="Actualizar precios" />
			</g:remoteLink>
		</g:if>
		
		<g:link controller='gastosDeImportacion' action="edit" id="${facturaGastos?.id}" target="_blank"
			class="btn btn-default">
			Cuenta de gastos: ${facturaGastos?.documento}
		</g:link>
		<g:link action="imprimirAnalisisDeCosteo" class="btn btn-default " id="id">
		    <i class="fa fa-print"></i> Imprimir análisis
		</g:link> 
	</div>
</div>
<div class="row">
	<div class="col-md-12 " >
		<g:render template="costosGrid" bean="${embarqueInstance}"/>	
	</div>
</div>
