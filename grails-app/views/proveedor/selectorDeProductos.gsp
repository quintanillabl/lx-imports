<%@ page import="com.luxsoft.impapx.Proveedor" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Selector de productos</title>
</head>
<body>

<content tag="header">
	Productos disponibles para asignar a: ${proveedor}
</content>

<content tag="subHeader">
	<ol class="breadcrumb">
    	<li><g:link action="edit" id="${proveedor.id}">${proveedor}</g:link></li>
    	<li class="active"><strong>Asignación de producto</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="ibox float-e-margins">
		<div class="ibox-title">
			<div class="btn-group">
				<a id="asignar" href="#" class="btn btn-outline btn-success">
					<i class="fa fa-plus"></i> Agregar
				</a>
			</div>
			<span class="badge badge-info">Disponibles: ${productos.size() } </span>
		    
		</div>
	    <div class="ibox-content">
			<table id="grid" class="table  table-hover table-bordered table-condensed grid">
				<thead>
					<tr>
						<th class="header"><g:message code="producto.descripcion.label" default="Clave"/></th>
						<th class="header"><g:message code="producto.descripcion.label" default="Descripción"/></th>
						<th class="header"><g:message code="producto.unidad.label" default="U"/></th>
						<th class="header"><g:message code="producto.kilos.label" default="K" /></th>
						<th class="header"><g:message code="producto.gramos.label" default="g" /></th>
						<th class="header"><g:message code="producto.m2.label" default="M2" /></th>
						<th class="header"><g:message code="producto.linea.label" default="Linea" /></th>
						<th class="header"><g:message code="producto.marca.label" default="Marca" /></th>
						<th class="header"><g:message code="producto.clase.label" default="Clase" /></th>
					</tr>
				</thead>
				
				<tbody>
					<g:each in="${productos}" var="row">
					<tr id="${row.id}">
						<td>${fieldValue(bean: row, field: "clave")}</td>
						<td>${fieldValue(bean: row, field: "descripcion")}</td>
						<td>${fieldValue(bean: row, field: "unidad.clave")}</td>
						<td>${fieldValue(bean: row, field: "kilos")}</td>
						<td>${fieldValue(bean: row, field: "gramos")}</td>
						<td>${fieldValue(bean: row, field: "m2")}</td>
						<td>${fieldValue(bean: row, field: "linea")}</td>
						<td>${fieldValue(bean: row, field: "marca")}</td>
						<td>${fieldValue(bean: row, field: "clase")}</td>
					</tr>
					</g:each> 
				</tbody>
			</table>
	    </div>
	</div>

	<script type="text/javascript">
		$(function(){
			$('#grid').dataTable({
			    responsive: true,
			    aLengthMenu: [[50, 100, 150, 200, -1], [50, 100,150, 200, "Todos"]],
			    "language": {
			        "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
			    },
			    
			    "order": []
			});
			$(".grid tbody tr").hover(function(){
				$(this).toggleClass("info");
			});
			$(".grid tbody tr").click(function(){
				$(this).toggleClass("success selected");
			});
			function selectedRows(){
				var res=[];
				var data=$("tbody tr.selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			};
			$("#asignar").click(function(){
				var res=selectedRows();
				if(res==""){
					alert("Debe seleccionar al menos una factura");
					return
				}
				var ok=confirm('Asignar  ' + res.length+' producto(s)?');
				if(!ok)
					return;
				
				$.post(
					"${createLink(action:'registrarProductos')}",
					{proveedorId:${proveedor.id},partidas:JSON.stringify(res)}
				).done(function(data){
					console.log('Rres: '+data.documento);
					window.location.href='${createLink(action:'edit',id:proveedor.id )}';
				}).fail(function( jqXHR, textStatus, errorThrown){
					console.log(errorThrown);
					alert("Error asignando facturas: "+errorThrown);
				});
				
				// $.ajax({
				// 	url:"${createLink(action:'registrarProductos')}",
				// 	dataType:"json",
				// 	data:{
				// 		proveedorId:${proveedor.id},partidas:JSON.stringify(res)
				// 	},
				// 	success:function(data,textStatus,jqXHR){
				// 		console.log('Rres: '+data.documento);
				// 		window.location.href='${createLink(action:'edit',params:[id:proveedor.id])}';

				// 	},
				// 	error:function(request,status,error){
				// 		console.log(error);
				// 		alert("Error asignando facturas: "+error);
				// 	},
				// 	complete:function(){
				// 		console.log('OK ');
				// 	}
				// });
			});
		});
	</script>
</content>






	
	
</body>
</html>
