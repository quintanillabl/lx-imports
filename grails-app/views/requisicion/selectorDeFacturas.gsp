<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<title>Facturas pendientes de pago</title>
	<r:require module="luxorSimpleTable"/>
</head>
<body>
	<div class="container">
	
		<div class="row">
			<div class="well alert">
			<strong>Facturas pendientes de pago</strong>
				<g:link action='edit' id="${requisicionInstance.id}" 	>
				 Requisición: ${requisicionInstance.id} Proveedor:${requisicionInstance.proveedor.nombre}  
				</g:link>
			
			</div>
		</div>
		
		<div class="row">
			<div class="span12">
				<a id="asignar" href="#" class="btn">Agregar</a>
				<table id="grid" class="grid table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<th class="header">Folio</th>
							<th class="header">Documento</th>
							<th class="header">Fecha</th>
							<th class="header">Vencimiento</th>
							<th class="header">Moneda</th>
							<th class="header">Total</th>
							<th class="header">Saldo</th>
							<th class="header">Requisitado</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${cuentaPorPagarInstanceList}" var="row">
						<tr id="${row.id}">	
							<td>${row.id}</td>
							<td>${fieldValue(bean: row, field: "documento")}</td>
							<td><lx:shortDate date="${row.fecha}"/></td>
							<td><lx:shortDate date="${row.vencimiento }"/></td>
							<td>${fieldValue(bean: row, field: "moneda")}</td>
							<td><lx:moneyFormat number="${row.total }"/></td>
							<td><lx:moneyFormat number="${row.saldoActual }"/></td>
							<td><lx:moneyFormat number="${row.requisitado }"/></td>
						</tr>
						</g:each> 
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${cuentasPorPagarTotal}" id="${requisicionInstance.id}" max="100"/>
				</div>
			</div>
		</div>
	</div>
	<r:script>
		$(function(){
		
			$("#asignar").click(function(){
				var res=selectedRows();
				if(res==""){
					alert("Debe seleccionar al menos una factura");
					return
				}
				$.ajax({
					url:"${createLink(controller:'requisicion',action:'asignarFacturas')}",
					dataType:"json",
					data:{
						requisicionId:${requisicionInstance.id},partidas:JSON.stringify(res)
					},
					success:function(data,textStatus,jqXHR){
						console.log('Rres: '+data.documento);
						window.location.href='${createLink(controller:'requisicion',action:'edit',params:[id:requisicionInstance.id])}';

					},
					error:function(request,status,error){
						console.log(error);
						alert("Error asignando facturas: "+error);
					},
					complete:function(){
						console.log('OK ');
					}
				});
			});
			
		});
	</r:script>
	
</body>
</html>
