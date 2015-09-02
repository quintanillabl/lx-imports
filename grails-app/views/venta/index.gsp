
<!DOCTYPE html>
<html>
<head>
	<title>Ventas</title>
	<meta name="layout" content="operaciones">
</head>
<body>

<content tag="header">
	Ventas
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
	<table id="grid" class="simpleGrid table table-striped table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th>Id</th>
				<th>Cliente</th>
				<th>Fecha</th>
				<th>Total</th>
				<th>Factura</th>
				<th>Modificada</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${ventaInstanceList}" var="row">
				<tr class="${row.cfdi?'':'warning'}">
					<td>
						<g:if test="${row.cfdi}">
							<g:link controller="cfdi" action="show" id="${row.cfdi}">${fieldValue(bean: row, field: "id")}</g:link>
						</g:if>
						<g:else>
							<g:link action="edit" id="${row.id}">${fieldValue(bean: row, field: "id")}</g:link>
						</g:else>
						
					</td>
					<td>${fieldValue(bean: row, field: "cliente")}</td>
					<td><lx:shortDate date="${row.fecha}" /></td>
					<td><lx:moneyFormat number="${row.total}"/></td>
					<td>
						<g:if test="${row.cfdi}">
							<a data-target="#showCfdiDialog" data-toggle="modal" data-cfdi="${row.cfdi}">${row.id}</a>
							%{-- <g:link controller="cfdi" action="show" id="${row?.cfdi}">
								${fieldValue(bean: row, field: "cfdi")}
							</g:link> --}%
						</g:if>
					</td>
					<td><g:formatDate date="${row.lastUpdated}" format="dd/MM/yy HH:mm"/></td>
				</tr>
			</g:each>
		</tbody>
	</table>

	<div class="modal fade" id="showCfdiDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">CFDI</h4>
				</div>
				<div class="modal-body">
					<div id="cfdiPanel">
						<p><strong>Propiedades</strong></p>
					</div>
				</div>
					
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					<button id="accesarBtn" class="btn btn-primary btn-outline">Accesar</button>
				</div>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>

</content>

<content tag="searchService">
	<g:createLink action="search"/>
</content>
	
</body>
</html>
