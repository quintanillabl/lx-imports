<%@ page import="com.luxsoft.impapx.DistribucionDet" %>
<!doctype html>
<html>
<head>
<meta name="layout" content="luxor">
<g:set var="entityName"
	value="${message(code: 'pedimento.label', default: 'Distribución')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
<r:require module="luxorTableUtils" />
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="alert">
					<h4>
						Lista de distribución: <g:link action='edit' id="${ distribucionId}">${distribucionId} <br></g:link>
						Embarque:${embarqueId}
					</h4>
				</div>

				<g:if test="${flash.message}">
					<bootstrap:alert class="alert-info">
						${flash.message}
					</bootstrap:alert>
				</g:if>
			</div>
		</div>


		<div class="row">
			<div class="span12">
				<div class="btn-group">
					<button id="asignar" class="btn btn-success" >
						<i class="icon-plus icon-white"></i>
						Asignar
					</button>
					<f:input bean="${new DistribucionDet() }" property="programacionDeEntrega" id="programacionDeEntrega"/>
				</div>
				
				<table id="grid"
					class="simpleGrid table table-striped table-bordered table-condensed"
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
								<td>
									${fieldValue(bean:row, field:"key")}
								</td>
								<td>
									${row.value.sum({it.cantidad}) }
								</td>
								<td>
									${row.value.sum({it.tarimas}) }
								</td>
								<td>
									${row.value.sum({it.cantidadPorTarima()}) }
								</td>
								
							</tr>

						</g:each>
					</tbody>
				</table>
			</div>
		</div>
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
					var fecha=$("#programacionDeEntrega").val();
					$.ajax({
						url:"${g.createLink(controller:'distribucion',action:'asignarFechaEntrega') }",
						dataType:'json',
						data:{distribucionId:${distribucionId},partidas:JSON.stringify(res),fechaEntrega:fecha},
						success:function(response){
							if(response.res=='OK'){
								window.location.href='${createLink(controller:'distribucion',action:'edit',params:[id:distribucionId])}';
							}else{
								alert('Eror asignando fecha: '+response.error)
							}
							
						},
						error:function(){
							alert('Error asignando fecha de entrega..')
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


