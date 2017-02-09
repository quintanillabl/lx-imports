
<%@ page import="com.luxsoft.impapx.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="catalogos">
	<title>Paises</title>
</head>
<body>

<content tag="header">
	Catálogo de paises de origen
</content>



<content tag="grid">
	<table id="grid2" class="table table-striped table-bordered table-condensed luxor-grid">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nombre</th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${paisDeOrigenInstanceList}" status="i" var="row">
			<tr>
				<td>
					<g:link action="show" id="${row.id}">
						${row.id}
					</g:link>
				</td>
				<td>
					<g:link action="show" id="${row.id}">
						${fieldValue(bean: row, field: "nombre")}
					</g:link>
				</td>
				
			</tr>
		</g:each>
		</tbody>
	</table>
	
</content>


	
	
</body>
</html>
