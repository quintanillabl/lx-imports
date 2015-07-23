<fieldset>
	<g:form class="form-horizontal" action="edit"
		id="${compraDetInstance?.id}">
		<g:hiddenField name="version" value="${compraDetInstance?.version}" />
		<fieldset>
			<f:with bean="${compraDetInstance}">
				<f:field property="producto"/>
				<f:field property="solicitado"/>
				<f:field property="entregado" input-readonly="true"/>
			</f:with>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<i class="icon-ok icon-white"></i>
					<g:message code="default.button.update.label" default="Update" />
				</button>
				<button type="submit" class="btn btn-danger" name="_action_delete"
					formnovalidate>
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.delete.label" default="Delete" />
				</button>
			</div>
		</fieldset>
	</g:form>
</fieldset>