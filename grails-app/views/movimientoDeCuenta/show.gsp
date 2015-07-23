<%@ page import="com.luxsoft.impapx.tesoreria.MovimientoDeCuenta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="taskView">
		<g:set var="entityName" value="${message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<content tag="header">
			<h3><g:message code="movimientoDeCuenta.update.label" default="Movimiento bancario" /></h2>
 		</content>
		
		<content tag="consultas">
			<li>
				<li><g:link  action="list">Movimientos</g:link></li>
			</li>
		</content>
		
		<content tag="operaciones">
 			<li><g:link  action="depositar">Depositar</g:link></li>
 			<li><g:link  action="retirar">Retirar</g:link></li>
 		</content>
		
		<content tag="document">
			<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<fieldset>
				<g:form class="form-horizontal" action="show" id="${movimientoDeCuentaInstance.id }">
					<fieldset>
						<f:with bean="movimientoDeCuentaInstance">
							<g:hiddenField name="ingreso" value="${movimientoDeCuentaInstance?.ingreso }"/>
							<f:field property="cuenta" input-class="span5" input-readOnly="true"/>
							<f:field property="concepto"  input-readOnly="true"/>
							<f:field property="fecha" input-readOnly="true"/>
							<f:field property="tipo" input-readOnly="true"/>
							<f:field property="referenciaBancaria" input-readOnly="true"/>
							<f:field property="tc" input-readOnly="true"/>
							<f:field property="importe" input-readOnly="true"/>
							<f:field property="comentario" input-class="input-xxlarge" input-readOnly="true"/>
						</f:with>
					</fieldset>
				</g:form>
			</fieldset>

				<g:form>
					<g:hiddenField name="id" value="${movimientoDeCuentaInstance?.id}" />
					<div class="form-actions">
						<%-- 
						<g:link class="btn" action="edit" id="${movimientoDeCuentaInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						--%>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
					</div>
				</g:form>
		</content>
		
		
	</body>
</html>
