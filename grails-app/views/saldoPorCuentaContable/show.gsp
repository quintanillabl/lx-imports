
<%@ page import="com.luxsoft.impapx.contabilidad.SaldoPorCuentaContable" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'saldoPorCuentaContable.label', default: 'SaldoPorCuentaContable')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
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
					<h3><g:message code="default.show.label" args="[entityName]" /></h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${saldoPorCuentaContableInstance?.debe}">
						<dt><g:message code="saldoPorCuentaContable.debe.label" default="Debe" /></dt>
						
							<dd><g:fieldValue bean="${saldoPorCuentaContableInstance}" field="debe"/></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.haber}">
						<dt><g:message code="saldoPorCuentaContable.haber.label" default="Haber" /></dt>
						
							<dd><g:fieldValue bean="${saldoPorCuentaContableInstance}" field="haber"/></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.saldoInicial}">
						<dt><g:message code="saldoPorCuentaContable.saldoInicial.label" default="Saldo Inicial" /></dt>
						
							<dd><g:fieldValue bean="${saldoPorCuentaContableInstance}" field="saldoInicial"/></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.saldoFinal}">
						<dt><g:message code="saldoPorCuentaContable.saldoFinal.label" default="Saldo Final" /></dt>
						
							<dd><g:fieldValue bean="${saldoPorCuentaContableInstance}" field="saldoFinal"/></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.cierre}">
						<dt><g:message code="saldoPorCuentaContable.cierre.label" default="Cierre" /></dt>
						
							<dd><g:formatDate date="${saldoPorCuentaContableInstance?.cierre}" /></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.cuenta}">
						<dt><g:message code="saldoPorCuentaContable.cuenta.label" default="Cuenta" /></dt>
						
							<dd><g:link controller="cuentaContable" action="show" id="${saldoPorCuentaContableInstance?.cuenta?.id}">${saldoPorCuentaContableInstance?.cuenta?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.dateCreated}">
						<dt><g:message code="saldoPorCuentaContable.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${saldoPorCuentaContableInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.fecha}">
						<dt><g:message code="saldoPorCuentaContable.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${saldoPorCuentaContableInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.lastUpdated}">
						<dt><g:message code="saldoPorCuentaContable.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${saldoPorCuentaContableInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.mes}">
						<dt><g:message code="saldoPorCuentaContable.mes.label" default="Mes" /></dt>
						
							<dd><g:fieldValue bean="${saldoPorCuentaContableInstance}" field="mes"/></dd>
						
					</g:if>
				
					<g:if test="${saldoPorCuentaContableInstance?.year}">
						<dt><g:message code="saldoPorCuentaContable.year.label" default="Year" /></dt>
						
							<dd><g:fieldValue bean="${saldoPorCuentaContableInstance}" field="year"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${saldoPorCuentaContableInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${saldoPorCuentaContableInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
					</div>
				</g:form>

			</div>

		</div>
	</body>
</html>
