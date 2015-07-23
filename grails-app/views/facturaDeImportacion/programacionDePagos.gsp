<%@ page import="com.luxsoft.impapx.FacturaDeImportacion" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="cxp">
	<title>Programación de pagos</title>
</head>
<body>

 	<content tag="document">
 		
		<div class="row toolbar-panel">
			<div class="col-md-12">
			    <div class="btn-group">
		            <g:link action="imprimirProgramacionDePagos" class="btn btn-default " >
		                <i class="fa fa-print"></i> Imprimir
		            </g:link> 
			    </div>
	     		
			</div>
		    
		</div>
		<table id="grid"
			class="display table table-striped table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<g:sortableColumn property="id" title="Id" />
					<g:sortableColumn property="proveedor.nombre" title="Proveedor" />
					<th class="header">Dcto</th>
					<th class="header">Fecha</th>
					<th class="header">BL</th>
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
				<g:each in="${facturaDeImportacionInstanceList}" var="row">
					<tr>
						<td>
							<g:link action="show" id="${row.id}">
								<lx:idFormat id="${row.id}"/>
							</g:link></td>
						<td><g:link action="show" id="${row.id}">
								${fieldValue(bean: row, field: "proveedor.nombre")}
							</g:link>
						</td>
						<td>${fieldValue(bean: row, field: "documento")}</td>
						<td><lx:shortDate date="${row.fecha }"/></td>
						<td><lx:shortDate date="${row.fechaBL }"/></td>
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
		<div class="pagination">
			<g:paginate total="${facturaDeImportacionInstanceCount ?: 0}" />
		</div>
 	</content>

 	
	
	
</body>
</html>
