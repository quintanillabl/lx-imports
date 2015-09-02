<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Gastos de importación</title>
</head>
<body>
 <content tag="header">
 	Gastos de importación
 </content>
 <content tag="periodo">
 	Periodo:${session.periodo.mothLabel()} 
 </content>
 <content tag="operaciones">
 	
 	<li>
 	    <g:link action="programacionDePagos"  >
 	        <i class="fa fa-list-ol"></i> Programación de pagos
 	    </g:link> 
 	</li>
 	<li>
 		<a href="#uploadFileDialog" data-toggle="modal" >
 			<i class="fa fa-upload"></i></span> Importar CFDI
 		</a>
 	</li>
 </content>
 <content tag="grid">
 	<table id="grid"
 				class="display table table-striped table-hover table-bordered table-condensed">
 				<thead>
 					<tr>
						<th>Id</th>
						<th>Proveedor</th>
 						<th>Dcto</th>
 						<th>Fecha</th>
 						<th>Vto</th>
 						<th>Moneda</th>
 						<th>Total</th>
 						<th>Pagos</th>
 						<th>Saldo</th>
 						<th>UUID</th>
 						<th>Modificada</th>
 					</tr>
 				</thead>
 				<tbody>
 					<g:each in="${gastosDeImportacionInstanceList}" var="row">
 						<tr class="${row.comprobante?'success':'warning'}">
 							<td>
 								<g:link action="edit" id="${row.id}">
 									<lx:idFormat id="${row.id}"/>
 								</g:link></td>
 							<td><g:link action="edit" id="${row.id}">
 									${fieldValue(bean: row, field: "proveedor.nombre")}
 								</g:link>
 							</td>
 							<td>${fieldValue(bean: row, field: "documento")}</td>
 							<td><lx:shortDate date="${row.fecha }"/></td>
 							<td><lx:shortDate date="${row.vencimiento }"/></td>
 							<td>
 								${fieldValue(bean: row, field: "moneda")}
 							</td>
 							<td><lx:moneyFormat number="${row.total}"/></td>
 							<td><lx:moneyFormat number="${row.pagosAplicados}"/></td>
 							<td><lx:moneyFormat number="${row.saldoActual}"/></td>
 							
 							<td>
 								<abbr title="${row?.comprobante?.uuid}">
 								${org.apache.commons.lang.StringUtils.substringAfterLast(row?.comprobante?.uuid,'-')}
 								</abbr>
 							</td>
 							<td><abbr title="${g.formatDate(date:row.lastUpdated)}">
 								...</abbr></td>
 						</tr>
 					</g:each>
 				</tbody>
 			</table>
 		<g:render template="/comprobanteFiscal/uploadXmlFile"/>
 </content>
 <content tag="searchService">
 	<g:createLink action="search"/>
 </content>
 	
	
</body>
</html>
