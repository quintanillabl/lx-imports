
<div style="overflow: auto;">
	<table id="grid" class="table table-striped table-bordered table-condensed" >
		<thead>
			<tr>
				<th>Clave</th>
				<th>Descripcion</th>
				<th>Cantidad</th>
				<th>Kg Net</th>
				<th>Kg Est</th>
				<th>Precio</th>
				<th>Importe</th>
				<th>TC</th>
				<th>Importe MN</th>
				<th>Incrementables</th>
				<th>Gastos</th>
				<th>Pedimento</th>
				<th>C. de imp(%)</th>
				<th>C.U. Neto</th>
				<th>Precio V.</th>
				<th>Importe V.</th>
			</tr>
		</thead>
			<tbody>
				<g:each in="${partidas}" var="row">
				<tr id="${fieldValue(bean:row, field:"id")}" name="embarqueDetId"
					class="${row?.kilosNetos<=0?'text-danger':'' } ">
					<td>
						<g:link action="edit" controller="embarqueDet" id="${row?.id}" params="[proveedorId:embarqueInstance.proveedor.id]">
							${fieldValue(bean:row, field:"producto.clave")}
						</g:link>
					</td>
					<td>
						<g:link action="edit" controller="embarqueDet" id="${row?.id}">
							${fieldValue(bean:row, field: "producto.descripcion")}
						</g:link>
					</td>
					
					<td>${fieldValue(bean: row, field: "cantidad")}</td>
					<td>${fieldValue(bean: row, field: "kilosNetos")}</td>
					<td>${fieldValue(bean: row, field: "kilosEstimados")}</td>
					<td><lx:moneyFormat number="${row.precio }"/></td>
					<td><lx:moneyFormat number="${row.importe }"/></td>
					<td>${fieldValue(bean:row,field:"tc") }</td>
					<td><lx:moneyFormat number="${row.costoBruto }"/></td>
					<td><lx:moneyFormat number="${row.incrementables}"/></td>
					<td><lx:moneyFormat number="${row.gastosHonorarios}"/></td>
					<td name="gastosPorPedimento">
						<g:if test="${row.pedimento}">
							<g:link action="edit" controller="pedimento" id="${row.pedimento.id}" target="_blank">
								<lx:moneyFormat number="${row.gastosPorPedimento}"/>
							</g:link>
						</g:if>
					</td>
					<td name="gastosPorPedimento">
						<g:formatNumber number="${row.getCostoDeImportacion()}" format='% ##.##'/>
					</td>
					<td name="costoUnitarioNeto"><lx:moneyFormat number="${row.costoUnitarioNeto}"/></td>
					<td name="precioDeVenta"><lx:moneyFormat number="${row.precioDeVenta}"/></td>
					<td name="importeDeVenta"><lx:moneyFormat number="${row.importeDeVenta}"/></td>
				</tr>
				</g:each>
			</tbody>
			<tfoot>
				<tr>
				
				<th></th>
				<th></th>
				<th id="totalCantidad"><g:formatNumber number="${embarqueInstance?.getTotal('cantidad')}" format="###,###,###.##"/></th>
				<th id="totalKilos"><g:formatNumber number="${embarqueInstance?.getTotal('kilosNetos')}" format="###,###,###.##"/></th>
				<th id="totalKilsoEstimados"><g:formatNumber number="${embarqueInstance?.getTotal('kilosEstimados')}" format="###,###,###.##"/></th>
				<th></th>
				<th id="totalImporte"><lx:moneyFormat number="${embarqueInstance?.getTotal('importe')}"/></th>
				<th></th>
				
				<th id="totalCostoBruto">
					<lx:moneyFormat number="${embarqueInstance?.getTotal('costoBruto')}"/>
				</th>
				<th id="totalIncrementables">
					<lx:moneyFormat number="${embarqueInstance?.getTotal('incrementables')}"/>
				</th>
				<th id="totalGasto">
					<lx:moneyFormat number="${embarqueInstance?.getTotal('gastosHonorarios')}"/>
				</th>
				<th id="totalPedimento">
					<lx:moneyFormat number="${embarqueInstance?.getTotal('gastosPorPedimento')}"/>
				</th>
				<th></th>
				<th></th>
				<th></th>
				<th id="totalImporteVenta"><g:formatNumber number="${embarqueInstance?.getTotal('importeDeVenta')}" format="\$###,###,###.##"/></th>
			</tr>
		</tfoot>
	</table>
</div>

