
<%@ page import="com.luxsoft.impapx.contabilidad.CuentaContable" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'cuentaContable.label', default: 'CuentaContable')}" />
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
				
					<g:if test="${cuentaContableInstance?.clave}">
						<dt><g:message code="cuentaContable.clave.label" default="Clave" /></dt>
						
							<dd><g:fieldValue bean="${cuentaContableInstance}" field="clave"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.descripcion}">
						<dt><g:message code="cuentaContable.descripcion.label" default="Descripcion" /></dt>
						
							<dd><g:fieldValue bean="${cuentaContableInstance}" field="descripcion"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.detalle}">
						<dt><g:message code="cuentaContable.detalle.label" default="Detalle" /></dt>
						
							<dd><g:formatBoolean boolean="${cuentaContableInstance?.detalle}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.tipo}">
						<dt><g:message code="cuentaContable.tipo.label" default="Tipo" /></dt>
						
							<dd><g:fieldValue bean="${cuentaContableInstance}" field="tipo"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.subTipo}">
						<dt><g:message code="cuentaContable.subTipo.label" default="Sub Tipo" /></dt>
						
							<dd><g:fieldValue bean="${cuentaContableInstance}" field="subTipo"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.naturaleza}">
						<dt><g:message code="cuentaContable.naturaleza.label" default="Naturaleza" /></dt>
						
							<dd><g:fieldValue bean="${cuentaContableInstance}" field="naturaleza"/></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.dateCreated}">
						<dt><g:message code="cuentaContable.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${cuentaContableInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.deResultado}">
						<dt><g:message code="cuentaContable.deResultado.label" default="De Resultado" /></dt>
						
							<dd><g:formatBoolean boolean="${cuentaContableInstance?.deResultado}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.lastUpdated}">
						<dt><g:message code="cuentaContable.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${cuentaContableInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.padre}">
						<dt><g:message code="cuentaContable.padre.label" default="Padre" /></dt>
						
							<dd><g:link controller="cuentaContable" action="show" id="${cuentaContableInstance?.padre?.id}">${cuentaContableInstance?.padre?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.presentacionContable}">
						<dt><g:message code="cuentaContable.presentacionContable.label" default="Presentacion Contable" /></dt>
						
							<dd><g:formatBoolean boolean="${cuentaContableInstance?.presentacionContable}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.presentacionFinanciera}">
						<dt><g:message code="cuentaContable.presentacionFinanciera.label" default="Presentacion Financiera" /></dt>
						
							<dd><g:formatBoolean boolean="${cuentaContableInstance?.presentacionFinanciera}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.presentacionFiscal}">
						<dt><g:message code="cuentaContable.presentacionFiscal.label" default="Presentacion Fiscal" /></dt>
						
							<dd><g:formatBoolean boolean="${cuentaContableInstance?.presentacionFiscal}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.presentacionPresupuestal}">
						<dt><g:message code="cuentaContable.presentacionPresupuestal.label" default="Presentacion Presupuestal" /></dt>
						
							<dd><g:formatBoolean boolean="${cuentaContableInstance?.presentacionPresupuestal}" /></dd>
						
					</g:if>
				
					<g:if test="${cuentaContableInstance?.subCuentas}">
						<dt><g:message code="cuentaContable.subCuentas.label" default="Sub Cuentas" /></dt>
						
							<g:each in="${cuentaContableInstance.subCuentas}" var="s">
							<dd><g:link controller="cuentaContable" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${cuentaContableInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${cuentaContableInstance?.id}">
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
