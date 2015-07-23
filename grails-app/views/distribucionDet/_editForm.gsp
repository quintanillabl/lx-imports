<fieldset>
	<g:form class="form-horizontal" action="edit" id="${distribucionDetInstance.id }">
		<fieldset>
			<f:with bean="${distribucionDetInstance}">
				<f:field property="embarqueDet.producto" >
					<g:field name="embarqueDet.producto" type="text" 
						value="${distribucionDetInstance?.embarqueDet?.producto}" readOnly="true"
						class="input-xxlarge"/>
				</f:field>
				
				<f:field property="contenedor" input-readOnly="true"/>
				<f:field property="cantidad" input-readOnly="true"/>
				<f:field property="tarimas" input-readOnly="true"/>
				<f:field property="kilosNetos" input-readOnly="true"/>
				<f:field property="comentarios" input-readOnly="true"/>
				<f:field property="instrucciones" input-class="input-xxlarge" />
			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary" onclick="return validar();">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Actualizar" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>
