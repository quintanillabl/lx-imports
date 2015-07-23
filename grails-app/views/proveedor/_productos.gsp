<asset:stylesheet src="datatables/dataTables.css"/>
<asset:javascript src="datatables/dataTables.js"/> 

<table id="grid" class="table table-striped table-hover table-bordered table-condensed " with="100%">
	<thead>
		<tr>
			<g:sortableColumn property="producto.clave" title="Clave"/>
			<g:sortableColumn property="producto.descripcion" title="Descripción"/>
			<g:sortableColumn property="producto.gramos" title="Línea"/>
			%{-- <g:sortableColumn property="codigo" title="Código Prov"/>
			<g:sortableColumn property="descripcion" title="Desc Prov"/>
			<g:sortableColumn property="costoUnitario" title="Costo U"/> --}%
		</tr>
	</thead>
	<tbody>
		<g:each in="${proveedorInstance?.productos.sort{it.codigo}}" var="det">
		<tr id="${det.id}" class="${det.costoUnitario<=0?'textError':''}">
			<td>${fieldValue(bean:det,field:"producto.clave") }</td>
			<td>${fieldValue(bean:det,field:"producto.descripcion") }</td>
			<td>${fieldValue(bean:det,field:"producto.linea") }</td>
			%{-- <td>
				<g:link controller="proveedorProducto" action="edit" id="${det.id }">
					${fieldValue(bean:det,field:"codigo") }
				</g:link>
			</td>
			<td>${fieldValue(bean:det,field:"descripcion") }</td>
			<td name="costoUnitario">${fieldValue(bean:det,field:"costoUnitario") }</td> --}%
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