
<div>
	<table id="grid" class="table table-striped table-bordered table-condensed" >
		<thead>
			<tr>
				<th>Embarque</th>
				<th>BL</th>
				<th>Contenedor</th>
				<th>Kg Netos</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${contenedores}" var="row">
			<tr>
				<td>${row.embarque}</td>
				<td>${row.bl}</td>
				<td>${row.contenedor}</td>
				<td>${row.kilosNetos}</td>
			</tr>
			</g:each>
		</tbody>
		<tfoot>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th id="totalKilos"><g:formatNumber number="${embarqueInstance?.getTotal('kilosNetos')}" format="###,###,###.##"/></th>
			</tr>
		</tfoot>
	</table>
</div>

