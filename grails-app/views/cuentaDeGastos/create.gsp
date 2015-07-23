<%@ page import="com.luxsoft.impapx.CuentaDeGastos" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'cuentaDeGastos.label', default: 'Cuenta')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span2">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Operaciones</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								<g:message code="cunetaDeGastos.list.label" default="Lista"/>
							</g:link>
						</li>
						<li class="active">
							<g:link class="create" action="create">
								<i class="icon-plus icon-white"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span10">

				<div class="page-header">
					<h3><g:message code="cuentaDeGastos.create.label" default="Alta de cuenta de gastos"/></h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${cuentaDeGastosInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${cuentaDeGastosInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>

				<fieldset>
					<g:form class="form-horizontal" action="create" >
						<fieldset>
							<f:with bean="cuentaDeGastosInstance">
								<f:field property="fecha"/>
								<f:field property="proveedor" label="Agente" input-required="true"/>
								<f:field property="embarque" input-class="span7"/>
								<f:field property="referencia"/>
								<f:field property="comentario">
									<g:textArea name="comentario" value="${cuentaDeGastos?.comentario }" cols="40" rows="4" class="span7"/>
								</f:field>
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									<g:message code="default.button.create.label" default="Create" />
								</button>
							</div>
						</fieldset>
					</g:form>
				</fieldset>
				
			</div>

		</div>
	</body>
</html>
