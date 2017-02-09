<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<title>Facturas pendientes de pago</title>
</head>
<body>


<content tag="header">
	Facturas pendientes (${abonoInstance.proveedor.nombre})  
</content>

<content tag="subHeader">
	<g:link action='edit' id="${abonoInstance.id}" 	>
		 Nota: ${abonoInstance.id}  Disponible:<lx:moneyFormat number="${abonoInstance.disponible }"/>
	</g:link>
</content>

<content tag="document">
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<div class="btn-group">
						%{-- <lxBackButton action="edit" id="${abonoInstance.id}" label="Regresar a nota ${abonoInstance.id}"/>
						<a id="asignar" href="#" class="btn btn-success">Agregar</a> --}%
						<lx:backButton label="Nota ${abonoInstance.id}" action="edit" id="${abonoInstance.id}"/>
					    <a id="asignar" class="btn btn-outline btn-success">Aplicar</a>
					</div>
				</div>
			    <div class="ibox-content">
					<table id="grid" class="grid table table-hover table-bordered table-condensed">
						<thead>
							<tr>
								<g:sortableColumn property="id"    title="Id"/>
								<g:sortableColumn property="documento" title="Documento"/>
								<th class="header">Fecha</th>
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
			// Grid y seleccion
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
				var ok=confirm('Generar aplicacion a  ' + res.length+' factura(s) para la nota:'+${abonoInstance.id}+'?');
				if(!ok)
					return;
				$.post(
					"${createLink(controller:'notaDeCredito',action:'registrarAplicaciones')}",
					{abonoId:${abonoInstance.id},partidas:JSON.stringify(res)})
				.done(function(data){
					console.log('Rres: '+data.documento);
					window.location.href='${createLink(controller:'notaDeCredito',action:'edit',params:[id:abonoInstance.id])}';
				}).fail(function(jqXHR, textStatus, errorThrown){
					alert("Error asignando facturas: "+errorThrown);
				});
			});


		});
	</script>
</content>

</body>
</html>
