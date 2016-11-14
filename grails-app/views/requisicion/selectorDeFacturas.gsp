<!doctype html>
<html>
<head>
	<title>Facturas pendientes de pago</title>
	<meta name="layout" content="luxor">
</head>
<body>

<content tag="header">
	Facturas pendientes de pago
</content>
	
<content tag="subHeader">
	<ol class="breadcrumb">
		<li class="active">
			<g:link action="edit" id="${requisicionInstance.id}">
				<strong>Requisición: ${requisicionInstance.id} Proveedor: ${requisicionInstance.proveedor.nombre}  </strong>
			</g:link>
		</li>
	</ol>
</content>

<content tag="document">
	<div class="row">
	    <div class="col-lg-12">
	        <div class="ibox float-e-margins">
	            <div class="ibox-title">
	                
	                <a id="asignar" class="btn btn-success btn-sm">
	                	<i class="fa fa-plus"></i> Agregar
	                </a>
	            </div>
	            <div class="ibox-content">
	            	<table id="grid" class="grid table table-responsive">
	            		<thead>
	            			<tr>
	            				<th>Folio</th>
	            				<th>Proveedor</th>
	            				<th>Documento</th>
	            				<th>Fecha</th>
	            				<th>Vencimiento</th>
	            				<th>Moneda</th>
	            				<th>Total</th>
	            				<th>Saldo</th>
	            				<th>Requisitado</th>
	            				<th>Ej-Sem</th>
	            				%{-- <th>Pendiente</th> --}%
	            			</tr>
	            		</thead>
	            		<tbody>
	            			<g:each in="${cuentaPorPagarInstanceList}" var="row">
	            			<tr id="${row.id}">	
	            				<td>${row.id}</td>
	            				<td>${fieldValue(bean: row, field: "proveedor.nombre")}</td>
	            				<td>${fieldValue(bean: row, field: "documento")}</td>
	            				<td><lx:shortDate date="${row.fecha}"/></td>
	            				<td><lx:shortDate date="${row.vencimiento }"/></td>
	            				<td>${fieldValue(bean: row, field: "moneda")}</td>
	            				<td><lx:moneyFormat number="${row.total }"/></td>
	            				<td><lx:moneyFormat number="${row.saldoActual }"/></td>
	            				<td><lx:moneyFormat number="${row.requisitado }"/></td>
	            				<td>${row.fecha[Calendar.YEAR]} - S${row.fecha[Calendar.WEEK_OF_YEAR]}</td>
	            				
	            				%{-- <td><lx:moneyFormat number="${row.pendienteRequisitar }"/></td> --}%
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
	</div>
	<script type="text/javascript">
		$(function(){
			
			$(".grid tbody tr").click(function(){
				$(this).toggleClass("success selected");
			});
 			$('#grid').dataTable({
                responsive: true,
                aLengthMenu: [[100,200, -1], [100,200, "Todos"]],
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
				var ok=confirm('Asignar  ' + res.length+' factura(s) a la requisición '+${requisicionInstance.id}+'?');
				if(!ok)
					return;
				$.post(
					"${createLink(controller:'requisicion',action:'asignarFacturas')}",
					{requisicionId:${requisicionInstance.id},partidas:JSON.stringify(res)})
				.done(function(data){
					console.log('Rres: '+data.documento);
					window.location.href='${createLink(controller:'requisicion',action:'edit',params:[id:requisicionInstance.id])}';
				});
			});


		});
	</script>
</content>
	
</body>
</html>
