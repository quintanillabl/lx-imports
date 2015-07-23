<r:require module="luxorTableUtils"/>
<div class="btn-toolbar">

	<div class="btn-group ">
		<button  id="#agregarBtn" class="btn" data-target="#agregarDialog" data-toggle="modal">
  			Agregar
  			</button>
  		<button id="eliminarBtn" class="btn btn-danger">
  			<i class="icon-trash icon-white"></i>Eliminar
  		</button>
	</div>
</div>

<table id="grid"
	class="simpleGrid table table-striped table-hover table-bordered table-condensed">
	<thead>
		<tr>
			<th>Clave</th>
			<th>Descripción</th>
			<th>Tipo</th>
			<th>Sub Tipo</th>
			<th>Naturaleza</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${cuenta.subCuentas}" var="row">
			<tr id="${fieldValue(bean:row, field:"id")}">
				
				<td><g:link action="edit" id="${row.id }">
					${fieldValue(bean: row, field: "clave")}</g:link>
				</td>
				
				<td>${fieldValue(bean: row, field: "descripcion")}</td>
				<td>${fieldValue(bean: row, field: "tipo")}</td>
				<td>${fieldValue(bean: row, field: "subTipo")}</td>
				<td>${fieldValue(bean: row, field: "naturaleza")}</td>
			</tr>
		</g:each>
	</tbody>
	
</table>

<div class="modal hide fade" id="agregarDialog" tabindex=-1 role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4 class="myModalLabel">Agregar sub cuenta</h4>
	</div>
	<div class="modal-body">
		<g:render template="crearSubCuentaForm"/>
	</div>
	<%-- 
	<div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal">Cancelar</a>
    	
	</div>
	--%>
</div>

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
		console.log('Eliminando sub cuentas: '+res);
		
		$.ajax({
			url:"${createLink(action:'eliminarSubCuentas')}",
			data:{
				cuentaId:${cuenta?.id},partidas:JSON.stringify(res)
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
