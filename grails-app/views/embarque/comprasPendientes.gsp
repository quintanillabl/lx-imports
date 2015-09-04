<%@ page import="com.luxsoft.impapx.CompraDet" %>
<!doctype html>
<html>
	<head>
		<title>Selector de compras a detalle</title>
	</head>
<body>
	<div class="container">
		
		<div class="row row-header">
			<div class="col-md-12">
				<div class="well well-sm">
					<g:link action='edit' id="${embarque.id}" class="commit">
						<h3>Compras disponibles embarque: ${embarque.id} <small>${embarque}</small></h3>
					</g:link>
					
				</div>
			</div>
		</div>

		<div class="row toolbar-panel">
		    
		    
    		<div class="col-md-3">
    			<div class="btn-group">
    				<g:link action="edit" id="${embarque.id}" class="btn btn-default">
    				    <i class="fa fa-step-backward"></i> Embarque: ${embarque.id}
    				</g:link> 
    				<a id="asignar" href="#" class="btn btn-primary">Asignar</a>
    			</div>
    		</div>	
    		<div class="col-md-3">
    			<input type='text' id="filtro" placeholder="Filtrar" class="form-control" autofocus="on">
    		</div>
		    
		</div>

		<div class="row">
			
			<div class="col-md-12">
				
				<table id="grid" class="grid table table-striped table-hover table-bordered table-condensed ">
					<thead>
						<tr>
							<g:sortableColumn property="id"    title="Id"/>
							<g:sortableColumn property="compra.folio" title="Compra"/>
							<th class="header">Fecha</th>
							<g:sortableColumn property="compra.proveedor.nombre" title="Proveedor"/>
							<g:sortableColumn property="producto.clave" title="Producto"/>
							<g:sortableColumn property="producto.descripcion" title="Descripción"/>
							<th class="header">Solicitado</th>
							<th class="header">Entregado</th>
							<th class="header">Pendiente</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${compraDetInstanceList}" var="row">
						<tr id="${row.id}">	
							<td>${row.id}</td>
							<td>${fieldValue(bean: row, field: "compra.folio")}</td>
							<td><lx:shortDate date="${row.compra.fecha}"/></td>
							<td>${fieldValue(bean: row, field: "compra.proveedor.nombre")}</td>
							<td>${fieldValue(bean: row, field: "producto.clave")}</td>
							<td>${fieldValue(bean: row, field: "producto.descripcion")}</td>
							<td>${fieldValue(bean: row, field: "solicitado")}</td>
							<td>${fieldValue(bean: row, field: "entregado")}</td>
							<td>${fieldValue(bean: row, field: "pendiente")}</td>
						</tr>
						</g:each> 
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${compraDetInstanceTotal}" id="${embarque.id}" max="100"/>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(function(){

			// $('#grid').dataTable({
			//     responsive: true,
			//     aLengthMenu: [[20, 40, 60, 100, -1], [20, 40,60, 100, "Todos"]],
			//     "language": {
			//         "url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
			//     },
			//     "dom": 'T<"clear">lfrtip',
			//     "tableTools": {
			//         "sSwfPath": "${assetPath(src: 'plugins/dataTables/swf/copy_csv_xls_pdf.swf')}"
			//     },
			//     "order": []
			// });

			var table=$('#grid').dataTable( {
			    	"paging":   true,
			    	"ordering": true,
			    	"info":     true,
			    	"autoWidth": false, //EVALUANDO DESDE 19-jul-2015
	    	    	"language": {
	    				"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
	    	    	},
	    	    	aLengthMenu: [[20, 40, 60, 100, -1], [20, 40, 60, 100, "Todos"]],
	    			iDisplayLength: 20,
			    	"order": []
				} );

				//new $.fn.dataTable.FixedHeader( table );
				
				$("#filtro").on('keyup',function(e){
					var term=$(this).val();
					$(table).DataTable().search(
						$(this).val()
					        
					).draw();
				});
					


			$(".grid tbody tr").hover(function(){
				$(this).toggleClass("info");
			});
			$(".grid tbody tr").click(function(){
				$(this).toggleClass("success selected");
			});
			
			$(document).on("keydown",function(event){
				var keycode = (event.keyCode ? event.keyCode : event.which);
				if(event.ctrlKey ){
					if(keycode==65){
						//console.log('detecting under ctrl');
						$(".grid tbody tr").addClass("success selected");
					}else if(keycode==67){
						$(".grid tbody tr").removeClass("success selected");
					}
				}
			})
			
			function selectAllRows(){
				$(".grid tbody tr").addClass("success selected");
			}
			function clearAllRows(){
				$(".grid tbody tr").removeClass("success selected");
			}
			
			function selectedRows(){
				var res=[];
				var data=$(".grid .selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			}
		
			$("#asignar").click(function(){
				var res=selectedRows();
				if(res==""){
					alert("Debe seleccionar al menos una compra unitaria");
					return
				}
				//var ok=confirm("Asignar partidas seleccionadas? "+res.length);
				//if(!ok)
					//return;
				$.ajax({
					url:"${createLink(controller:'embarque',action:'asignarComprasUnitarias')}",
					dataType:"json",
					data:{
						embarqueId:${embarque.id},partidas:JSON.stringify(res)
					},
					success:function(data,textStatus,jqXHR){
						console.log('Rres: '+data.documento);
						//alert('Compras unitarias asignadas exitosamente');
						window.location.href="${createLink(controller:'embarque',action:'edit',params:[id:embarque.id])}";


					},
					error:function(request,status,error){
						console.log(error);
						alert("Error asignando compras: "+error);
					},
					complete:function(){
						console.log('OK ');
					}
				});
			});

			
		});
	</script>
	
</body>
</html>
