
<%@ page import="com.luxsoft.impapx.Compra" %>
<!DOCTYPE html>
<html>
<head>
	<title>Compras</title>
	<meta name="layout" content="operaciones">
</head>
<body>

<content tag="header">
	Compras
</content>

<content tag="periodo">
	Periodo:${session.periodo.mothLabel()} 
</content>

<content tag="operaciones">
	<li>
		<a data-toggle="modal" data-target="#importarDialog"><i class="fa fa-upload"></i> Importar compra</a>
	</li>
</content>

<content tag="grid">
	<table id="grid" class="table table-striped table-bordered table-condensed luxor-grid" width="100%">
		<thead>
			<tr>
				<th>Id</th>
				<th>Proveedor</th>
				<th>Folio</th>
				<th>Fecha</th>
				<th>Entrega</th>
				<th>Depuración</th>
				<th>Comentario</th>
				<th>Moneda</th>
				<th>Creado</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${compraInstanceList}" status="i" var="row">
				<tr>
					<lx:idTableRow id="${row.id}"/>
					<td><g:link action="show" id="${row.id}">${fieldValue(bean: row, field: "proveedor")}</g:link></td>
					<td>${fieldValue(bean: row, field: "folio")}</td>
					<td><g:formatDate date="${row.fecha}" /></td>
					<td><g:formatDate date="${row.entrega}" /></td>
					<td><g:formatDate date="${row.depuracion}" /></td>
					<td>${fieldValue(bean: row, field: "comentario")}</td>
					<td>${fieldValue(bean: row, field: "moneda")}</td>
					<td><g:formatDate date="${row.dateCreated}" /></td>
				</tr>
			</g:each>
		</tbody>
	</table>

	<div class="modal fade" id="importarDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Importar compra desde SIIPAP</h4>
				</div>
				
				<g:form controller="importador" action="importarCompra"	name="importarForm">
					<input id="folio" class="form-control" type="text" name="folio"  placeholder="Digite el folio a importar" autofocus="autofocus" required="true">
					
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Importar" />
					</div>
				</g:form>
			</div><!-- moda-content -->
		</div><!-- modal-di -->
	</div>

</content>

<content tag="searchService">
	<g:createLink action="search"/>
</content>
	
</body>
</html>
