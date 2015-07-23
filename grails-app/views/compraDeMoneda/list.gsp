<%@ page import="com.luxsoft.impapx.tesoreria.CompraDeMoneda" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="compraDeMoneda.list.label" default="Compra de Moneda"/></title>
</head>
<body>
	
	<content tag="header">
		<h3>Tesorería - Compra de moneda extranjera</h3>
 	</content>
	<content tag="consultas">
		<li>
			<g:link controller="movimientoDeCuenta" action="list">
				Tesorería
			</g:link>
		</li>
 		
 	</content>
 	
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de compra</g:link></li>
 	</content>
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
 		<table class="table table-striped table-hover table-bordered table-condensed">
			<thead>
			<tr>
				<g:sortableColumn property="id" title="${"Id"}" />
				<g:sortableColumn property="fecha" title="${"Fecha"}" />
				<g:sortableColumn property="requisicion.proveedor" title="${"Proveedor"}" />
				<g:sortableColumn property="requisicion.id" title="${"Req"}" />
				<th class="header">Mon</th>
				<th class="header">Cuenta Origen</th>
				<th class="header">Cuenta Destino</th>
				<th class="header">Total</th>
				<th class="header">Dif Cambiaria</th>
			</tr>
			</thead>
			<tbody>
			<g:each in="${compraDeMonedaInstanceList}"
				var="row">
				<tr>
					<td>
						<g:link action="show" id="${row.id}">
							${fieldValue(bean: row, field: "id")}
						</g:link>
					</td>
					<td><lx:shortDate date="${row.fecha }"/></td>
					<td>${fieldValue(bean: row, field: "requisicion.proveedor")}</td>
					<td>${fieldValue(bean: row, field: "requisicion.id")}</td>
					<td>${fieldValue(bean: row, field: "moneda")}</td>
					<td>${fieldValue(bean: row, field: "cuentaOrigen")}</td>
					<td>${fieldValue(bean: row, field: "cuentaDestino")}</td>
					<td><lx:moneyFormat number="${row.requisicion.total}"/></td>
					<td><lx:moneyFormat number="${diferenciaCambiaria}"/></td>
				</tr>
			</g:each>
		</tbody>
			
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${compraDeMonedaInstanceTotal}" />
		</div>
		
 	</content>
	
</body>
</html>