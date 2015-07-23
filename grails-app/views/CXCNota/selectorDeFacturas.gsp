<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<title>Facturas </title>
<r:require module="luxorSimpleTable"/>
</head>
<body>
	<div class="container">
	
		<div class="row">
			<div class="well alert">
			<strong>Facturas para concepto de descuento </strong>
				<g:link action='edit' id="${pago.id}" 	>
				 Nota: ${pago.id} Cliente:${pago.cliente.nombre}  
				 <p>Disponible:${pago.disponibleMN}
				</g:link>
			</div>
		</div>
		
		<div class="row">
			<div class="span12">
				<a id="asignar" href="#" class="btn">Agregar</a>
				Facturas: ${facturas.size() }
				<table id="grid" class="grid table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<g:sortableColumn property="id"    title="Id"/>
							<th class="Documento">Documento2</th>
							<th class="header">Fecha</th>
							<th class="header">Vencimiento</th>
							<th class="header">Moneda</th>
							<th class="header">Total</th>
							<th class="header">Saldo</th>
							
						</tr>
					</thead>
					<tbody>
						<g:each in="${facturas}" var="row">
						<tr id="${row.id}">	
							<td>${row.id}</td>
							<td>${fieldValue(bean: row, field: "factura")}</td>
							<td>${fieldValue(bean: row, field: "cfd.fecha")}</td>
							<td><lx:shortDate date="${row.vencimiento }"/></td>
							<td>${fieldValue(bean: row, field: "moneda")}</td>
							<td><lx:moneyFormat number="${row.total }"/></td>
							<td><lx:moneyFormat number="${row.saldoActual }"/></td>
						</tr>
						</g:each> 
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${facturasTotal}" max="20" id="${pago.id}"  params="[pagoId:pago.id]"/>
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
					url:"${createLink(action:'generarAplicaciones')}",
					dataType:"json",
					data:{
						abonoId:${pago.id},partidas:JSON.stringify(res)
					},
					success:function(data,textStatus,jqXHR){
						console.log('Rres: '+data.documento);
						window.location.href='${createLink(action:'edit',params:[id:pago.id])}';

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
