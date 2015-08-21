<%@ page import="com.luxsoft.impapx.CuentaDeGastos" %>
<!doctype html>
<html>
<head>
	<title>Cuentas de gastos</title>
	<meta name="layout" content="operaciones">
</head>

<content tag="header">
	Cuentas de gastos 
</content>
<content tag="periodo">
	Periodo: ${session.periodo.mothLabel()}
</content>

<content tag="grid">
	<table id="grid" class="table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th>Folio</th>
				<th>Embarque</th>
				<th>BL</th>
				<th>Agente</th>
				<th>Referencia</th>
				<th>Fecha</th>
				<th>Total</th>
				<th>Comentario</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${cuentaDeGastosInstanceList}" var="cuentaDeGastosInstance">
				<tr>
					<td>${fieldValue(bean: cuentaDeGastosInstance, field: "id")}</td>
					<td><g:link action="edit" id="${cuentaDeGastosInstance.id}">
						${fieldValue(bean: cuentaDeGastosInstance, field: "embarque.id")}
						</g:link>
					</td>
					<td><g:link action="edit" id="${cuentaDeGastosInstance.id}">
						${fieldValue(bean: cuentaDeGastosInstance, field: "embarque.bl")}
						</g:link>
					</td>
					<td>${fieldValue(bean: cuentaDeGastosInstance, field: "proveedor.nombre")}</td>
					<td>${fieldValue(bean: cuentaDeGastosInstance, field: "referencia")}</td>
					<td><lx:shortDate date="${cuentaDeGastosInstance.fecha}" /></td>
					<td><lx:moneyFormat number="${cuentaDeGastosInstance.total }"/></td>
					<td>
						<g:link action="edit" id="${cuentaDeGastosInstance.id}">
							${fieldValue(bean: cuentaDeGastosInstance, field: "comentario")}
						</g:link>
					</td>
					
				</tr>
			</g:each>
		</tbody>
	</table>
</content>
<content tag="searchService">
	<g:createLink action="search"/>
</content>

</html>
