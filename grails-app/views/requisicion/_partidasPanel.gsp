<r:require module="luxorTableUtils"/>
<div class="btn-toolbar">

	

	<div class="btn-group ">
	
		<g:link action="create" controller="requisicionDet"
				params="['requisicionId':requisicionInstance.id]"
				class="btn btn-primary" >
			<i class="icon-ok icon-white"></i> Agregar concepto
		</g:link>
		
		<g:link action="selectorDeFacturas" 
				params="['requisicionId':requisicionInstance?.id]"
				class="btn btn-primary" >
			<i class="icon-ok icon-white"></i> Agregar Factura
		</g:link>
		
		<%-- 
		<g:if test="  ${requisicionInstance.concepto.startsWith('ANTICIPO') || requisicionInstance.concepto.startsWith('FLETE')} ">
			<g:link action="create" controller="requisicionDet"
				params="['requisicionId':requisicionInstance.id]"
				class="btn btn-primary" >
			<i class="icon-ok icon-white"></i> Agregar
			</g:link>
		</g:if>
		
		<g:else>
			<g:link action="selectorDeFacturas" 
				params="['requisicionId':requisicionInstance?.id]"
				class="btn btn-primary" >
			<i class="icon-ok icon-white"></i> Agregar
			</g:link>
		</g:else>
		--%>
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
			<th class="header">Documento</th>
			<th class="header">Fecha</th>
			<th class="header">A Pagar</th>
			<th class="header">Embarque</th>
			
		</tr>
	</thead>
	<tbody>
		<g:each in="${partidasList}" var="row">
			<tr id="${row.id}">
				<td>
					<g:link controller="requisicionDet" action="edit" id="${row.id}">${row.id}</g:link>	
		        </td>
			    <td>${fieldValue(bean: row, field: "documento")}</td>
				<td><lx:shortDate date="${row.fechaDocumento}" /></td>
				<td><lx:moneyFormat number="${row.total }" /></td>
				<td>${fieldValue(bean: row, field: "embarque.id")}</td>
			</tr>
		</g:each>
	</tbody>
	<tfoot>
		<tr>
			<td></td>
			<td></td>
			<td><label class="pull-right" >Total: </label></td>
			<td><lx:moneyFormat number="${requisicionInstance.total }" /></td>
			<%-- <td><lx:moneyFormat number="${requisicionInstance.sumar('importe') }" /></td>--%>
			<td></td>
		</tr>
	</tfoot>
</table>
<g:jasperReport jasper="Requisicion" format="PDF,HTML" name="Imprimir">
	<g:hiddenField name="ID" value="${requisicionInstance.id}"/>
	<g:hiddenField name="MONEDA" value="${requisicionInstance.moneda}"/>
</g:jasperReport>
<r:script>

$(function(){
	
	$("#eliminarBtn").click(function(e){
		eliminar();
	});
	
	
	$("#editarBtn").click(function(e){
		
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
			url:"${createLink(controller:'requisicion',action:'eliminarPartidas')}",
			data:{
				requisicionId:${requisicionInstance.id},partidas:JSON.stringify(res)
			},
			success:function(response){
				//$("#facturasGrid").html(response);
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
