<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Consultas especializadas</title>
</head>
<body>
	<div class="row"><!-- List header -->
		<div class="span12">
			<h2>Numero de sucursales: ${rows.size}</h2>
		</div>
	</div>
	
	<div class="row"> <!-- Table  -->
			<div class="span8 ">
				<table>
					<thead>
						<tr>
							<g:sortableColumn property="id" title="id" />
						</tr>
					</thead>
					<tbody>
						<g:each in="${rows}" status="i" var="row">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td><g:textField name="row.id" value="${row.sucursal_id}"/></td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>	
	
</body>
</html>
