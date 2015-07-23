
<%@ page import="com.luxsoft.impapx.tesoreria.SaldoDeCuenta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'saldoDeCuenta.label', default: 'SaldoDeCuenta')}" />
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
				
					<g:if test="${saldoDeCuentaInstance?.cierre}">
						<dt><g:message code="saldoDeCuenta.cierre.label" default="Cierre" /></dt>
						
							<dd><g:formatDate date="${saldoDeCuentaInstance?.cierre}" /></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.cuenta}">
						<dt><g:message code="saldoDeCuenta.cuenta.label" default="Cuenta" /></dt>
						
							<dd><g:link controller="cuentaBancaria" action="show" id="${saldoDeCuentaInstance?.cuenta?.id}">${saldoDeCuentaInstance?.cuenta?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.dateCreated}">
						<dt><g:message code="saldoDeCuenta.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${saldoDeCuentaInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.egresos}">
						<dt><g:message code="saldoDeCuenta.egresos.label" default="Egresos" /></dt>
						
							<dd><g:fieldValue bean="${saldoDeCuentaInstance}" field="egresos"/></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.ingresos}">
						<dt><g:message code="saldoDeCuenta.ingresos.label" default="Ingresos" /></dt>
						
							<dd><g:fieldValue bean="${saldoDeCuentaInstance}" field="ingresos"/></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.lastUpdated}">
						<dt><g:message code="saldoDeCuenta.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${saldoDeCuentaInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.mes}">
						<dt><g:message code="saldoDeCuenta.mes.label" default="Mes" /></dt>
						
							<dd><g:fieldValue bean="${saldoDeCuentaInstance}" field="mes"/></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.saldoFinal}">
						<dt><g:message code="saldoDeCuenta.saldoFinal.label" default="Saldo Final" /></dt>
						
							<dd><g:fieldValue bean="${saldoDeCuentaInstance}" field="saldoFinal"/></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.saldoInicial}">
						<dt><g:message code="saldoDeCuenta.saldoInicial.label" default="Saldo Inicial" /></dt>
						
							<dd><g:fieldValue bean="${saldoDeCuentaInstance}" field="saldoInicial"/></dd>
						
					</g:if>
				
					<g:if test="${saldoDeCuentaInstance?.year}">
						<dt><g:message code="saldoDeCuenta.year.label" default="Year" /></dt>
						
							<dd><g:fieldValue bean="${saldoDeCuentaInstance}" field="year"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${saldoDeCuentaInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${saldoDeCuentaInstance?.id}">
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
