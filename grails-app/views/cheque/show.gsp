
<%@ page import="com.luxsoft.impapx.tesoreria.Cheque" %>
<%@ page import="luxsoft.cfd.ImporteALetra" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'cheque.label', default: 'Cheque')}" />
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
					<dt>Id</dt>
						<dd>${chequeInstance.id}</dd>
						
					<g:if test="${chequeInstance?.egreso}">
						<dt><g:message code="cheque.egreso.label" default="Egreso" /></dt>
						
							<dd><g:link controller="movimientoDeCuenta" action="show" id="${chequeInstance?.egreso?.id}">${chequeInstance?.egreso?.encodeAsHTML()}</g:link></dd>
						
					</g:if>	
				
					<g:if test="${pago}">
						<dt>Pago Proveedor</dt>
							<dd><g:fieldValue bean="${pago}" field="id"/></dd>
					</g:if>
					
					<g:if test="${pago}">
						<dt>Requisición</dt>
							<dd><g:fieldValue bean="${pago}" field="requisicion"/></dd>
					</g:if>
				
					<g:if test="${chequeInstance?.fechaImpresion}">
						<dt><g:message code="cheque.fechaImpresion.label" default="Fecha Impresion" /></dt>
						
							<dd><g:formatDate date="${chequeInstance?.fechaImpresion}" /></dd>
						
					</g:if>
				
					<g:if test="${chequeInstance?.folio}">
						<dt><g:message code="cheque.folio.label" default="Folio" /></dt>
						
							<dd><g:fieldValue bean="${chequeInstance}" field="folio"/></dd>
					</g:if>
				
					<g:if test="${chequeInstance?.cancelacion}">
						<dt>Cancelación</dt>
							<dd><g:fieldValue bean="${chequeInstance}" field="cancelacion"/></dd>
							<dd><g:fieldValue bean="${chequeInstance}" field="comentarioCancelacion"/></dd>
					</g:if>
				
				</dl>
				
				<g:jasperReport jasper="${chequeInstance.cuenta.nombre}-Cheque" format="PDF" name="Cheque">
						<g:hiddenField name="ID" value="${chequeInstance.id}"/>
						<g:hiddenField name="IMPLETRA" value="${importeALetra}"/>
					</g:jasperReport>
				
				<g:jasperReport jasper="PolizaCheque" format="PDF" name=" Póliza">
						<g:hiddenField name="ID" value="${chequeInstance.id}"/>
						<g:hiddenField name="IMPLETRA" value="${importeALetra}"/>
					</g:jasperReport>
								

			</div>

		</div>
	
	</body>
</html>
