<%@ page import="com.luxsoft.impapx.Pedimento"%>
<fieldset>
	<g:form class="form-horizontal" action="create">
		<fieldset>
			<f:with bean="${pedimentoInstance}" >
				<g:if test="${pedimentoInstance.id}">
					<f:field property="id" input-disabled="true"/>
				</g:if>
				
				<f:field property="pedimento" input-autofocus="true"/>
				<f:field property="proveedor" input-required="true" label="Agente aduanal"/>
				<f:field property="fecha"/>
				<f:field property="dta"/>
				<f:field property="prevalidacion"/>
				<f:field property="arancel"/>
				<f:field property="tipoDeCambio"/>
				<f:field property="impuestoTasa"/>
				<f:field property="comentario" input-class="input-xxlarge"/>
			</f:with>
			<div class="form-actions">
				
				<g:if test="${!pedimentoInstance.id}">
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						<g:message code="default.button.create.label" default="Create" />
					</button>	
				</g:if>
				<g:else>
					<button type="submit" class="btn btn-primary">
						<i class="icon-ok icon-white"></i>
						<g:message code="default.button.create.label" default="Salvar" />
					</button>
					<button type="submit" class="btn btn-danger" name="_action_delete" formnovalidate>
						<i class="icon-trash icon-white"></i>
						<g:message code="default.button.delete.label" default="Delete" />
					</button>
					
				</g:else>
				
			</div>
		</fieldset>
	</g:form>
</fieldset>