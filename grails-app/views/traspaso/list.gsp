<%@ page import="com.luxsoft.impapx.tesoreria.Traspaso" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'traspaso.label', default: 'Traspaso')}" />
<title><g:message code="traspaso.list.label" default="Traspaso entre cuentas"/></title>
</head>
<body>
	
	<content tag="header">
		<h3>Tesorería (Traspaso entre cuentas)</h3>
 	</content>
	<content tag="consultas">
 		<g:render template="/movimientoDeCuenta/consultas"/>
 	</content>
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de traspaso</g:link></li>
 	</content>
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
 		<table class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<g:sortableColumn property="folio" title="${message(code: 'traspaso.comentario.label', default: 'Folio')}" />
					<g:sortableColumn property="fecha" title="${message(code: 'traspaso.fecha.label', default: 'Fecha')}" />
					<th class="header"><g:message code="traspaso.cuentaOrigen.label" default="Cuenta Origen" /></th>
					<th class="header"><g:message code="traspaso.cuentaDestino.label" default="Cuenta Destino" /></th>
					<g:sortableColumn property="importe" title="${message(code: 'traspaso.comision.label', default: 'Importe')}" />
					<g:sortableColumn property="comision" title="${message(code: 'traspaso.comision.label', default: 'Comision')}" />
					<th class="header"><g:message code="traspaso.comentario.label" default="Comentario" /></th>
					
				</tr>
			</thead>
			<tbody>
				<g:each in="${traspasoInstanceList}" var="traspasoInstance">
					<tr>
						<td><g:link action="show" id="${traspasoInstance.id}">${fieldValue(bean: traspasoInstance, field: "id")}</g:link></td>
						<td><lx:shortDate date="${traspasoInstance.fecha}"/></td>
						<td>${fieldValue(bean: traspasoInstance, field: "cuentaOrigen.nombre")} (${fieldValue(bean: traspasoInstance, field: "cuentaOrigen.numero")})</td>
						<td>${fieldValue(bean: traspasoInstance, field: "cuentaDestino.nombre")} (${fieldValue(bean: traspasoInstance, field: "cuentaDestino.numero")})</td>
						
						<td><lx:moneyFormat number="${traspasoInstance.importe}"/></td>
						<td><lx:moneyFormat number="${traspasoInstance.comision}"/></td>
						<td>${fieldValue(bean: traspasoInstance, field: "comentario")}</td>
						
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${traspasoInstanceTotal}" />
		</div>
		
 	</content>
	
</body>
</html>
