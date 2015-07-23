<div>
	<table id="facturasTable" class=" simpleGrid table table-striped table-bordered table-condensed" cellpadding="0" cellspacing="0" border="0">
		<thead>
			<tr>
				<th>Documento</th>
				<th>Fecha</th>
				<th>Proveedor</th>
				<th>Total </th>
				<th>Total MN</th>
				
			</tr>
		</thead>
		<tbody>
			<g:each in="${facturas}" var="row">
				<tr id="${fieldValue(bean:row, field:"id")}" >
					<td>${fieldValue(bean:row, field:"documento")}</td>
					<td><lx:shortDate date="${row.fecha}"/></td>
					<td>${fieldValue(bean: row, field: "proveedor.nombre")}</td>
					<td><lx:moneyFormat number="${row.total}"/> </td>
					<td><lx:moneyFormat number="${row.totalMN}"/> </td>
				</tr>
			</g:each>
		</tbody>
	</table>
</div>
