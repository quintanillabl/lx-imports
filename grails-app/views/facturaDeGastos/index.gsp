<%@ page import="com.luxsoft.impapx.FacturaDeGastos" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="operaciones">
	<title>Facturas de gastos</title>
</head>
<body>
<content tag="header">
	Facturas de gastos
</content>
<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>

<content tag="operaciones">
	
	%{-- <li>
	    <g:link action="programacionDePagos"  >
	        <i class="fa fa-list-ol"></i> Programación de pagos
	    </g:link> 
	</li> --}%
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
				<g:sortableColumn property="id" title="Id" />
				<g:sortableColumn property="proveedor.nombre" title="Proveedor" />

				<th class="header">Dcto</th>
				<th class="header">Fecha</th>
				<th class="header">Vto</th>
				<th class="header">Moneda</th>
				<th class="header">Total</th>
				<th class="header">Pagos</th>
				<th class="header">Saldo</th>
				<th class="header">Creada</th>
				<th class="header">Modificada</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${facturaDeGastosInstanceList}" var="row">
				<tr>
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
					
					<td><abbr title="${g.formatDate(date:row.dateCreated)}">
						...</abbr></td>
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
