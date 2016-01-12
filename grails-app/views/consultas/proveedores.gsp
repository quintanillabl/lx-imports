
<%@ page import="com.luxsoft.impapx.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="consultas_bi">
	<title>Proveedores</title>
</head>
<body>

<content tag="header">
	Consulta rápida de proveedores
</content>

<content tag="grid">
	<table id="grid2" class="table table-striped table-bordered table-condensed luxor-grid">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>RFC</th>
				<th>Email</th>
				<th>A. Aduanal</th>
				<th>Pais</th>
				<th>Modificado</th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${proveedorInstanceList}" status="i" var="proveedorInstance">
			<tr>
				<td>
					<g:link action="proveedor" id="${proveedorInstance.id}">
						${fieldValue(bean: proveedorInstance, field: "nombre")}
					</g:link>
				</td>
				<td>${fieldValue(bean: proveedorInstance, field: "rfc")}</td>
				<td>${fieldValue(bean: proveedorInstance, field: "correoElectronico")}</td>
				<td><g:checkBox name="agenciaAduanl" value="${proveedorInstance.agenciaAduanal}" disabled="true"/></td>
				<td>${fieldValue(bean: proveedorInstance, field: "direccion.pais")}</td>
				<td>${formatDate(date: proveedorInstance.lastUpdated)}</td>
			</tr>
		</g:each>
		</tbody>
	</table>
	<script type="text/javascript">
		$(function(){
			$('#grid2').dataTable({
			    responsive: true,
			    aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
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
