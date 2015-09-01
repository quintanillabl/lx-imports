<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<title>Facturas pendientes de pago</title>
</head>
<body>

<content tag="header">
	Facturas pendientes proveedor:${abonoInstance.proveedor.nombre}  
				
</content>
<content tag="subHeader">
	<ol class="breadcrumb">
		<li><g:link action="edit" id="${abonoInstance.id}"><strong>Abono ${abonoInstance.id}</strong></g:link></li>
		<li><strong>Disponible:  ${g.formatNumber(number:abonoInstance.disponible,type:'currency') }</strong></li>
	</ol>
</content>

<content tag="document">
	<div class="row">
		<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<a id="asignar" href="#" class="btn btn-success btn-outline">Agregar</a>
				</div>
			    <div class="ibox-content">
					<table id="grid" class="grid table table-hover table-bordered table-condensed">
						<thead>
							<tr>
								<th>Id</th>
								<th>Documento</th>
								<th>Fecha</th>
								<th>Proveedor</th>
								<th>Vencimiento</th>
								<th>Moneda</th>
								<th>Total</th>
								<th>Pagos</th>
								<th>Saldo</th>
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
	</div>
	
	
	<script type="text/javascript">
		$(function(){

			$('#grid').dataTable( {
	        	"paging":   false,
	        	"ordering": false,
	        	"info":     false,
	        	"language": {
					"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    		},
	    		//"dom": '',
	    		"order": []
	    	} );
			
			$("tbody tr").on('click',function(){
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
	</script>
</content>
	
</body>
</html>
