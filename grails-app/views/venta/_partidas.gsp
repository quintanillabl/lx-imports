<table id="grid" class=" table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>Clave</th>
				<th>Descripcion</th>
				<th>Contenedor</th>
				<th>BL</th>
				<th>Kg </th>
				<th>Cantidad </th>
				<th>Precio </th>
				<th>Importe</th>
				<th>Desc</th>
				<th>SubTotal</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${ventaInstance.partidas}" var="row">
				<tr id="${row.id }" >
					
					<td>
						<g:link controller='ventaDet' action='edit' id="${row?.embarque?.embarque?.id}" target="_blank"> 
						${fieldValue(bean:row, field:"producto.clave")}
						</g:link>
					</td>
					
					<td>${fieldValue(bean:row, field:"producto.descripcion")}</td>
					<td>${fieldValue(bean:row, field:"contenedor")}</td>
					<td>
						<g:link controller='embarque' action='edit' id="${row?.embarque?.embarque?.id}" target="_blank"> 
						${fieldValue(bean:row, field:"embarque.embarque.bl")}
						</g:link>
					</td>
					<td><g:formatNumber number="${row.kilos}" format="###,###,###.###"/></td>
					<td><g:formatNumber number="${row.cantidad}" format="###,###,###.###"/></td>
					<td><lx:moneyFormat number="${row.precio}"/> </td>
					<td><lx:moneyFormat number="${row?.importe}"/> </td>
					<td><lx:moneyFormat number="${row.descuentos}"/> </td>
					<td><lx:moneyFormat number="${row.subtotal}"/> </td>
				</tr>
			</g:each>
		</tbody>
		<tfoot>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th>Importe</th>
				<th><lx:moneyFormat number="${ventaInstance?.importe }"/> </th>
			</tr>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th>Impuesto</th>
				<th><lx:moneyFormat number="${ventaInstance.impuestos }"/> </th>
			</tr>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th>Total</th>
				<th><lx:moneyFormat number="${ventaInstance.total }"/> </th>
			</tr>
		</tfoot>
	</table>