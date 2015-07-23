<%@ page import="com.luxsoft.impapx.tesoreria.Cheque"%>
<div class="btn-group">
	<a href="#cancelarDialog" data-toggle="modal" class="btn btn-danger">
		<i class="icon-trash icon-white"></i>
		Cancelar
	</a>
	
</div>

<div id="cancelarDialog" class="modal hide fade" role="dialog"
	aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4>Cancelar cheque</h4>
	</div>

	<div class="modal-body">
		<fieldset>
			<g:form action="cancelar" class="form-horizontal" >
				<g:hiddenField name="id" value="${chequeInstance.id}" />
				<f:with bean="${new Cheque()}">
					<f:field property="motivoCancelacion" input-class="input-xlarge" input-required="true"/>
				</f:with>
				<div class="form-actions">
					<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						Aceptar
					</button>
				</div>
			</g:form>
		</fieldset>
	</div>
	
</div>