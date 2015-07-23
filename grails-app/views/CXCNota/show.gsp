
<%@ page import="com.luxsoft.impapx.cxc.CXCNota" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="luxor">
		<g:set var="entityName" value="${message(code: 'CXCNota.label', default: 'CXCNota')}" />
		<title>Nota : ${CXCNotaInstance.id }</title>
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
					<h3>Nota : ${CXCNotaInstance.id }</h3>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<dl>
				
					<g:if test="${CXCNotaInstance?.importe}">
						<dt><g:message code="CXCNota.importe.label" default="Importe" /></dt>
						
							<dd><g:fieldValue bean="${CXCNotaInstance}" field="importe"/></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.impuesto}">
						<dt><g:message code="CXCNota.impuesto.label" default="Impuesto" /></dt>
						
							<dd><g:fieldValue bean="${CXCNotaInstance}" field="impuesto"/></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.total}">
						<dt><g:message code="CXCNota.total.label" default="Total" /></dt>
						
							<dd><g:fieldValue bean="${CXCNotaInstance}" field="total"/></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.tc}">
						<dt><g:message code="CXCNota.tc.label" default="Tc" /></dt>
						
							<dd><g:fieldValue bean="${CXCNotaInstance}" field="tc"/></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.impuestoTasa}">
						<dt><g:message code="CXCNota.impuestoTasa.label" default="Impuesto Tasa" /></dt>
						
							<dd><g:fieldValue bean="${CXCNotaInstance}" field="impuestoTasa"/></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.comentario}">
						<dt><g:message code="CXCNota.comentario.label" default="Comentario" /></dt>
						
							<dd><g:fieldValue bean="${CXCNotaInstance}" field="comentario"/></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.cfd}">
						<dt><g:message code="CXCNota.cfd.label" default="Cfd" /></dt>
						
							<dd><g:link controller="comprobanteFiscal" action="show" id="${CXCNotaInstance?.cfd?.id}">${CXCNotaInstance?.cfd?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.aplicaciones}">
						<dt><g:message code="CXCNota.aplicaciones.label" default="Aplicaciones" /></dt>
						
							<g:each in="${CXCNotaInstance.aplicaciones}" var="a">
							<dd><g:link controller="CXCAplicacion" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.aplicado}">
						<dt><g:message code="CXCNota.aplicado.label" default="Aplicado" /></dt>
						
							<dd><g:fieldValue bean="${CXCNotaInstance}" field="aplicado"/></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.cliente}">
						<dt><g:message code="CXCNota.cliente.label" default="Cliente" /></dt>
						
							<dd><g:link controller="cliente" action="show" id="${CXCNotaInstance?.cliente?.id}">${CXCNotaInstance?.cliente?.encodeAsHTML()}</g:link></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.disponible}">
						<dt><g:message code="CXCNota.disponible.label" default="Disponible" /></dt>
						
							<dd><g:fieldValue bean="${CXCNotaInstance}" field="disponible"/></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.fecha}">
						<dt><g:message code="CXCNota.fecha.label" default="Fecha" /></dt>
						
							<dd><g:formatDate date="${CXCNotaInstance?.fecha}" /></dd>
						
					</g:if>
				
					<g:if test="${CXCNotaInstance?.moneda}">
						<dt><g:message code="CXCNota.moneda.label" default="Moneda" /></dt>
						<dd><g:fieldValue bean="${CXCNotaInstance}" field="moneda"/></dd>
						
					</g:if>
					
					<g:if test="${CXCNotaInstance.cfdi}">
						<dt>CFDI</dt>
						<dd><g:link controller="cfdi" action="show" id="${CXCNotaInstance.cfdi}">
							${CXCNotaInstance?.cfdi}
							</g:link>
						</dd>
							
					</g:if>
					
				
					<g:if test="${CXCNotaInstance?.partidas}">
						<dt><g:message code="CXCNota.partidas.label" default="Partidas" /></dt>
						
							<g:each in="${CXCNotaInstance.partidas}" var="p">
							<dd><g:link controller="CXCNotaDet" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></dd>
							</g:each>
						
					</g:if>
					
					
				
				</dl>
				
				<g:if test="${CXCNotaInstance.comprobanteFiscal}">
					<button  id="#cancelarCfdBtn" class="btn btn-danger" data-target="#cancelarCfdDialog" data-toggle="modal">
  					Cancelar CFD
  					</button>
				</g:if>
				<g:else>
					<g:form>
					<g:hiddenField name="id" value="${CXCNotaInstance?.id}" />
					<div class="form-actions">
						<g:link class="btn" action="edit" id="${CXCNotaInstance?.id}">
							<i class="icon-pencil"></i>
							<g:message code="default.button.edit.label" default="Edit" />
						</g:link>
						<button class="btn btn-danger" type="submit" name="_action_delete">
							<i class="icon-trash icon-white"></i>
							<g:message code="default.button.delete.label" default="Delete" />
						</button>
					</div>
				</g:form>
				
				</g:else>
				
				<div class="modal hide fade" id="cancelarCfdDialog" tabindex=-1 role="dialog" aria-labelledby="myModalLabel">
					
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="myModalLabel">Cancelación de CFD</h4>
					</div>
					
					<div class="modal-body">
						<g:form controller="venta" action="cancelar">
							<g:hiddenField name="id" value="${CXCNotaInstance?.id}" />
							<input id="comentario" type="text" name="comentario" value="" placeholder="Comentario de cancelación" class="input-xxlarge" required="true">
						<div class="form-actions">	
							<button class="btn btn-danger" type="submit" name="cancelar">
								<i class="icon-trash icon-white"></i>
								<g:message code="default.button.cancel.label" default="Cancelar" />
							</button>
						</div>
						
						</g:form>
					</div>				
			</div>
			<g:if test="${CXCNotaInstance.cfd}">
				<g:jasperReport
							controller="CXCNota"
							action="imprimirCfd"
							jasper="ComprobanteCFD" 
							format="PDF,HTML" 
							name="Imprimir CFD">
				<g:hiddenField name="ID" value="${CXCNotaInstance.id}"/>
							
			</g:jasperReport>
			</g:if>
			

		</div>
	</body>
</html>
