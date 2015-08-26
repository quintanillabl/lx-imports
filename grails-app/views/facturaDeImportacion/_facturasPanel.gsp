<div id="facturasPanel">
	<table id="grid"
		class="display table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<g:sortableColumn property="id" title="Id" />
				<g:sortableColumn property="proveedor.nombre" title="Proveedor" />

				<th class="header">Dcto</th>
				<th class="header">Fecha</th>
				<th class="header">BL</th>
				<th class="header">Vto</th>
				<th class="header">Moneda</th>
				<th>T.C.</th>
				<th class="header">Total</th>
				<th class="header">Pagos</th>
				<th class="header">Saldo</th>
				<th>Requisitado</th>
				<th class="header">Modificada</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${facturasList}" var="row">
				<tr>
					<td>
						<g:link action="show" id="${row.id}">
							<lx:idFormat id="${row.id}"/>
						</g:link></td>
					<td><g:link action="show" id="${row.id}">
							${fieldValue(bean: row, field: "proveedor.nombre")}
						</g:link>
					</td>
					<td>${fieldValue(bean: row, field: "documento")}</td>
					<td><lx:shortDate date="${row.fecha }"/></td>
					<td><lx:shortDate date="${row.fechaBL }"/></td>
					<td><lx:shortDate date="${row.vencimiento }"/></td>
					<td>
						${fieldValue(bean: row, field: "moneda")}
					</td>
					<td>${row.tc}</td>
					<td><lx:moneyFormat number="${row.total}"/></td>
					<td><lx:moneyFormat number="${row.pagosAplicados}"/></td>
					<td><lx:moneyFormat number="${row.saldoActual}"/></td>
					
					<td><lx:moneyFormat number="${row.requisitado}"/></td>
					<td><abbr title="${g.formatDate(date:row.lastUpdated)}">
						...</abbr></td>
				</tr>
			</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${facturaDeImportacionInstanceCount ?: 0}" />
	</div>
</div>

