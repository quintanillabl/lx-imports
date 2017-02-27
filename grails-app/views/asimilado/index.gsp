
<%@ page import="com.luxsoft.nomina.Asimilado" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="catalogos">
	<title>Asimilados</title>
</head>
<body>

<content tag="header">
	Catálogo de personas con ingreso asimilado a salarios
</content>



<content tag="grid">
	<table id="grid2" class="table table-striped table-bordered table-condensed luxor-grid">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>RFC</th>
				<th>Forma de pago</th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${asimiladoInstanceList}" status="i" var="row">
			<tr>
				<td>
					<g:link action="show" id="${row.id}">
						${fieldValue(bean: row, field: "nombre")}
					</g:link>
				</td>
				<td>${fieldValue(bean: row, field: "rfc")}</td>
				<td>${fieldValue(bean: row, field: "formaDePago")}</td>
			</tr>
		</g:each>
		</tbody>
	</table>
	<script type="text/javascript">
		$(function(){
			$('#grid2').dataTable({
			    responsive: true,
			    aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
			    iDisplayLength: 500,
			    "language": {
			        "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
			    },
			    "dom": '<"clear">t',
			    "tableTools": {
			        "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
			    },
			    "order": []
			});
			$("#filtro").on('keyup',function(e){
			    var term=$(this).val();
			    $('#grid2').DataTable().search(
			        $(this).val()
			            
			    ).draw();
			});
		})
	</script>
</content>


	
	
</body>
</html>


