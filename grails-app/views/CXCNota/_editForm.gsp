<div class="row">
	<div class="col-md-6">
		<br>
		<form class="form-horizontal"  id="${CXCNotaInstance?.id}">
			<f:with bean="CXCNotaInstance">
				<f:display property="cliente" />
				<f:display property="fecha"/>
				<f:display property="moneda"/>
				<f:display property="tc"/>
				<f:display property="total" />
				<f:display property="aplicado" />
				<f:display property="disponible" />
				<f:display property="comentario"/>
			</f:with>
		</form>
		<div class="col-md-6 col-md-offset-2">
			<div class="btn-group">
    			%{-- <g:if test="${!CXCNotaInstance.cfdi}">
    				<g:link  action="generarCFDI" class="btn btn-info btn-outline" 
    					onclick="return confirm('Generar comprobante fiscal: ${CXCNotaInstance.id}');"
    					id="${CXCNotaInstance.id}">
    		 				Generar CFDI
    					</g:link>
    				<lx:deleteButton bean="${CXCNotaInstance}"/>
    			</g:if>
    			<g:else>
    				<g:link  action="cancelarCFDI" class="btn btn-info" 
    					onclick="return confirm('Generar comprobante fiscal: ${CXCNotaInstance.id}');"
    					id="${CXCNotaInstance.id}">
    		 				Cancelar CFD
    					</g:link>
    			</g:else> --}%
		 	</div>
		</div>
		 		    	
	</div>
</div>

