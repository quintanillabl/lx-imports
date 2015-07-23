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
				<g:link action='edit' id="${abonoInstance.id}" 	>
				 Abono: ${abonoInstance.id} Proveedor:${abonoInstance.proveedor.nombre}  
				 Disponible: ${abonoInstance.disponible }
				</g:link>
			</div>
		</div>
		
		<div class="row">
			<div class="span12">
				<a id="asignar" href="#" class="btn">Agregar</a>
				<table id="grid" class="grid table table-striped table-hover table-bordered table-condensed">
					<thead>
						<tr>
							<g:sortableColumn property="id"    title="Id"/>
							<g:sortableColumn property="documento" title="Documento"/>
							<th class="header">Fecha</th>
							<th class="header">Proveedor</th>
							<th class="header">Vencimiento</th>
							<th class="header">Moneda</th>
							<th class="header">Total</th>
							<th class="header">Pagos</th>
							<th class="header">Saldo</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${cuentaPorPagarInstanceList}" var="row">
						<tr id="${row.id}">	
							<td>${row.id}</td>
							<td>${fieldValue(bean: row, field: "documento")}</td>
							<td><lx:shortDate date="${row.fecha}"/></td>
							<td>${fieldValue(bean: row, field: "proveedor")}</td>
							<td><lx:shortDate date="${row.vencimiento }"/></td>
							<td>${fieldValue(bean: row, field: "moneda")}</td>
							<td><lx:moneyFormat number="${row.total }"/></td>
							<td><lx:moneyFormat number="${row.pagosAplicados }"/></td>
							<td><lx:moneyFormat number="${row.saldoActual }"/></td>
						</tr>
						</g:each> 
					</tbody>
				</table>
				
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
					url:"${createLink(controller:'pago',action:'registrarAplicaciones')}",
					dataType:"json",
					data:{
						abonoId:${abonoInstance.id},partidas:JSON.stringify(res)
					},
					success:function(data,textStatus,jqXHR){
						console.log('Resultado ajax: '+data.res);
						if(data.error!=null){
							alert(data.error)
						}else{						
							window.location.href='${createLink(controller:'pago',action:'edit',params:[id:abonoInstance.id])}';
						}

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
