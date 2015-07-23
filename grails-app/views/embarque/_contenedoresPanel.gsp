<div class="toolbar-panel">
	<div class="btn-group">
		<g:if test="${embarqueInstance.facturado<=0}">
			<g:remoteLink class="btn btn-success" id="${embarqueInstance.id}"
			action="contenedoresDeEmbarque" controller="embarque"
			update="contenedoresGrid">
			<i class="icon-refresh icon-white"></i>
			<g:message code="default.button.load.label" default="Refrescar" />
		</g:remoteLink>
		</g:if>
		
		<g:link controller='gastosDeImportacion' action="edit" id="${facturaGastos?.id}" target="_blank"
			class="btn btn-default">
			Cuenta de gastos: ${facturaGastos?.documento}
		</g:link>
		<g:link action="print" class="btn btn-default" id="${embarqueInstance.id}">
		    <i class="fa fa-print"></i> Imprimir
		</g:link> 
		
	</div>
</div>
<div class="row">
	<div class="col-md-12 " >
		<div id="contenedoresGrid">
			<g:render template="contenedoresGrid" bean="${embarqueInstance}"/>
		</div>
	</div>
</div>


