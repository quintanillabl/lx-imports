<%@ page import="com.luxsoft.impapx.cxp.Anticipo" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'anticipo.label', default: 'Anticipo')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<r:require module="luxorForms"/>
	</head>
	<body>
		<div class="row-fluid">

			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">${entityName}</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								<g:message code="default.list.label" args="[entityName]" />
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								<g:message code="default.create.label" args="[entityName]" />
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="page-header">
					<h3>Mantenimiento a anticipo: ${anticipoInstance.id }</h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${anticipoInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${anticipoInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>

				<fieldset>
					<g:form class="form-horizontal" action="edit" id="${anticipoInstance?.id}" >
						<g:hiddenField name="version" value="${anticipoInstance?.version}" />
						<fieldset>
							<f:with bean="anticipoInstance">
								
								<f:field property="fecha" input-disabled="true"/>
								<f:field property="requisicion"/>
								<f:field property="sobrante"/>
								<f:field property="complemento"/>
								<f:field property="gastosDeImportacion" >
									<g:field type="text" name="gastosDeImportacion" disabled="true"
										value="${lx.moneyFormat(number:anticipoInstance.gastosDeImportacion) }"/>
								</f:field>
								<f:field property="impuestosAduanales" >
									<g:field type="text" name="impuestosAduanales" disabled="true"
										value="${lx.moneyFormat(number:anticipoInstance.impuestosAduanales) }"/>
								</f:field>
								<f:field property="total" >
									<g:field type="text" name="total" disabled="true"
										value="${lx.moneyFormat(number:anticipoInstance.total) }"/>
								</f:field>
							</f:with>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">
									<i class="icon-ok icon-white"></i>
									<g:message code="default.button.update.label" default="Update" />
								</button>
								<button type="submit" class="btn btn-danger" name="_action_delete" formnovalidate>
									<i class="icon-trash icon-white"></i>
									<g:message code="default.button.delete.label" default="Delete" />
								</button>
							</div>
						</fieldset>
					</g:form>
				</fieldset>

			</div>

		</div>
		
	</body>
</html>
