<%@ page import="com.luxsoft.impapx.Distribucion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Distribución</title>
</head>
<body>
<content tag="header">
	Registro de distribución
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
	    <li ><strong>Todas</strong></li>
	</ol>
</content>

<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>
<content tag="grid">
	<table id="grid" class="table table-striped table-bordered table-hover ">
		<thead>
			<tr>
				<th>Id</th>
				<th>Fecha</th>
				<th>Embarque</th>
				<th>Modificada</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${distribucionInstanceList}" var="row">
				<tr>
					<lx:idTableRow id="${row.id}" action="edit"/>	
					<td><lx:shortDate date="${row.fecha}"/></td>	
					<td>${fieldValue(bean: row, field: "embarque")}</td>
					<td><g:formatDate date="${row.lastUpdated}" /></td>
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
