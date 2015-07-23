<r:require module="luxorTableUtils"/>
<div class="btn-toolbar">
	<div class="btn-group ">
		<g:if test="${CXCAbonoInstance.cfd==null}">
			<g:if test="${CXCAbonoInstance.tipo!='BONIFICACION' && !CXCAbonoInstance.cfd}">
				<g:link action="selectorDeFacturas" 
					params="['pagoId':CXCAbonoInstance.id,'disponible':CXCAbonoInstance.disponible]"
					class="btn btn-primary" >
					<i class="icon-ok icon-white"></i> Agregar
				</g:link>
  				<button id="eliminarBtn" class="btn btn-danger">
  					<i class="icon-trash icon-white"></i>Eliminar
  				</button>
			</g:if>
		</g:if>
		
	</div>
</div>
<table id="grid"
	class="simpleGrid table table-striped table-hover table-bordered table-condensed">
	<thead>
		<tr>
			<th class="header">Cantidad</th>
			<th class="header">Unidad</th>
			<th class="header">Clave</th>
			<th class="header">Descripción</th>
			<th class="header">Venta</th>
			<th class="header">Valor U</th>
			<th class="header">Importe</th>
			
		</tr>
	</thead>
	<tbody>
		<g:each in="${conceptos}" var="row">
			<tr id="${fieldValue(bean:row, field:"id")}">
			    
				<td>${fieldValue(bean: row, field: "cantidad")}</td>
				<td>${fieldValue(bean: row, field: "unidad")}</td>
				<td>${fieldValue(bean: row, field: "numeroDeIdentificacion")}</td>
				<td>${fieldValue(bean: row, field: "descripcion")}</td>
				<td><g:link  action="showCargo" id="${row.venta?.id}">
					${fieldValue(bean: row, field: "venta.id")}</g:link>
				</td>
				<td><lx:moneyFormat number="${row.valorUnitario }" /></td>
				<td><lx:moneyFormat number="${row.importe }" /></td>
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
			<td><label class="pull-right" >Importe: </label></td>
			<td><lx:moneyFormat number="${CXCAbonoInstance.importe}" />
			</td>
			
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td><label class="pull-right" >Impuesto: </label></td>
			<td><lx:moneyFormat number="${CXCAbonoInstance.impuesto}" />
			</td>
			
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td><label class="pull-right" >Total: </label></td>
			<td><lx:moneyFormat number="${CXCAbonoInstance.total}" />
			</td>
			
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
