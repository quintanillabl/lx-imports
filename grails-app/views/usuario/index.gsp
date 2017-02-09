<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Usuarios</title>
</head>
	
<body>

	<content tag="header">
		Consola para la administración usuarios y derechos
	</content>
	<content tag="subHeader"></content>

	<content tag="periodo"></content>
	<content tag="operaciones">
		<li>
			<a href="#importarModal" data-toggle="modal">
				<span class="glyphicon glyphicon-upload"></span> Importar empleado
			</a>
		</li>
		<g:render template="importarEmpleadoDialog"/>
	</content>
	
	<content tag="reportes">
		<li><g:link controller="reporte" action="usuariosDelSistema"> Catálogo</g:link></li>
	</content>

	<content tag="grid">
		<table id="usuariosGrid" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>UseName</th>
					<th>Nombre</th>
					<th>Número</th>
					<th>Puesto</th>
					<th>Sucursal</th>
					<th>Activo</th>
					
				</tr>
			</thead>
			<tbody>
				<g:each in="${usuarioInstanceList}" var="row">
					<tr>
						<td>
							<g:link action="edit" id="${row.id}">
								${row.username}
							</g:link>
						</td>
						<td>
							<g:link action="show" id="${row.id}">
								${row.nombre}
							</g:link>
						</td>
						<td>${row.numeroDeEmpleado}</td>
						<td>${row.puesto}</td>
						<td>${row.sucursal}</td>
						<td>
							<g:if test="${row.enabled}">
								<i class="fa fa-check"></i>
							</g:if>
							<g:else>
								<i class="fa fa-toggle-off"></i>
							</g:else>
						</td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</content>

	<content tag="searchService">
		<g:createLink action="search"/>
	</content>

			
	
		
</body>
	
</html>
