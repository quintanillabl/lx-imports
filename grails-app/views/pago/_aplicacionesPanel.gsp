<r:require module="luxorTableUtils"/>
<div class="btn-toolbar">
	<div class="btn-group ">
		<g:if test="${abonoInstance.disponible>0}">
			<g:link action="selectorDeFacturas" 
				params="['disponible':abonoInstance.disponible]"
				class="btn btn-primary" 
				id="${abonoInstance.id }">
			<i class="icon-ok icon-white"></i> Agregar
		</g:link>
		</g:if>
		
  		<button id="eliminarBtn" class="btn btn-danger">
  			<i class="icon-trash icon-white"></i>Eliminar
  		</button>
	</div>
</div>
<table id="grid"
	class="simpleGrid table table-striped table-hover table-bordered table-condensed">
	<thead>
		<tr>
			<th class="header">Id</th>
			<th class="header">Fecha</th>
			<th class="header">Documento</th>
			<th class="header">Dcto Fecha</th>
			<th class="header">Dcto Vto</th>
			<th class="header">Aplicado</th>
			
		</tr>
	</thead>
	<tbody>
		<g:each in="${aplicaciones}" var="row">
			<tr id="${fieldValue(bean:row, field:"id")}">
			    
				<td><g:link controller="aplicacion" action="edit" id="${row.id}">
					${fieldValue(bean: row, field: "id")}</g:link>
				</td>
				<td><lx:shortDate date="${row.factura.fecha}" /></td>
				<td><lx:shortDate date="${row.factura.vencimiento}" /></td>
				<td><lx:shortDate date="${row.fecha}" /></td>
				<td>${fieldValue(bean: row, field: "factura.documento")}</td>
				<td><lx:moneyFormat number="${row.total }" /></td>
				
			</tr>
		</g:each>
	</tbody>
	<tfoot>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td><label class="pull-right" >Total: </label></td>
			<td><lx:moneyFormat number="${abonoInstance.aplicado }" /></td>
		</tr>
	</tfoot>
</table>
<r:script>

$(function(){
	
	$("#eliminarBtn").click(function(e){
		eliminar();
	});
	
	function eliminar(){
		var res=selectedRows();
		if(res.length==0){
			alert('Debe seleccionar al menos un registro');
			return;
		}
		var ok=confirm('Eliminar  ' + res.length+' partida(s)?');
		if(!ok)
			return;
		console.log('Cancelando facturas: '+res);
		
		$.ajax({
			url:"${createLink(action:'eliminarAplicaciones')}",
			data:{
				abonoId:${abonoInstance.id},partidas:JSON.stringify(res)
			},
			success:function(response){
				
				location.reload();
			},
			error:function(request,status,error){
				alert("Error: "+status);
			}
		});
	}
	
});
function selectedRows(){
	var res=[];
	var data=$("tbody tr.selected").each(function(){
		var tr=$(this);
		res.push(tr.attr("id"));
	});
	return res;
}
</r:script>
