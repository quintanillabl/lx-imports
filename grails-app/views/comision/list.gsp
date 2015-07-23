<%@ page import="com.luxsoft.impapx.tesoreria.Comision" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="taskView">
<title><g:message code="comision.list.label" default="Comisiones bancarias"/></title>
</head>
<body>
	
	<content tag="header">
		<h3>Tesorería - Comisiones bancarias</h3>
 	</content>
	<content tag="consultas">
 		<g:render template="/movimientoDeCuenta/consultas"/>
 	</content>
 	<content tag="operaciones">
 		<li><g:link  action="create">Alta de Comisión</g:link></li>
 	</content>
 	<content tag="document">
 		<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
		</g:if>
 		<table class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<g:sortableColumn property="folio" title="${'Folio'}" />
					<g:sortableColumn property="fecha" title="${'Fecha'}" />
					<g:sortableColumn property="cuenta" title="${'Cuenta'}" />
					<th class="header"><g:message code="comision.comision.label" default="Comisión" /></th>
					<th class="header"><g:message code="comision.impuesto.label" default="Impuesto" /></th>
					<th class="header"><g:message code="comision.comentario.label" default="Comentario" /></th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${comisionInstanceList}" var="comisionInstance">
					<tr>
						<td><g:link action="show" id="${comisionInstance.id}">${fieldValue(bean: comisionInstance, field: "id")}</g:link></td>
						<td><lx:shortDate date="${comisionInstance.fecha}"/></td>
						<td>${fieldValue(bean: comisionInstance, field: "cuenta")}</td>
						<td><lx:moneyFormat number="${comisionInstance.comision}"/></td>
						<td><lx:moneyFormat number="${comisionInstance.impuesto}"/></td>
						<td>${fieldValue(bean: comisionInstance, field: "comentario")}</td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<bootstrap:paginate total="${comisionInstanceTotal}" />
		</div>
		
 	</content>
	
</body>
</html>
