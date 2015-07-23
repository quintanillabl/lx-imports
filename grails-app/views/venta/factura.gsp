
<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'venta.label', default: 'Venta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span2">
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
			
			<div class="span10">

				<div class="page-header">
					<h3>Factura :${ventaInstance?.id}</h3>
				</div>
				

				<dl>
				
					<g:if test="${ventaInstance?.cliente}">
						<dt><g:message code="venta.cliente.label" default="Cliente" /></dt>
						
							<dd><g:link controller="cliente" action="show" id="${ventaInstance?.cliente?.id}">${ventaInstance?.cliente?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.fecha}">
						<dt><g:message code="venta.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${ventaInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.importe}">
						<dt><g:message code="venta.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.descuentos}">
						<dt><g:message code="venta.descuentos.label" default="Descuentos" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="descuentos"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.subtotal}">
						<dt><g:message code="venta.subtotal.label" default="Subtotal" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="subtotal"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.impuestos}">
						<dt><g:message code="venta.impuestos.label" default="Impuestos" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="impuestos"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.total}">
						<dt><g:message code="venta.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="total"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.moneda}">
						<dt><g:message code="venta.moneda.label" default="Moneda" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="moneda"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.tc}">
						<dt><g:message code="venta.tc.label" default="Tc" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="tc"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.plazo}">
						<dt><g:message code="venta.plazo.label" default="Plazo" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="plazo"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.vencimiento}">
						<dt><g:message code="venta.vencimiento.label" default="Vencimiento" /></dt>
						
							<dd><g:formatDate date="${ventaInstance?.vencimiento}" /></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.formaDePago}">
						<dt><g:message code="venta.formaDePago.label" default="Forma De Pago" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="formaDePago"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.cuentaDePago}">
						<dt><g:message code="venta.cuentaDePago.label" default="Cuenta De Pago" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="cuentaDePago"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.kilos}">
						<dt><g:message code="venta.kilos.label" default="Kilos" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="kilos"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.comentario}">
						<dt><g:message code="venta.comentario.label" default="Comentario" /></dt>
						
							<dd><g:fieldValue bean="${ventaInstance}" field="comentario"/></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.dateCreated}">
						<dt><g:message code="venta.dateCreated.label" default="Date Created" /></dt>
						
							<dd><g:formatDate date="${ventaInstance?.dateCreated}" /></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.lastUpdated}">
						<dt><g:message code="venta.lastUpdated.label" default="Last Updated" /></dt>
						
							<dd><g:formatDate date="${ventaInstance?.lastUpdated}" /></dd>
						
					</g:if>
				
					<g:if test="${ventaInstance?.partidas}">
						<dt><g:message code="venta.partidas.label" default="Partidas" /></dt>
						
							<g:each in="${ventaInstance.partidas}" var="p">
							<dd><g:link controller="ventaDet" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
				</dl>
				
				<g:if test="${ventaInstance.total>0.0}">
					<button  id="#cancelarFacturaBtn" class="btn btn-danger" data-target="#cancelarFacturaDialog" data-toggle="modal">
  					Cancelar CFD
  					</button>
				</g:if>

				
				

			</div>
			
			<div class="modal hide fade" id="cancelarFacturaDialog" tabindex=-1 role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4 class="myModalLabel">Cancelación de factura</h4>
	</div>
	<div class="modal-body">
		<g:form controller="venta" action="cancelar">
			<g:hiddenField name="id" value="${ventaInstance?.id}" />
			<input id="comentario" type="text" name="comentario" value="${ventaInstance?.comentario}" placeholder="Comentario de cancelación" class="input-xxlarge" required="true">
			<div class="form-actions">
				<button class="btn btn-danger" type="submit" name="cancelar">
					<i class="icon-trash icon-white"></i>
					<g:message code="default.button.cancel.label" default="Cancelar" />
				</button>	
			</div>
		</g:form>
	</div>
	
</div>
			<g:jasperReport
							controller="venta"
							action="imprimirCfd"
							jasper="ComprobanteCFD" 
							format="PDF,HTML" 
							name="Imprimir CFD" 
							
							>
							<g:hiddenField name="ID" value="${ventaInstance.id}"/>
							
						</g:jasperReport>

		</div>
	</body>
</html>
