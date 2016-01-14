<%@ page import="com.luxsoft.impapx.Venta" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Ventas</title>
	<asset:javascript src="forms/forms.js"/>
</head>

<content tag="header"> 
	Venta ${ventaInstance.clase?.toUpperCase()}: ${ventaInstance.id} ( ${ventaInstance.cliente})
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="index">Ventas</g:link></li>
    	<li class="active"><strong>Edición</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<lx:iboxTitle title="Cuenta de gastos"/>
	    <div class="ibox-content">

			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a href="#partidas" data-toggle="tab"><i class="fa fa-th-list"></i>  Partidas</a></li>
				<li class=""><a href="#venta" data-toggle="tab"><i class="fa fa-pencil"></i>  Propiedades</a></li>
			</ul>

	  		<div class="tab-content"> <!-- Tab Content -->
	  			<div class="tab-pane active" id="partidas">
	  				<br>
	  				<table id="grid" class=" table table-striped table-bordered table-condensed">
	  						<thead>
	  							<tr>
	  								<th>Clave</th>
	  								<th>Descripcion</th>
	  								<th>Contenedor</th>
	  								<th>BL</th>
	  								<th>Kg </th>
	  								<th>Cantidad </th>
	  								<th>Precio </th>
	  								<th>Importe</th>
	  								<th>Desc</th>
	  								<th>SubTotal</th>
	  							</tr>
	  						</thead>
	  						<tbody>
	  							<g:each in="${ventaInstance.partidas}" var="row">
	  								<tr id="${row.id }" >
	  									
	  									<td>
	  										${fieldValue(bean:row, field:"producto.clave")}
	  									</td>
	  									
	  									<td>${fieldValue(bean:row, field:"producto.descripcion")}</td>
	  									<td>${fieldValue(bean:row, field:"contenedor")}</td>
	  									<td>
	  										${fieldValue(bean:row, field:"embarque.embarque.bl")}
	  									</td>
	  									<td><g:formatNumber number="${row.kilos}" format="###,###,###.###"/></td>
	  									<td><g:formatNumber number="${row.cantidad}" format="###,###,###.###"/></td>
	  									<td><lx:moneyFormat number="${row.precio}"/> </td>
	  									<td><lx:moneyFormat number="${row?.importe}"/> </td>
	  									<td><lx:moneyFormat number="${row.descuentos}"/> </td>
	  									<td><lx:moneyFormat number="${row.subtotal}"/> </td>
	  								</tr>
	  							</g:each>
	  						</tbody>
	  						<tfoot>
	  							<tr>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th>Importe</th>
	  								<th><lx:moneyFormat number="${ventaInstance?.importe }"/> </th>
	  							</tr>
	  							<tr>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th>Impuesto</th>
	  								<th><lx:moneyFormat number="${ventaInstance.impuestos }"/> </th>
	  							</tr>
	  							<tr>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th></th>
	  								<th>Total</th>
	  								<th><lx:moneyFormat number="${ventaInstance.total }"/> </th>
	  							</tr>
	  						</tfoot>
	  					</table>
	  			</div>			
				<div class="tab-pane " id="venta">
					<br>
					<g:render template="showForm" bean="${ventaInstance}"/>
				</div>
				
	  		</div>	<!-- End Tab Content -->
	    </div>
	</div>
	<script type="text/javascript">
		$(function(){
			//$('.chosen-select').chosen();
			$(".numeric").autoNumeric('init',{vMin:'0'},{vMax:'9999'});
			$(".money").autoNumeric('init',{wEmpty:'zero',mRound:'B',aSign: '$'});
			$(".tc").autoNumeric('init',{vMin:'0.0000'});
			$('#grid').dataTable({
			    responsive: true,
			    aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
			    "language": {
			        "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
			    },
			    "dom": 'T<"clear">lfrtip',
			    "tableTools": {
			        "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
			    },
			    "order": []
			});
		});
	</script>
</content>

	
</html>




