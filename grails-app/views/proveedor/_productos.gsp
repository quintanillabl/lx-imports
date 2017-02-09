
<table id="grid" class="grid table table-striped table-hover table-bordered table-condensed " with="100%">
	<thead>
		<tr>
			<th>Clave</th>
			<th>Descripción</th>
			<th>Línea</th>
			<th>Código Prov</th>
			<th>Desc Prov</th>
			<th>Costo U</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${proveedorInstance?.productos.sort{it.codigo}}" var="det">
		<tr id="${det.id}" class="${det.costoUnitario<=0?'text-danger':''}">
			<td>
				<g:link controller="proveedorProducto" action="edit" id="${det.id }">
					${fieldValue(bean:det,field:"producto.clave") }
				</g:link>
			</td>
			<td>${fieldValue(bean:det,field:"producto.descripcion") }</td>
			<td>${fieldValue(bean:det,field:"producto.linea") }</td>
			<td>
				${fieldValue(bean:det,field:"codigo") }
			</td>
			<td>${fieldValue(bean:det,field:"descripcion") }</td>
			<td name="costoUnitario">${fieldValue(bean:det,field:"costoUnitario") }</td>
		</tr>
		</g:each>
	</tbody>
</table>
<script type="text/javascript">
	$(function(){
		var table=$('#grid').dataTable( {
	    	"language": {
				"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    	},
	    	aLengthMenu: [[20, 40, 60, 100, -1], [20, 40, 60, 100, "Todos"]],
			iDisplayLength: 20,
			"autoWidth": false
		} );
		
		
	});
</script>