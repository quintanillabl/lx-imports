
<%@ page import="com.luxsoft.impapx.cxp.ConceptoDeGasto" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'conceptoDeGasto.label', default: 'ConceptoDeGasto')}" />
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
				
					<g:if test="${conceptoDeGastoInstance?.concepto}">
						<dt><g:message code="conceptoDeGasto.concepto.label" default="Concepto" /></dt>
						
							<dd><g:link controller="cuentaContable" action="show" id="${conceptoDeGastoInstance?.concepto?.id}">${conceptoDeGastoInstance?.concepto?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.descripcion}">
						<dt><g:message code="conceptoDeGasto.descripcion.label" default="Descripcion" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="descripcion"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.egreso}">
						<dt><g:message code="conceptoDeGasto.egreso.label" default="Egreso" /></dt>
						
							<dd><g:link controller="movimientoDeCuenta" action="show" id="${conceptoDeGastoInstance?.egreso?.id}">${conceptoDeGastoInstance?.egreso?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.tipo}">
						<dt><g:message code="conceptoDeGasto.tipo.label" default="Tipo" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="tipo"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.factura}">
						<dt><g:message code="conceptoDeGasto.factura.label" default="Factura" /></dt>
						
							<dd><g:link controller="facturaDeGastos" action="show" id="${conceptoDeGastoInstance?.factura?.id}">${conceptoDeGastoInstance?.factura?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.ietu}">
						<dt><g:message code="conceptoDeGasto.ietu.label" default="Ietu" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="ietu"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.importe}">
						<dt><g:message code="conceptoDeGasto.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.impuesto}">
						<dt><g:message code="conceptoDeGasto.impuesto.label" default="Impuesto" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="impuesto"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.impuestoTasa}">
						<dt><g:message code="conceptoDeGasto.impuestoTasa.label" default="Impuesto Tasa" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="impuestoTasa"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.retension}">
						<dt><g:message code="conceptoDeGasto.retension.label" default="Retension" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="retension"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.retensionIsr}">
						<dt><g:message code="conceptoDeGasto.retensionIsr.label" default="Retension Isr" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="retensionIsr"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.retensionIsrTasa}">
						<dt><g:message code="conceptoDeGasto.retensionIsrTasa.label" default="Retension Isr Tasa" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="retensionIsrTasa"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.retensionTasa}">
						<dt><g:message code="conceptoDeGasto.retensionTasa.label" default="Retension Tasa" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="retensionTasa"/></dd>
						
					</g:if>
				
					<g:if test="${conceptoDeGastoInstance?.total}">
						<dt><g:message code="conceptoDeGasto.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${conceptoDeGastoInstance}" field="total"/></dd>
						
					</g:if>
				
				</dl>

				<g:form>
					<g:hiddenField name="id" value="${conceptoDeGastoInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="editConcepto" id="${conceptoDeGastoInstance?.id}">
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
