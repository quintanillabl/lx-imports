
<div class="btn-group ">
		<g:link action="selectorDeFacturas" 
				id="${CXCAbonoInstance.id}"
				class="btn btn-primary" >
			<i class="icon-ok icon-white"></i> Agregar
		</g:link>
  		<button id="eliminarAplicacionesBtn" class="btn btn-danger">
  			<i class="icon-trash icon-white"></i>Eliminar
  		</button>
	</div>
<table id="aplicacionesGrid"
	class="table table-hover table-bordered table-condensed">
	<thead>
		<tr>
			<th class="header">Id</th>
			<th class="header">Fecha</th>
			<th class="header">Documento</th>
			<th class="header">Fecha(Docto)</th>	
			<th class="header">Importe (Docto)</th>
			<th class="header">Saldo (Docto)</th>
			<th class="header">Aplicado</th>
			
		</tr>
	</thead>
	<tbody>
		<g:each in="${aplicaciones}" var="row">
			<tr id="${row.id}">
			    
				<td>${fieldValue(bean: row, field: "id")}</td>
				<td><lx:shortDate date="${row.fecha}" /></td>
				<td>${fieldValue(bean: row, field: "factura.facturaFolio")}</td>
				<td>${fieldValue(bean: row, field: "factura.fechaFactura")}</td>
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
<script>

$(function(){
	
	// Grid y seleccion
	$('#aplicacionesGrid').dataTable( {
    	"paging":   false,
    	"ordering": false,
    	"info":     false,
    	"language": {
			"url": "${assetPath(src: 'datatables/dataTables.spanish.txt')}"
		},
		//"dom": '',
		"order": []
	} );
	
	$("#aplicacionesGrid tbody tr").on('click',function(){
		$(this).toggleClass("info selected");
	});
	
	$("#eliminarAplicacionesBtn").click(function(e){
		var res=[];
		var data=$("#aplicacionesGrid tbody tr.selected").each(function(){
			var tr=$(this);
			res.push(tr.attr("id"));
		});

		if(res.length==0){
			alert('Debe seleccionar al menos un registro');
			return;
		}
		var ok=confirm('Eliminar '+res.length+' aplicaciones ?');
		if(!ok)
			return;
		console.log('Eliminando aplicaciones: '+res);
		$.post(
			"${createLink(action:'eliminarAplicaciones')}",
			{id:${CXCAbonoInstance.id},partidas:JSON.stringify(res)}
		).done(function(data){
			console.log('OK :'+data);
			window.location.reload(true);
		}).fail(function(jqXHR, textStatus, errorThrown){
			console.log(errorThrown);
			alert("Error: "+errorThrown);
		});
		
	});
	
	
	
});

</script>
