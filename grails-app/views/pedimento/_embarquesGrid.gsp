<g:if test="${flash.gridMessage}">
	<bootstrap:alert class="alert-info">${flash.gridMessage}</bootstrap:alert>
</g:if>
<table id="embarquesDetGrid"
	class="simpleGrid table table-striped table-bordered table-condensed"
	cellpadding="0" cellspacing="0" border="0">
	<thead>
		<tr>
			<th>Clave</th>
			<th>Descripcion</th>
			<th>Compra</th>
			<th>Cantidad</th>
			<th>Kg Net</th>
			<th>Kg Est</th>
			<th>Factura</th>
			<th>Pedimento</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${pedimentoInstance.embarques}" var="row">
			<tr id="${fieldValue(bean:row, field:"id")}" name="embarqueDetId"
				class="${row.kilosNetos<=0?'textError':'' }">
				
				<td>
					${fieldValue(bean:row, field:"producto.clave")}
				</td>
				<td>
					${fieldValue(bean:row, field: "producto.descripcion")}
				</td>
				<td>
					${fieldValue(bean: row, field: "compraDet.compra.folio")}
				</td>
				<td>
					${fieldValue(bean: row, field: "cantidad")}
				</td>
				<td>
					${fieldValue(bean: row, field: "kilosNetos")}
				</td>
				<td>
					${fieldValue(bean: row, field: "kilosEstimados")}
				</td>
				
				
				<td name="factura"><g:link controller="cuentaPorPagar"
						action="edit" id="${row?.factura?.id} ">
						${fieldValue(bean: row, field: "factura.documento")}
					</g:link>
				</td>
				
				<td name="gastosPorPedimento">
					<lx:moneyFormat number="${row.gastosPorPedimento}"/>
				</td>
				
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
			
			<th>Total:</th>
			<th id="totalPedimento">
				<lx:moneyFormat number="${pedimentoInstance?.embarques.sum{ it.'gastosPorPedimento'} }"/>
				
			</th>
			
		</tr>
	</tfoot>
</table>