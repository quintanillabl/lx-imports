<!doctype html>
<html>
<head>
	<title>Facturas pendientes de pago</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">Facturas pendientes de pago </content>
<content tag="subHeader">
	<g:link action='edit' id="${pago.id}" 	>
		 Nota: ${pago.id} Cliente:${pago.cliente.nombre} <br> Disponible:<lx:moneyFormat number="${pago.disponible}"/>
	</g:link>
</content>

<content tag="document">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					
					<div class="ibox-title">
						<lx:backButton label="Nota ${pago.id}" action="edit" id="${pago.id}"/>
					    <a id="asignar" class="btn btn-outline btn-success">Aplicar</a>
					</div>
					
				    <div class="ibox-content">
						<table class=" grid table  table-hover table-bordered table-condensed">
							<thead>
								<tr>
									<th>Folio</th>
									<th>Documento</th>
									<th>Fecha</th>
									<th>Vencimiento</th>
									<th>Moneda</th>
									<th>Total</th>
									<th>Aplicaciones</th>
									<th>Saldo</th>
									
								</tr>
							</thead>
							<tbody>
								<g:each in="${facturas}" var="row">
								<tr id="${row.id}">	
									<td>${row.id}</td>
									<td>${fieldValue(bean: row, field: "facturaFolio")}</td>	
									<td>${fieldValue(bean: row, field: "fechaFactura")}</td>
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

	
	</div>

	<script type="text/javascript">
		$(function(){
			
			$(".grid tbody tr").click(function(){
				$(this).toggleClass("success selected");
			});
 			$('#grid').dataTable({
                responsive: true,
                "language": {
					"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    		},
	    		"order": []
            });
			var selectRows=function(){
				var res=[];
				var data=$(".grid .selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			};
			$("#asignar").on('click',function(){
				var res=selectRows();
				if(res==""){
					alert("Debe seleccionar al menos una factura");
					return
				}
				var ok=confirm('Generar aplicacion a  ' + res.length+' factura(s) al la nota:'+${pago.id}+'?');
				if(!ok)
					return;
				$.post(
					"${createLink(action:'generarAplicaciones')}",
					{id:${pago.id},partidas:JSON.stringify(res)})
				.done(function(data){
					console.log('Rres: '+data.documento);
					window.location.href='${createLink(action:'edit',params:[id:pago.id])}';
				}).fail(function(jqXHR, textStatus, errorThrown){
					alert("Error asignando facturas: "+errorThrown);
				});
			});
		});

		
	</script>
	
</content>
	

</body>
</html>


