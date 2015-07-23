
<div class="gridPanel" id="analisisGridPanel">
	<table id="grid"
			class="table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<th>Tipo</th>
					<th>CtaID</th>
					<th>ID</th>
					<th>Tipo Poliza</th>
					<th>Folio</th>
					<th>Fecha</th>
					<th>Clave</th>
					<th>Concepto</th>
					<th>Descripción</th>
					<th>Base</th>					
					<th>Proveedor</th>
					<th>RFC</th>
					<th>Nal</th>
					<th>Pais</th>
					<th>Nacionalidad</th>
					<th>Base11</th>
					<th>Ret1</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${rows}" var="row">
					<tr>
						<td>${row.tipo }</td>
						<td>${row.ctaid }</td>
						<td>${row.id }</td>
						<td>${row.tipoPol }</td>
						<td>${row.folio }</td>
						<td>${row.fecha }</td>
						<td>${row.clave }</td>
						<td>${row.concepto }</td>
						<td>${row.descripcion}</td>
						<td>${row.debe}</td>						
						<td>${row.nombre}</td>
						<td>${row.rfc}</td>
						<td>${row.nacional}</td>
						<td>${row.pais}</td>
						<td>${row.nacionalidad}</td>
						<td>${row.base11}</td>
						<td>${row.ret1}</td>
					</tr>
				</g:each>
			</tbody>
		</table>
</div>