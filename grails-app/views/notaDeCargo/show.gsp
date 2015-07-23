
<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<title>Nota de cargo</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span3">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Nota de cargo</li>
						<li>
							<g:link class="list" action="list">
								<i class="icon-list"></i>
								Notas
							</g:link>
						</li>
						<li>
							<g:link class="create" action="create">
								<i class="icon-plus"></i>
								Alta de Cargo
							</g:link>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="span9">

				<div class="page-header">
					<h3>Nota de Cargo</h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

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

				<g:form>
					<g:hiddenField name="id" value="${ventaInstance?.id}" />
					<div class="form-actions">
						<g:if test="${!ventaInstance?.factura}">
							<g:link class="btn" action="edit" id="${ventaInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
						</g:if>
						
						
					</div>
				</g:form>

			</div>
			<g:if test="${ventaInstance?.cfd}">
				<g:jasperReport
							controller="venta"
							action="imprimirCfd"
							jasper="ComprobanteCFD" 
							format="PDF,HTML" 
							name="Imprimir CFD" 
							
							>
							<g:hiddenField name="ID" value="${ventaInstance.id}"/>
						</g:jasperReport>
			</g:if>
			
			<g:if test="${ventaInstance?.cfdi}">
				
				<g:jasperReport
						controller="cfdi"
						action="imprimirCfdi"
						jasper="CFDI" 
						format="PDF" 
						name="">
							<g:hiddenField name="id" value="${ventaInstance.cfdi}"/>
						</g:jasperReport>
			</g:if>

		</div>
	</body>
</html>
