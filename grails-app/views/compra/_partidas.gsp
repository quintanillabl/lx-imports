<table id="compraDetTable" 
	class=" grid table table-striped table-bordered table-condensed" cellpadding="0" cellspacing="0" border="0">
	<thead>
		<tr>
			<th><g:message code="producto.clave.label" default="Clave" /></th>
			<th><g:message code="producto.descripcion.label" default="Descripción" /></th>
			<th><g:message code="producto.unidad.label" default="Unidad" /></th>
			<th><g:message code="compraDet.solicitado" default="Solicitado" /></th>
			<th><g:message code="compraDet.entregado.label" default="Entregado" /></th>
			<th><g:message code="compraDet.pendiente.label" default="Pendiente" /></th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${compraInstance.partidas}" var="row">
		<tr id="${row.id }">
			<td>
				<g:if test="${webRequest.actionName=='edit'}">
					<g:link controller="compraDet" action="edit" id="${row.id}">
					${fieldValue(bean:row, field:"producto.clave")}
					</g:link>
				</g:if>
				<g:else>
					${fieldValue(bean:row, field:"producto.clave")}
				</g:else>
			</td>
			<td>${fieldValue(bean:row, field:"producto.descripcion")}</td>
			<td>${fieldValue(bean:row, field:"producto.unidad.clave")}</td>
			<td><g:formatNumber number="${row.solicitado}" format="###,####,###"/></td>
			<td><g:formatNumber number="${row.entregado}" format="###,####,###"/></td>
			<td><g:formatNumber number="${row.pendiente}" format="###,####,###"/></td>
		</tr>
		</g:each>
	</tbody>
</table>