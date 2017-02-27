<%@ page import="com.luxsoft.nomina.NominaAsimilado" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Nómina de asimilados</title>
</head>
<body>
 <content tag="header">
 	Comprobantes de pago a personas asimilados a sueldo
 </content>
 <content tag="periodo">
 	Periodo:${session.periodo.mothLabel()} 
 </content>
 <content tag="operaciones">
 </content>

 <content tag="grid">
 	<table id="grid" class="table table-striped table-hover table-bordered table-condensed">
 		<thead>
 			<tr>
 				<td>Id</td>
 				<td>Nombre</td>
 				<td>Fecha</td>
 				
 				
 				<td>Concepto</td>
 				<td>Percepciones</td>
 				<td>Deducciones</td>
 				<td>CFDI</td>
 				<td>UUID</td>
 				
 			</tr>
 		</thead>
 		<tbody>
 			<g:each in="${nominaAsimiladoInstanceList}" var="row">
 				<tr>
 					<td><g:link action="show" id="${row.id}">
 						${fieldValue(bean: row, field: "id")}
 						</g:link>
 					</td>
 					<td>${fieldValue(bean: row, field: "asimilado.nombre")}</td>
 					<td><lx:shortDate date="${row.fecha }"/></td>
 					
 					
 					<td>${fieldValue(bean: row, field: "concepto")}</td>
 					<td><lx:moneyFormat number="${row.percepciones }"/></td>
 					<td><lx:moneyFormat number="${row.deducciones }"/></td>
 					<td>${row?.cfdi?.id}</td>
 					<td>${row?.cfdi?.uuid}</td>
 					
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


