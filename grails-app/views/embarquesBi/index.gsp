
<%@ page import="com.luxsoft.impapx.Embarque" %>
<!DOCTYPE html>
<html>
<head>
	<title>Embarques</title>
	<meta name="layout" content="consultas_bi">
</head>
<body>

<content tag="header">
	Análisis de embarques
</content>


<content tag="grid">
	<table id="grid" class="table table-striped table-bordered table-condensed ">
		<thead>
			<th>Embarque</th>
			<th>BL</th>
			<th>Nombre</th>
			<th>Proveedor</th>
			<th>Aduana</th>
			<th>Producto</th>
			<th>Descripción</th>
			<th>Contenedor</th>
			<th>Pedimento</th>
			<th>Factura</th>
		</thead>
		<tbody>
			<g:each in="${rows}"  var="row">
				<tr>
					<td>
						<g:link controller="embarque" action="edit" id="${row.embarque.id}">
							<lx:idFormat id="${row.embarque.id}"/>
						</g:link>
					</td>
					<td>
						<g:link controller="embarque" action="edit" id="${row.embarque.id}">
							${row.embarque.bl}
						</g:link>
					</td>
					<td>${row.embarque.nombre}</td>
					<td>${row.embarque.proveedor}</td>
					<td>${row.embarque.aduana}</td>
					<td>${row.producto.clave}</td>
					<td>${row.producto.descripcion}</td>
					<td>${row.contenedor}</td>
					<td>
						<g:if test="${row.pedimento}">
							<g:link controller="pedimento" action="edit" id="${row.pedimento.id}">
								${row.pedimento.pedimento}
							</g:link>
						</g:if>
					</td>
					<td>
						<g:if test="${row.factura}">
							<g:link controller="facturaDeImportacion" action="edit" id="${row.factura.id}">
								${row.factura.documento}
							</g:link>
						</g:if>
					</td>
					
					
				</tr>
			</g:each>
		</tbody>
	</table>

	<div class="modal fade" id="searchDialog" tabindex="-1">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Buscar embaques</h4>
				</div>
				<g:form action="search" class="form-horizontal" >
					<div class="modal-body">
						<f:with bean="searchCommand">
							<f:field property="bl" widget-class="form-control"/>
							<f:field property="pedimento" widget-class="form-control"/>
							<f:field property="contenedor" widget-class="form-control"/>
							<f:field property="factura" widget-class="form-control"/>
							<f:field property="descripcion" widget-class="form-control"/>
						</f:with>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<g:submitButton class="btn btn-info" name="aceptar"
								value="Buscar" />
					</div>
				</g:form>
	
			</div>
			<!-- moda-content -->
		</div>
		<!-- modal-di -->
	</div>
</content>




</body>
</html>