<%@ page import="com.luxsoft.impapx.DistribucionDet" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="luxor">
	<title>Lista de distribucion ${distribucionId}</title>
	%{-- <r:require module="luxorTableUtils" /> --}%
</head>
<body>
<content tag="header">
	Lista de distribución ${distribucionId} embarque:${embarqueId}
</content>
<content tag="subHeader">
	<lx:backButton id="${distribucionId}" action="edit" id="${distribucionId}"/>
</content>

<content tag="document">

	<div class="ibox float-e-margins">
		<lx:iboxTitle title="Partidas"/>
	    <div class="ibox-content">
			<div class="row">
				<div class="col-md-2">
					<g:select class="form-control chosen-select"  id="sucursal"
						name="sucursal"
						from="${['CALLE 4','TACUBA','BOLIVAR','ANDRADE','QUERETARO','TRANSITO','CF5FEBRERO','VERTIZ 176','ALESA','PROMASA NORTE','PROMASA SUR','GACELA','PROGRESO','INTERCARTON+','SOLIS']}" />
				</div>
				<div class="btn-group">
					<button id="asignar" class="btn btn-success" >
						<i class="icon-plus icon-white"></i> Asignar
					</button>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table id="grid"
						class="simpleGrid table  table-bordered table-condensed"
						cellpadding="0" cellspacing="0" border="0">
						<thead>
							<tr>
								<th>Contenedor</th>
								<th>Cantidad</th>
								<th>Tarimas</th>
								<th>Cant X Tarima</th>
								
							</tr>
						</thead>
						<tbody>
							<g:each in="${partidas}" var="row">
								<tr id="${fieldValue(bean:row, field:"key")}">
									<td>${fieldValue(bean:row, field:"key")}</td>
									<td>${row.value.sum({it.cantidad}) }</td>
									<td>${row.value.sum({it.tarimas}) }</td>
									<td>${row.value.sum({it.cantidadPorTarima()}) }</td>
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

			$("tbody tr").on('click',function(){
				$(this).toggleClass("success selected");
			});

			function selectedRows(){
				var res=[];
				var data=$(".simpleGrid  .selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			}

			$("#asignar").click(function(){
				var res=selectedRows();
				if(res==""){
					alert("Debe seleccionar al menos un registro");
					return;
				}
				var ok=confirm("Asignar "+res.length+" contenedores ?");
				if(!ok)
					return
				console.log('Asignando: '+res)
				var sucursal=$("#sucursal").val();
				$.ajax({
					url:"${g.createLink(controller:'distribucion',action:'asignarContenedor') }",
					data:{id:${distribucionId},partidas:JSON.stringify(res),sucursal:sucursal},
					success:function(response){
						window.location.href='${createLink(controller:'distribucion',action:'edit',params:[id:distribucionId])}';
					},
					error:function(){
						alert('Error asignando contenedor..')
					}
				});
				
			});
		})
	</script>
</content>
	<div class="container">
		


		<r:script>
			$(function(){
			
				$("#asignar").click(function(){
					var res=selectedRows();
					if(res==""){
						alert("Debe seleccionar al menos un registro");
						return;
					}
					var ok=confirm("Asignar "+res.length+" contenedores ?");
					if(!ok)
						return
					console.log('Asignando: '+res)
					var sucursal=$("#sucursal").val();
					$.ajax({
						url:"${g.createLink(controller:'distribucion',action:'asignarContenedor') }",
						data:{distribucionId:${distribucionId},partidas:JSON.stringify(res),sucursal:sucursal},
						success:function(response){
							window.location.href='${createLink(controller:'distribucion',action:'edit',params:[id:distribucionId])}';
						},
						error:function(){
							alert('Error asignando contenedor..')
						}
					});
					
				});
				
				function selectedRows(){
					var res=[];
					var data=$(".simpleGrid  .selected").each(function(){
						var tr=$(this);
						res.push(tr.attr("id"));
					});
					return res;
				}
				
			});
		</r:script>
	</div>
</body>
</html>


