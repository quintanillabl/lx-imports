<r:require module="luxorTableUtils"/>
<div class="btn-toolbar">
	<div class="btn-group ">
		<g:link action="selectorDeFacturas" 
				params="['pagoId':CXCAbonoInstance.id,'disponible':CXCAbonoInstance.disponibleMN]"
				class="btn btn-primary" >
			<i class="icon-ok icon-white"></i> Agregar
		</g:link>
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
			<th class="header">Fecha(Docto)</th>	
			<th class="header">Importe (Docto)</th>
			<th class="header">Saldo (Docto)</th>
			<th class="header">Pagado</th>
			
		</tr>
	</thead>
	<tbody>
		<g:each in="${aplicaciones}" var="row">
			<tr id="${row.id}">
			    
				<td><g:link controller="CXCAplicacion" action="edit" id="${row.id}">
					${fieldValue(bean: row, field: "id")}</g:link>
				</td>
				<td><lx:shortDate date="${row.fecha}" /></td>
				<td>${fieldValue(bean: row, field: "factura.cfd.folio")}</td>
				<td>${fieldValue(bean: row, field: "factura.cfd.fecha")}</td>
				<td><lx:moneyFormat number="${row.factura.total }" /></td>	
				<td><lx:moneyFormat number="${row.factura.saldoActual }" /></td>
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
			<td></td>
			<td><label class="pull-right" >Total: </label></td>
			<td><lx:moneyFormat number="${CXCAbonoInstance.aplicado }" /></td>
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
				pagoId:${CXCAbonoInstance.id},partidas:JSON.stringify(res)
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
