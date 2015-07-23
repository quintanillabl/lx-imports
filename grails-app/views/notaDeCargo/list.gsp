

<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">

<title>Notas de cargo</title>
<r:require modules="luxorTableUtils,dataTables"/>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<div class="span2">
				<div class="well">
					<ul class="nav nav-list">
						<li class="nav-header">Operaciones</li>
						<li class="active"><g:link class="list" action="list">
								<i class="icon-list icon-white"></i>
								Todas
							</g:link></li>
						<li><g:link class="create" action="create" >
								<i class="icon-plus"></i>
								Alta de Nota
							</g:link></li>
					</ul>
				</div>
			</div>

			<div class="span10">

				<h3>
						Notas de Cargo
					</h3>

				<g:if test="${flash.message}">
					<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<table id="ventasGrid" class="simpleGrid table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<g:sortableColumn property="id" title="${message(code: 'venta.fecha.label', default: 'Folio')}" />
							<g:sortableColumn property="cliente" title="${message(code: 'venta.fecha.label', default: 'Cliente')}" />
							<g:sortableColumn property="fecha" title="${message(code: 'venta.fecha.label', default: 'Fecha')}" />
							<g:sortableColumn property="importe" title="${message(code: 'venta.importe.label', default: 'Importe')}" />
							<g:sortableColumn property="impuestos" title="${message(code: 'venta.impuestos.label', default: 'Impuestos')}" />
							<g:sortableColumn property="descuentos" title="${message(code: 'venta.descuentos.label', default: 'Descuentos')}" />
							<g:sortableColumn property="subtotal" title="${message(code: 'venta.subtotal.label', default: 'Subtotal')}" />
							<g:sortableColumn property="total" title="${message(code: 'venta.subtotal.label', default: 'Total')}" />
							<td>CFD</td>
							<g:sortableColumn property="dateCreated" title="Creada" />
							<g:sortableColumn property="lastUpdated" title="Modificada" />
						</tr>
					</thead>
					<tbody>
						<g:each in="${ventaInstanceList}" var="ventaInstance">
							<tr class="${ventaInstance.cfd?'warning':''}">
								<td>
									<g:link action="${ventaInstance.factura?'show':'edit'}" 
										id="${ventaInstance.id}">${fieldValue(bean: ventaInstance, field: "id")}
									</g:link>
								</td>
								<td>${fieldValue(bean: ventaInstance, field: "cliente")}</td>
								<td><lx:shortDate date="${ventaInstance.fecha}" /></td>
								<td>${fieldValue(bean: ventaInstance, field: "importe")}</td>
								<td>${fieldValue(bean: ventaInstance, field: "impuestos")}</td>
								<td>${fieldValue(bean: ventaInstance, field: "descuentos")}</td>
								<td>${fieldValue(bean: ventaInstance, field: "subtotal")}</td>
								<td>${fieldValue(bean: ventaInstance, field: "total")}</td>
								<td>
									<g:if test="${ ventaInstance.cfd}">
										<g:link controller="venta" action="showFactura" id="${ventaInstance?.cfd?.id}">
											${fieldValue(bean: ventaInstance, field: "factura")}
										</g:link>
									</g:if>
									<g:elseif test="${ventaInstance.cfdi}">
										<g:link controller="cfdi" action="show" id="${ventaInstance.cfdi}">
											${fieldValue(bean: ventaInstance, field: "factura")}
										</g:link>
									</g:elseif>
									
								</td>
								<td><g:formatDate date="${ventaInstance.dateCreated}"/></td> 
								<td><g:formatDate date="${ventaInstance.lastUpdated}"/></td>
							</tr>
						</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${ventaInstanceTotal}" />
				</div>
			</div>

		</div>
	</div>
	<r:script>
		$(function(){
		
			$("#ventasGrid2").dataTable({
			"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
			aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
          	iDisplayLength: 100,
          	"oLanguage": {
      			"oPaginate": {
        				"sFirst": "Inicio",
        				"sNext": "Siguiente",
        				"sPrevious": "Página anterior",

      				},
      				"sSearch": "Filtrar:",
      				"sEmptyTable": "No hay datos disponibles",
      				"sLoadingRecords": "Cargando datos",
      				"sProcessing": "procesando....",
    			},
    			"bPaginate": false,
    			"bInfo": false
			});
			
			$(function(){
			$("#ventasGrid").dataTable({
			//"sDom": "<'row'<'span3'l><'span9'f>r>t<'row'<'span6'i><'span6'p>>",
			//"sDom": "<'row'<'span4'f>r>t<'row'<'span6'i><'span6'p>>",
			aLengthMenu: [[100, 150, 200, 250, -1], [100, 150, 200, 250, "Todos"]],
          	iDisplayLength: 50,
          	 	"oLanguage": {
      			"sUrl":"<g:resource dir="js" file="dataTables.spanish.txt" />"
	    	},
    		"aoColumnDefs": [
                        { "sType": "numeric","bSortable": true,"aTargets":[0] }
                    ],
           "bPaginate": false  
			});
		});
			
		});
	</r:script>
</body>
</html>
