<div id="facturasPanel">
	<table id="grid"
		class="display table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<td>Id</td>
				<td>Proveedor</td>
				<th>Dcto</th>
				<th>Fecha</th>
				<th>BL</th>
				<th>Vto</th>
				<th>Moneda</th>
				<th>T.C.</th>
				<th>Total</th>
				<th>Pagos</th>
				<th>Saldo</th>
				<th>Requisitado</th>
				<th>Modificada</th>
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
					<td><abbr title="${g.formatDate(date:row.lastUpdated,format:'dd/MM/yy HH:mm')}">
						...</abbr></td>
				</tr>
			</g:each>
		</tbody>
	</table>
	
</div>

