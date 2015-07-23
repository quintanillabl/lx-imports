<table id="partidasGrid"
	class="simpleGrid table table-striped table-bordered table-condensed"
	cellpadding="0" cellspacing="0" border="0">
	<thead>
		<tr>
			<th>Folio</th>
			<th>Sucursal</th>
			<th>Contenedor</th>
			<th>Com</th>
			<th>Producto</th>
			<th>Descripcion</th>
			
			<th>Ins</th>
			<th>Can</th>
			<th>K Net</th>
			<th>Tar</th>
			<th>Cant</th>
			<th>Ent</th>
			<th>Rec</th>
		</tr>
	</thead>
	<tbody>
		
		<g:each in="${partidas}" var="row">
			<tr id="${row.id}" name="distribucionDetId">
				<td><g:formatNumber number="${row.id }" format='########'/></td>
				<td>
					<g:link action='fraccionar' controller='distribucionDet' id="${row?.id}">
						${fieldValue(bean:row, field:"sucursal")}
					</g:link>
				</td>
				<td>
					<g:link action='fraccionar' controller='distribucionDet' id="${row?.id}">
					
						${fieldValue(bean:row, field:"contenedor")}
					</g:link>
					
				</td>
				<td>
					${fieldValue(bean:row, field:"embarqueDet.compraDet.compra.folio")}
				</td>
				<td>
					${fieldValue(bean:row, field:"embarqueDet.producto.clave")}
				</td>
				<td>
					${fieldValue(bean:row, field:"embarqueDet.producto.descripcion")}
				</td>
				
				<td>
					<g:link action='edit' controller='distribucionDet' id="${row?.id}">
						${fieldValue(bean:row, field:"instrucciones")?:'...'}
					</g:link>
					
				</td>
				<td>
					${fieldValue(bean:row, field:"cantidad")}
				</td>
				<td>
					${fieldValue(bean:row, field:"kilosNetos")}
				</td>
				<td>
					${fieldValue(bean:row, field:"tarimas")}
				</td>
				<td>
					${fieldValue(bean:row, field:"cantidadPorTarima")}
				</td>
				<td><lx:shortDate date="${row?.programacionDeEntrega }"/></td>
				<td><lx:shortDate date="${row?.fechaDeEntrada }"/></td>
			</tr>
		</g:each>
	</tbody>
	
</table>
