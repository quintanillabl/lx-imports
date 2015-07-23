<%@ page import="com.luxsoft.impapx.Requisicion" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title>Anticipos (CxP)</title>
</head>
<body>
 	<content tag="consultas">
 		<g:render template="/cuentaPorPagar/actions"/>
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="" action="createAnticipo">
				<i class="icon-plus "></i>
				Nuevo Anticipo
			</g:link>
		</li>
 	</content>
 	
 	<content tag="document">
 	
 		<h3><g:message code="anticipo.list.label" default="Requisiciones de anticipos"/></h3>
 		<g:if test="${flash.message}">
			<bootstrap:alert class="alert-info">
				${flash.message}
			</bootstrap:alert>
		</g:if>
		<table
			class="table table-striped table-hover table-bordered table-condensed">
			<thead>
			<tr>
				<th class="header"><g:message
						code="requisicion.proveedor.label" default="Proveedor" /></th>
				<g:sortableColumn property="concepto"
					title="${message(code: 'requisicion.concepto.label', default: 'Concepto')}" />
				<g:sortableColumn property="fecha"
					title="${message(code: 'requisicion.fecha.label', default: 'Fecha')}" />
				<g:sortableColumn property="fechaDelPago"
					title="${message(code: 'requisicion.fechaDelPago.label', default: 'Fecha Del Pago')}" />
				<g:sortableColumn property="moneda"
					title="${message(code: 'requisicion.moneda.label', default: 'Moneda')}" />
				<g:sortableColumn property="tc"
					title="${message(code: 'requisicion.tc.label', default: 'Tc')}" />
				<g:sortableColumn property="total"
					title="${message(code: 'requisicion.total.label', default: 'Total')}" />
				<g:sortableColumn property="comentario"
					title="${message(code: 'requisicion.comentario.label', default: 'Comentario')}" />
			</tr>
			</thead>
			<tbody>
			<g:each in="${requisicionInstanceList}" var="requisicionInstance">
				<tr>
					<td><g:link action="show" id="${requisicionInstance.id}">
							${fieldValue(bean: requisicionInstance, field: "proveedor")}
						</g:link>
					</td>
					<td>${fieldValue(bean: requisicionInstance, field: "concepto")}</td>
					<td><lx:shortDate date="${requisicionInstance.fecha}"/></td>
					<td><lx:shortDate date="${requisicionInstance.fechaDelPago}"/></td>
					<td>${fieldValue(bean: requisicionInstance, field: "moneda")}</td>
					<td>${fieldValue(bean: requisicionInstance, field: "tc")}</td>
					<td><lx:moneyFormat number="${requisicionInstance.total}"/></td>
					<td>${fieldValue(bean: requisicionInstance, field: "comentario")}</td>
				</tr>
			</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${requisicionInstanceTotal}" />
		</div>	
	</content>
	

			
</body>
</html>
