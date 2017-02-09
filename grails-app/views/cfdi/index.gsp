
<!DOCTYPE html>
<html>
<head>
	<title>CFDI s</title>
	<meta name="layout" content="operaciones">
</head>
<body>

<content tag="header">
	Comprobantes fiscales digitales CFDI
</content>

<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>

<content tag="operaciones">
	<li>
		<a data-toggle="modal" data-target="#importarDialog"><i class="fa fa-upload"></i> Pendiente</a>
	</li>
</content>

<content tag="grid">
	<table id="grid" class="table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th>Serie</th>
				<th>Folio</th>
				<th>Fecha</th>
				<th>Receptor</th>
				<th>UUID</th>
				<th>Timbrado</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${cfdiInstanceList}" status="i" var="cfdiInstance">
				<tr>
					<td>${fieldValue(bean: cfdiInstance, field: "serie")}</td>
					<td><g:link action="show" id="${cfdiInstance.id}">${fieldValue(bean: cfdiInstance, field: "folio")}</g:link></td>
					<td><g:formatDate date="${cfdiInstance.fecha}" /></td>
					<td>${fieldValue(bean: cfdiInstance, field: "receptor")}</td>
					<td>${fieldValue(bean: cfdiInstance, field: "uuid")}</td>
					<td><g:formatDate date="${cfdiInstance.timbrado}" /></td>
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

