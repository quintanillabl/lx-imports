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
		<lx:iboxTitle title="Asignación de fecha"/>
	    <div class="ibox-content">
	    	<div class="btn-group">
    			<button id="asignar" class="btn btn-success" >
					<i class="icon-plus icon-white"></i> Asignar
				</button>
    		</div>
    		<div class="btn-group">
    			<f:input bean="${new DistribucionDet() }" property="programacionDeEntrega" id="programacionDeEntrega"/>
    		</div>
			<table id="grid"
				class="grid table table-striped table-bordered table-condensed"
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
	<script type="text/javascript">
		$(function(){
			function selectedRows(){
				var res=[];
				var data=$(".grid  .selected").each(function(){
					var tr=$(this);
					res.push(tr.attr("id"));
				});
				return res;
			};
			$('.date').bootstrapDP({
			    format: 'dd/mm/yyyy',
			    keyboardNavigation: false,
			    forceParse: false,
			    autoclose: true,
			    todayHighlight: true
			});
			$("tbody tr").on('click',function(){
				$(this).toggleClass("success selected");
			});
			$("#asignar").click(function(){
				var res=selectedRows();
				if(res==""){
					alert("Debe seleccionar al menos un registro");
					return;
				}
				var fecha=$("#programacionDeEntrega").val();
				if(!fecha){
					alert("Debe seleccionar la fecha de asignación");
					return;
				}

				var ok=confirm("Asignar la fecha: "+fecha+" a "+res.length+" registro");
				if(!ok)
					return
				console.log('Asignando: '+res)
				
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
			
			
			
		});
	</script>
</content>
	
</body>
</html>


