<%@ page import="com.luxsoft.impapx.tesoreria.MovimientoDeCuenta" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<g:set var="entityName"
	value="${message(code: 'movimientoDeCuenta.label', default: 'MovimientoDeCuenta')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<content tag="header">
		<div class="page-header">
			<h3>Pagos a proveedores</h3>
		</div>
 		
 	</content>
	<content tag="consultas">
 		<g:render template="/movimientoDeCuenta/consultas"/>
 	</content>
 	<content tag="operaciones">
 		<li>
 			<g:link class="" action="create">
				<i class="icon-plus "></i>
				Generar pago
			</g:link>
		</li>
 	</content>
 	<content tag="document">
	<table
		class="table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<g:sortableColumn property="id" title="${"Id"}" />
				<g:sortableColumn property="cuenta" title="${"Cuenta"}" />
				<th class="header"><g:message code="movimientoDeCuenta.moneda.label" default="Mon" /></th>
				<th class="header"><g:message code="movimientoDeCuenta.tc.label" default="TC" /></th>
				<g:sortableColumn property="fecha" title="${"Fecha"}" />
				<g:sortableColumn property="origen" title="${"Origen"}" />
				<g:sortableColumn property="tipo" title="${"Tipo"}" />
				<th class="header"><g:message code="movimientoDeCuenta.importe.label" default="Importe" /></th>
				<th class="header"><g:message code="movimientoDeCuenta.comentario.label" default="Comentario" /></th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${movimientoDeCuentaInstanceList}"
				var="movimientoDeCuentaInstance">
				<tr>
					<td><g:link action="show"
							id="${movimientoDeCuentaInstance.id}">
							${fieldValue(bean: movimientoDeCuentaInstance, field: "id")}
						</g:link></td>
					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "cuenta")}
					</td>
					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "moneda")}
					</td>

					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "tc")}
					</td>

					<td>
						<lx:shortDate date="${movimientoDeCuentaInstance.fecha }"/>
					</td>

					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "origen")}
					</td>

					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "tipo")}
					</td>
					<td><lx:moneyFormat number="${movimientoDeCuentaInstance.importe }"/></td>
					<td>
						${fieldValue(bean: movimientoDeCuentaInstance, field: "comentario")}
					</td>

				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bootstrap:paginate total="${movimientoDeCuentaInstanceTotal}" />
	</div>
	</content>
 	
	
</body>
</html>
